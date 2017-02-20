(ns ruminant.scale-test
  (:require [ruminant.scale :refer [linear->abstract abstract->linear]]
            #?(:clj [clojure.test :as t]
               :cljs [cljs.test :as t :include-macros true])))

(t/deftest scale-descale-test
  (t/testing :linear->abstract
    (doseq [[out & in] [[1   0 10 10]
                        [0.5 5 5  7.5]]]
      (t/testing [out in]
        (t/is (= out (apply linear->abstract in))))))
  (t/testing :abstract->linear
    (doseq [[out & in] [[10 0 10 1]]]
      (t/testing [out in]
        (t/is (= out (apply abstract->linear in)))))))

