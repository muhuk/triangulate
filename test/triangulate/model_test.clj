(ns triangulate.model-test
  (:require [clojure.test :refer :all]
            [triangulate.model :refer [->Edge
                                       ->Point
                                       ->Triangle
                                       edges?
                                       points?
                                       triangles?]]))


(deftest test-edges?
  (testing "A scalar evaluates to false."
    (are [input] (not (edges? input))
         nil
         1
         \a
         :b))
  (testing "Collections of other types evaluate to false."
    (are [input] (not (edges? input))
         (list 1 2 3)
         [1 2 3]
         (list :a :b :c)
         [:a :b :c]))
  (testing "Empty collections evaluate to true."
    (is (edges? [])))
  (testing "Collections of edges evaluate to true."
    (are [input] (edges? input)
         (list (->Edge (->Point 1 2) (->Point 3 4))
               (->Edge (->Point 3 4) (->Point 4 7)))
         [(->Edge (->Point 1 2) (->Point 3 4))
          (->Edge (->Point 3 4) (->Point 4 7))])))


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


(deftest test-triangles?
  (testing "A scalar evaluates to false."
    (are [input] (not (triangles? input))
         nil
         1
         \a
         :b))
  (testing "Collections of other types evaluate to false."
    (are [input] (not (triangles? input))
         (list 1 2 3)
         [1 2 3]
         (list :a :b :c)
         [:a :b :c]))
  (testing "Empty collections evaluate to true."
    (is (triangles? [])))
  (testing "Collections of triangles evaluate to true."
    (are [input] (triangles? input)
         (list (->Triangle (->Point 1 1) (->Point 1 5) (->Point 3 4))
               (->Triangle (->Point 1 5) (->Point 3 4) (->Point 3 8)))
         [(->Triangle (->Point 1 1) (->Point 1 5) (->Point 3 4))
          (->Triangle (->Point 1 5) (->Point 3 4) (->Point 3 8))])))
