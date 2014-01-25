(ns triangulate.geom-test
  (:require [clojure.test :refer :all]
            [triangulate.geom :refer [circumcircle
                                      distance
                                      point-in-circle?
                                      sides]]
            [triangulate.model :refer [->Circle ->Point ->Triangle]]))


(defn- fuzzy=
  ([a b]
    (fuzzy= a b 0.001))
  ([a b tolerance]
    (let [diff (Math/abs (- a b))]
      (< diff tolerance))))


(deftest test-circumcircle
  (testing "Circumcircle on (1 1) with radius 1."
    (let [triangle (->Triangle (->Point 0.070589167 0.6309532502)
                               (->Point 1.237614345 1.9713595746)
                               (->Point 1.0196496048 0.0001930721))
          {:keys [center radius]} (circumcircle triangle)
          Ox (:x center)
          Oy (:y center)]
    (fuzzy= Ox 1.0)
    (fuzzy= Oy 1.0)
    (fuzzy= radius 1.0)))
  (testing "Circumcircle on (300 200) with radius 150."
    (let  [triangle (->Triangle (->Point 343.3170006374 56.390677685)
                               (->Point 156.3872340216 243.3055821811)
                               (->Point 445.6158448089 236.0003575065))
           {:keys [center radius]} (circumcircle triangle)
           Ox (:x center)
           Oy (:y center)]
    (fuzzy= Ox 300.0)
    (fuzzy= Oy 200.0)
    (fuzzy= radius 150.0))))


(deftest test-distance
  (testing "Two points in exact same locations have zero distance."
    (let [point (->Point 22.797 3.001)]
      (is (fuzzy= (distance point point) 0.0))))
  (testing "Two points on the same axis."
    (is (fuzzy= (distance (->Point 150 10) (->Point 150 90)) 80.0))
    (is (fuzzy= (distance (->Point 10 40) (->Point 60 40)) 50.0))))


(deftest test-point-in-circle?
  (testing "Center of a circle is inside."
    (let [center (->Point 7 7)
          circle (->Circle center 4)]
      (is (point-in-circle? center circle))))
  (testing "Points outside of a circle evaluates false."
    (let [center (->Point 7 7)
          circle (->Circle center 4)]
      (is (not (point-in-circle? (->Point 1 7) circle)))
      (is (not (point-in-circle? (->Point 13 7) circle)))
      (is (not (point-in-circle? (->Point 7 1) circle)))
      (is (not (point-in-circle? (->Point 7 13) circle))))))


(deftest test-sides
  (testing "Sides of a right triangle."
    (is (sides (->Triangle (->Point 5 5)
                            (->Point 9 5)
                            (->Point 9 8)))
        [4 3 5])))
