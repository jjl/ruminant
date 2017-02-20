(ns ruminant.colors)

(defprotocol AddAlpha
  (add-alpha [s] [s amount]))

(deftype rgb [r g b]
  Object
  (toString [_]
    (str "rgb(" (int r) "," (int g) "," (int b) ")"))
  #?(:cljs IEquiv)
  (#?(:clj equals :cljs -equiv) [a b]
    (and (instance? rgb b)
         (= (.-r a) (.-r b))
         (= (.-g a) (.-g b))
         (= (.-b a) (.-b b)))))
     
(deftype rgba [r g b a]
  Object
  (toString [_]
    (str "rgba(" (int r) "," (int g) "," (int b) "," a ")"))
  #?(:cljs IEquiv)
  (#?(:clj equals :cljs -equiv) [a b]
    (and (instance? rgba b)
         (= (.-r a) (.-r b))
         (= (.-g a) (.-g b))
         (= (.-b a) (.-b b))
         (= (.-a a) (.-a b)))))

(deftype hsl [h s l]
  Object
  (toString [_]
    (str "hsl(" h "," s "," l ")"))
  #?(:cljs IEquiv)
  (#?(:clj equals :cljs -equiv) [a b]
    (and (instance? hsl b)
         (= (.-h a) (.-h b))
         (= (.-s a) (.-s b))
         (= (.-l a) (.-l b)))))

(deftype hsla [h s l a]
  Object
  (toString [_]
    (str "hsla(" h "," s "," l "," a ")"))
  #?(:cljs IEquiv)
  (#?(:clj equals :cljs -equiv) [a b]
    (and (instance? hsla b)
         (= (.-h a) (.-h b))
         (= (.-s a) (.-s b))
         (= (.-l a) (.-l b))
         (= (.-a a) (.-a b)))))

(extend-protocol AddAlpha
  rgb
  (add-alpha [rgb]
    (->rgba (.-r rgb) (.-g rgb) (.-b rgb) 1))
  hsl
  (add-alpha [hsl]
    (->hsla (.-h hsl) (.-s hsl) (.-l hsl) 1)))
