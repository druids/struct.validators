(ns struct.api-test
  (:require
    #?(:cljs [cljs.test :as t]
       :clj [clojure.test :as t])
    [struct.api :as st]))


(t/deftest test-additional-validators
  (t/testing "required fields"
    (let [scheme {:name [st/required st/non-blank]
                  :role [st/required st/keyword-like]}]
      (t/are [expected input] (= expected (st/validate input scheme))

             [nil {:name "foo", :role :admin}] {:name "foo", :role "admin"}
             [{:name "must not be blank", :role "this field is mandatory"} {}] {:name " ", :role nil})))

  (t/testing "optional fields"
    (let [scheme {:name [st/non-blank]
                  :title [st/non-blank-like]
                  :role [st/keyword-like]}]
      (t/are [expected input] (= expected (st/validate input scheme))

             [{:name "must not be blank"} {:role :admin, :title "Ing."}] {:name "", :role "admin", :title "Ing."}
             [nil {:name "foo", :role :admin, :title "Ing."}] {:name "foo", :role "admin", :title "Ing."}
             [{:name "must not be blank"} {:role nil, :title nil}] {:name " ", :role " ", :title ""}
             [nil {:name nil, :role nil, :title nil}] {:name nil, :role nil, :title nil}))))