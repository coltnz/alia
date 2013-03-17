(ns qbits.alia.types
  (:require
   [clojure.core.typed :as t]
   ;; [qbits.hayt.types :refer :all]
   )
  (:import
   [clojure.lang
    Seqable
    ;; IPersistentVector
    ;; ISeq
    ;; PersistentList
    Named
    IDeref] ;; the IPMap need to be replace with HaytQuery Type
    [com.datastax.driver.core
    PreparedStatement
    Query]))

;; we only deal with string values in reality but we could support
;; j.n.* instances. Can also be just a string in case it's a single host
(t/def-alias Hosts (U String
                      (I (Seqable String) (CountRange 1))))
;; (t/ann foo [Hosts -> String])
;; (defn foo [x]
;;   (apply str x))

;; (t/cf (foo ["s" "s"]))

;; (prn (t/cf ["b"]))

(t/def-alias Values
  (t/Option (Seqable Any)))

;; Types
(t/def-alias ConsistencyValue (U ':one ':two ':three ':quorum ':local
                                 ':local-quorum ':each-quorum))

(t/def-alias HostDistanceValue (U ':local ':remote ':none))
(t/def-alias CompressionValue (U ':snappy ':none))

(t/def-alias HaytQuery '{Named Any}) ;; replace with HaytQuery Type when merged
(t/def-alias CQLQuery (U Query
                         PreparedStatement
                         String
                         HaytQuery))

(t/def-alias ColumnName (U String Named))
(t/def-alias CQLResult (t/Option '{ColumnName Any}))
(t/def-alias CQLAsyncResult (IDeref CQLResult))
