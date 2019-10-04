(ns hello-clojurescript.core
  (:require [jayq.core :as jayq :refer [$]]
            [rum.core :as rum]
            [sablono.util]))

(enable-console-print!)

(defn attrs [a]
      (clj->js (sablono.util/html-to-dom-attrs a)))

(rum/defc tile [text number]
          [:div {:class "tile" :id number} text])

(rum/defc tiles []
          [:.tiles {}
           (tile "first" 1)
           (tile "second" 2)])

(rum/defc card [number]
          [:.card {:data-number number
                      :id number}])

(rum/defc cards []
          [:.cards {}
           (card 1)
           (card 2)])

(rum/defc content []
          [:div {}
           (tiles)
           (cards)])

(defn make-draggable []
      (.draggable ($ ".card") (attrs {:revert true :cursor "move"})))

(defn handle-drop [event ui]
      (let [draggable-id (jayq/data (.-draggable ui) "number")]
           (println "Dropping element with id" draggable-id)
           (.draggable (.-draggable ui) (attrs {:revert false}))   ;; will prevent dragging. dragged element stays in droppable box
           (.draggable (.-draggable ui) "disable")   ;; will prevent dragging and add class ui-draggable-disabled. Dragged element stays in initial position
           (.droppable ($ (str "#" (.-id (.-target event)))) "disable") ;; will prevent dropping more elements into this box
           (.position (.-draggable ui)
                      (attrs {:of ($ (str "#" (.-id (.-target event)))) :my "left top" :at "left top"}))))

(defn make-droppable []
      (.droppable ($ (str ".tile"))
                  (attrs {:hoverClass "hovered-tile" :drop handle-drop})))

(defn on-js-reload []
      (rum/mount (content) (.getElementById js/document "app"))
      (make-draggable)
      (make-droppable))