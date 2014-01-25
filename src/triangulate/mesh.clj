(ns triangulate.mesh
     (:require [triangulate.model :refer [->Point
                                          make-edge
                                          make-triangle
                                          points?
                                          triangles?]]
               [triangulate.geom :refer [circumcircle point-in-circle?]])
     (:import [triangulate.model Point]))


(defn find-edges
  "Find bordering edges of the polygon(s) form given triangles.
  Returns a sequence of two-tuples of point indices."
  [triangles]
  {:pre [(triangles? triangles)]}
  (let [edges (mapcat #(vector (make-edge (:a %) (:b %))
                               (make-edge (:a %) (:c %))
                               (make-edge (:b %) (:c %))) triangles)]
    (map first
         (filter #(= (second %) 1)
                 (frequencies edges)))))


(defn push-vertex
  "Build a new mesh with the given point added to existing vertices."
  [^Point point triangles]
  {:pre [(triangles? triangles)]}
  (let [group-fn #(if (point-in-circle? point (circumcircle %))
                      :inside
                      :outside)
        {:keys [inside outside]} (group-by group-fn triangles)
        new-triangles (map #(make-triangle (:a %) (:b %) point)
                           (find-edges inside))]
    (concat outside new-triangles)))


(defn push-vertices
  "Build a new mesh starting with an initial mesh and adding points."
 [points triangles]
 {:pre [(points? points)
        (triangles? triangles)]}
 (if (empty? points)
   triangles
   (recur (rest points) (push-vertex (first points) triangles))))


(defn super-mesh-corners
  ""
  [points margin]
  {:pre [(points? points)]}
  (let [xs (map #(:x %) points)
        ys (map #(:y %) points)
        min-x (apply min xs)
        max-x (apply max xs)
        min-y (apply min ys)
        max-y (apply max ys)]
    [(->Point (- min-x margin) (- min-y margin))
     (->Point (+ max-x margin) (- min-y margin))
     (->Point (- min-x margin) (+ max-y margin))
     (->Point (+ max-x margin) (+ max-y margin))]))
