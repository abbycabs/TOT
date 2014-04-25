(ns tot.handler
  (:require [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [ANY context defroutes]]
            [liberator.dev :refer [wrap-trace]]
            [liberator.core :refer [defresource]]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clj-time.core :as t]))

(defn init [] (println "TOT is starting..."))
(defn destroy [] (println "TOT is shutting down..."))

(defn get-csv-data [file]
  (with-open [in-file (io/reader file)]
    (doall
      (csv/read-csv in-file))))

(def tips
  (let [csv (get-csv-data "tot.csv")
        headers (first csv)
        records (rest csv)]
    (map #(zipmap headers %) records)))

(def resource-defaults
  {
    :available-media-types ["application/json"]
    :allowed-methods       [:get]
    :handle-not-found      (fn [_] "Ops.")
    }
  )

(defresource current-resource resource-defaults
             :handle-ok (fn [_] (nth tips (t/month (t/now)))))

(defresource past-resource resource-defaults
             :handle-ok (fn [_] (take (t/month (t/now)) tips)))

(defroutes api
           (context "/api" []
                    (ANY "/current" {} current-resource)
                    (ANY "/past" {} past-resource)))


(def app
  (-> api
      wrap-params
      (wrap-trace :ui)))
