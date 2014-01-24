(ns triangulate.model)


(defrecord Point [^double x ^double y])


(defrecord Triangle [^Point a ^Point b ^Point c])


(defrecord TriangleData [^long a
                         ^long b
                         ^long c
                         ^Point A
                         ^Point B
                         ^Point C
                         ^Point circumcenter
                         ^double radius])
