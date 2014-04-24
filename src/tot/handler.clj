(ns tot.handler
  (:require [ring.middleware.params :refer [wrap-params]]
            [liberator.dev :refer [wrap-trace]]
            [compojure.core :refer [defroutes ANY]]
            [tot.routes :as routes]))

(defn init [] (println "TOT is starting..."))
(defn destroy [] (println "TOT is shutting down..."))

(def app
  (-> routes/api
      wrap-params
      (wrap-trace :ui)))
