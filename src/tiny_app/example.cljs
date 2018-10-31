
(ns tiny-app.example
  (:require [respo.comp.space :refer [=<]]
            [respo.comp.inspect :refer [comp-inspect]]
            [respo.core :refer [defcomp <> div button span]]
            [tiny-app.core :refer [create-app->]]))

(def store {:states {}
            :count 0})

(defn updater [store op op-data]
  (case op
    :inc (update store :count #(+ % 1))
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
  (create-app-> {:model store
                 :updater updater
                 :view comp-container
                 :mount-target (.querySelector js/document ".app")
                 :show-ops? true}))

(def main! (:init! app))
(def reload! (:reload! app))
