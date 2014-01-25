(ns triangulate.core-test
  (:require [clojure.test :refer :all]
            [triangulate.core :refer [points? push-vertex]]
            [triangulate.geom :refer [point-in-circle?]]
            [triangulate.model :refer [->Edge ->Point ->Triangle]]))


(def find-edges (ns-resolve 'triangulate.core 'find-edges))


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
  (testing "A point within a triangle evaluates to three triangles that share this point."
    (let [triangles [(->Triangle (->Point 1 1) (->Point 4 5) (->Point 5 4))]
          point (->Point 2 2)]
      (is (= (set [(->Triangle (->Point 1 1) (->Point 2 2) (->Point 4 5))
                   (->Triangle (->Point 2 2) (->Point 4 5) (->Point 5 4))
                   (->Triangle (->Point 1 1) (->Point 2 2) (->Point 5 4))])
             (set (push-vertex point triangles)))))))
