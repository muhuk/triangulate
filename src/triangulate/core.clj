(ns triangulate.core)


(defrecord Triangle [^long a ^long b ^long c A B C circumcenter ^double radius])


(declare distance sides)
(defn circumcircle [a b c]
  (let [[Ax Ay] a
        [Bx By] b
        [Cx Cy] c
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
        circumcenter [Ox Oy]
        radius (/ (* AB BC AC)
                  (Math/sqrt (* (+ AB BC AC)
                                (- (+ AB BC) AC)
                                (- (+ BC AC) AB)
                                (- (+ AB AC) BC))))]
    [circumcenter radius]))


(defn distance
  "Calculate distance between two points."
  [[x1 y1] [x2 y2]]
  (Math/sqrt (+ (Math/pow (- x2 x1) 2)
                (Math/pow (- y2 y1) 2))))


(defn make-triangle
  ""
  [points ^long a ^long b ^long c]
  (let [A (nth points a)
        B (nth points b)
        C (nth points c)
        [circumcenter radius] (circumcircle A B C)]
    (Triangle. a b c A B C circumcenter radius)))


(defn sides
  "Calculate the lengths of the sides of a triangle."
  [a b c]
  (vector (distance a b)
          (distance b c)
          (distance a c)))
