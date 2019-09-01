(ns packt-clj.tennis.elo
  (:require
    [packt-clj.tennis.query :as query]
    [clojure.java.jdbc :as jdbc]))

(def k-factor 32)

(defn match-probability [player-1-rating player-2-rating]
  (/ 1
     (+ 1 (Math/pow 10 (/ (- player-2-rating player-1-rating) 1000)))))

(defn recalculate-rating [previous-rating expected-outcome real-outcome]
  (+ previous-rating (* k-factor (- real-outcome expected-outcome))))

(defn expected-outcomes
  [winner-rating loser-rating]
  (let [winner-expected-outcome (match-probability winner-rating loser-rating)]
    [winner-expected-outcome (- 1 winner-expected-outcome)]))

(defn calculate-all
  [db]
  (->> (query/all-tennis-matches db)
       (reduce
         (fn [{:keys [current-player-rating] :as acc} match]
           (let [{:keys [winner_id loser_id]} match
                 winner-rating (get current-player-rating winner_id 1000)
                 loser-rating (get current-player-rating loser_id 1000)
                 [winner-expected-outcome loser-expected-outcome] (expected-outcomes winner-rating loser-rating)
                 new-winner-rating (recalculate-rating winner-rating winner-expected-outcome 1)
                 new-loser-rating (recalculate-rating loser-rating loser-expected-outcome 0)]
             (-> acc
                 (update :elo-ratings into [{:player_id winner_id
                                             :rating    new-winner-rating}
                                            {:player_id loser_id
                                             :rating    new-loser-rating}])
                 (assoc-in [:current-player-rating winner_id] new-winner-rating)
                 (assoc-in [:current-player-rating loser_id] new-loser-rating))))
         {:elo-ratings           []
          :current-player-rating {}})
       :elo-ratings))

(defn persist-all
  [db]
  (let [elo-ratings (calculate-all db)]
    (jdbc/insert-multi! db :elo elo-ratings)))
