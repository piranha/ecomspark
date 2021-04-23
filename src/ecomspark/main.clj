(ns ecomspark.main
  (:gen-class)
  (:require [mount.core :as mount]
            [org.httpkit.server :as httpd]

            [ecomspark.app :as app]))


(mount/defstate httpd
  :start (httpd/run-server app/app {:port 5454})
  :stop  (httpd))


(defn -main [& _args]
  (mount/start))
