(ns packt-clj.exercises.test-connection-pooling
  (:require
    [clojure.test :refer :all]
    [clojure.java.jdbc :as jdbc]
    [packt-clj.exercises.connection-pooling :as connection-pooling]))

(deftest test-connection-pool-with-jdbc-url
  (is (instance? com.zaxxer.hikari.pool.HikariProxyConnection
                 (jdbc/get-connection connection-pooling/db-jdbc-url))))

(deftest test-connection-pool-with-datasource-class-name
  (is (instance? com.zaxxer.hikari.pool.HikariProxyConnection
                 (jdbc/get-connection connection-pooling/db-datasource-class-name))))


