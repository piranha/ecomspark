(ns ecomspark.views.cart
  (:require [ring.util.codec :as codec]
            [hiccup.core :as hi]))


(defn Buy [id]
  [:button.btn.btn-primary {:ts-data       (codec/form-encode {:id id})
                            :ts-req        "/cart-add"
                            :ts-req-method "post"}
   "Buy"])


(defn HeaderCart [body]
  (if (:count body)
    [:a#cart.btn.btn-link {:href         "/cart"
                           :ts-swap-push "#cart"}
     (when (pos? (:count body))
       [:span.chip (:count body)])
     "Cart"]
    [:a#cart.btn.btn-link {:href       "/cart"
                           :ts-req     "/cart-info"
                           :ts-trigger "load"}
     "Cart"]))


(defn CartAdd [body]
  (hi/html
    [:a.btn.btn-primary {:href "/cart"}
     "Cart"]
    (HeaderCart {:count (:count body)})))
