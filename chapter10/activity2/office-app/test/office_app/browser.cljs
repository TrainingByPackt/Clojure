(ns office-app.browser
  (:require [doo.runner :refer-macros [doo-tests]]
            [office-app.utils-test]))

(doo-tests 'office-app.utils-test)