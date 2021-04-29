(ns ecomspark.views.product
  (:require [hiccup.core :as hi]
            [ring.util.codec :as codec]

            [ecomspark.views.cart :as cart]))


(defn Product [{:keys [id pic price]} {:keys [cart?]}]
  (hi/html
    [:div.product.column.col-3.col-xs-6.p-1
     [:div.card
      [:div.card-image
       [:img.img-responsive {:style "width: 100%; aspect-ratio: 4/5"
                             :src   pic}]]
      [:div.card-header
       [:div.card-title.h5.columns
        [:div.column.col-6 {} price "₴"]
        [:div.column.col-6.text-right
         (if cart?
           (cart/Remove id)
           (cart/Buy id))]]
       [:div.card-subtitle.text-gray "Product " id]]]]))


(defn ProductList [{:keys [products offset cart?]}]
  (hi/html
    [:div#products.columns
     (for [p products]
       (Product p {:cart? cart?}))
     (when offset
       [:a.column.col-12 {:href            (str "/?" (codec/form-encode {:offset offset}))
                          :ts-trigger      "visible"
                          :ts-req          ""
                          :ts-req-selector "children #products"}
        "Next page >>"])]))
