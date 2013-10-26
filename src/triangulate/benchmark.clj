(ns triangulate.benchmark
  (:require [triangulate.core :refer [triangulate]])
  (:gen-class))


(def points [[1 2] [3 4] [1 5] [4 5]])


(defn -main
  "Run the benchmarks"
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  (prn (triangulate points)))
