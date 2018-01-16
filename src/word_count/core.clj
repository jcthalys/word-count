(ns word-count.core
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.core.reducers :as r])
  (:gen-class))



(defn read-lines [name]
  (let [reader (io/reader name)]
    (line-seq reader)))



(defn read-words [lines]
  (mapcat #(re-seq #"\w+" %) lines))


(def count-chards-fn (fn [ac word]
                       (+ (count word) ac)))



(defmulti word-count (fn [flag _] flag) :default "--parallel")



(defmethod word-count "--all" [_ name]
  (let [lines (read-lines name)
        words (read-words lines)]
    (str "line:" (count lines)
         ", words:" (count words)
         ", chars:" (reduce count-chards-fn 0 words))))



(defmethod word-count "--frequencies" [_ name]
  (let [lines (read-lines name)
        words (read-words lines)]

    (->> (frequencies words)
         (sort-by (fn [[token freq]] [(- freq) token]))
         (map (fn [[token freq]]
                [token freq]))
         (string/join))))


(defmethod word-count "--parallel" [_ name]
  (->> (read-lines name)
       (r/map (fn [line-str]
                (let [val (->> line-str
                               (re-seq #"\w+")
                               (count))]
                  val)))
       (r/fold +)))



(defn -main
  [& [flag fname]]
  (println (if (and flag fname)
             (word-count flag fname)
             (word-count nil flag))))
