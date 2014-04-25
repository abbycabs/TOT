(ns tot.handler
  (:require [ring.middleware.params :refer [wrap-params]]
            [ring.adapter.jetty :as jetty]
            [liberator.dev :refer [wrap-trace]]
            [tot.web :as web]
            [tot.twitter :as twitter]
            [environ.core :refer [env]]))

(defn- init []
  (println "TOT is starting...")
  (twitter/tweet-yet?))

(defn- destroy []
  (println "TOT is shutting down..."))

(def app
  (-> web/api
      wrap-params
      (wrap-trace :ui)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
        (jetty/run-jetty app {:port (Integer. port) :join? false})))
