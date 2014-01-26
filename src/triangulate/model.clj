(ns triangulate.model)


(defrecord Point [^double x ^double y]
  Comparable
  (compareTo [this other]
    (let [compare-x (compare (:x this) (:x other))
          compare-y (compare (:y this) (:y other))]
      (if (zero? compare-x)
          compare-y
          compare-x))))


(defrecord Circle [^Point center ^double radius])


(defrecord Edge [^Point a ^Point b])


(defrecord Triangle [^Point a ^Point b ^Point c])


(defn edges?
  "Returns true if points is a collection that consists of edges."
  [edges]
  (and (coll? edges)
       (every? #(instance? Edge %) edges)))


(defn make-edge
  "Same as ->Edge, but sorts points."
  [^Point a ^Point b]
  (apply ->Edge (sort [a b])))


(defn make-triangle
  "Same as ->Triangle, but sorts points."
  [^Point a ^Point b ^Point c]
  (apply ->Triangle (sort [a b c])))


(defn points?
  "Returns true if points is a collection that consists of points."
  [points]
  (and (coll? points)
       (every? #(instance? Point %) points)))


(defn triangles?
  "Returns true if triangles is a collection that consists of triangles."
  [triangles]
  (and (coll? triangles)
       (every? #(instance? Triangle %) triangles)))
