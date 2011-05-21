(ns enlivesamples.sample1
  (:require [net.cgrand.enlive-html :as html])
  (:use [net.cgrand.moustache :only [app]]
        [enlivesamples.utils :only [run-server render-to-response]]))

;; ----------------------------------------------------------------------------
;; Code adapted from https://github.com/swannodette/enlive-tutorial
;; ----------------------------------------------------------------------------

(html/deftemplate index "enlivesamples/sample1.html"
  [ctxt]
  [:p#message] (html/content (:message ctxt)))

;; ========================================
;; The App
;; ========================================

(def routes
  (app
   [""]       (fn [req] (render-to-response
                        (index {:message (str "Date is: " (java.util.Date.))})))
   [&]        {:status 404
               :body "Page Not Found"}))
 
(defonce *server* (run-server routes))

'(do (require 'enlivesamples.sample1)
    (in-ns 'enlivesamples.sample1)
    (use ['clojure.contrib.repl-utils :only ['show]]
         ['clojure.test :only '[run-tests]]
         ['clojure.repl]
         ['enlivesamples.utils]))
