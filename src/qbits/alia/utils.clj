(ns qbits.alia.utils
  (:import [clojure.lang Named])
  (:require
   [clojure.string :as string]
   [clojure.core.typed :as t]))

(t/ann enum-values->map (All [x]
                             [(Array x) -> '{x Named}]))
(defn enum-values->map
  [enum-values]
  (reduce
   (fn [m hd]
     (assoc m (-> (.name ^Enum hd)
                  (.toLowerCase)
                  (string/replace "_" "-")
                  keyword)
            hd))
   {}
   enum-values))

(defmacro var-root-setter [x]
  `(fn [arg#]
     (alter-var-root (var ~x)
                     (constantly arg#)
                     (when (thread-bound? (var ~x))
                       (set! ~x arg#)))))
