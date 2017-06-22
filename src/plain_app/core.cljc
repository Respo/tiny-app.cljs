
(ns plain-app.core
  (:require [respo.core :refer [render! clear-cache! falsify-stage! render-element]]
            [respo.cursor :refer [mutate]]))

#?(:cljs (def server-rendered? (some? (js/document.querySelector "meta#server-rendered")))
   :clj (def server-rendered? false))

(defmacro create-plain-app-> [configs]
  (let [store (:model configs)
        updater (:updater configs)
        comp-container (:view configs)
        mount-target (:mount-target configs)]
    `(do
      (~'defonce ~'*store (~'atom ~store))
      (~'defn ~'dispatch! [~'op ~'op-data]
        (if ~(:show-ops? configs)
          (println ~'op ~'op-data))
        (~'let [~'next-store (if (= ~'op :states)
                                 (update ~'@*store :states (mutate 'op-data))
                                 (~updater ~'@*store ~'op ~'op-data))]
          (reset! ~'*store ~'next-store)))
      (~'defn ~'render-app! []
        (render! ~mount-target (~comp-container ~'@*store) ~'dispatch!))
      (~'defn ~'run-app! []
        (if ~server-rendered?
          (falsify-stage! ~mount-target
                          (render-element (~comp-container ~'@*store))
                          ~'dispatch!))
        (~'render-app!)
        (~'add-watch ~'*store :changes ~'render-app!)
        (println "App started."))
      (~'defn ~'reload! []
        (clear-cache!)
        (~'render-app!)
        (println "Code updated."))
      )))
