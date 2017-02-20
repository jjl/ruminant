(set-env!
  :resource-paths #{"src"}
  :source-paths #{"src"}
  :dependencies '[[org.clojure/clojure "1.9.0-alpha14"]
                  [org.clojure/clojurescript "1.9.473"]
                  [irresponsible/spectra "0.1.0"]
                  [rum "0.10.7"] ; react
                  [datascript "0.15.5"]
                  ;; [better-cond "2.0.0-SNAPSHOT"]
                  [doo "0.1.7" :scope "test"]
                  [binaryage/devtools             "0.8.2"          :scope "test"]
                  [binaryage/dirac                "0.6.6"          :scope "test"]
                  [org.clojure/tools.nrepl        "0.2.12"         :scope "test"]
                  [com.cemerick/piggieback        "0.2.1"          :scope "test"]
                  [weasel                         "0.7.0"          :scope "test"]
                  [powerlaces/boot-figreload      "0.1.0-SNAPSHOT" :scope "test"]
                  [adzerk/boot-cljs               "2.0.0-SNAPSHOT" :scope "test"]
                  [adzerk/boot-test               "1.2.0"          :scope "test"]
                  [crisptrutski/boot-cljs-test    "0.3.0"          :scope "test"]
                  [adzerk/boot-cljs-repl          "0.3.3"          :scope "test"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[crisptrutski.boot-cljs-test :refer [test-cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[dirac.nrepl]
         '[adzerk.boot-test :as t]
         '[powerlaces.boot-figreload :refer [reload]])

(task-options!
 pom {:project 'irresponsible/ruminant
      :version "0.1.0"}
 repl {:port 7001 :middleware '[dirac.nrepl/middleware]}
 target {:dir #{"target"}})

(deftask testing []
  (set-env! :source-paths   #(conj % "test")
            :resource-paths #(conj % "test"))
  identity)

(deftask dev []
  (testing)
  (comp
   (repl :server true)
   (cljs-repl :nrepl-opts {:port 7002})
   (watch)
   (notify)
   (reload :client-opts {:debug true})
   ;; (cljs-repl)
   (cljs :source-map true :optimizations :none :ids #{"dev"})
   (t/test)
   (test-cljs)
   (target)))
   
;; (require '[powerlaces.boot-figreload.server :as bfs])
;; (bfs/stop)
;; (deftask prod []
;;   (comp
;;    (cljs :ids #{"main"} :optimizations :advanced)
;;    (pom)
;;    (jar)))


