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


(defn make-edge
  [^Point a ^Point b]
  (apply ->Edge (sort [a b])))


(defn make-triangle
  [^Point a ^Point b ^Point c]
  (apply ->Triangle (sort [a b c])))
