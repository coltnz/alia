(ns qbits.alia.utils
  (:import [clojure.lang
            Named
            APersistentMap])
  (:require
   [clojure.string :as string]
   [clojure.core.typed :as t]))

(t/ann enum-values->map (All [x]
                             [(Array x) -> (APersistentMap Named x)]))
(t/tc-ignore
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
    enum-values)))

(defmacro var-root-setter [x]
  `(fn [arg#]
     (alter-var-root (var ~x)
                     (constantly arg#)
                     (when (thread-bound? (var ~x))
                       (set! ~x arg#)))))


(import [com.datastax.driver.core ConsistencyLevel])
(t/non-nil-return com.datastax.driver.core.ConsistencyLevel/values :all)
(t/cf (enum-values->map (ConsistencyLevel/values)))

;; (t/check-ns)
