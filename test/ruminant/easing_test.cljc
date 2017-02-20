(ns ruminant.easing-test
  (:require [ruminant.colors :as c #?@(:cljs [:refer [rgb rgba hsl hsla]])]
            [ruminant.easing :as e]
            #?(:clj [clojure.test :as t]
               :cljs [cljs.test :as t :include-macros true]))
  #?(:clj (:import [ruminant.colors rgb rgba hsl hsla])))

(t/deftest easing-test
  (t/testing :linear-easing
    (doseq [in [1 2.0 3.5]]
      (t/is (= in (e/linear in)))))
  (t/testing :tweening-ints
    (doseq [[s e a x] [[0  10 0.5 5.0]
                       [10 0  0.5 5.0]
                       [5  15 0.5 10.0]
                       [15 5  0.5 10.0]]]
      (t/is (= x (e/tween s e a)))))
  (t/testing :tweening-colors
    (let [rgb-nil  (rgb.  0 0 0)
          rgb-max  (rgb.  255 255 255)
          rgb-half (rgb. 127.5 127.5 127.5)
          rgba-nil (rgba. 0 0 0 0)
          rgba-max (rgba. 255 255 255 1)
          rgba-half (rgba. 127.5 127.5 127.5 0.5)
          hsl-nil  (hsl.  0 0 0)
          hsla-nil (hsla. 0 0 0 0)]
    (doseq [[s e a x] [[rgb-nil   rgb-max  0.5 rgb-half]
                       [rgba-nil  rgb-max  0.5 rgba-half]
                       [rgb-max   rgba-nil 0.5 rgba-half]
                       [rgba-max  rgba-nil 0.5 rgba-half]
                       ]]
      (t/testing [s e a x]
        (t/is (= x (e/tween s e a))))))))
