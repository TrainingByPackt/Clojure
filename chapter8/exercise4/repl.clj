(in-ns 'garden)

(def vegetables ["cucumber" "carrot"])

(def fruits ["orange" "apple" "melon"])

(in-ns 'shop)

(clojure.core/refer 'garden :only '(vegetables))

fruits

garden/fruits

(in-ns 'market)

(clojure.core/refer 'garden :exclude '(vegetables))

fruits

vegetables

garden/vegetables

(in-ns 'shops)

(clojure.core/refer 'garden :rename '{vegetables Gemuse})
