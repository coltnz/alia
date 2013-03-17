(ns qbits.alia.types
  (:require
   [clojure.core.typed :as t]
   ;; [qbits.hayt.types :refer :all]
   )
  (:import
   [clojure.lang
    IPersistentVector
    ISeq
    Named
    IDeref] ;; the IPMap need to be replace with HaytQuery Type
    [com.datastax.driver.core
    PreparedStatement
    Query]))

;; we only deal with string values in reality but we could support
;; j.n.* instances
(t/def-alias Hosts (All [x]
                        (U (I (ISeq x) (CountRange 1))
                           (I (IPersistentVector x) (CountRange 1)))))

(t/def-alias Values
  (t/Option (U (ISeq Any)
               (IPersistentVector Any))))

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
