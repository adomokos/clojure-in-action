(ns cookbook.01-primitives-test-02
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [inflections.core :as inf]
            [cookbook.01-primitives :refer :all]))

(deftest formatting-strings-test
  (testing "with format"
    (is (= "042-my-awesome-file.txt"
           (filename "my-awesome-file.txt" 42)))))

(deftest searching-a-string-by-pattern
  (testing "finding part of a string"
    (is (= "my" (re-find #"\w+" "my-param"))))
  (testing "matching the entire string"
    (is (= nil (re-matches #"\w+" "my-param")))
    (is (= "justLetters" (re-matches #"\w+" "justLetters")))))

(deftest finding-and-replacing-strings
  (testing "simple replace"
    (is (= "My favorite color is red!"
           (str/replace "My favorite color is green!" "green" "red")))))

(deftest splitting-strings-into-parts
  (testing "split by comma"
    (is (= ["HEADER1" "HEADER2" "HEADER3"]
           (str/split "HEADER1,HEADER2,HEADER3" #","))))
  (testing "split by spaces"
    (is (= ["Spaces" "Newlines"]
           (str/split "Spaces    Newlines\n\n" #"\s+"))))
  (let [delimiter #"[ :-]"]
    (testing "using multiple delimiters"
      (is (= ["2013" "04" "05" "14" "39"]
             (str/split "2013-04-05 14:39" delimiter))))
    (testing "using multiple delimiters with limit"
      (is (= ["2013" "04-05 14:39"]
             (str/split "2013-04-05 14:39" delimiter 2))))))

(deftest test-inflections
  (are [amount string pluralized]
    (= (inf/pluralize amount string) pluralized)
      1 "monkey" "1 monkey"
      2 "monkey" "2 monkeys"
      1 "milk" "1 milk"
      2 "milk" "2 milk")
  (is (= "MyObject" (inf/camel-case "my_object")))
  (is (= "my-most-favorite-url" (inf/parameterize "My most favorite url!")))
  (is (= "42nd" (inf/ordinalize 42))))
