(ns quest.fx
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [re-frame.core :refer [dispatch reg-fx]]
            [cljs.core.async :refer [<!]]))

(reg-fx
  :intermine/io
  (fn [{:keys [on-success on-failure response-format chan params]}]
    (go
      (let [{:keys [statusCode] :as response} (<! chan)]
        (when (not statusCode)
          (dispatch (conj on-success response)))))))