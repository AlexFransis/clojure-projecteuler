(ns projecteuler.functions)

;;---------------------------------------------------------------------
;; USEFUL FUNCTIONS
;;---------------------------------------------------------------------

(def fibo
  "Returns a lazy Fibonacci sequence"
  ((fn rfib [a b]
     (lazy-seq (cons a (rfib b (+ a b)))))
   0 1))

(defn prime?
  "Checks if number is prime"
  [n]
  (if (<= n 1)
    false
    (or (= 2 n)
        (not-any? #(zero? (mod n %))
                  (cons 2 (range 3 (inc (Math/sqrt n)) 2))))))

(defn nth-prime
  "Takes the nth prime number"
  [n]
  (->> (iterate #(+ % 2) 3)
       (cons 2)
       (filter #(prime? %))
       (take n)
       (last)))

(defn palindrome?
  "Checks if the number is a palindrome"
  [n]
  (= (seq (str n)) (->> n
                        (str)
                        (seq)
                        (reverse))))

(defn digits-of
  "Takes a number and returns a list of all its digits"
  [n]
  (map #(Integer/parseInt (str %)) (str n)))
