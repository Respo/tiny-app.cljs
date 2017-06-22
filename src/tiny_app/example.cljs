
(ns tiny-app.example
  (:require-macros [tiny-app.core :refer [create-tiny-app->]]
                   [respo.macros :refer [defcomp <> div button span]])
  (:require [tiny-app.example.comp.container :refer [comp-container]]
            [respo.cursor :refer [mutate]]
            [respo.comp.space :refer [=<]]
            [respo.comp.inspect :refer [comp-inspect]]
            [respo.core :refer [create-comp]]))

(def store {:states {}
            :count 0})

(defn updater [store op op-data]
  (case op
    :inc (update store :count inc)
    store))

(defn on-click [e dispatch!]
  (dispatch! :inc nil))

(defcomp comp-container [store]
  (div {}
    (comp-inspect "Store" store {:top 20})
    (button {:inner-text "inc"
             :event {:click on-click}})
    (=< 8 nil)
    (<> span (:count store) nil)))

(create-tiny-app-> {:model store
                     :updater updater
                     :view comp-container
                     :mount-target (.querySelector js/document ".app")
                     :show-ops? true})

(set! (.-onload js/window) run-app!)
