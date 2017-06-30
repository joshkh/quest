(ns quest.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [quest.core-test]))

(doo-tests 'quest.core-test)
