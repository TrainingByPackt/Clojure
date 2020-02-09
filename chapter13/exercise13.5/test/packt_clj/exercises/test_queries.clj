(ns packt-clj.exercises.test-queries
  (:require
    [clojure.test :refer :all]
    [packt-clj.exercises.fixtures :as fixtures :refer [*db*]]
    [packt-clj.exercises.queries :as queries]))

(use-fixtures :once fixtures/db-fixture)

(deftest test-queries
  (is (= [{:first_name "Andre"
           :height     180
           :id         1
           :surname    "Agassi"
           :weight     80}]
         (queries/all-users *db*)))

  (is (= [{:activity_type "run"
           :distance      8.67M
           :duration      2520
           :id            1
           :user_id       1}
          {:activity_type "cycle"
           :distance      17.68M
           :duration      2703
           :id            2
           :user_id       1}]
         (queries/all-activities *db*)))

  (is (= [{"FIRST_NAME" "Andre"
           "HEIGHT"     180
           "ID"         1
           "SURNAME"    "Agassi"
           "WEIGHT"     80}]
         (queries/all-users-upper-case *db*)))

  (is (= [#:app_user{:FIRST_NAME "Andre"
                     :HEIGHT     180
                     :ID         1
                     :SURNAME    "Agassi"
                     :WEIGHT     80}]
         (queries/all-users-upper-case-qualified *db*)))

  (is (= [:app_user/ID
          :app_user/FIRST_NAME
          :app_user/SURNAME
          :app_user/HEIGHT
          :app_user/WEIGHT]
         (queries/all-users-upper-case-qualified-keys-only *db*)))

  (is (= [[:id
           :activity_type
           :distance
           :duration
           :user_id]
          [1
           "run"
           8.67M
           2520
           1]
          [2
           "cycle"
           17.68M
           2703
           1]]
         (queries/all-activities-as-arrays *db*))))
