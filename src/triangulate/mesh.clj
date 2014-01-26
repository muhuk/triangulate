(ns triangulate.mesh
     (:require [triangulate.model :refer [->Point
                                          edges?
                                          make-edge
                                          make-triangle
                                          points?
                                          triangles?]]
               [triangulate.geom :refer [circumcircle point-in-circle?]])
     (:import [triangulate.model Point]))


(defn find-edges
  "Find bordering edges of the polygon(s) form given triangles.
  Returns a sequence of Edge's."
  [triangles]
  {:pre [(triangles? triangles)]
   :post [(edges? %)]}
  (let [edges (mapcat #(vector (make-edge (:a %) (:b %))
                               (make-edge (:a %) (:c %))
                               (make-edge (:b %) (:c %))) triangles)]
    (map first
         (filter #(= (second %) 1)
                 (frequencies edges)))))


(defn push-vertex
  "Build a new mesh with the given point added to existing triangles."
  [^Point point triangles]
  {:pre [(triangles? triangles)]
   :post [(triangles? %)]}
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
         (triangles? triangles)]
   :post [(triangles? %)]}
  (letfn [(f [points triangles]
             (if (empty? points)
                 triangles
                 (recur (rest points)
                        (push-vertex (first points) triangles))))]
  (f points triangles)))


(defn super-mesh-corners
  "Calculate corners of a bounding rectangle for the point cloud."
  [points margin]
  {:pre [(points? points)
         (number? margin)
         (> margin 0)]}
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


(defn with-super-mesh
  "Call f with the point cloud and it's super mesh then clean the triangles
  using the super mesh vertices before returning the result.

  f is typically push-vertices."
  [f points]
  {:pre [(ifn? f)
         (points? points)]
   :post [(triangles? %)]}
  (let [margin 1
        [k l m n :as super-vertices] (super-mesh-corners points margin)
        super-mesh [(make-triangle k l m)
                    (make-triangle l m n)]
        super-vertices (set super-vertices)
        in-super-mesh? #(or (contains? super-vertices (:a %))
                            (contains? super-vertices (:b %))
                            (contains? super-vertices (:c %)))]
    (remove in-super-mesh? (f points super-mesh))))
