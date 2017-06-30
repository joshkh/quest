(ns quest.events.boot
  (:require [re-frame.core :refer [reg-event-db reg-event-fx dispatch]]
            [day8.re-frame.async-flow-fx]
            [quest.db :as db]
            [quest.fx]
            [imcljs.fetch :as fetch]))

;; Boot procedure
(defn boot-flow
  []
  {:first-dispatch [::fetch-token]
   :rules [{:when :seen?
            :events ::store-token
            :dispatch-n [[::fetch-model]
                         [::fetch-lists]
                         [::fetch-templates]]}
           {:when :seen-all-of?
            :events [::fetch-model ::fetch-lists ::fetch-templates]
            :dispatch [::finished-loading]}]})

;; Initial boot
(reg-event-fx
  ::boot
  (fn [_ _]
    {:db db/default-db
     :async-flow (boot-flow)}))

;;; Token
(reg-event-fx
  ::fetch-token
  (fn [{db :db}]
    (let [service (get-in db [:mine :service])]
      {:intermine/io {:chan (fetch/session service)
                      :on-success [::store-token]}})))
(reg-event-db ::store-token (fn [db [_ token]] (assoc-in db [:mine :service :token] token)))

;;; Model
(reg-event-fx
  ::fetch-model
  (fn [{db :db}]
    (let [service (get-in db [:mine :service])]
      {:intermine/io {:chan (fetch/model service)
                      :on-success [::store-model]}})))
(reg-event-db ::store-model (fn [db [_ model]] (assoc-in db [:mine :service :model] model)))
;;; Lists
(reg-event-fx
  ::fetch-lists
  (fn [{db :db}]
    (let [service (get-in db [:mine :service])]
      {:intermine/io {:chan (fetch/lists service)
                      :on-success [::store-lists]}})))
(reg-event-db ::store-lists (fn [db [_ lists]] (assoc-in db [:mine :assets :lists] lists)))

;;; Templates
(reg-event-fx
  ::fetch-templates
  (fn [{db :db}]
    (let [service (get-in db [:mine :service])]
      {:intermine/io {:chan (fetch/templates service)
                      :on-success [::store-templates]}})))
(reg-event-db ::store-templates (fn [db [_ templates]] (assoc-in db [:mine :assets :templates] templates)))

;; We're done
(reg-event-db
  ::finished-loading
  (fn [db]
    (assoc db :loaded? true)))

