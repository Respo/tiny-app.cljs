
(ns tiny-app.example
  (:require [respo.comp.space :refer [=<]]
            [respo.comp.inspect :refer [comp-inspect]]
            [respo.macros :refer [defcomp <> div button span]]
            [tiny-app.core :refer [create-tiny-app->]]))

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

(def app
  (create-tiny-app-> {:model store
                      :updater updater
                      :view comp-container
                      :mount-target (.querySelector js/document ".app")
                      :show-ops? true}))

(set! (.-onload js/window) (:start-app! app))

(def reload! (:reload! app))
