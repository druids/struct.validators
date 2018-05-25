struct.validators
=================

Additional validators for [Struct library](https://github.com/funcool/struct). It *DOES NOT* include Struct itself.

[![CircleCI](https://circleci.com/gh/druids/struct.validators.svg?style=svg)](https://circleci.com/gh/druids/struct.validators)
[![Dependencies Status](https://jarkeeper.com/druids/struct.validators/status.png)](https://jarkeeper.com/druids/struct.validators)
[![License](https://img.shields.io/badge/MIT-Clause-blue.svg)](https://opensource.org/licenses/MIT)


Leiningen/Boot
--------------

```clojure
[struct.validators "0.1.0"]
```


Documentation
-------------

This library adds following validators:

- `non-blank`: forces a non-nil value not to be blank (spaces aren't allowed)
- `non-blank-like`: coerces a non-nil value to `nil` if the value is an empty string or contains only white spaces
  coerces the value to `nil`, otherwise makes no changes
- `keyword-like`: coerces a non-blank value to a `keyword`


Consider using namespace `struct.api` that combines all public functions and validators from `struct.core`
 and `struct.validators` at one place. Example:

```clojure
(require '[struct.api :as st])

(st/validate {:name "foo"} {:name [st/required st/non-blank]})
[nil {:name "foo"}]
```
