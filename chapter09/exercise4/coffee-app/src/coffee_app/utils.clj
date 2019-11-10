(ns coffee-app.utils)

;;; orders calculating and formatting function

(defn calculate-coffee-price [coffees coffee-type number]
      (->
        (get coffees coffee-type)
        (* number)
        float))

(defn display-order [order]
      (str "Bought " (:number order) " cups of " (name (:type order)) " for €" (:price order)))

(defn display-bought-coffee-message [type number total]
      (println "Buying" number (name type) "coffees for total:€" total))