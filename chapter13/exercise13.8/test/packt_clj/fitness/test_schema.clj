(ns packt-clj.fitness.test-schema
  (:require
    [clojure.java.jdbc :as jdbc]
    [clojure.test :refer :all]
    [packt-clj.fitness.schema :as schema]))

(deftest test-load-schema
  (jdbc/db-do-commands schema/db ["drop table activity" "drop table app_user"])
  (schema/load))
