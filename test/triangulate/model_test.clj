(ns triangulate.model-test
  (:require [clojure.test :refer :all]
            [triangulate.model :refer [->Point points?]]))


(deftest test-points?
  (testing "A scalar evaluates to false."
    (are [input] (not (points? input))
         nil
         1
         \a
         :b))
  (testing "Collections of other types evaluate to false."
    (are [input] (not (points? input))
         (list 1 2 3)
         [1 2 3]
         (list :a :b :c)
         [:a :b :c]))
  (testing "Empty collections evaluate to true."
    (is (points? [])))
  (testing "Collections of points evaluate to true."
    (are [input] (points? input)
         (list (->Point 1 2) (->Point 3 4))
         [(->Point 1 2) (->Point 3 4)])))
