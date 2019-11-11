(ns packt-clj.tennis-reduce
  (:require 
   [clojure.java.io :as io]
   [clojure.data.csv :as csv]
   [semantic-csv.core :as sc]))


;;; 7. group by tournament
(defn tennis-csv->tournament-match-counts [csv]
  (with-open [r (io/reader csv)]
    (->> (csv/read-csv r)
         sc/mappify
         (map #(select-keys % [:tourney_slug :winner_name :loser_name]))
         (group-by :tourney_slug))))

;;; 12. matches per tournament
(defn tennis-csv->tournament-match-counts [csv]
  (with-open [r (io/reader csv)]
    (->> (csv/read-csv r)
         sc/mappify
         (group-by :tourney_slug)
         (map (fn [[k ms]] [k (count ms)]))
         (into {}))))
