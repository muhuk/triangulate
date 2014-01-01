(ns triangulate.geom
    (:require [triangulate.model :refer [->Point]])
    (:import [triangulate.model Point]))


(declare distance sides)


(defn circumcircle [^Point a ^Point b ^Point c]
  "The circumcircle is a triangle's circumscribed circle,
  i.e., the unique circle that passes through each of the
  triangle's three vertices."
  (let [Ax (:x a)
        Ay (:y a)
        Bx (:x b)
        By (:y b)
        Cx (:x c)
        Cy (:y c)
        dA (+ (* Ax Ax) (* Ay Ay))
        dB (+ (* Bx Bx) (* By By))
        dC (+ (* Cx Cx) (* Cy Cy))
        [AB BC AC] (sides a b c)
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
    [circumcenter radius]))


(defn distance
  "Calculate distance between two points."
  [^Point a ^Point b]
  (Math/sqrt (+ (Math/pow (- (:x b) (:x a)) 2)
                (Math/pow (- (:y b) (:y a)) 2))))


(defn sides
  "Calculate the lengths of the sides of a triangle."
  [^Point a ^Point b ^Point c]
  (vector (distance a b)
          (distance b c)
          (distance a c)))


(defn point-in-circle?
  "Test whether the point is inside the circle."
  [^Point point ^Point center ^double radius]
  (< (distance point center) radius))
