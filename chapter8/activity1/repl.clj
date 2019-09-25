(use '[clojure.string :only (capitalize)])

(def users #{"paul" "john" "katie"})

(map #(capitalize %) users)

(use '[clojure.set :exclude (join)])

(def admins #{"paul" "katie" "mike" "tracy"})

(subset?  users admins)