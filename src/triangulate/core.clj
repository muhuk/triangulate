(ns triangulate.core)


(defn distance
  "Calculate distance between two points."
  [[x1 y1] [x2 y2]]
  (Math/sqrt (+ (Math/pow (- x2 x1) 2)
                (Math/pow (- y2 y1) 2))))


(defn sides
  "Calculate the lengths of the sides of a triangle."
  [a b c]
  (vector (distance a b)
          (distance b c)
          (distance a c)))
