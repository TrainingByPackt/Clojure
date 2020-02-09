(ns packt-clj.exercises.test-data-insertion
  (:require
    [clojure.test :refer :all]
    [packt-clj.exercises.fixtures :as fixtures]
    [packt-clj.exercises.data-insertion :as data-insertion]))

(use-fixtures :once fixtures/db-fixture)

(deftest test-ingestion
  (is (= [{:1 1M}] (data-insertion/insert-user-1 fixtures/*db*)))
  (is (= [1] (data-insertion/insert-user-2 fixtures/*db*)))
  (is (= [{:1 1M} {:1 2M}] (data-insertion/insert-activities fixtures/*db*))))