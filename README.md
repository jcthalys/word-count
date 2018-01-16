# word-count

A Clojure library designed to a job test to Clojure Developer

## Usage

To execute the tests in the project, execute this command in the project folder
```shell
echo "Very nice counting. Really very nice!" > temp.txt
```
and
```shell
curl http://www.gutenberg.org/files/2600/2600-0.txt > war-and-peace-full-book.txt
```
Then you could execute 
`lein do clean, test`

Using with parameters:
After compile `lein do clean, uberjar`

```shell
java -jar target/word-count.jar temp.txt           
6

java -jar target/word-count.jar --all temp.txt
line:1, words:6, chars:30

java -jar target/word-count.jar --frequencies temp.txt
["nice" 2]["Really" 1]["Very" 1]["counting" 1]["very" 1]

java -jar target/word-count.jar --parallel war-and-peace-full-book.txt 
586914
```

Comparing with this book file, that was used in the description. I tried some different solutions with parallelism and the execution time compared with this one more simple was very similar.

Options:

```clojure
(time
  (->> (iota/seq "war-and-peace-full-book.txt")
      (r/mapcat #(re-seq #"\w+" %))
      (r/fold + (fn [a words]
                  (if words
                    (+ a (count words)) a)))))
"Elapsed time: 69.185075 msecs"
=> 2522715 ;wrong result

(time (->> (io/reader "war-and-peace-full-book.txt")
           (line-seq)
           (pmap (fn [line]
                   (re-seq #"\w+" line)))
           (r/fold + (fn [a words]
                       (if words
                         (+ a (count words)) a))))
      )
"Elapsed time: 228.528501 msecs"

(time (->> (io/reader "war-and-peace-full-book.txt")
           (line-seq)
           (pmap (fn [line]
                   (re-seq #"\w+" line)))
           (r/fold + (fn [a words]
                       (if words
                         (+ a (count words)) a))))
      )
"Elapsed time: 204.105477 msecs" 
```

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
