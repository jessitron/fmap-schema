(ns fmap-schema.core
  (require [schema.core :refer [Schema subschema-walker explain]]
           [schema.macros :as macros]
           [schema.utils :as utils]))

(clojure.core/defrecord FMap [f schema]
  Schema
  (walker [this]
          (let [sub-walker (subschema-walker schema)]
            (clojure.core/fn [x]
              (try
                (let [result (sub-walker (f x))]
                  ( if (utils/error? result)
                    result ;;todo: add to error
                    x)
                  )
                (catch Exception e
                   (macros/validation-error this x (list 'fmap f (explain schema)) (str "Exception thrown during function evaluation: " (.getMessage e))))))))
  (explain [this] (list 'fmap f (explain schema))))

(clojure.core/defn fmap
  "A value that, passed to f, leads to a result that must satisfy schema"
  [f schema]
    (FMap. f schema))
