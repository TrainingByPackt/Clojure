(ns packt-clj.fitness.test-ingest-and-query
  (:require
    [clojure.java.jdbc :as jdbc]
    [clojure.test :refer :all]
    [packt-clj.fitness.ingest :as ingest]
    [packt-clj.fitness.query :as query]
    [packt-clj.fitness.schema :as schema]))

(def users [{:first_name "Andre"
             :surname    "Agassi"
             :height     180
             :weight     80}
            {:first_name "Pete"
             :surname    "Sampras"
             :height     185
             :weight     77}
            {:first_name "Steffi"
             :surname    "Graff"
             :height     176
             :weight     64}])

(def activities [{:activity_type "swim", :distance "5100.00", :duration "9180", :user_id "1", :activity_date "2019-01-22"}
                 {:activity_type "run", :distance "25750.00", :duration "6180", :user_id "1", :activity_date "2019-03-05"}
                 {:activity_type "cycle", :distance "8866.67", :duration "2280", :user_id "1", :activity_date "2019-04-19"}
                 {:activity_type "swim", :distance "4400.00", :duration "5280", :user_id "1", :activity_date "2019-03-07"}
                 {:activity_type "swim", :distance "1466.67", :duration "2640", :user_id "1", :activity_date "2019-03-17"}
                 {:activity_type "swim", :distance "4450.00", :duration "5340", :user_id "1", :activity_date "2019-04-08"}
                 {:activity_type "swim", :distance "1700.00", :duration "3060", :user_id "1", :activity_date "2019-02-03"}
                 {:activity_type "swim", :distance "2000.00", :duration "2400", :user_id "1", :activity_date "2019-04-09"}
                 {:activity_type "cycle", :distance "16133.33", :duration "2640", :user_id "1", :activity_date "2019-02-02"}
                 {:activity_type "cycle", :distance "49933.33", :duration "6420", :user_id "1", :activity_date "2019-04-20"}
                 {:activity_type "run", :distance "8000.00", :duration "1920", :user_id "1", :activity_date "2019-01-05"}
                 {:activity_type "run", :distance "22600.00", :duration "6780", :user_id "1", :activity_date "2019-03-12"}
                 {:activity_type "swim", :distance "2200.00", :duration "2640", :user_id "1", :activity_date "2019-01-29"}
                 {:activity_type "swim", :distance "5550.00", :duration "6660", :user_id "1", :activity_date "2019-04-02"}
                 {:activity_type "swim", :distance "7750.00", :duration "9300", :user_id "1", :activity_date "2019-01-24"}
                 {:activity_type "run", :distance "29333.33", :duration "10560", :user_id "1", :activity_date "2019-03-05"}
                 {:activity_type "run", :distance "29466.67", :duration "8160", :user_id "1", :activity_date "2019-01-23"}
                 {:activity_type "cycle", :distance "44400.00", :duration "6660", :user_id "1", :activity_date "2019-02-24"}
                 {:activity_type "cycle", :distance "27766.67", :duration "7140", :user_id "1", :activity_date "2019-04-12"}
                 {:activity_type "cycle", :distance "66666.67", :duration "9600", :user_id "1", :activity_date "2019-04-05"}
                 {:activity_type "run", :distance "25800.00", :duration "7740", :user_id "2", :activity_date "2019-03-25"}
                 {:activity_type "cycle", :distance "27516.67", :duration "7620", :user_id "2", :activity_date "2019-01-17"}
                 {:activity_type "swim", :distance "2450.00", :duration "2940", :user_id "2", :activity_date "2019-02-03"}
                 {:activity_type "run", :distance "17050.00", :duration "5580", :user_id "2", :activity_date "2019-01-25"}
                 {:activity_type "cycle", :distance "49000.00", :duration "8820", :user_id "2", :activity_date "2019-04-04"}
                 {:activity_type "run", :distance "31533.33", :duration "10320", :user_id "2", :activity_date "2019-04-30"}
                 {:activity_type "swim", :distance "5900.00", :duration "10620", :user_id "2", :activity_date "2019-04-11"}
                 {:activity_type "run", :distance "10266.67", :duration "3360", :user_id "2", :activity_date "2019-04-17"}
                 {:activity_type "cycle", :distance "76266.67", :duration "10560", :user_id "2", :activity_date "2019-04-27"}
                 {:activity_type "cycle", :distance "10450.00", :duration "1980", :user_id "2", :activity_date "2019-04-26"}
                 {:activity_type "cycle", :distance "32200.00", :duration "8280", :user_id "2", :activity_date "2019-04-26"}
                 {:activity_type "run", :distance "41500.00", :duration "9960", :user_id "2", :activity_date "2019-01-20"}
                 {:activity_type "cycle", :distance "30333.33", :duration "5460", :user_id "2", :activity_date "2019-01-31"}
                 {:activity_type "swim", :distance "8400.00", :duration "10080", :user_id "2", :activity_date "2019-02-12"}
                 {:activity_type "run", :distance "24016.67", :duration "7860", :user_id "2", :activity_date "2019-01-06"}
                 {:activity_type "swim", :distance "3533.33", :duration "6360", :user_id "2", :activity_date "2019-04-29"}
                 {:activity_type "cycle", :distance "51716.67", :duration "6420", :user_id "2", :activity_date "2019-04-12"}
                 {:activity_type "run", :distance "16600.00", :duration "4980", :user_id "2", :activity_date "2019-03-30"}
                 {:activity_type "swim", :distance "3700.00", :duration "4440", :user_id "2", :activity_date "2019-01-08"}
                 {:activity_type "swim", :distance "2750.00", :duration "3300", :user_id "2", :activity_date "2019-02-11"}
                 {:activity_type "run", :distance "23283.33", :duration "7620", :user_id "3", :activity_date "2019-03-14"}
                 {:activity_type "cycle", :distance "24800.00", :duration "3720", :user_id "3", :activity_date "2019-03-07"}
                 {:activity_type "swim", :distance "2600.00", :duration "3120", :user_id "3", :activity_date "2019-04-26"}
                 {:activity_type "swim", :distance "6800.00", :duration "8160", :user_id "3", :activity_date "2019-02-10"}
                 {:activity_type "run", :distance "25500.00", :duration "9180", :user_id "3", :activity_date "2019-03-10"}
                 {:activity_type "swim", :distance "2700.00", :duration "3240", :user_id "3", :activity_date "2019-02-09"}
                 {:activity_type "run", :distance "31750.00", :duration "7620", :user_id "3", :activity_date "2019-02-08"}
                 {:activity_type "swim", :distance "7550.00", :duration "9060", :user_id "3", :activity_date "2019-04-14"}
                 {:activity_type "swim", :distance "5166.67", :duration "9300", :user_id "3", :activity_date "2019-04-23"}
                 {:activity_type "run", :distance "41300.00", :duration "10620", :user_id "3", :activity_date "2019-03-27"}
                 {:activity_type "run", :distance "10183.33", :duration "2820", :user_id "3", :activity_date "2019-04-20"}
                 {:activity_type "run", :distance "17833.33", :duration "6420", :user_id "3", :activity_date "2019-01-15"}
                 {:activity_type "run", :distance "35533.33", :duration "9840", :user_id "3", :activity_date "2019-02-07"}
                 {:activity_type "run", :distance "6000.00", :duration "2400", :user_id "3", :activity_date "2019-02-04"}
                 {:activity_type "cycle", :distance "50983.33", :duration "9660", :user_id "3", :activity_date "2019-04-25"}
                 {:activity_type "cycle", :distance "32633.33", :duration "5340", :user_id "3", :activity_date "2019-02-17"}
                 {:activity_type "swim", :distance "2550.00", :duration "3060", :user_id "3", :activity_date "2019-02-09"}
                 {:activity_type "cycle", :distance "21233.33", :duration "5460", :user_id "3", :activity_date "2019-01-24"}
                 {:activity_type "cycle", :distance "18000.00", :duration "2160", :user_id "3", :activity_date "2019-02-02"}
                 {:activity_type "swim", :distance "1866.67", :duration "3360", :user_id "3", :activity_date "2019-04-06"}])


(deftest test-ingest-and-query

  (schema/load)

  (doseq [user users]
    (ingest/user schema/db user))

  (doseq [activity activities]
    (ingest/activity schema/db activity))

  (is (= 3 (count (query/all-users schema/db))))
  (is (= '({:id 1, :first_name "Andre", :surname "Agassi", :height 180, :weight 80})
         (query/user schema/db 1)))
  (is (= '({:id            1,
            :activity_type "swim", :distance 5100.00M, :duration 9180, :activity_date #inst"2019-01-22T00:00:00.000-00:00", :user_id 1})
         (query/activity schema/db 1)))
  (is (= 20 (count (query/activities-by-user schema/db 1))))
  (is (= ["Pete Sampras" 136680]
         (query/most-active-user schema/db)))

  (jdbc/execute! schema/db ["drop table activity"])
  (jdbc/execute! schema/db ["drop table app_user"]))
