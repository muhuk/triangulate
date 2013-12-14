# triangulate

A Clojure implementation of [Efficient Triangulation Algorithm Suitable for Terrain Modelling][1].


## Usage

```clojure
(require 'triangulate.core)
(def points [(triangulate.core/->Point Ax Ay)
             (triangulate.core/->Point Bx By)
             ...
             (triangulate.core/->Point Zx Zy)])
(def triangles (triangulate.core/triangulate points))
```

## License

Copyright © 2013 Atamert Ölçgen

The use and distribution terms for this software are covered by the
[Eclipse Public License 1.0](http://opensource.org/licenses/eclipse-1.0.php)
which can be found in the file epl.html at the root of this distribution. By
using this software in any fashion, you are agreeing to be bound by the terms
of this license. You must not remove this notice, or any other, from this
software.


[1]: http://paulbourke.net/papers/triangulate/
