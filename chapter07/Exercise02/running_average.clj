;;; In REPL: 2. Simulate a source of potatoes
(def endless-potatoes (repeatedly (fn [] (+ 10 (rand-int 390)))))

;;; In REPL: 3. Test
(take 5 endless-potatoes)
(take 10 endless-potatoes)


;;; In REPL: 7. The complete function
(defn average-potatoes [prev arrivals]
  (lazy-seq
    (if-not arrivals
      '()
      (let [[_ n total] prev
            current [(first arrivals)
                     (inc (or n 0))
                     (+ (first arrivals) (or total 0))]]
        (cons
          current
          (average-potatoes
            current
            (next arrivals)))))))
