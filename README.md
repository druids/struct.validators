struct.validators
=================

Additional validators for [Struct library](https://github.com/funcool/struct). It *DOES NOT* include Struct itself.

[![CircleCI](https://circleci.com/gh/druids/struct.validators.svg?style=svg)](https://circleci.com/gh/druids/struct.validators)
[![Dependencies Status](https://jarkeeper.com/druids/struct.validators/status.png)](https://jarkeeper.com/druids/struct.validators)
[![License](https://img.shields.io/badge/MIT-Clause-blue.svg)](https://opensource.org/licenses/MIT)


Leiningen/Boot
--------------

```clojure
[struct.validators "0.3.0"]
```


Documentation
-------------

This library adds following validators:

- `non-blank`: forces a non-nil value not to be blank (spaces aren't allowed)
- `non-blank-like`: coerces a non-nil value to `nil` if the value is an empty string or contains only white spaces
  coerces the value to `nil`, otherwise makes no changes
- `keyword-like`: coerces a non-blank value to a `keyword`
- `bigdec-str`: coerces a non-blank value to a `bigdec` (Clojure only)
- `cz-phone`: validates a given value if it's a valid phone number, if so it formats the value into `E164`
 (+420777666555), if the value is without prefix, `+420` will be used as default (Clojure only)

Validators for other countries can be defined via `phone-factory` function e.g.:

```clojure
(require '[structs.api :as st])

(def de-phone (st/phone-factory "DE"))
```


Consider using namespace `struct.api` that combines all public functions and validators from `struct.core`
 and `struct.validators` at one place. Example:

```clojure
(require '[struct.api :as st])

(st/validate {:name "foo"} {:name [st/required st/non-blank]})
[nil {:name "foo"}]
```
