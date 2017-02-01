; Clojure Mouth & Open Yer Eyes(script)
;   or, a whistlestop tour of clojurescript, by Sam Marshall
;
; buzzwords bit!
;
; clojurescript is a compile-to-javascript functional language.
;  "pragmatic" pure anyway - pure where it counts!
;
; it has a focus on immutable, persistent data structures
;
; it's also a lisp, a list processing language. Everything is expressed as lists.
; you'll get used to it. In fact, programs are entirely a composition of expressions,
; creating a tree-like structure that helps the compiler to evaluate it
;
; going to focus a little on vec manipulation today - it's super useful and
; I can't show you *everything* clojurescript has to offer - but it should
; be a good taste!
;
; lisp fact! The creator of lisp, John McCarthy is known as 'The Grandfather of AI'
; - and the teacher of the original 'Hackers'
;
; and this is a namespace - they open a file...
(ns clojurescript-talk.core)

; our first list! (ignoring the namespace)
;
; Keep in mind that everything is an expression
;
; 1. variadic!
; 2. + is a fn!
; 3. fn goes first in list, rest of list are args
;
;/- fn
;v v- arguments...
(+ 1 2)
(+ 1 2 3)
(+ 1 2 3 4 5 6 7 8 9)
(+ 283 939 23 2832439 29 9 9 9 9 9 9 9 9 9 9 9 9 9 99 99 9 9 99 9 9 99388)
; also, commas are whitespace
; (whitespace are commas?)
(+ 1, 2, 3),,,,,,,,,,,,,,,,,

; lisp fact! fns are a 1st class type in lisp (like in JS - JS was heavily inspired by a lisp, scheme)
;
; macros change the structure of the language - if anything looks out of the
; fn -> args pattern shown above, it's a macro!
;
; def is a macro!
;  |    /- the name
;  v   v      v- a vector (a coll)
(def numvec [2 4 6 8 10])
; def creates a binding between a symbol (name) and a value (the vector)
;  note I didn't say variable!

; lisp fact! symbols don't have types, values do! symbols are just pointers

; reduce of "[1, 2, 3].reduce((a, b) => a + b);" fame
(reduce + numvec)

; We can use a function called 'partial' to make this into a simpler to call fn,
; as fns are 1st class!
(def sum (partial reduce +))

(sum numvec)

; partial application gets used fairly often in JS also.
; you might be used to seeing something like:

; var addTwo = addNumbers.bind(null, 2);

; which would partially apply addNumbers with the argument 2
; - the same as going
;
; var addTwo = (n) => addNumbers(2, n);
;
; in clojurescript, partial can be done in this way:
;
; v- defn is the same as def, but makes a fn
(defn partial'
  "supply a function and an original argument, get a function that will take the rest..."
  [func arg]
  ;    v- this allows us to do those variadic fns - we'll get a coll of args called 'extras'
  (fn [& extras]
    ; note the implicit automatic returns in clojurescript.
    ; in reality, whatever the fn resolves to is the return. Think of the interpreter finding
    ;   the innermost pair of brackets, resolving them to a value, then taking the surrounding brackets...
    (apply func (cons arg extras))))

; so now, we can make a new sum, with our new partial
; note the ' is used to depict something with a slight difference
(def sum' (partial' reduce +))

; bam
(sum' numvec)

; we can use partial for all sorts
(def m2 (partial' * 2))

(m2 8)

; we even have map, like in JS.
; like reduce, it's a normal fn that takes another fn to apply, and a coll
(map m2 numvec)


; but clojurescript doesn't have loops...at least, no for, for/in, while or do loops.
;   so how do map and reduce work?
;
; if you've been keeping up with the ES2015+ specs, you might have seen something
; called tail-call recursion. Recursion, but without stack overflows.
; clojurescript uses this technique. For map, we might do the following...
(defn map'
  "apply a fn to each member of a collection"
  ([fnc args] (map' fnc args []))
  ([fnc args ret] (if (empty? args)
                    ret
                    (map'
                      fnc
                      (rest args)
                      (conj ret (fnc (first args)))))))

; lisp fact! conditionals and lisp were created by John McCarthy

(map' inc [1 2 3 4 5])

; have you noticed the lack of 'state' variables yet?

; interestingly, we can use JS from inside clojurescript if we want
(def xs (array 1 2 3 4 5 6 7))
; array gives us a normal JS array, rather than an immutable one

xs
  (.splice xs 0 3) ; we can even call JS fns on the JS array
xs

; we can call JS globals too
(.log js/console "hello, world" xs)

; lisp fact! McCarthy came up with the concept of Garbage Collection as part of his plan for lisp
