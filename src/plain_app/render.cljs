
(ns plain-app.render
  (:require-macros [respo.macros :refer [html head title script style meta' div link body]])
  (:require [respo.render.html :refer [make-html make-string]]
            [respo.core :refer [create-element]]
            [plain-app.example.comp.container :refer [comp-container]]
            ["fs" :refer [readFileSync writeFileSync]]))

(defn spit [file-name content]
  (writeFileSync file-name content)
  (println "spit to file:" file-name))

(defn html-dsl [resources html-content]
  (make-html
   (html
    {}
    (head
     {}
     (title {:innerHTML (:title resources)})
     (link {:rel "icon", :type "image/png", :href (:icon resources)})
     (link {:rel "manifest", :href "manifest.json"})
     (meta' {:charset "utf8"})
     (meta' {:name "viewport", :content "width=device-width, initial-scale=1"})
     (if (:build? resources) (meta' {:id "server-rendered", :type "text/edn"}))
     (if (contains? resources :css)
       (link {:rel "stylesheet", :type "text/css", :href (:css resources)})))
    (body
     {}
     (div {:class-name "app", :innerHTML html-content})
     (if (:build? resources)
       (script {:src (:vendor resources)}))
     (script {:src (:main resources)})))))

(defn generate-empty-html [configs]
  (html-dsl {:build? false,
             :main "/main.js",
             :title (:title configs)
             :icon (:icon configs)}
            ""))

(defn slurp [x] (readFileSync x "utf8"))

(defn generate-html [configs]
  (let [tree (comp-container {})
        html-content (make-string tree)
        resources (let [manifest (js/JSON.parse (slurp (:manifest-path configs)))]
                    {:build? true,
                     :css (aget manifest "main.css"),
                     :title (:title configs)
                     :icon (:icon configs)
                     :main (aget manifest "main.js"),
                     :vendor (aget manifest "vendor.js")})]
    (html-dsl resources html-content)))

(defn write-page! [configs]
  (let [env js/process.env.env]
    (println "environment:" env)
    (if (= env "dev")
      (spit (:dev-html-path configs) (generate-empty-html configs))
      (spit (:prod-html-path configs) (generate-html configs)))))
