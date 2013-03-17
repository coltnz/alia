(ns qbits.alia.utils
  (:import [clojure.lang Named APersistentMap Seqable]
           [com.datastax.driver.core ConsistencyLevel])
  (:require
   [clojure.string :as string]
   [clojure.core.typed :as t]))

(t/ann enum-values->map (All [x]
                             [(Array x) -> (APersistentMap Named x)]))

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



(t/cf (enum-values->map (ConsistencyLevel/values)))
;; (clojure.lang.APersistentMap clojure.lang.Named (U com.datastax.driver.core.ConsistencyLevel nil))


(t/cf (ConsistencyLevel/values))
;; (Array3 com.datastax.driver.core.ConsistencyLevel (U com.datastax.driver.core.ConsistencyLevel nil) (U com.datastax.driver.core.ConsistencyLevel nil))


;; (t/check-ns )

(defmacro var-root-setter [x]
  `(fn [arg#]
     (alter-var-root (var ~x)
                     (constantly arg#)
                     (when (thread-bound? (var ~x))
                       (set! ~x arg#)))))
