(ns test-app.core
    (:require [rum.core :as rum]
              [test-app.utils :refer [handle-click]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:counter 0}))

(rum/defc counter [number]
          [:div {:on-click #(handle-click app-state)}
           (str "Click times: " number)])

(rum/defc page-content < rum/reactive []
          [:div {}
           (counter (:counter (rum/react app-state)))])

(defn on-js-reload []
      (rum/mount (page-content) (.getElementById js/document "app")))

(on-js-reload)