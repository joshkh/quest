(ns quest.db)

(def default-db
  {:name "re-frame"
   :loaded? false
   :mine {:service {:root "beta.flymine.org/beta"
                    :model nil
                    :token nil}
          :assets {:lists nil
                   :templates nil}}})
