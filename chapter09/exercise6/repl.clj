(throw (java.io.FileNotFoundException. "No such file"))

(throw (OutOfMemoryError. "Run out of RAM"))

(try (throw (java.io.FileNotFoundException. "No such file"))
     (catch java.io.FileNotFoundException ex
       (format "Exception during file access %s" ex)))

(try (+ 1 2)
     (catch ClassCastException ex
       "Both arguments must be numbers")
     (finally (println "This always gets executed")))

(try (+ 1 "2")
     (catch ClassCastException ex
       "Both arguments must be numbers")
     (finally (println "This always gets executed")))