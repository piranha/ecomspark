(ns ecomspark.views.index
  (:require [hiccup.core :as hi]
            [ring.util.codec :as codec]

            [ecomspark.views.base :as base]
            [ecomspark.views.cart :as cart]))


(defn gen-price [id]
  (+ 10 (mod (hash id) 90)))


(defn Product [id]
  [:div.column.col-3.col-xs-6.p-1
   [:div.card
    [:div.card-image
     [:img.img-responsive {:style {:min-height 200}
                           :src   (format "https://picsum.photos/seed/%s/240/300" id)}]]
    [:div.card-header
     [:div.card-title.h5.columns
      [:div.column.col-6 (gen-price id) "₴"]
      [:div.column.col-6.text-right (cart/Buy id)]]
     [:div.card-subtitle.text-gray "Product " id]]]])


(defn ProductList [{:keys [offset]}]
  [:div#products.columns
   (for [i (range offset (+ offset 24))]
     (Product i))
   [:div.column.col-12 {:ts-req          "/products"
                        :ts-req-selector "children #products"
                        :ts-data         (codec/form-encode {:offset (+ offset 24)})
                        :ts-trigger      "visible"}
    "PAGINATION"]])


(defn Index []
  (base/wrap "EcomSpark"
    (hi/html
      [:h1 "Products"]
      (ProductList {:offset 0}))))
