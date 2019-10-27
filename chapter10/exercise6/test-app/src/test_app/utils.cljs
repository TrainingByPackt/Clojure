(ns test-app.utils)

(defn handle-click [state]
      (swap! state update-in [:counter] inc))