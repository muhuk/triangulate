(defproject triangulate "0.1.1-SNAPSHOT"
  :description "A Clojure implementation of \"Efficient Triangulation Algorithm Suitable for Terrain Modelling\" (Bourke, 1989)."
  :url "https://github.com/muhuk/triangulate"
  :license {:name "Eclipse Public License"
            :url "http://opensource.org/licenses/eclipse-1.0.php"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :aliases {"benchmark" ["trampoline" "run" "-m" "triangulate.benchmark"]}
  :jar-exclusions [#"^triangulate/benchmark\.clj$"]
  :plugins  [[codox "0.6.6"]
             [lein-cloverage "1.0.2"]]
  :profiles {:dev {:dependencies [[criterium "0.4.2"]]}})
