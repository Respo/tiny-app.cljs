
(ns plain-app.core
  (:require [respo.core]))

(defmacro create-plain-app!! [store updater target comp-container]
  `(~'defonce ~'*store (~'atom ~store)))
