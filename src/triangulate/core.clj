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


(defn find-edges
  "TODO"
  [triangles]
  (let [edge-fn (fn [triangle a b] (vec (sort (vector (a triangle)
                                                      (b triangle)))))
        all-edges (vec (map #(vector (edge-fn % :a :b)
                                (edge-fn % :b :c)
                                (edge-fn % :a :c)) triangles))
        flattened-edges (reduce concat [] all-edges)]
    (vec (map first
              (filter #(= (second %) 1)
                      (frequencies flattened-edges))))))


(defn make-triangle
  "TODO"
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


(defn point-in-circle?
  "Test whether the point is inside the circle."
  [point center radius]
    (< (distance point center) radius))


(declare point-in-circle?)
(defn push-vertex
  "TODO"
  [triangles points i]
  (let [point (nth points i)
        grouper (fn [triangle]
                  (if (point-in-circle? point
                                        (:circumcenter triangle)
                                        (:radius triangle))
                      :inside
                      :outside))
        {:keys [inside outside]} (group-by grouper triangles)
        edges (find-edges inside)
        new-triangles (map #(make-triangle points (first %) (second %) i) edges)]
    (concat outside new-triangles)))


(defn push-vertices
  "TODO"
 [triangles points n]
 (loop [tris triangles i 0]
    (if (< i n)
        (recur (push-vertex tris points i) (inc i))
        tris)))


(defn triangulate
  "TODO"
  [points]
  (let [xs (map first points)
        ys (map second points)
        min-x (apply min xs)
        max-x (apply max xs)
        min-y (apply min ys)
        max-y (apply max ys)
        n (count points)
        margin (/ (max (- max-x min-x) (- max-y min-y)) 10)
        temp-points [[(- min-x margin) (- min-y margin)]
                     [(+ max-x margin) (- min-y margin)]
                     [(- min-x margin) (+ max-y margin)]
                     [(+ max-x margin) (+ max-y margin)]]
        all-points (concat points temp-points)
        super-triangles [(make-triangle all-points
                                        n
                                        (+ n 1)
                                        (+ n 2))
                         (make-triangle all-points
                                        (+ n 1)
                                        (+ n 2)
                                        (+ n 3))]]
    (filter #(and (< (:a %) n)
                  (< (:b %) n)
                  (< (:c %) n))
            (push-vertices super-triangles all-points n))))
