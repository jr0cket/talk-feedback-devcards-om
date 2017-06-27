(ns feedback-prototype.core
  (:require
   #_[om.core :as om :include-macros true]
   [sablono.core :as sab :include-macros true]
   [om.next :as om :refer-macros [defui]]
   [om.dom :as dom])
  (:require-macros
   [devcards.core :as dc :refer [defcard deftest]]))

(enable-console-print!)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; A simple devcard to show what they are like without Om

(defcard first-card
  (sab/html [:div
             [:h1 "This is your first devcard!"]
             [:p "Devcards are another awesome project by the author of figwheel, Bruce hauman"]
             [:a {:href "https://github.com/bhauman/devcards"} "devcards github project"]]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Om driven card with self-contained state (map)

(defcard interactive-card
  (fn [state-atom owner]                    ;wrapper function that can accept a state atom
    (dom/div nil "A single top-level element."
             (dom/br nil)
             (dom/br nil)
             (dom/span nil (str "Level of awesomeness: " (:awesome @state-atom)))
             (dom/br nil)
             (dom/span nil (str "Level of goodness: " (:good @state-atom)))
             (dom/br nil)
             (dom/span nil (str "Level of sleepyness: " (:sleepy @state-atom)))
             (dom/br nil)
             (dom/button #js {:onClick #(swap! state-atom update-in [:awesome] inc)} "Awesome")
             (dom/button #js {:onClick #(swap! state-atom update-in [:good] inc)} "Good")
             (dom/button #js {:onClick #(swap! state-atom update-in [:sleepy] inc)} "Sleep")
             ))
  {:awesome 0 :good 0 :sleepy 0}  ; The initial state as a map which devcards creates an atom from
  {:inspect-data true}              ; shows the current value of data, ie. state-atom
  )


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Using an atom for state

(def start-state {:awesome 1 :good 1 :sleepy 1})

(defcard interactive-card-2
  (fn [state-atom owner]                    ;wrapper function that can accept a state atom
    (dom/div nil "A single top-level element."
             (dom/br nil)
             (dom/br nil)
             (dom/span nil (str "Level of awesomeness: " (:awesome @state-atom)))
             (dom/br nil)
             (dom/span nil (str "Level of goodness: " (:good @state-atom)))
             (dom/br nil)
             (dom/span nil (str "Level of sleepyness: " (:sleepy @state-atom)))
             (dom/br nil)
             (dom/button #js {:onClick #(swap! state-atom update-in [:awesome] inc)} "Awesome")
             (dom/button #js {:onClick #(swap! state-atom update-in [:good] inc)} "Good")
             (dom/button #js {:onClick #(swap! state-atom update-in [:sleepy] inc)} "Sleep")
             ))
  state                            ; The initial state as a map which devcards creates an atom from
  {:inspect-data true}              ; shows the current value of data, ie. state-atom
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Om render function

(defn main []
  ;; conditionally start the app based on whether the #main-app-area
  ;; node is on the page
  (if-let [node (.getElementById js/document "main-app-area")]
    (.render js/ReactDOM (sab/html [:div "This is working"]) node)))

(main)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/cards.html














;; (def session-number (atom 0))

;; (current-talk my-talks)


#_(def my-talks [{:title "Introduction to DevCards"
                :abstract "A very gentle introduction to devcards for building simple single pages apps with Clojurescript"
                :speaker "Bruce Hauman"
                :twitter "bhauman"
                :github  "bhauman"
                :slides  "slides"
                :current true}
               {:title "Waiting for Scala"
                :abstract "A very dull demo watching Scala compile"
                :speaker "Andy Pandy"
                :twitter "apandy"
                :github  "apandy"
                :slides  "slides"
                :current false}
               {:title "Functional Web & Games"
                :abstract "Creating a wonderful functional web app and cool functional games"
                :speaker "Cozi Fantooti"
                :twitter "cfanti"
                :github  "cfanti"
                :slides  "slides"
                :current false}])

#_(defn current-talk [talks]
    (filter #(= (:current %) true) talks))

#_(defcard talk
    (let [current-talk (current-talk my-talks)])
    (sab/html [:div
               :h1 (str (get current-talk :title))]))
