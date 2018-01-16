(ns word-count.core-test
  (:require [clojure.test :refer :all]
            [word-count.core :refer :all]))

(deftest word-count-all-test
  (is (= "line:1, words:6, chars:30"
         (word-count "--all" "temp.txt"))))


(deftest word-count-frequencies-test
  (is (= "[\"nice\" 2][\"Really\" 1][\"Very\" 1][\"counting\" 1][\"very\" 1]"
         (word-count "--frequencies" "temp.txt"))))


(deftest word-count-parallel-test
  (is (= 586914
         (word-count "--parallel" "war-and-peace-full-book.txt"))))