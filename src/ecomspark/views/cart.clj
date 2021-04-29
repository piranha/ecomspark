(ns ecomspark.views.cart
  (:require [hiccup.core :as hi]
            [ring.util.codec :as codec]))


(defn HeaderCart [opts]
  (hi/html
    (if opts
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
    [:form {:method "post"
            :action "/cart/add"
            :ts-req ""}
     [:input {:type "hidden" :name "id" :value id}]
     [:button.btn.btn-primary "Buy"]]))


(defn RemoveResult [opts]
  (hi/html
    [:button.btn.btn-primary
     {:disabled   true
      :ts-trigger "load"
      :ts-action  "target 'parent .product', class+ fade, wait transitionend, remove"}
     "Remove"]
    (HeaderCart {:count (:count opts)})))


(defn Remove [id]
  (hi/html
    [:form {:method        "post"
            :action        "/cart/add"
            :ts-req        ""
            :ts-req-method "delete"}
     [:input {:type "hidden" :name "_method" :value "delete"}]
     [:input {:type "hidden" :name "id" :value id}]
     [:button.btn.btn-primary "Remove"]]))
