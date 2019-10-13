(in-ns 'garden)

(def vegetables ["cucumber" "carrot"])

(def fruits ["orange" "apple" "melon"])

(in-ns 'shops)

(clojure.core/refer 'garden :rename '{vegetables Gemuse})

vegetables

fruits

owoce