(ns packt-authors.fitness.ingest
  (:require
    [clojure.java.jdbc :as jdbc]))

(defn user
  [db app_user]
  (first (jdbc/insert! db :app_user app_user)))

(defn activity
  [db activity]
  (first (jdbc/insert! db :activity activity)))

(defn seed-db
  [db user-defs activity-generator]
  (doseq [app-user user-defs]
    (let [{user-id :1} (ingest/user db app-user)]
      (doseq [activity (repeatedly 20 #(activity-generator user-id))] ;; 20 random activities
        (ingest/activity db activity)))))

