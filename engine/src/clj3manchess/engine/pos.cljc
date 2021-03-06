(ns clj3manchess.engine.pos
  (:require
    [clj3manchess.engine.color :as col]
    #?(:clj [clojure.spec :as sc]
       :cljs [cljs.spec :as sc])
    [schema.core :as s]))

;(defrecord Pos [rank file])
(def Rank (apply s/enum (range 6)))
(sc/def ::rank (sc/and int? #(<= 0 % 5)))
(sc/def ::file (sc/and int? #(<= 0 % 24)))
(sc/def ::fileonsegm (sc/and int? #(<= 0 % 7)))
(def File (apply s/enum (range 24)))
(def FileOnSegm (apply s/enum (range 8)))
(def Pos [(s/one Rank "rank") (s/one File "file")])
(sc/def ::pos (sc/tuple ::rank ::file))

(s/defn rank :- Rank [pos :- Pos] (first pos))
(s/defn file :- File [pos :- Pos] (last pos))

(def kfm 4)

(s/defn color-segm :- col/Color [pos :- Pos] (col/colors (quot (file pos) 8)))

(s/defn pos-on-segm :- Pos [color :- col/Color, rank :- Rank, file-on-segm :- FileOnSegm]
  [rank (+ file-on-segm
           (bit-shift-left (col/segm color) 3))])

(s/defn same-rank :- s/Bool [a :- Pos, b :- Pos] (= (rank a) (rank b)))
(s/defn same-file :- s/Bool [a :- Pos, b :- Pos] (= (file a) (file b)))

(s/defn file-dist :- File [a :- Pos, b :- Pos]
  (let [fileminus (mod (- (file a) (file b)) 24)]
    (cond (> fileminus 12) (- 24 fileminus) :else fileminus)))

(s/defn opposite-file :- s/Bool [a :- Pos, b :- Pos] (= (file-dist a b) 12))

(s/defn same-or-opposite-file :- s/Bool [a :- Pos, b :- Pos] (zero? (mod (file-dist a b) 12)))

;; (defn canIDiagonal [^Pos from ^Pos to] (cond
;;                                      (= from to) {:short false :long false}
;;                                      :else (letfn [(equalsToFile [^int what] (= (.file to) what))
;;                                                    (eqModToFile [^int what] (equalsToFile (mod what 24)))]
;;                                              (cond
;;                                                (equalsToFile ))

(def all-pos
  (->> (range 6)
       (map (fn [rank] (->> (range 24)
                            (map (fn [file] [rank, file])))))
       (apply concat)))
(sc/def all-pos (sc/coll-of ::pos :kind sequential? :count 144 :distinct true))
