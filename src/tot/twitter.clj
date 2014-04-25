(ns tot.twitter
  (:require [clj-time.core :as t]))

(defn- tweet []
  "TWEET!"
  (println "tweeting!"))

(defn tweet-yet? []
  "Checks if the week has changed and it is time for a new tweet!"
  (future
    (loop [last-week (t/minute (t/now))]
      (let [this-week (t/minute (t/now))]
        (when (not= last-week this-week)
          (tweet))
        (println "no tweet =( ... " this-week)
        (Thread/sleep 10000)
        (recur this-week)))))
