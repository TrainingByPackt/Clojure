(ns hello-test.browser
  (:require [doo.runner :refer-macros [doo-tests]]
            [hello-test.core-test]))

(doo-tests 'hello-test.core-test)