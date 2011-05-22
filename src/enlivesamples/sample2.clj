(ns enlivesamples.sample2
  (:require [net.cgrand.enlive-html :as html])
  (:use [net.cgrand.moustache :only [app]]
        [enlivesamples.utils :only [run-server render-to-response]]))

;; ----------------------------------------------------------------------------
;; Code adapted from https://github.com/swannodette/enlive-tutorial
;; ----------------------------------------------------------------------------

(defn rgb-to-web
  "Convert a RGB triplet to a web color (ie #02ff5a)"
  [[r g b]] (apply str "#"
                 (map #(let [hex (Integer/toHexString %)]
                         (if (= (.length hex) 1)
                           (str "0" hex)
                           hex))
                      [r g b])))

(html/deftemplate index "enlivesamples/sample1.html"
  [{:keys [color]}]
  [:p#message] (html/set-attr :style (str "background-color:" color)))

;; ========================================
;; The App
;; ========================================

(def routes
  (app
   [""]       (fn [req] (render-to-response
                        (index {:color (rgb-to-web (map (fn [x] (rand-int 256))
                                                        (range 3)))})))
   [&]        {:status 404
               :body "Page Not Found"}))
 
(defonce *server* (run-server routes))
