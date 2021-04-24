(ns ecomspark.views.base
  (:require [hiccup.core :as hi]
            [hiccup.page]

            [ecomspark.views.cart :as cart]))


(defn Head [title]
  (hi/html
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
     [:link {:rel  "stylesheet"
             :href "/static/spectre.css"}]
     [:script {:src "/static/twinspark.js"}]
     [:style "
       .fade {opacity: 0; transition: opacity 0.5s ease-in-out; }
     "]
     [:title {} title]]))


(defn Nav []
  (hi/html
    [:header.navbar
     [:section.navbar-section
      [:a.navbar-brand.mr-2 {:href "/"} "EcomSpark"]]
     [:section.navbar-section
      (cart/HeaderCart nil)]]))


(defn Footer []
  (hi/html
    [:footer]))


(defn wrap [title content]
  (hi/html (:html5 hiccup.page/doctype)
    [:html
     (Head title) ;;; <-- title
     [:body.container {}
      [:div.columns
       [:div {:class "column col-6 col-xl-8 col-lg-10 col-md-12 col-mx-auto"}
        (Nav)
        content ;;; <-- content
        (Footer)]]]]))
