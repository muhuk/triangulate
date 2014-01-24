(ns triangulate.core
     (:require [triangulate.model :refer [->Point ->Triangle ->TriangleData]]
               [triangulate.geom :refer [circumcircle
                                         distance
                                         sides
                                         point-in-circle?]])
     (:import [triangulate.model Point TriangleData]))


(defn- find-edges
  "Find bordering edges of the polygon(s) form given triangles.
  Returns a sequence of two-tuples of point indices."
  [triangles]
  {:pre [(coll? triangles)
         (every? #(instance? TriangleData %) triangles)]}
  (let [edges (mapcat #(vector (sort [(:A %) (:B %)])
                               (sort [(:A %) (:C %)])
                               (sort [(:B %) (:C %)])) triangles)]
    (map first
         (filter #(= (second %) 1)
                 (frequencies edges)))))


(defn- make-triangle
  [points ^long a ^long b ^long c]
  (let [A (nth points a)
        B (nth points b)
        C (nth points c)
        [circumcenter radius] (circumcircle A B C)]
    (->TriangleData A B C circumcenter radius)))


(defn- push-vertex
  "Build a new mesh with the given point added to existing vertices."
  [triangles points i]
  (let [point (nth points i)
        grouper (fn [triangle]
                  (if (point-in-circle? point
                                        (:circumcenter triangle)
                                        (:radius triangle))
                      :inside
                      :outside))
        {:keys [inside outside]} (group-by grouper triangles)
        edges (find-edges inside)
        new-triangles (map #(make-triangle points (first %) (second %) i) edges)]
    (concat outside new-triangles)))


(defn- push-vertices
  "Build a new mesh starting with an initial mesh and adding the first n
  points."
 [triangles points n]
 (loop [tris triangles i 0]
    (if (< i n)
        (recur (push-vertex tris points i) (inc i))
        tris)))


(defn triangulate
  "TODO"
  [points]
  (let [xs (map #(:x %) points)
        ys (map #(:y %) points)
        min-x (apply min xs)
        max-x (apply max xs)
        min-y (apply min ys)
        max-y (apply max ys)
        n (count points)
        margin (/ (max (- max-x min-x) (- max-y min-y)) 10)
        temp-points [(->Point (- min-x margin) (- min-y margin))
                     (->Point (+ max-x margin) (- min-y margin))
                     (->Point (- min-x margin) (+ max-y margin))
                     (->Point (+ max-x margin) (+ max-y margin))]
        all-points (concat points temp-points)
        super-triangles [(make-triangle all-points
                                        n
                                        (+ n 1)
                                        (+ n 2))
                         (make-triangle all-points
                                        (+ n 1)
                                        (+ n 2)
                                        (+ n 3))]
        cached-triangles (filter #(and (< (:a %) n)
                                       (< (:b %) n)
                                       (< (:c %) n))
                                 (push-vertices super-triangles all-points n))]
    (map #(->Triangle (:A %) (:B %) (:C %)) cached-triangles)))
