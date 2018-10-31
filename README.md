
Tiny App for [Repso](https://github.com/Respo/respo)
----

> Try how tiny it could be with help of macros.
> Project is experimental, don't use in real-world projects.

[![Clojars Project](https://img.shields.io/clojars/v/respo/tiny-app.svg)](https://clojars.org/respo/tiny-app)

```edn
[respo/tiny-app "0.3.0"]
```

### Example

```clojure
(def store
  ; retusn a Map with data and states: {:states {}}
)
(defn updater [store op op-data]
  ; op is a keyword, op-data is an arbitrary value
  ; retuns a new store
)
(defcomp comp-container [store]
  ; returns virtual DOM like: (div {})
)
(def app
  (create-app-> {:model store
                 :updater updater
                 :view comp-container
                 :mount-target (.querySelector js/document ".app")
                 :show-ops? true})

(def main! (:init! app))
(def reload! (:reload! app))
```

More explanations for options:

* `store`: pure data
* `updater`: pure function
* `view`: pure function
* `mount-target`: DOM node for mounting app
* `show-ops?`: if `true`, calling `dispatch!` prints logs

Require code from namespaces:

```clojure
(ns tiny-app.example
  (:require [respo.core :refer [defcomp <> div button span]]
            [tiny-app.core :refer [create-app->]]))
```

### Develop

```bash
yarn
yarn try
# open localhost:8080
```

### License

MIT
