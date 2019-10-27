(ns test-app.browser
  (:require [doo.runner :refer-macros [doo-tests]]
            [test-app.utils-test]))

(doo-tests 'test-app.utils-test)