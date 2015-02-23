(ns fmap-schema.core-test
  (:require [clojure.test :refer :all]
            [fmap-schema.core :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [schema.core :as s]
            [schema.test]))

(use-fixtures :once schema.test/validate-schemas)

(def starts-with-a ( fmap first (s/eq \a)))

(deftest fmap-schema-test
  (doseq [valid-value ["armadillo" "a" "ape" "actually"]]
    (is ( = valid-value (s/validate starts-with-a valid-value))))
  (doseq [invalid-value [ "bonobo" "poophead" ""]]
    (is (thrown? clojure.lang.ExceptionInfo (s/validate starts-with-a invalid-value)))))

(deftest function-throws-an-exception
  (let [boom (fn [_] (throw ( RuntimeException. "poo")))
        s (fmap boom s/Any)]
     (is (thrown? clojure.lang.ExceptionInfo (s/validate s "anything")))))


