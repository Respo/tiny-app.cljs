
(ns plain-app.example.main
  (:require-macros [plain-app.core :refer [create-plain-app->]]
                   [respo.macros :refer [defcomp <> div span]])
  (:require [plain-app.example.comp.container :refer [comp-container]]
            [respo.cursor :refer [mutate]]))

(def store {:states {}
            :count 0})

(defn updater [store op op-data]
  (case op
    :inc (update store :count inc)
    store))

(create-plain-app-> {:model store
                     :updater updater
                     :view comp-container
                     :mount-target (.querySelector js/document ".app")
                     :show-ops? true})

(set! (.-onload js/window) run-app!)
