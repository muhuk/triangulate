(ns triangulate.core
     (:require [triangulate.model :refer [->Point make-edge make-triangle]]
               [triangulate.geom :refer [circumcircle point-in-circle?]])
     (:import [triangulate.model Point Triangle]))


(defn- find-edges
  "Find bordering edges of the polygon(s) form given triangles.
  Returns a sequence of two-tuples of point indices."
  [triangles]
  {:pre [(coll? triangles)
         (every? #(instance? Triangle %) triangles)]}
  (let [edges (mapcat #(vector (make-edge (:a %) (:b %))
                               (make-edge (:a %) (:c %))
                               (make-edge (:b %) (:c %))) triangles)]
    (map first
         (filter #(= (second %) 1)
                 (frequencies edges)))))


(defn- points?
  "Returns true if points is a collection that consists of points."
  [points]
  (and (coll? points)
       (every? #(instance? Point %) points)))


(defn- push-vertex
  "Build a new mesh with the given point added to existing vertices."
  [point triangles]
  (let [group-fn #(if (point-in-circle? point (circumcircle %))
                      :inside
                      :outside)
        {:keys [inside outside]} (group-by group-fn triangles)
        new-triangles (map #(make-triangle (:a %) (:b %) point)
                           (find-edges inside))]
    (concat outside new-triangles)))


(defn- push-vertices
  "Build a new mesh starting with an initial mesh and adding points."
 [points triangles]
 (if (empty? points)
   triangles
   (recur (rest points) (push-vertex (first points) triangles))))


(defn triangulate
  "TODO"
  [points]
  {:pre [(points? points)
         (> (count points) 2)]}
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
