(ns triangulate.core-test
  (:require [clojure.test :refer :all]
            [triangulate.core :refer :all]))


(defn- fuzzy= [tolerance x y]
  (let [diff (Math/abs (- x y))]
    (< diff tolerance)))


(deftest test-circumcircle
  (testing "Circumcircle on (1 1) with radius 1."
    (let [points [[0.070589167 0.6309532502]
                  [1.237614345 1.9713595746]
                  [1.0196496048 0.0001930721]]
          [[Ox Oy] radius] (apply circumcircle points)]
    (fuzzy= 0.001 Ox 1.0)
    (fuzzy= 0.001 Oy 1.0)
    (fuzzy= 0.001 radius 1.0)))
  (testing "Circumcircle on (300 200) with radius 150."
    (let [points [[343.3170006374 56.390677685]
                  [156.3872340216 243.3055821811]
                  [445.6158448089 236.0003575065]]
          [[Ox Oy] radius] (apply circumcircle points)]
    (fuzzy= 0.001 Ox 300.0)
    (fuzzy= 0.001 Oy 200.0)
    (fuzzy= 0.001 radius 150.0))))


(deftest test-distance
  (testing "Two points in exact same locations have zero distance."
    (let [point [22.797 3.001]]
      (is (fuzzy= 0.001 (distance point point) 0.0))))
  (testing "Two points on the same axis."
    (is (fuzzy= 0.001 (distance [150 10] [150 90]) 80.0))
    (is (fuzzy= 0.001 (distance [10 40] [60 40]) 50.0))))


(deftest test-sides
  (testing "Sides of a right triangle."
    (is (sides [5 5] [9 5] [9 8]) [4 3 5])))
