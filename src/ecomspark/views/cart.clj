(ns ecomspark.views.cart
  (:require [hiccup.core :as hi]
            [ring.util.codec :as codec]))


(defn HeaderCart [body]
  (hi/html
    (if (:count body)
      [:a#cart.btn.btn-link {:href         "/cart"
                             :ts-swap-push "#cart"}
       (when (pos? (:count body))
         [:span.chip (:count body)])
       "Cart"]
      [:a#cart.btn.btn-link {:href       "/cart"
                             :ts-req     "/cart"
                             :ts-trigger "load"}
       "Cart"])))


(defn BuyResult [opts]
  (hi/html
    [:a.btn.btn-primary {:href "/cart"}
     "Cart"]
    (HeaderCart {:count (:count opts)})))


(defn Buy [id]
  (hi/html
    [:button.btn.btn-primary {:ts-data       (codec/form-encode {:id id})
                              :ts-req        "/cart/add"
                              :ts-req-method "post"}
     "Buy"]))


(defn RemoveResult [opts]
  (hi/html
    [:div {:ts-action "remove"
           :ts-trigger "load"}]
    (HeaderCart {:count (:count opts)})))


(defn Remove [id]
  (hi/html
    [:button.btn.btn-primary {:ts-data       (codec/form-encode {:id id})
                              :ts-req        "/cart/add"
                              :ts-req-method "delete"
                              :ts-target     "parent .product"}
     "Remove"]))
