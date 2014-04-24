(ns tot.resources
  (:require [liberator.core :refer [defresource]]))

(def dbg-counter (atom 0))

(def resource-defaults
  {
    :available-media-types ["application/edn", "application/json"]
    :allowed-methods       [:get]
    :handle-not-found      (fn [_] "Ops.")
    }
  )

(defresource tips resource-defaults
             :handle-ok (fn [_] (format "The counter is %d" @dbg-counter)))

(defresource tip resource-defaults
             :handle-ok (fn [_] (format "The counter is %d" @dbg-counter)))

(defresource current resource-defaults
             :handle-ok (fn [_] (format "The counter is %d" @dbg-counter)))

(defresource random resource-defaults
             :handle-ok (fn [_] (format "The counter is %d" @dbg-counter)))
