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


(defrecord Triangle [^Point a ^Point b ^Point c])
