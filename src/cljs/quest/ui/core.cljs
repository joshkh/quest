(ns quest.ui.core
  (:require [re-frame.core :refer [dispatch subscribe]]
            [reagent.core :refer [atom]]))

(defn starting-question []
  (fn []
    [:div.step
     [:h3 "Start with..." [:i.fa.fa-cog]]]))

(defn main []
  (fn []
    [:div
     [starting-question]]))