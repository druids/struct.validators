(ns struct.validators
  (:require
    [clojure.string :refer [blank?]]))


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
