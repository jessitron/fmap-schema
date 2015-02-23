(ns fmap-schema.core
  (require [schema.core :refer [Schema subschema-walker explain]]
           [schema.macros :as macros]))

(clojure.core/defrecord FMap [f schema]
  Schema
  (walker [this]
          (let [sub-walker (subschema-walker schema)]
            (clojure.core/fn [x]
              (try
                (sub-walker (f x))
                (catch Exception e
                   (macros/validation-error this x (list 'fmap f (explain schema)) (str "Exception thrown during function evaluation: " (.getMessage e))))))))
  (explain [this] (list 'fmap f (explain schema))))

(clojure.core/defn fmap
  "A value that, passed to f, leads to a result that must satisfy schema"
  [f schema]
    (FMap. f schema))
