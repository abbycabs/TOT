(ns tot.routes
  (:require [compojure.core :refer [ANY context defroutes]]
            [tot.resources :as resources]))

(defroutes api
           (context "/api" []
                    (context "/tips" []
                      (ANY "/" {} resources/tips)
                      (ANY "/:week" {} resources/tip)
                      (ANY "/current" {} resources/current))))
