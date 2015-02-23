# fmap-schema

One additional method around prismatic/schema:
functor map

## Usage
include prismatic/schema, and:

    [jessitron/fmap-schema "0.1.0"]

require:

    (:require [fmap-schema :refer [fmap]
              [schema.core :as s]])

Specify a schema that should apply to some transformation of the value
you're checking.

    (def FirstElementString (fmap first s/Str))

    (s/check FirstElementString ["hello" 4]) ;; nil
    (s/check FirstElementSTring [4 "hello"]) ;; fail!

## License

Copyright © 2015 Jessica Kerr

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
