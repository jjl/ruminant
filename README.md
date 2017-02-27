# Ruminant

## Usage

A tween is defined as:

```clojure
{:start 100
 :end 200
 :cursor <rum-cursor>
 :duration 100 ;; ms
 ;; Optional
 :on-end #(println "Tween ended")}
```

Here's an example of how to define a set of tweens, using the Javascript `requestAnimationFrame` as 
the scheduler.

```clojure
(require '[ruminant.anim :as anim])

(anim/tween 
 anim/js-raf 
 [{:start 100
   :end 200
   :cursor (atom nil)
   :duration 100}
  {:start 0
   :end 300
   :cursor (atom nil)}
   :duration 50])
```

## LICENSE

[MIT](LICENSE)
