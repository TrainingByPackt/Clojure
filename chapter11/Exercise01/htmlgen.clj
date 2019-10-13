(ns packt-clj.htmlgen
  (:require [clojure.string :as string]))

(defn attributes [m]
  (clojure.string/join " "
                       (map (fn [[k v]]
                              (if (string? v)
                                (str (name k) "=\"" v "\"")
                                (name k)))
                            m)))

(defn ->closed-tag [tagname attrs]
  (if attrs
    (str "<" tagname " " (attributes attrs) ">")
    (str "<" tagname "/>")))

(defn ->opening-tag [tagname attrs]
  (if attrs
    (str "<" tagname " " (attributes attrs) ">")
    (str "<" tagname ">")))

(defn ->end-tag [tagname]
  (str "</" tagname ">"))

(defn h1 [& content]
  (let [attrs?  (map? (first content))
        content-items (if attrs? (rest content)
                          content)]
    (if (empty? content-items)
      (->closed-tag "h1" (when attrs? (first content)))
      (str
        (->opening-tag "h1" (when attrs? (first content)))
        (apply str content-items)
        (->end-tag "h1")))))

(defn tag-fn [tagname]
  (fn [& content]
    (let [attrs?  (map? (first content))
          real-content-items (if attrs? (rest content)
                            content)
          content-items (mapcat (fn [i]
                                  (if (sequential? i)
                                    i
                                    [i])) real-content-items)]
      (if (empty? content-items)
        (->closed-tag tagname (when attrs? (first content)))

        (str
          (->opening-tag tagname (when attrs? (first content)))
          (apply str content-items)
          (->end-tag tagname))))))
