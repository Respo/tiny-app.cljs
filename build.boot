
(defn read-password [guide]
  (String/valueOf (.readPassword (System/console) guide nil)))

(set-env!
  :resource-paths #{"src"}
  :dependencies '[]
  :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"
                                     :username "jiyinyiyong"
                                     :password (read-password "Clojars password: ")}]))

(def +version+ "0.2.2")

(deftask deploy []
  (comp
    (pom :project     'respo/tiny-app
         :version     +version+
         :description "Respo tiny app template"
         :url         "https://github.com/Respo/tiny-app"
         :scm         {:url "https://github.com/Respo/tiny-app"}
         :license     {"MIT" "http://opensource.org/licenses/mit-license.php"})
    (jar)
    (push :repo "clojars" :gpg-sign false)))
