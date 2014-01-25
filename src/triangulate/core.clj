(ns triangulate.core
     (:require [triangulate.mesh :refer [points?
                                         push-vertices
                                         super-mesh-corners]]
               [triangulate.model :refer [->Point make-triangle]])
     (:import [triangulate.model Point Triangle]))


(defn triangulate
  "TODO"
  [points]
  {:pre [(points? points)
         (> (count points) 2)]}
  (let [n (count points)
        margin (/ (max (- max-x min-x) (- max-y min-y)) 10)
        temp-points (super-mesh-corners points margin)
        all-points (concat points temp-points)
        super-triangles [(make-triangle (temp-points 0)
                                        (temp-points 1)
                                        (temp-points 2))
                         (make-triangle (temp-points 1)
                                        (temp-points 2)
                                        (temp-points 3))]
        temporary-triangle? (fn [triangle] (or (contains? temp-points (:a triangle))
                                               (contains? temp-points (:b triangle))
                                               (contains? temp-points (:c triangle))))]
    (remove temporary-triangle?
            (push-vertices super-triangles all-points n))))
