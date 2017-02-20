(ns ruminant.scale)

(defn linear->abstract
  "Turns an absolute position in a fully open linear range into an
   abstract factor linear between 0 and 1 representing the position
   args: [start range val]
     range: end - start
   returns: float between 0 and 1"
  [start range val]
  (/ (- val start) range))

(defn abstract->linear
  "Turns an abstract factor (float 0-1) into an absolute value
   linear within the given (fully open) range
   args: [start range val]
     range: end - start
   returns: absolute position"
  [start range val]
  (+ start (* range val)))

;; (defn log->abstract [])
;; (defn abstract->log [])
