(ns packt-clj.exercises.test-db-connection
  (:require
    [clojure.test :refer :all]
    [clojure.java.jdbc :as jdbc]
    [packt-clj.exercises.db-connection :as db-connection]))

(deftest test-establishing-db-connection
  (is (instance? org.apache.derby.impl.jdbc.EmbedConnection
                 (jdbc/get-connection db-connection/db))))
