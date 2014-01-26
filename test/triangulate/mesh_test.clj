(ns triangulate.mesh-test
  (:require [clojure.test :refer :all]
            [triangulate.mesh :refer [find-edges
                                      push-vertex
                                      push-vertices
                                      super-mesh-corners
                                      with-super-mesh]]
            [triangulate.model :refer [->Edge ->Point ->Triangle]]))


(deftest test-find-edges
  (testing "An empty collection evaluates to an empty sequence."
    (is (empty? (find-edges []))))
  (testing "A single triangle evaluates to its three edges."
    (let [triangles [(->Triangle (->Point 1 1) (->Point 2 3) (->Point 3 2))]
          result (find-edges triangles)]
      (is (seq result))
      (is (= (count result) 3))
      (is (= (set result) (set [(->Edge (->Point 1 1) (->Point 2 3))
                                (->Edge (->Point 1 1) (->Point 3 2))
                                (->Edge (->Point 2 3) (->Point 3 2))])))))
  (testing "Two triangles sharing an edge evaluates to a 4-sided polygon."
    (let [triangles [(->Triangle (->Point 1 1) (->Point 2 3) (->Point 3 2))
                     (->Triangle (->Point 2 3) (->Point 3 2) (->Point 4 4))]
          result (find-edges triangles)]
      (is (seq result))
      (is (= (count result) 4))
      (is (= (set result)
             (set [(->Edge (->Point 1 1) (->Point 2 3))
                   (->Edge (->Point 1 1) (->Point 3 2))
                   (->Edge (->Point 2 3) (->Point 4 4))
                   (->Edge (->Point 3 2) (->Point 4 4))]))))))


(deftest test-push-vertex
  (testing "A point within circumcircle of a quad would replace them."
    (let [triangles [(->Triangle (->Point 1 1) (->Point 1 9) (->Point 7 1))
                     (->Triangle (->Point 1 9) (->Point 7 1) (->Point 7 9))
                     (->Triangle (->Point 7 1) (->Point 7 9) (->Point 11 5))]
          point (->Point 2 2)]
      (is (= (set [(->Triangle (->Point 1 1) (->Point 2 2) (->Point 7 1))
                   (->Triangle (->Point 1 9) (->Point 2 2) (->Point 7 9))
                   (->Triangle (->Point 1 1) (->Point 1 9) (->Point 2 2))
                   (->Triangle (->Point 2 2) (->Point 7 1) (->Point 7 9))
                   (->Triangle (->Point 7 1) (->Point 7 9) (->Point 11 5))])
             (set (push-vertex point triangles)))))))


(deftest test-push-vertices
  (testing "Pushing multiple vertices evaluates to the same result as
            successively applying push-vertex on the previous call's result."
    (let [triangles [(->Triangle (->Point 0 0) (->Point 0 100) (->Point 100 0))
                     (->Triangle (->Point 0 100) (->Point 100 0) (->Point 100 100))]
          points [(->Point 2 2) (->Point 50 50) (->Point 98 98)]]
      (is (= (set (push-vertex (->Point 2 2)
                               (push-vertex (->Point 50 50)
                                            (push-vertex (->Point 98 98) triangles))))
             (set (push-vertices points triangles)))))))


(deftest test-super-mesh-corners
  (testing "Super mesh corners take margin into account."
    (let [margin 1
          points [(->Point 1 1)
                  (->Point 1 3)
                  (->Point 3 1)
                  (->Point 3 3)]]
      (is (= (set [(->Point 0 0) (->Point 0 4) (->Point 4 0) (->Point 4 4)])
             (set (super-mesh-corners points margin)))))))


(deftest test-with-super-mesh
  (testing "Super mesh is built and then removed."
    (let [mock-triangle (->Triangle (->Point 1 1) (->Point 1 3) (->Point 3 1))
          mock #(conj %2 mock-triangle)
          points [(->Point 1 1)
                  (->Point 1 3)
                  (->Point 3 1)
                  (->Point 3 3)]]
      (is (= [mock-triangle]
             (with-super-mesh mock points))))))
