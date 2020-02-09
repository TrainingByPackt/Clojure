(ns packt-clj.exercises.fixtures
  (:require
    [clojure.java.jdbc :as jdbc]
    [clojure.test :refer :all]
    [hikari-cp.core :as hikari]))

(def create-app-user-ddl
  "CREATE TABLE app_user
  (id INT GENERATED ALWAYS AS IDENTITY CONSTRAINT USER_ID_PK PRIMARY KEY,
   first_name VARCHAR(32),
   surname VARCHAR(32),
   height SMALLINT,
   weight SMALLINT)")

(def create-activity-ddl
  "CREATE TABLE activity (
   id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
   activity_type VARCHAR(32),
   distance DECIMAL(5,2),
   duration INT,
   user_id INT REFERENCES app_user ON DELETE CASCADE)")

(defn insert-user
  [db]
  (jdbc/insert!
    db
    :app_user
    {:first_name "Andre"
     :surname    "Agassi"
     :height     180
     :weight     80}))

(defn insert-activities
  [db]
  (jdbc/insert-multi!
    db
    :activity
    [{:activity_type "run" :distance 8.67 :duration 2520 :user_id 1}
     {:activity_type "cycle" :distance 17.68 :duration 2703 :user_id 1}]))

(def ^:dynamic *db* {:datasource (hikari/make-datasource {:jdbc-url "jdbc:derby:derby-local;create=true"})})

(defn db-fixture
  [f]
  (jdbc/db-do-commands *db* [create-app-user-ddl create-activity-ddl])
  (insert-user *db*)
  (insert-activities *db*)
  (f)
  (jdbc/execute! *db* ["drop table activity"])
  (jdbc/execute! *db* ["drop table app_user"]))
