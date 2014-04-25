(ns tot.web
  (:require [clj-http.client :as client]
            [compojure.core :refer [ANY context defroutes]]
            [liberator.core :refer [defresource]]
            [clojure.data.csv :as csv]
            [clj-time.core :as t]))

(defn- get-csv-data []
  (let [data (:body (client/get "https://raw.githubusercontent.com/acabunoc/TOT/master/tot.csv"))]
    (csv/read-csv data)))

(defn- csv->edn [csv]
  (let [headers (first csv)
        records (rest csv)]
    (map #(zipmap headers %) records)))

(defn- get-tips []
  (csv->edn (get-csv-data)))

(def resource-defaults
  {
    :available-media-types ["application/json"]
    :allowed-methods       [:get]
    }
  )

(defresource current-resource resource-defaults
             :handle-ok (fn [_] (nth (get-tips) (t/month (t/now)))))

(defresource past-resource resource-defaults
             :handle-ok (fn [_] (take (t/month (t/now)) (get-tips))))

(defroutes api
           (context "/api" []
                    (ANY "/current" {} current-resource)
                    (ANY "/past" {} past-resource)))
