
(ns tiny-app.core)

(declare ssr?)

(defmacro create-tiny-app-> [configs]
  (let [store (:model configs)
        updater (:updater configs)
        comp-container (:view configs)
        mount-target (:mount-target configs)]
    `(do
      (defonce *store# (atom ~store))

      (defn dispatch!# [~'op ~'op-data]
        (if ~(:show-ops? configs)
          (println ~'op ~'op-data))
        (let [~'next-store (if (= ~'op :states)
                                 (update @*store# :states (respo.cursor/mutate 'op-data))
                                 (~updater @*store# ~'op ~'op-data))]
          (reset! *store# ~'next-store)))

      (defn render-app!# [~'renderer]
        (~'renderer ~mount-target (~comp-container @*store#) dispatch!#))

      (defn ~'run-app! []
        (if ~ssr?
          (render-app!# respo.core/realize-ssr!))
        (render-app!# respo.core/render!)
        (~'add-watch *store# :changes
          (fn []
            (render-app!# respo.core/render!)))
        (println "App started."))

      (defn ~'reload! []
        (respo.core/clear-cache!)
        (render-app!# respo.core/render!)
        (println "Code updated.")))))
