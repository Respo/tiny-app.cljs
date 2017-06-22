
(ns plain-app.example.render
  (:require [plain-app.render :refer [write-page!]]))

(defn main! []
  (write-page! {:title "Plain App"
                :icon "http://logo.mvc-works.org/mvc.png"
                :dev-html-path "target/index.html"
                :prod-html-path "dist/index.html"
                :manifest-path "dist/manifest.json"}))
