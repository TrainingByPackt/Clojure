(ns packt-clj.inflection-points)

;;; In REPL: 1. Define some sample data
(def sample-data
  [[24.2 420031]
   [25.8 492657]
   [25.9 589014]                        ;max
   [23.8 691995]                        ;min
   [24.7 734902]                        ;max
   [23.2 794243]
   [23.1 836204]                        ;min
   [23.5 884120]])

;;; In REPL: 2. local-max? and local-min?
(defn local-max? [[a b c]]
  (and (< (first a) (first b)) (< (first c) (first b))))

(defn local-min? [[a b c]]
  (and (> (first a) (first b)) (> (first c) (first b))))

;;; In REPL: local-max? and local-min? with "clever" destructuring
(defn local-max? [[[a _] [b _] [c _]]]
  (and (< a b) (< c b)))

(defn local-min? [[[a _] [b _] [c _]]]
  (and (> a b) (> c b)))

(local-max? (take 3 sample-data))
(local-min? (take 3 (drop 2 sample-data)))


;;; In REPL
(defn inflection-points [data]
  (lazy-seq
    (let [current-series (take 3 data)]
      
      (cond (< (count current-series) 3)
            '()

            (local-max? current-series)
            (cons
              (conj (second current-series) :max)
              (inflection-points (rest data)))

            (local-min? current-series)
            (cons
              (conj (second current-series) :min)
              (inflection-points (rest data)))

            :otherwise
            (inflection-points (rest data))))))





