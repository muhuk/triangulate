(ns triangulate.model)


(defrecord Point [^double x ^double y])


(defrecord Triangle [^long a
                     ^long b
                     ^long c
                     ^Point A
                     ^Point B
                     ^Point C
                     ^Point circumcenter
                     ^double radius])
