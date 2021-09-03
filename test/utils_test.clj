(ns utils-test
  (:require [clojure.test :refer [deftest is testing]]
            [utils :refer [process-move game-over? get-winner make-move]]
            [data :refer [empty-square]]))

(def board-with-winner {:a {:owner 1 :count 3} :b empty-square :c empty-square
                        :d empty-square :e empty-square :f empty-square
                        :g empty-square :h empty-square :i {:owner 1 :count 3}})

(def board-without-winner {:a {:owner 1 :count 3} :b empty-square :c empty-square
                        :d empty-square :e empty-square :f empty-square
                        :g empty-square :h empty-square :i {:owner 2 :count 3}})

(def simple-board {:a {:owner 1 :count 3} :b empty-square :c empty-square})
(def simple-move (process-move "ab3"))
(def move-pair [simple-move simple-move])

(deftest process-move-valid-1-digit-count
  (testing "process-move handles a count with 1 digit"
    (is (= (get (process-move "ab3") :count) 3))))

(deftest process-move-valid-2-digits-count
  (testing "process-move handles a count with 2 digits"
    (is (= (get (process-move "ab31") :count) 31))))

(deftest process-move-valid-3-digits-count
  (testing "process-move handles a count with 3 digits"
    (is (= (get (process-move "ab312") :count) 312))))

;; this raises an error which is currently not handled...
;; (deftest process-move-invalid
;;   (testing "process-move handles invalid moves"
;;     (is (= (get (process-move "ab") :count) 0))))

(deftest game-over?-works-when-only-one-team-left
  (testing "game-over? returns true when there is only one team left on the board"
    (is (= (game-over? board-with-winner) true))))

(deftest game-over?-works-when-two-teams-left
  (testing "game-over? returns false when there are two teams left on the board"
    (is (= (game-over? board-without-winner) false))))

(deftest get-winner-works
  (testing "get-winner returns correct team name when there is only one team left on the board"
    (is (= (get-winner board-with-winner) 1))))

(deftest make-moves-simple
  (testing "make-move works in simple case"
    (is (= (make-move simple-board move-pair) {:a {:owner 1 :count 0} :b {:owner 1 :count 3} :c empty-square}))))
