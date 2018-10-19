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
                  :role [st/non-blank-like st/keyword-like]}]
      (t/are [expected input] (= expected (st/validate input scheme))

             [{:name "must not be blank"} {:role :admin, :title "Ing."}] {:name "", :role "admin", :title "Ing."}
             [nil {:name "foo", :role :admin, :title "Ing."}] {:name "foo", :role "admin", :title "Ing."}
             [{:name "must not be blank"} {:role nil, :title nil}] {:name " ", :role " ", :title ""}
             [nil {:name nil, :role nil, :title nil}] {:name nil, :role nil, :title nil})))

  (t/testing "keyword-like"
    (let [schema {:name [st/keyword-like]}]
      (t/are [expected input] (= expected (st/validate input schema))
        [nil {:name :bar}] {:name :bar}
        [nil {:name nil}] {:name 3}
        [nil {:name :bar}] {:name "bar"}
        [nil {:name nil}] {:name nil})))


  (t/testing "truth validator"
    (let [scheme {:pep? [st/truth]}]
      (t/are [expected input] (= expected (st/validate input scheme))

        [{:pep? "must be checked"} {}] {:pep? nil}
        [{:pep? "must be checked"} {}] {:pep? 1}
        [nil {:pep? true}] {:pep? true}
        [{:pep? "must be checked"} {}] {:pep? false}))))


(t/deftest test-enum-factory
  (let [scheme {:lang [st/required st/non-blank st/keyword-like (st/enum-factory #{:CZK :EUR})]
                :num [st/required (st/enum-factory #{0 1 2 4 8})]}]
    (t/are [expected input] (= expected (st/validate input scheme))

           [{:lang "allowed values: CZK, EUR", :num "allowed values: 0, 1, 2, 4, 8"} {}] {:lang "ASD", :num "-1"}
           [nil {:lang :CZK, :num 8}] {:lang "CZK", :num 8})))


(t/deftest test-every-factory
  (t/testing "test every-factory"
    (let [scheme {:values [(st/every-factory {:num [st/required st/integer-str]})]}]
      (t/are [expected input] (= expected (st/validate input scheme))

             [{:values "must match all items in a sequence"} {}] {:values [{:num "A"} {:num "B"}]}
             [nil {:values [{:num 1} {:num 2}]}] {:values [{:num "1"} {:num "2"}]})))

  (t/testing "should pass an option map"
    (t/is (= [nil {:values [{:num 8}]}] (st/validate {:values [{:num 8, :foo 10}]}
                                                     {:values [(st/every-factory {:num [st/required]})]})))))


#?(:clj
   (t/deftest test-phone-number
     (let [scheme {:phone [st/cz-phone]}]

       (t/are [expected input] (= expected (st/validate input scheme))

              [nil {:phone "+420777666555"}] {:phone "777 666 555"}
              [nil {:phone "+420777666555"}] {:phone "+420 777 666 555"}
              [{:phone "this field is not a valid phone number"} {}] {:phone "777 666 55"}
              [nil {:phone nil}] {:phone ""}))))


#?(:clj
   (t/deftest bigdec-str-test
     (let [scheme {:amount [st/bigdec-str]
                   :volume [st/bigdec-str]}]
      (t/are [expected input] (= expected (st/validate input scheme))

             [nil {:amount 1.0M, :volume 1M}] {:amount "1.0", :volume 1}

             [{:amount "must be a number"} {:volume 1M}] {:amount "", :volume 1}))))


#?(:clj
   (t/deftest uuid-like-str-test
     (let [scheme {:id [st/uuid-like-str]}]

       (t/is (= [nil {:id #uuid "0f834594-3839-b9b7-6001-9c9f16793d4e"}]
                (st/validate {:id "0f834594-3839-b9b7-6001-9c9f16793d4e"} scheme))))))


#?(:cljs
   (t/deftest uuid-like-str-test
     (let [scheme {:id [st/uuid-like-str]}]
       (t/is (= [nil {:id #uuid "0f834594-3839-b9b7-6001-9c9f16793d4e"}]
                (st/validate {:id "0f834594-3839-b9b7-6001-9c9f16793d4e"} scheme))))))
