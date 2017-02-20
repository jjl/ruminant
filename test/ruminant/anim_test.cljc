(ns ruminant.anim-test
  (:require [ruminant.anim :as a]
            [ruminant.easing :as e]
            [ruminant.scale :as s]
            #?(:clj [clojure.test :as t]
               :cljs [cljs.test :as t :include-macros true])))
(t/deftest utils-test
  (t/testing :partition-tweens
    (t/is (= [[{:duration 1} {:duration 2}] [{:duration 3} {:duration 4}]]
             (a/partition-tweens [{:duration 1} {:duration 2} {:duration 3} {:duration 4}] 2))))
  (t/testing :shortest-first
    (t/is (= [{:duration 1} {:duration 2} {:duration 3} {:duration 4}]
             (a/shortest-first [{:duration 3} {:duration 4} {:duration 1} {:duration 2}])))))

(t/deftest tween-value-test
  (let [a1 (atom nil)]
    (t/testing :range-capping
      (t/is (= 3 (a/tween-value {:duration 2 :start 3 :end 5 :easing e/linear :cursor a1} 3 2)))
      (t/is (= 5 (a/tween-value {:duration 2 :start 3 :end 5 :easing e/linear :cursor a1} 3 6))))
    (t/testing :linear-easing
      (t/is (= 4 (a/tween-value {:duration 2 :start 3 :end 5 :easing e/linear :cursor a1} 3 4)))
      (t/is (= 4 (a/tween-value {:duration 2 :start 3 :end 5 :easing e/backwards :cursor a1} 3 4)))
      )))

(t/deftest tween-frame-test
  (let [a1 (atom nil)]
    (t/testing :range-capping
      (t/is (= 3 (a/tween-value {:duration 2 :start 3 :end 5 :easing e/linear :cursor a1} 3 2)))
      (t/is (= 5 (a/tween-value {:duration 2 :start 3 :end 5 :easing e/linear :cursor a1} 3 6))))
    (t/testing :linear-easing
      (t/is (= 4 (a/tween-value {:duration 2 :start 3 :end 5 :easing e/linear :cursor a1} 3 4)))
      (t/is (= 4 (a/tween-value {:duration 2 :start 3 :end 5 :easing e/backwards :cursor a1} 3 4)))
      )))

(t/deftest tween-frame-test
  (let [a1 (atom nil)]
    (t/testing :range-capping
      (a/tween-frame {:duration 2 :start 3 :end 5 :easing e/linear :cursor a1} 3 2)
      (t/is (= 3  @a1))
      (a/tween-frame {:duration 2 :start 3 :end 5 :easing e/linear :cursor a1} 3 6)
      (t/is (= 5 @a1)))
    (t/testing :linear-easing
      (a/tween-frame {:duration 2 :start 3 :end 5 :easing e/linear :cursor a1} 3 4)
      (t/is (= 4 @a1))
      (a/tween-frame {:duration 2 :start 3 :end 5 :easing e/backwards :cursor a1} 3 4)
      (t/is (= 4 @a1))
      )))

(defn fake-sched
  "Returns a scheduler that runs immediately, adding plus each time it is called"
  [start plus]
  (let [a (atom start)]
    (fn [f]
      (f @a)
      (swap! a + plus))))

(t/deftest make-tweener-test)
(t/deftest tween-test)
