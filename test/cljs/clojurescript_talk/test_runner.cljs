(ns clojurescript-talk.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [clojurescript-talk.core-test]
   [clojurescript-talk.common-test]))

(enable-console-print!)

(doo-tests 'clojurescript-talk.core-test
           'clojurescript-talk.common-test)
