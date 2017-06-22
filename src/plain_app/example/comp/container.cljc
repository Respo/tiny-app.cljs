
(ns plain-app.example.comp.container
  (:require-macros [respo.macros :refer [defcomp <> div button span]])
  (:require [respo.core :refer [create-comp]]
            [respo.comp.space :refer [=<]]
            [respo.comp.inspect :refer [comp-inspect]]))

(defn on-click [e dispatch!]
  (dispatch! :inc nil))

(defcomp comp-container [store]
  (div {}
    (comp-inspect "Store" store {:top 20})
    (button {:inner-text "inc"
             :event {:click on-click}})
    (=< 8 nil)
    (<> span (:count store) nil)))
