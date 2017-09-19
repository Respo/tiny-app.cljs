
Tiny App for [Repso](https://github.com/Respo/respo)
----

> Try how tiny it could be with help of macros.
> Project is experimental, don't use in real-world projects.

[![Clojars Project](https://img.shields.io/clojars/v/respo/tiny-app.svg)](https://clojars.org/respo/tiny-app)

```edn
[respo/tiny-app "0.2.0-alpha"]
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
  (create-tiny-app-> {:model store
                      :updater updater
                      :view comp-container
                      :mount-target (.querySelector js/document ".app")
                      :ssr? (some? (js/document.querySelector "meta.respo-ssr"))
                      :show-ops? true})

(set! (.-onload js/window) (:start-app! app))

(def reload! (:reload! app))
```

More explanations for options:

* `store`: pure data
* `updater`: pure function
* `view`: pure function
* `mount-target`: DOM node for mounting app
* `ssr?`: if you have server rendered HTML, set it `true`, app will patch instead of mounting
* `show-ops?`: if `true`, calling `dispatch!` prints logs

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
