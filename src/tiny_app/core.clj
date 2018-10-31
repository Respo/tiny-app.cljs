
(ns tiny-app.core)

(defmacro create-app-> [configs]
  (let [store (:model configs)
        updater (:updater configs)
        comp-container (:view configs)
        mount-target (:mount-target configs)
        show-ops? (:show-ops? configs)]
    `(do
      (assert (map? ~store) "store should be a map")
      (assert (fn? ~updater) "updater should be a function")
      (assert (fn? ~comp-container) "view should be a function of component")
      (assert (instance? js/Element ~mount-target) "mount-target should be an element")
      (defonce *store# (atom ~store))

      (defn dispatch!# [~'op ~'op-data]
        (if ~show-ops? (println ~'op ~'op-data))
        (let [~'next-store (if (= ~'op :states)
                                 (update @*store# :states (respo.cursor/mutate 'op-data))
                                 (~updater @*store# ~'op ~'op-data))]
          (reset! *store# ~'next-store)))

      (defn render-app!# [~'renderer]
        (~'renderer ~mount-target (~comp-container @*store#) dispatch!#))

      (defn start-app!# []
        (render-app!# respo.core/render!)
        (add-watch *store# :changes
          (fn []
            (render-app!# respo.core/render!)))
        (println "App started."))

      (defn reload!# []
        (respo.core/clear-cache!)
        (render-app!# respo.core/render!)
        (println "Code updated."))

      {:init! start-app!#,
       :reload! reload!#})))
