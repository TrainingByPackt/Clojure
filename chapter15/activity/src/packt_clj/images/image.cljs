(ns packt-clj.images.image
    (:require [reagent.core :as r]))

(defonce author-display (r/atom true))

(defn image [{:keys [download_url author]}]
  [:div
   [:img {:src download_url
          :width "500px"
          :style {:border "solid gray 3px"
                  :border-radius "10px"}}]
   (when @author-display
      [:div {:style {:font-size "20px"
                  :color "gray" }}
         (str "Image by ") author])])


(defn image-grid [images]
   (if (empty? images)
      [:div "Click the button to fetch images"]
      (into [:div] (map (fn [image-data] [:div {:style {:float "left"
                                                        :margin-left "20px"}}
                                               [image image-data]]) images))))

(defn author-display-button []
  (let [text (if @author-display
                 "Hide author"
                 "Show author")]
  [:button.btn {:on-click #(swap! author-display not)}
           text]))

