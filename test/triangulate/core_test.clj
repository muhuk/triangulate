(ns triangulate.core-test
  (:require [clojure.test :refer :all]
            [triangulate.core :refer [triangulate]]
            [triangulate.model :refer [->Point ->Triangle]]))


(deftest test-triangulate
  (testing "Test the whole algorithm."
    (let [points [(->Point 1 1)
                  (->Point 1 3)
                  (->Point 2 2)
                  (->Point 3 1)
                  (->Point 3 3)]]
      (is (= (set [(->Triangle (->Point 1 1) (->Point 1 3) (->Point 2 2))
                   (->Triangle (->Point 1 1) (->Point 2 2) (->Point 3 1))
                   (->Triangle (->Point 1 3) (->Point 2 2) (->Point 3 3))
                   (->Triangle (->Point 2 2) (->Point 3 1) (->Point 3 3))])
             (set (triangulate points)))))))

