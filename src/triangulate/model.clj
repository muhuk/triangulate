(ns triangulate.model)


(defrecord Point [^double x ^double y]
  Comparable
  (compareTo [this other]
    (let [compare-x (compare (:x this) (:x other))
          compare-y (compare (:y this) (:y other))]
      (if (zero? compare-x)
          compare-y
          compare-x))))


(defrecord Triangle [^Point a ^Point b ^Point c])


(defrecord TriangleData [^Point A
                         ^Point B
                         ^Point C
                         ^Point circumcenter
                         ^double radius])
