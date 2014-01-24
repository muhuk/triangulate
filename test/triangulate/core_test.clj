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
  (testing "An empty collection evaluates to an empty sequence."
    (is (empty? (find-edges []))))
  (testing "A single triangle evaluates to its three edges."
    (let [triangles [(make-triangle points 0 1 2)]
          result (find-edges triangles)]
      (is (seq result))
      (is (= (count result) 3))
      (is (= (set result) (set [[(->Point 1 1) (->Point 2 3)]
                                [(->Point 1 1) (->Point 3 2)]
                                [(->Point 2 3) (->Point 3 2)]])))))
  (testing "Two triangles sharing an edge evaluates to a 4-sided polygon."
    (let [triangles [(make-triangle points 0 1 2)
                     (make-triangle points 1 2 3)]
          result (find-edges triangles)]
      (is (seq result))
      (is (= (count result) 4))
      (is (= (set result)
             (set [[(->Point 1 1) (->Point 2 3)]
                   [(->Point 1 1) (->Point 3 2)]
                   [(->Point 2 3) (->Point 4 4)]
                   [(->Point 3 2) (->Point 4 4)]]))))))
