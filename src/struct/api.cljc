(ns struct.api
  "This namespace provides a friendly API for Struct functions and validators at one place."
  (:refer-clojure :exclude [keyword uuid vector boolean long map set])
  (:require
    [struct.core :as st]
    [struct.validators :as v]))


(def validate st/validate)
(def validate-single st/validate-single)
(def validate! st/validate!)
(def valid? st/valid?)
(def valid-single? st/valid-single?)

;; validators
;; struct
(def keyword st/keyword)
(def uuid st/uuid)
(def uuid-str st/uuid-str)
(def email st/email)
(def required st/required)
(def number st/number)
(def integer st/integer)
(def integer-str st/integer-str)
(def boolean st/boolean)
(def boolean-str st/boolean-str)
(def string st/string)
(def string-like st/string-like)
(def in-range st/in-range)
(def positive st/positive)
(def negative st/negative)
(def map st/map)
(def set st/set)
(def coll st/coll)
(def vector st/vector)
(def every st/every)
(def member st/member)
(def function st/function)
(def identical-to st/identical-to)
(def min-count st/min-count)
(def max-count st/max-count)

;; additional
(def non-blank v/non-blank)
(def non-blank-like v/non-blank-like)
(def keyword-like v/keyword-like)

#?(:clj
   (def phone-factory v/phone-factory))
#?(:clj
   (def cz-phone v/cz-phone))
