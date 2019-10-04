(ns error-handling.core)


(throw (js/Error. "Bug"))

(try
  (throw (js/Error. "Bug"))
  (catch js/Error e
    "Caught some error"))

(try
  (throw (js/Error. "Bug"))
  (catch js/Error e
    "Caught some error")
  (finally (println "Finally block")))