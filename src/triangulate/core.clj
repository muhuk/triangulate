(ns triangulate.core)


(defrecord Point [^double x ^double y])


(defrecord Triangle [^long a
                     ^long b
                     ^long c
                     ^Point A
                     ^Point B
                     ^Point C
                     ^Point circumcenter
                     ^double radius])


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
        circumcenter (Point. Ox Oy)
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


(defn find-edges
  "Find bordering edges of the polygon(s) given triangles form."
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
  [points ^long a ^long b ^long c]
  (let [A (nth points a)
        B (nth points b)
        C (nth points c)
        [circumcenter radius] (circumcircle A B C)]
    (Triangle. a b c A B C circumcenter radius)))


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


(defn push-vertex
  "Build a new mesh with the given point added to existing vertices."
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
  "Build a new mesh starting with an initial mesh and adding the first n
  points."
 [triangles points n]
 (loop [tris triangles i 0]
    (if (< i n)
        (recur (push-vertex tris points i) (inc i))
        tris)))


(defn triangulate
  "TODO"
  [points]
  (let [xs (map #(:x %) points)
        ys (map #(:y %) points)
        min-x (apply min xs)
        max-x (apply max xs)
        min-y (apply min ys)
        max-y (apply max ys)
        n (count points)
        margin (/ (max (- max-x min-x) (- max-y min-y)) 10)
        temp-points [(Point. (- min-x margin) (- min-y margin))
                     (Point. (+ max-x margin) (- min-y margin))
                     (Point. (- min-x margin) (+ max-y margin))
                     (Point. (+ max-x margin) (+ max-y margin))]
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
