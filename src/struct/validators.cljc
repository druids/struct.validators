(ns struct.validators
  (:require
    [clojure.string :refer [blank?]]
    #?(:clj [libphonenumber.core :as libphonenumber])))


(def non-blank
  {:message "must not be blank"
   :validate #(or (nil? %) (not (blank? %)))
   :optional true})


(def non-blank-like
  {:optional true
   :coerce #(when-not (blank? %) %)})


(def keyword-like
  {:optional true
   :coerce #(if (blank? %) nil (keyword %))})


#?(:clj
   (defn phone-factory
     [country-code]
     {:optional true
      :message "this field is not a valid phone number"
      :validate #(if (blank? %)
                   true
                   (let [[status _] (libphonenumber/parse-phone % country-code)]
                     (= :valid status)))
      :coerce #(let [[_ params] (libphonenumber/parse-phone % country-code)]
                 (:e164 params))}))

#?(:clj
   (def cz-phone (phone-factory "CZ")))
