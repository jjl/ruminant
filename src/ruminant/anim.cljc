(ns ruminant.anim
  (:require [#?(:clj clojure.spec :cljs cljs.spec) :as s]
            [ruminant.easing :as e]
            [ruminant.scale :as scale]
            [irresponsible.spectra :as ss]))

(s/def ::start    number?)
(s/def ::end      number?)
(s/def ::easing   fn?)
(s/def ::duration number?)
(s/def ::on-end   fn?)
(s/def ::props    (s/map-of keyword? any?))
(s/def ::cursor   #(instance? #?(:clj clojure.lang.IAtom :cljs cljs.core.ISwap) %))
(s/def ::tween (s/keys :req-un [::start ::end ::easing ::cursor ::duration] :opt-un [::on-end]))
  
;; Schedulers
;; A scheduler will call our given callback at some time in the future with a timestamp
;; timestamps are in milliseconds, but are floating for greater precision. because js

#?
(:cljs
 (defn js-raf [f]
   (js/requestAnimationFrame f)))

(defn partition-tweens [tweens delta]
  (split-with #(<= (:duration %) delta) tweens))

(defn shortest-first [tweens]
  (sort-by :duration tweens))

(defn tween-value [{:keys [duration on-end start end easing cursor] :as tween} start-time time]
  ;; (ss/assert! ::tween tween)
  (let [delta (- time start-time)]
    (cond (<= time start-time) start
          (>= delta duration)  end
          :else
          (->> (scale/linear->abstract start-time duration time)
               easing
               (e/tween start end)))))

(defn tween-frame
  ""
  [{:keys [cursor] :as tween} start-time time]
  (reset! cursor (tween-value tween start-time time)))

(defn make-tweener [sched tweens start-time]
  (let [tweens (atom tweens)]
    (fn inner [time]
      (let [delta (- time start-time)
            ts @tweens]
        (when (seq ts)
          (let [[expired keep] (partition-tweens ts delta)]
            (reset! tweens keep)
            (doseq [t ts]
              (tween-frame t start-time time))
            (doseq [{:keys [cursor at-end] :as t} expired]
              (and at-end (at-end cursor)))
            ;; reset the cursor with only the tweens that have not expired
            (when (seq keep)
              (sched inner))))))))

(defn tween
  [sched & tweens]
  (when (seq tweens)
    (let [tweens (atom (sort-by :duration tweens))]
      (sched (partial make-tweener sched tweens)))))
