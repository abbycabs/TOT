(defproject tot "0.1.0-SNAPSHOT"
  :description "Tips on the TV/Toilet"
  :url "https://github.com/acabunoc/TOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.7.0"]
                 [clj-http "0.9.1"]
                 [compojure "1.1.6"]
                 [Liberator "0.11.0"]]
  :plugins []
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]]
                   :plugins [[lein-ring "0.8.10"]]}}
  :ring {:handler tot.handler/app
         :init tot.handler/init
         :destroy tot.handler/destroy})

