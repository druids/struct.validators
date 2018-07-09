(ns struct.validators
  (:require
    [clojure.string :refer [blank? join]]
    [cuerdas.core :as cuerdas]
    #?(:clj [libphonenumber.core :as libphonenumber])
    #?(:cljs [goog.string :refer [format]])
    #?(:cljs [goog.string.format])))


(defmulti ->str type)

#?(:clj
   (defmethod ->str clojure.lang.Keyword
     [value]
     (name value)))

#?(:cljs
   (defmethod ->str cljs.core.Keyword
     [value]
     (name value)))


(defmethod ->str :default
  [value]
  (str value))


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


(defn enum-factory
  [choices]
  {:optional true
   :validate #(contains? choices %)
   :message (format "allowed values: %s"
                    (->> choices
                         (map ->str)
                         sort
                         (join ", ")))})


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


#?(:clj
   (def bigdec-str
     {:message "must be a number"
      :optional true
      :validate #(or (number? %) (and (string? %) (cuerdas/numeric? %)))
      :coerce bigdec}))
