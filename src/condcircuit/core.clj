(ns condcircuit.core)

(defn third
  [coll]
  (get coll 2))

(defn fourth
  [coll]
  (get coll 3))

(defmacro condcircuit
  "Condcircuit is a circuit"
  [& forms]
  (let [final (last forms)]
    (loop [forms (reverse (butlast forms)) acc final]
      (let [f (first forms)]
        (if f
          (recur
            (rest forms)
            `(let [~(symbol (first f)) ~(second f)]
               (if ~(or (third f) nil)
                 ~(fourth f)
                 ~acc)))
          acc)))))
