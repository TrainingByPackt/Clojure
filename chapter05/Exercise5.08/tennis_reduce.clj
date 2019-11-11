;;; In REPL: the formula for recalculating a player's rating after a match
(defn recalculate-rating [previous-rating expected-outcome real-outcome]
        (+ previous-rating (* k-factor (- real-outcome expected-outcome))))
