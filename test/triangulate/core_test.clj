(ns triangulate.core-test
  (:require [clojure.test :refer :all]
            [triangulate.core]
            [triangulate.model :refer [->Point]]))


(def find-edges (ns-resolve 'triangulate.core 'find-edges))
(def make-triangle (ns-resolve 'triangulate.core 'make-triangle))
(def points [(->Point 1 1)
             (->Point 2 3)
             (->Point 3 2)
             (->Point 4 4)
             (->Point 1 4)
             (->Point 4 2)])


(deftest test-find-edges
  (testing "Two triangles sharing an edge form a polygon with 4 sides."
    (let [triangles [(make-triangle points 0 1 2)
                     (make-triangle points 1 2 3)]]
      (is (= (set (find-edges triangles))
             (set [[0 1] [0 2] [1 3] [2 3]])))))
  (testing "Two triangles that don't share edges return their edges."
    (let [triangles [(make-triangle points 0 1 2)
                     (make-triangle points 3 4 5)]]
      (is (= (set (find-edges triangles))
             (set [[0 1] [0 2] [1 2] [3 4] [3 5] [4 5]]))))))
