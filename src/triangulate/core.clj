(ns triangulate.core
     (:require [triangulate.mesh :refer [push-vertices
                                         with-super-mesh]]
               [triangulate.model :refer [points?
                                          triangles?]]))


(defn triangulate
  "Build a triangle mesh over points.

  Usage:

      (def points [(->Point Ax Ay) (->Point Bx By) ...])
      (def triangles (triangulate points))
      (first triangles) -> (->Triangle (->Point Ax Ay)
                                       (->Point Bx By)
                                       (->Point Cx Cy))
  "
  [points]
  {:pre [(points? points)
         (> (count points) 2)]
   :post [(triangles? %)]}
  (with-super-mesh push-vertices points))
