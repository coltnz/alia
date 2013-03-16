(ns qbits.alia.types
  (:require
   [clojure.core.typed :as t]
   ;; [qbits.hayt.types :refer :all]
   )
  (:import
   [clojure.lang Named IDeref] ;; the IPMap need to be replace with HaytQuery Type
    [com.datastax.driver.core
    PreparedStatement
    Query]))

(t/def-alias Sequential* (TFn [[x :variance :covariant]]
                              (U (Vector* x)
                                 (List* x)
                                 (Seq* x))))

;; Types
(t/def-alias ConsistencyValue (U ':one ':two ':three ':quorum ':local
                                 ':local-quorum ':each-quorum))

(t/def-alias HaytQuery '{Named Any}) ;; replace with HaytQuery Type when merged
(t/def-alias CQLQuery (U Query
                         PreparedStatement
                         String
                         HaytQuery))

(t/def-alias ColumnName (U String Named))
(t/def-alias CQLResult (t/Option '{ColumnName Any}))
(t/def-alias CQLAsyncResult (IDeref CQLResult))
