(ns condcircuit.core-test
  (:require [clojure.test :refer :all]
            [condcircuit.core :refer :all]))

(deftest condcircuit-test

  (testing "Empty condcircuit evaluates to nil"
    (is (nil? (condcircuit))))

  (testing "Single value condcircuit evaluates to value"
    (is (= 1 (condcircuit 1))))

  (testing "Super simple binding"
    (is (= 1 (condcircuit [a 1] a))))

  (testing "Multiple bindings available within body"
    (is (= 2 (condcircuit [a 1] [b 1] (+ a b)))))

  (testing "Shortcircuiting"
    (is (= :bazaam (condcircuit [a nil (nil? a) :bazaam] a))))

  (testing "Shortcircuiting part deux"
    (is (= :bazaam (condcircuit [a nil] [b :b (nil? a) :bazaam] b))))

  (testing "Does not evaluate after short circuit"
    (let [evaled (atom false)]
      (is (= :bazaam (condcircuit [a nil (nil? a) :bazaam] [b (swap! evaled (constantly true))] b)))
      (is (false? @evaled)))))
