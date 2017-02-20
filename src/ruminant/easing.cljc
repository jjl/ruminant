(ns ruminant.easing
  (:require [ruminant.colors :as c #?@(:cljs [:refer [rgb rgba hsl hsla]])])
  #?(:clj (:import [ruminant.colors rgb rgba hsl hsla])))

(def linear identity)
(def backwards (partial - 1))

(defmulti tween
  "Given a start value, an end value and an abstract (float 0-1) factor,
   returns the appropriate inbetween value
   args: [start end factor]
   returns: value"
  (fn [start end _]
    [(type start) (type end)]))

;; i suspect the closure compiler is more likely to DCE these if we move them to colors
;; it's hard to prove a defmethod will not be accessed, but easy to prove a defmulti won't
;; experimentation is needed!

(defmethod tween [rgb rgba]
  [start end val]
  (tween (c/add-alpha start) end val))

(defmethod tween [rgba rgb]
  [start end val]
  (tween start (c/add-alpha end) val))

(defmethod tween [rgb rgb]
  [start end val]
  (let [r (tween (.-r start) (.-r end) val)
        g (tween (.-g start) (.-g end) val)
        b (tween (.-b start) (.-b end) val)]
    (rgb. r g b)))

(defmethod tween [rgba rgba]
  [start end val]
  (let [r (tween (.-r start) (.-r end) val)
        g (tween (.-g start) (.-g end) val)
        b (tween (.-b start) (.-b end) val)
        a (tween (.-a start) (.-a end) val)]
    (rgba. r g b a)))

(defmethod tween [hsl hsla]
  [start end val]
  (tween (c/add-alpha start) end val))

(defmethod tween [hsla hsl]
  [start end val]
  (tween start (c/add-alpha end) val))

(defmethod tween [hsl hsl]
  [start end val]
  (let [h (tween (.-h start) (.-h end) val)
        s (tween (.-s start) (.-s end) val)
        l (tween (.-l start) (.-l end) val)]
    (hsl. h s l)))

(defmethod tween [hsla hsla]
  [start end val]
  (let [h (tween (.-h start) (.-h end) val)
        s (tween (.-s start) (.-s end) val)
        l (tween (.-l start) (.-l end) val)
        a (tween (.-a start) (.-a end) val)]
    (hsla. h s l a)))

(defmethod tween :default
  [start end val]
  (when-not (every? number? [start end val])
    (throw (ex-info "don't know how to tween these" {:start start :end end :val val})))
  (+ start (* val (- end start))))
    
