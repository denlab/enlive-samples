(ns enlivesamples.test.sample2
  (:use [enlivesamples.sample2])
  (:use [clojure.test]))

(deftest test-rgb-to-web
  (is (= "#010203" (rgb-to-web [1 2 3])))
  (is (= "#0a0b0c" (rgb-to-web [10 11 12])))
  (is (= "#aabbcc" (rgb-to-web [170 187 204]))))
