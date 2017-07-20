
(ns tiny-app.core
  (:require [respo.core :refer [render! clear-cache! realize-ssr! render-element]]
            [respo.cursor :refer [mutate]]))

#?(:cljs (def ssr? (some? (js/document.querySelector "meta.respo-ssr")))
   :clj (def ssr? false))

(defmacro create-tiny-app-> [configs]
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

      (~'defn ~'render-app! [~'renderer]
        (~'renderer ~mount-target (~comp-container ~'@*store) ~'dispatch!))

      (~'defn ~'run-app! []
        (if ~ssr?
          (~'render-app! realize-ssr!))
        (~'render-app! render!)
        (~'add-watch ~'*store :changes
          (~'fn []
            (~'render-app! render!)))
        (println "App started."))

      (~'defn ~'reload! []
        (clear-cache!)
        (~'render-app! render!)
        (println "Code updated.")))))
