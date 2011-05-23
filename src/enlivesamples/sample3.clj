(ns enlivesamples.sample3
  (:use [net.cgrand.enlive-html
         :only [deftemplate defsnippet content clone-for
                nth-of-type first-child do-> set-attr sniptest at emit*]]
        [net.cgrand.moustache :only [app]]
        [enlivesamples.utils :only [run-server render-to-response page-not-found]]))

;; =============================================================================
;; Dummy Data
;; =============================================================================

(def *dummy-context*
  {:title "Enlive Sample 3"
   :sections [{:title "denstuff"
               :data (mapcat #(list (hash-map :text (str "text" (rand-int 100) %)))
                             (range 10))}]})

;; ----------------------------------------------------------------------------
;; Original code from https://github.com/swannodette/enlive-tutorial
;; ----------------------------------------------------------------------------

; we only want to select a model link
; TODO understand this
(def *link-sel* [[:.content (nth-of-type 1)] :> first-child])

(defsnippet link-model "enlivesamples/sample3.html" *link-sel*
  [{:keys [text href]}]
  [:a] (do->
        (content text)))

; we only want to select the model h2 ul range
(def *section-sel* {[:.title] [[:.content (nth-of-type 1)]]})

(defsnippet section-model "enlivesamples/sample3.html" *section-sel*
  [{:keys [title data]} model]
  [:.title]   (content title)
  [:.content] (content (map model data)))

(deftemplate index "enlivesamples/sample3.html"
  [{:keys [title sections]}]
  [:#title] (content title)
  [:body]   (content (map #(section-model % link-model) sections)))

(def routes
     (app
      [""]  (fn [req] (render-to-response (index *dummy-context*)))
      [&]   page-not-found))

;; ========================================
;; The App
;; ========================================

(defonce *server* (run-server routes))

