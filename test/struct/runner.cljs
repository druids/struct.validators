(ns struct.runner
  (:require
    [doo.runner :refer-macros [doo-tests]]
    [struct.api-test]))

(doo-tests
  'struct.api-test)
