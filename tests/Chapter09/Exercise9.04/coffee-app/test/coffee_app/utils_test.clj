(ns coffee-app.utils-test
    (:require [clojure.test :refer [are is deftest testing]]
              [coffee-app.core :refer [price-menu]]
              [coffee-app.utils :refer :all]))

(deftest calculate-coffee-price-test-with-single-is
         (testing "Single test with is macro."
                  (is (= (calculate-coffee-price price-menu :latte 1) 0.5))))

(deftest calculate-coffee-price-test-with-multiple-is
         (testing "Multiple tests with is macro."
                  (is (= (calculate-coffee-price price-menu :latte 1) 0.5))
                  (is (= (calculate-coffee-price price-menu :latte 2) 1.0))
                  (is (= (calculate-coffee-price price-menu :latte 3) 1.5))))

(deftest calculate-coffee-price-test-with-are
         (testing "Multiple tests with are macro"
                  (are [coffees-hash coffee-type number-of-cups result]
                       (= (calculate-coffee-price coffees-hash coffee-type number-of-cups) result)
                       price-menu :latte 1 0.5
                       price-menu :latte 2 1.0
                       price-menu :latte 3 1.5)))

(deftest calculate-coffee-price-throws-class-cast-error
         (testing "Number of coffee cups needs to be a number"
                  (is (thrown? ClassCastException (calculate-coffee-price price-menu :latte "1")))))

(deftest display-bought-coffee-message-test-with-are
         (testing "Multiple tests with are macro"
                  (are [type number total expected] (is (= (display-bought-coffee-message type number total) expected))
                       :latte 2 1 "Buying 2 latte coffees for total:€1"
                       :latte 3 1.5 "Buying 3 latte coffees for total:€1.5"
                       :mocha 3 3 "Buying 3 mocha coffees for total:€3")))