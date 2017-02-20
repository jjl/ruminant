(ns ruminant.colors-test
  (:require [ruminant.colors :as c #?@(:cljs [:refer [rgb rgba hsl hsla]])]
            #?(:clj [clojure.test :as t]
               :cljs [cljs.test :as t :include-macros true]))
  #?(:clj (:import [ruminant.colors rgb rgba hsl hsla])))

(t/deftest colors-test
  (let [t1 (rgb.  45  46  47)
        t2 (rgba. 45  46  47 1)
        t3 (hsl.  0.2 0.3 0.4)
        t4 (hsla. 0.2 0.3 0.4 1)
        ts [t1 t2 t3 t4]]
    (t/is (= "rgb(45,46,47)"       (str t1)))
    (t/is (= "rgba(45,46,47,1)"    (str t2)))
    (t/is (= "hsl(0.2,0.3,0.4)"    (str t3)))
    (t/is (= "hsla(0.2,0.3,0.4,1)" (str t4)))
    (t/is (= t2 (c/add-alpha t1)))
    (t/is (= t4 (c/add-alpha t3)))
    (doseq [tt1 ts]
      (t/is (= tt1 tt1))
      (t/is (= 1 (count (filter (partial = tt1) ts)))))))

