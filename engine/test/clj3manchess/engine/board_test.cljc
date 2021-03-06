(ns clj3manchess.engine.board-test
  (:require [clj3manchess.engine.board :as b]
            #?(:clj [clojure.test :as t]
               :cljs [cljs.test :as t :include-macros true])
            #?(:clj [clojure.spec.test :as stest]
               :cljs [cljs.spec.test :as stest])))

;;(stest/instrument `b/string-of-arrayboard)

(defonce string-of-newgame-board
  "[[__ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __] 
 [__ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __] 
 [__ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __] 
 [__ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __] 
 [P_ P_ P_ P_ P_ P_ P_ P_ ^_ ^_ ^_ ^_ ^_ ^_ ^_ ^_ p_ p_ p_ p_ p_ p_ p_ p_] 
 [R  N  B  Q  K  B  N  R  #  ń  |  Ω  ¥  |  ń  #  r  n  b  q  k  b  n  r ]]")

(t/deftest newgame-map []
  (t/is (= (b/string-of-arrayboard (b/fill-array-board (b/fill-map-board ::b/newgame))) string-of-newgame-board))
  (t/is (= (b/getb ::b/newgame [0 0]) {:type :rook :color :white}))
  (t/is (= (b/string-of-arrayboard (b/fill-array-board ::b/newgame)) string-of-newgame-board)))

(t/deftest newgame-where-are-bishops []
  (t/is (b/where-are-figs-of-type ::b/newgame :bishop) '([0 2] [0 5] [0 10] [0 13] [0 18] [0 21])))

(t/deftest newgame-figs-white []
  (t/is (vec (b/where-are-figs-of-color ::b/newgame :white))
        [[0 0] [0 1] [0 2] [0 3] [0 4] [0 5] [0 6] [0 7] [1 0] [1 1] [1 2] [1 3] [1 4] [1 5] [1 6] [1 7]]))
