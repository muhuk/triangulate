(ns triangulate.core-test
  (:require [clojure.test :refer :all]
            [triangulate.core]
            [triangulate.geom :refer [point-in-circle?]]
            [triangulate.model :refer [->Edge ->Point ->Triangle]]))


(def find-edges (ns-resolve 'triangulate.core 'find-edges))
(def points? (ns-resolve 'triangulate.core 'points?))
(def push-vertices (ns-resolve 'triangulate.core 'push-vertices))
(def push-vertex (ns-resolve 'triangulate.core 'push-vertex))


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
  (testing ""
    (let [triangles [(->Triangle (->Point 0 0) (->Point 0 100) (->Point 100 0))
                     (->Triangle (->Point 0 100) (->Point 100 0) (->Point 100 100))]
          points [(->Point 2 2) (->Point 50 50) (->Point 98 98)]]
      (is (= (set (push-vertex (->Point 2 2)
                               (push-vertex (->Point 50 50)
                                            (push-vertex (->Point 98 98) triangles))))
             (set (push-vertices points triangles)))))))
