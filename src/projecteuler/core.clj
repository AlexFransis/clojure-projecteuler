(ns projecteuler.core)

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

(defn square
  "Squares the number"
  [x]
  (* x x))

(defn palindrome?
  "Checks if the number is a palindrome"
  [n]
  (= (seq (str n)) (->> n
                        (str)
                        (seq)
                        (reverse))))

;;---------------------------------------------------------------------
;; Problem 1 -- Sum of multiples of 3 or 5 under 1000
;;---------------------------------------------------------------------

(->> (range 1000)
     (filter #(or (zero? (mod % 3))
                  (zero? (mod % 5))))
     (reduce +))

;;---------------------------------------------------------------------
;; Problem 2 -- Sum of even Fibonacci numbers under 4 million
;;---------------------------------------------------------------------

(loop [x 1]
  (if (< 4000000 (->> (take-nth 3 fibo)
                      (take x)
                      (last)))
    (->> (take-nth 3 fibo)
         (take (dec x))
         (reduce +))
    (recur (inc x))))

;;---------------------------------------------------------------------
;; Problem 3 -- Largest prime factor of 600851475143
;;---------------------------------------------------------------------

(loop [i 2 number 600851475143]
 (if (< i number)
  (if (zero? (mod number i))
    (recur i (/ number i))
    (recur (inc i) number))
  i))

;;---------------------------------------------------------------------
;; Problem 4 -- Largest palindrome product
;;---------------------------------------------------------------------

(def three-digits-multiples
  (for [x1 (range 100 1000)
        x2 (range 100 1000)]
    (* x1 x2)))

(apply max (filter palindrome? three-digits-multiples))

;;---------------------------------------------------------------------
;; Problem 6 -- Sum square difference
;;---------------------------------------------------------------------


(- (square (+ (/ (* 100 100) 2) (/ 100 2)))
   (->> (range 1 101)
        (map square)
        (reduce +)))

;;---------------------------------------------------------------------
;; Problem 7 -- What is the 10001st prime?
;;---------------------------------------------------------------------

(->> (iterate #(+ % 2) 3)
     (cons 2)
     (filter #(prime? %))
     (take 10001)
     (last))

;;---------------------------------------------------------------------
;; Problem 8 -- Largest product in a series
;;---------------------------------------------------------------------

(def large-number 7316717653133062491922511967442657474235534919493496983520312774506326239578318016984801869478851843858615607891129494954595017379583319528532088055111254069874715852386305071569329096329522744304355766896648950445244523161731856403098711121722383113622298934233803081353362766142828064444866452387493035890729629049156044077239071381051585930796086670172427121883998797908792274921901699720888093776657273330010533678812202354218097512545405947522435258490771167055601360483958644670632441572215539753697817977846174064955149290862569321978468622482839722413756570560574902614079729686524145351004748216637048440319989000889524345065854122758866688116427171479924442928230863465674813919123162824586178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450)

(defn drop-first
  "Drops the first number in a series and returns the series"
  [x]
  (as-> x n
       (str n)
       (seq n)
       (rest n)
       (apply str n)
       (if-not (clojure.string/blank? n)
         (bigint n)
         n)))

(defn digit-product
  "Returns the product of its digits"
  [x]
  (->> x
       (str)
       (seq)
       (map #(Character/digit % 10))
       (reduce *)))

(defn current-digits
  "Takes a large number and returns the first 13 digits"
  [x]
  (->> x
       (str)
       (seq)
       (take 13)
       (apply str)
       (Long.)))

(loop [x 0
       y large-number]
  (if-not (clojure.string/blank? (str y))
    (recur (if (< (digit-product x)
                  (digit-product (current-digits y)))
             (current-digits y)
             x)
           (drop-first y))
    (digit-product x)))