(ns test-app.utils-test
  (:require [cljs.test :refer-macros [are deftest is testing use-fixtures]]
            [test-app.utils :refer [handle-click]]))

(use-fixtures :each {:before (fn [] (def app-state (atom {:counter 0})))
                     :after (fn [] (reset! app-state nil))})

(deftest handle-click-test-multiple
         (testing "Handle multiple clicks"
                  (are [result] (= result (handle-click app-state))
                       {:counter 1}
                       {:counter 2}
                       {:counter 3})))

(deftest handle-click-test-one
         (testing "Handle one click"
                  (is (= {:counter 1} (handle-click app-state)))))