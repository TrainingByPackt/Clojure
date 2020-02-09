(ns packt-clj.exercises.test-update-delete-data
  (:require
    [clojure.test :refer :all]
    [packt-clj.exercises.fixtures :as fixtures :refer [*db*]]
    [packt-clj.exercises.update-delete-data :as update-delete-data]))

(use-fixtures :once fixtures/db-fixture)

(deftest test-update-delete-data
  (is (= [{:first_name "Andre"
           :height     180
           :id         1
           :surname    "Agassi"
           :weight     80}]
         (update-delete-data/all-users *db*)))
  (update-delete-data/update-agassi *db*)
  (is (= [{:first_name "Andre"
           :height     180
           :id         1
           :surname    "Agassi"
           :weight     78}]
         (update-delete-data/all-users *db*)))
  (update-delete-data/delete-agassi *db*)
  (is (= [] (update-delete-data/all-users *db*))))
