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
   :sections [{:title (str "another stuff" (rand-int 100))
               :data (mapcat #(list (hash-map :text (str "text" (rand-int 100) %)))
                             (range 10))}
              {:title (str "denstuff" (rand-int 100))
               :data (mapcat #(list (hash-map :text (str "text" (rand-int 100) %)))
                             (range 10))}]})

;; ----------------------------------------------------------------------------
;; Original code from https://github.com/swannodette/enlive-tutorial
;; ----------------------------------------------------------------------------

; we only want to select a model link

; we only want to select the model h2 ul range
(def *section-sel* {[:.title] [[:.content (nth-of-type 1)]]})

(defsnippet section-model "enlivesamples/sample3.html" *section-sel*
  [{:keys [title data]}]
  [:.title]   (content title))

(deftemplate index "enlivesamples/sample3.html"
  [{:keys [title sections]}]
  [:#title] (content title)
  [:body]   (content (map #(section-model %) sections)))

(def routes
     (app
      [""]  (fn [req] (render-to-response (index *dummy-context*)))
      [&]   page-not-found))

;; ========================================
;; The App
;; ========================================

(defonce *server* (run-server routes))

