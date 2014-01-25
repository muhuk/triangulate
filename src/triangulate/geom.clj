(ns triangulate.geom
    (:require [triangulate.model :refer [->Circle ->Point]])
    (:import [triangulate.model Circle Point Triangle]))


(declare sides)


(defn circumcircle
  "The circumcircle is a triangle's circumscribed circle,
  i.e., the unique circle that passes through each of the
  triangle's three vertices."
  [^Triangle triangle]
  (let [Ax (get-in triangle [:a :x])
        Ay (get-in triangle [:a :y])
        Bx (get-in triangle [:b :x])
        By (get-in triangle [:b :y])
        Cx (get-in triangle [:c :x])
        Cy (get-in triangle [:c :y])
        dA (+ (* Ax Ax) (* Ay Ay))
        dB (+ (* Bx Bx) (* By By))
        dC (+ (* Cx Cx) (* Cy Cy))
        [AB BC AC] (sides triangle)
        origin-denom (* 2
                        (+ (* Ax (- Cy By))
                           (* Bx (- Ay Cy))
                           (* Cx (- By Ay))))
        Ox (/ (+ (* dA (- Cy By))
                 (* dB (- Ay Cy))
                 (* dC (- By Ay)))
              origin-denom)
        Oy (/ (+ (* dA (- Cx Bx))
                 (* dB (- Ax Cx))
                 (* dC (- Bx Ax)))
              (* -1 origin-denom))
        circumcenter (->Point Ox Oy)
        radius (/ (* AB BC AC)
                  (Math/sqrt (* (+ AB BC AC)
                                (- (+ AB BC) AC)
                                (- (+ BC AC) AB)
                                (- (+ AB AC) BC))))]
    (->Circle circumcenter radius)))


(defn distance
  "Calculate distance between two points."
  [^Point a ^Point b]
  (Math/sqrt (+ (Math/pow (- (:x b) (:x a)) 2)
                (Math/pow (- (:y b) (:y a)) 2))))


(defn sides
  "Calculate the lengths of the sides of a triangle."
  [^Triangle triangle]
  (let [{:keys [a b c]} triangle]
    (vector (distance a b)
            (distance b c)
            (distance a c))))


(defn point-in-circle?
  "Test whether the point is inside the circle."
  [^Point point ^Circle circle]
  (< (distance point (:center circle)) (:radius circle)))
