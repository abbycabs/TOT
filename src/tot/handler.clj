(ns tot.handler
  (:require [ring.middleware.params :refer [wrap-params]]
            [liberator.dev :refer [wrap-trace]]
            [tot.web :as web]
            [tot.twitter :as twitter]))

(defn- init []
  (println "TOT is starting...")
  (twitter/tweet-yet?))

(defn- destroy []
  (println "TOT is shutting down..."))

(def app
  (-> web/api
      wrap-params
      (wrap-trace :ui)))
