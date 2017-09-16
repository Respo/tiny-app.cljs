
(ns tiny-app.core
  (:require-macros [tiny-app.core])
  (:require [respo.core :as core]
            [respo.cursor :as cursor]))

(def ssr? (some? (js/document.querySelector "meta.respo-ssr")))
