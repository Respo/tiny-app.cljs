
(set-env!
  :resource-paths #{"src"}
  :dependencies '[[respo "0.5.14"]])

(def +version+ "0.1.0")

(deftask build []
  (comp
    (pom :project     'respo/tiny-app
         :version     +version+
         :description "Respo tiny app template"
         :url         "https://github.com/Respo/tiny-app"
         :scm         {:url "https://github.com/Respo/tiny-app"}
         :license     {"MIT" "http://opensource.org/licenses/mit-license.php"})
    (jar)
    (install)
    (target)))

(deftask deploy []
  (set-env!
    :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"}]))
  (comp
    (build)
    (push :repo "clojars" :gpg-sign (not (.endsWith +version+ "-SNAPSHOT")))))
