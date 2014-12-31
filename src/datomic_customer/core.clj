(ns datomic-customer.core
  (:gen-class)
  (:require [clojure.set :as set]
            [datomic.api :as d]))


(def uri "datomic:dev://customer")

(d/create-database uri)

(def conn (d/connect uri))
(def db (d/db conn))

;; parse schema edn file
(def schema-tx (read-string (slurp "customer-schema.edn")))

@(d/transact conn schema-tx)

;; parse seed data edn file
(def data-tx (read-string (slurp "customer-data0.edn")))

;; submit seed data transaction
@(d/transact conn data-tx)

(def results (q '[:find ?c :where [?c :customer/name]] (db conn)))
(count results)


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
