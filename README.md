
Tiny App for [Repso](https://github.com/Respo/respo)
----

> Try how tiny it could be with help of macros.
> Project is experimental, don't use in real-world projects.

[![Clojars Project](https://img.shields.io/clojars/v/respo/tiny-app.svg)](https://clojars.org/respo/tiny-app)

### Example

```clojure
(def store
  ; ...
)
(defn updater [store op op-data]
  ; ...
)
(defcomp comp-container [store]
  ; ...
)
(create-tiny-app-> {:model store
                    :updater updater
                    :view comp-container
                    :mount-target (.querySelector js/document ".app")
                    :show-ops? true})

(set! (.-onload js/window) run-app!)
```

However the namespace part can be long, since macros does handle dependencies in ClojureScript.

```clojure
(ns tiny-app.example
  (:require-macros [tiny-app.core :refer [create-tiny-app->]]
                   [respo.macros :refer [defcomp]])
  (:require [respo.cursor :refer [mutate]]
            [respo.core :refer [create-comp]]))
```

### Develop

```bash
yarn
yarn try
# open localhost:8080
```

### License

MIT
