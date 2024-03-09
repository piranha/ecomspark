(ns ecomspark.main
  (:gen-class)
  (:require [mount.core :as mount]
            [org.httpkit.server :as httpd]

            [ecomspark.app :as app]))


(def default-port 5454)

(mount/defstate httpd
  :start (let [port (or (some->> "PORT" System/getenv Integer/parseInt)
                        default-port)]
           (printf "Starting HTTPd on :%s...\n" port)
           (flush)
           (httpd/run-server app/app {:port port})
           (println "Done!"))
  :stop  (httpd))


(defn -main [& _args]
  (mount/start))
