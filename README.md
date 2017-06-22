
Tiny App for [Repso](https://github.com/Respo/respo)
----

> Try how tiny it could be with help of macros.

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

### License

MIT
