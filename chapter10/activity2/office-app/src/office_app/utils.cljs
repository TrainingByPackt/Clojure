(ns office-app.utils)

(defn attrs [a]
      (clj->js (sablono.util/html-to-dom-attrs a)))

(defn get-sort-message [items-count]
      (str (cond
             (< items-count 3) "little"
             (< items-count 6) "medium"
             :else "many") " (" items-count ")"))

(defn get-priorities-list [list priority]
      (filter #(<= (:priority %) priority) list))

(defn get-sorted-priorities-list [list]
      (sort-by :priority list))

(defn delete-item-from-list-by-title [title list]
      (remove #(= title (:title %)) list))

(defn handle-sort-finish [state]
      (fn [ev ui]
          (swap! state update-in [:sort-counter] inc)))