(ns ecomspark.main
  (:gen-class)
  (:require [mount.core :as mount]
            [clojure.string :as str]
            [org.httpkit.server :as httpd]
            [ring.middleware.resource :as resource-mw]
            [ring.middleware.session :as session-mw]
            [ring.middleware.params :as params-mw]
            [ring.middleware.json :as json-mw]
            [hiccup.core :as hi]

            [ecomspark.views.index :as index]
            [ecomspark.views.cart :as cart]))


(defn h404 [_req]
  {:status  404
   :headers {"Content-Type" "text/plain"}
   :body    "Not Found"})


(defn index [_req]
  {:status  200
   :headers {"Content-Type" "text/html; charset=UTF-8"}
   :body    (index/Index)})


(defn cart-add [{:keys [form-params session request-method]}]
  (if (not= request-method :post)
    {:status 405
     :body   "Method not allowed"}

    (let [id   (get form-params "id")
          cart (conj (set (:cart session)) id)]

      (if (nil? id)
        {:status 400
         :body {:error "No id supplied"}}
        {:status  200
         :session {:cart cart}
         :partial cart/CartAdd
         :body    {:success true
                   :count   (count cart)}}))))


(defn cart-info [{:keys [session]}]
  {:status 200
   :partial cart/HeaderCart
   :body {:count (count (:cart session))}})


(defn products [{:keys [query-params]}]
  {:status  200
   :body    {:offset (Integer. (get query-params "offset" 0))}
   :partial index/ProductList})


;;;;;;;;;;;;;;

(defn render-partial [_req res]
  (let [func (:partial res)
        html (hi/html (func (:body res)))]
    (-> res
        (update :headers assoc "Content-Type" "text/html; charset=UTF-8")
        (assoc :body html))))


(defn twinspark-request? [req]
  (some-> (get-in req [:headers "accept"])
          (str/starts-with? "text/html+partial")))


(defn twinspark-mw [handler]
  (fn [req]
    (let [res (handler req)]
      (if (and (:partial res)
               (twinspark-request? req))
        (render-partial req res)
        res))))


(defn -app [req]
  ;;; ROUTER
  (let [func (case (:uri req)
               "/" #'index
               "/cart-add" #'cart-add
               "/cart-info" #'cart-info
               "/products" #'products
               h404)]
    (func req)))

(def app (-> -app
             (twinspark-mw)
             (json-mw/wrap-json-response)
             (params-mw/wrap-params)
             (session-mw/wrap-session)
             (resource-mw/wrap-resource "public")))


(mount/defstate httpd
  :start (httpd/run-server app {:port 5454})
  :stop  (httpd))


(defn -main [& args]
  (mount/start))
