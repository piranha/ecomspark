(ns ecomspark.views.cart
  (:require [hiccup.core :as hi]
            [ring.util.codec :as codec]))


(defn HeaderCart [opts]
  (hi/html
    (if (:count opts)
      [:a#cart.btn.btn-link {:href         "/cart"
                             :ts-swap-push "#cart"}
       (when (pos? (:count opts))
         [:span.chip (:count opts)])
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
    [:button.btn.btn-primary
     {:disabled   true
      :ts-action  "class+ fade, wait transitionend, remove"
      :ts-target  "parent .product"
      :ts-trigger "load"}
     "Remove"]
    (HeaderCart {:count (:count opts)})))


(defn Remove [id]
  (hi/html
    [:button.btn.btn-primary {:ts-data       (codec/form-encode {:id id})
                              :ts-req        "/cart/add"
                              :ts-req-method "delete"}
     "Remove"]))
