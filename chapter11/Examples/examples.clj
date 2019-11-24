;;; In REPL: minimal macros
(defmacro minimal-macro []
  '(println "I'm trapped inside a macro!"))

(defn minimal-function []
  (println "I'm trapped inside a function!"))

(macroexpand '(minimal-function))
;;; returns: (minimal-function)

(macroexpand '(minimal-macro))
;;; returns:  (println "I'm trapped inside a macro!")

(defmacro mistaken-macro []
  (println "I'm trapped... somewhere!"))

(mistaken-macro)
;;; returns: nil
;;; prints: I'm trapped... somewhere!

(macroexpand '(mistaken-macro))
;;; returns: nil
;;; prints: I'm trapped... somewhere!

;;; In REPL: minimal, but multiple times
(defmacro multi-minimal [n-times]
  (cons 'do (repeat n-times '(println "Macro"))))

(multi-minimal 3)
;;; returns: nil
;;; prints:
;;; Macro
;;; Macro
;;; Macro

(macroexpand '(multi-minimal 3))
;;; returns:
;;; (do
;;;  (println "Macro")
;;;  (println "Macro")
;;;  (println "Macro"))

;;; In REPL: a function to help the macro
(defn multi-min [n-times]
  (cons 'do (repeat n-times '(println "Macro"))))

(defmacro multi-minimal-two [n-times]
  (multi-min n-times))

(multi-minimal-two 3)
;;; returns: nil
;;; prints:
;;; Macro
;;; Macro
;;; Macro

;;; In REPL: run time parameters
;;; Warning: compiles but causes exception when used
(defmacro parameterized-multi-minimal [n-times s]
        (cons 'do (repeat n-times '(println s))))

;;; This version works
(defmacro parameterized-multi-minimal [n-times s]
  (concat (list 'let ['string-to-print s])
          (repeat n-times '(println string-to-print))))

(parameterized-multi-minimal 3 "My own text.")
;;; returns: nil
;;; prints:
;;; My own text.
;;; My own text.
;;; My own text.

;;; In REPL: syntax quoting
(defmacro parameterized-with-syntax [n-times s]
  `(do ~@(repeat n-times `(println ~s))))

(macroexpand '(parameterized-with-syntax 3 "Syntax quoting!"))
;;; returns:
;;; (do
;;;  (clojure.core/println "Syntax quoting!")
;;;  (clojure.core/println "Syntax quoting!")
;;;  (clojure.core/println "Syntax quoting!"))





