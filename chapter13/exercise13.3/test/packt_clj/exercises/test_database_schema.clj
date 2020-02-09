(ns packt-clj.exercises.test-database-schema
  (:require
    [clojure.string :as str]
    [clojure.test :refer :all]
    [packt-clj.exercises.database-schema :as database-schema]))

(deftest test-create-table-ddl
  (is (= (-> database-schema/create-app-user-ddl
             (str/replace #"\n" "")
             (str/replace #"\s+" " ")
             str/lower-case)
         (str/lower-case database-schema/create-app-user-ddl-2))))
