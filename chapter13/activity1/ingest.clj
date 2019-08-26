(ns packt-authors.tennis.ingest
  (:require
    [clojure.data.csv :as csv]
    [clojure.java.io :as io]
    [packt-authors.tennis.parse :as parse]
    [semantic-csv.core :as sc]
    [clojure.java.jdbc :as jdbc]))

(defn historic
  [db file-path]
  (let [{:keys [players matches]} (parse/historic file-path)]
    (jdbc/insert-multi! db :player players)
    (jdbc/insert-multi! db :tennis_match matches)))