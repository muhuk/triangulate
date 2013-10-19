(ns triangulate.core-test
  (:require [clojure.test :refer :all]
            [triangulate.core :refer :all]))


(deftest test-distance
  (testing "Two points in exact same locations have zero distance."
    (let [point [22.797 3.001]]
      (is (distance point point) 0)))
  (testing "Two points on the same axis."
    (is (distance [150 10] [150 90]) 80)
    (is (distance [10 40] [60 40]) 50)))


(deftest test-sides
  (testing "Sides of a right triangle."
    (is (sides [5 5] [9 5] [9 8]) [4 3 5])))
