(defproject tot "0.1.0-SNAPSHOT"
  :description "Tips on the TV/Toilet/Twitter"
  :url "https://github.com/acabunoc/TOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [clj-time "0.7.0"]
                 [clj-http "0.9.1"]
                 [compojure "1.1.6"]
                 [liberator "0.11.0"]
                 [ring/ring-servlet "1.2.2"]
                 [environ "0.5.0"]]
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.2.1"]]
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]]
                   :plugins [[lein-ring "0.8.10"]]}
             :production {:env {:production true}}}
  :hooks [environ.leiningen.hooks]
  :ring {:handler tot.handler/app
         :init tot.handler/init
         :destroy tot.handler/destroy}
  :main tot.handler)

