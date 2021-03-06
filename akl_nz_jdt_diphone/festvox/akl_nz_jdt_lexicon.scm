;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;                                                                     ;;;
;;;                     Carnegie Mellon University                      ;;;
;;;                  and Alan W Black and Kevin Lenzo                   ;;;
;;;                      Copyright (c) 1998-2000                        ;;;
;;;                        All Rights Reserved.                         ;;;
;;;                                                                     ;;;
;;; Permission is hereby granted, free of charge, to use and distribute ;;;
;;; this software and its documentation without restriction, including  ;;;
;;; without limitation the rights to use, copy, modify, merge, publish, ;;;
;;; distribute, sublicense, and/or sell copies of this work, and to     ;;;
;;; permit persons to whom this work is furnished to do so, subject to  ;;;
;;; the following conditions:                                           ;;;
;;;  1. The code must retain the above copyright notice, this list of   ;;;
;;;     conditions and the following disclaimer.                        ;;;
;;;  2. Any modifications must be clearly marked as such.               ;;;
;;;  3. Original authors' names are not deleted.                        ;;;
;;;  4. The authors' names are not used to endorse or promote products  ;;;
;;;     derived from this software without specific prior written       ;;;
;;;     permission.                                                     ;;;
;;;                                                                     ;;;
;;; CARNEGIE MELLON UNIVERSITY AND THE CONTRIBUTORS TO THIS WORK        ;;;
;;; DISCLAIM ALL WARRANTIES WITH REGARD TO THIS SOFTWARE, INCLUDING     ;;;
;;; ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS, IN NO EVENT  ;;;
;;; SHALL CARNEGIE MELLON UNIVERSITY NOR THE CONTRIBUTORS BE LIABLE     ;;;
;;; FOR ANY SPECIAL, INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES   ;;;
;;; WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN  ;;;
;;; AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION,         ;;;
;;; ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF      ;;;
;;; THIS SOFTWARE.                                                      ;;;
;;;                                                                     ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;; Lexicon, LTS and Postlexical rules for akl_nz
;;;

;;; Load any necessary files here

(define (akl_nz_addenda)
  "(akl_nz_addenda)
Basic lexicon should (must ?) have basic letters, symbols and punctuation."

;;; Pronunciation of letters in the alphabet
;(lex.add.entry '("a" nn (((a) 0))))
;(lex.add.entry '("b" nn (((b e) 0))))
;(lex.add.entry '("c" nn (((th e) 0))))
;(lex.add.entry '("d" nn (((d e) 0))))
;(lex.add.entry '("e" nn (((e) 0))))
; ...

;;; Symbols ...
;(lex.add.entry 
; '("*" n (((a s) 0) ((t e) 0) ((r i1 s) 1)  ((k o) 0))))
;(lex.add.entry 
; '("%" n (((p o r) 0) ((th i e1 n) 1) ((t o) 0))))

;; Basic punctuation must be in with nil pronunciation
(lex.add.entry '("." punc nil))
;(lex.add.entry '("." nn (((p u1 n) 1) ((t o) 0))))
(lex.add.entry '("'" punc nil))
(lex.add.entry '(":" punc nil))
(lex.add.entry '(";" punc nil))
(lex.add.entry '("," punc nil))
;(lex.add.entry '("," nn (((k o1) 1) ((m a) 0))))
(lex.add.entry '("-" punc nil))
(lex.add.entry '("\"" punc nil))
(lex.add.entry '("`" punc nil))
(lex.add.entry '("?" punc nil))
(lex.add.entry '("!" punc nil))

;; abreviations 
 (lex.add.entry 
   '( "awb" n ((( ei ) 1) ((d uh) 1) ((b @ l) 0) ((y uu) 0) ((b ii) 1))))
(lex.add.entry 
   '( "Jr" n (((jh uu) 1) ((n y @) 0)))) 
  (lex.add.entry 
   '("khz" n (((k i) 1) ((l ou) 0) ((h @@r t z) 1))))
  (lex.add.entry 
   '("reuter" n (((r oi) 1) ((t @@r) 0))))
)



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;; Hand written letter to sound rules
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; This section has been modified by Sophie 27/07

;;;  Function called when word not found in lexicon
;;(define (akl_nz_lts_function word features)
;;  "(akl_nz_lts_function WORD FEATURES)
;;Return pronunciation of word not in lexicon."
;;  (format stderr "failed to find pronunciation for %s\n" word)
;;  (let ((dword (downcase word)))
;;    ;; Note you may need to use a letter to sound rule set to do
;;    ;; casing if the language has non-ascii characters in it.
;;    (if (lts.in.alphabet word 'akl_nz)
;;	(list
;;	 word
;;	 features
;;	 ;; This syllabification is almost certainly wrong for
;;	 ;; this language (its not even very good for English)
;;	 ;; but it will give you something to start off with
;;	 (lex.syllabify.phstress
;;	   (lts.apply word 'akl_nz)))
;;	(begin
;;	  (format stderr "unpronouncable word %s\n" word)
;;	  ;; Put in a word that means "unknown" with its pronunciation
;;	  '("meh" nil (((m e) 0))))
;;))
;;)

(defvar oaldlexdir (path-append lexdir "oald"))

(require 'pos)



(define (akl_nz_lts_function word feats)
  "( akl_nz_lts_function word feats)
Function called for OALD when word is not found in lexicon.  Uses
LTS rules trained from the original lexicon, and lexical stress
prediction rules."
  (require 'lts)
  (if (not (boundp 'oald_lts_rules))
      (load (path-append oaldlexdir "oald_lts_rules.scm")))
  (let ((dcword (downcase word))
	(syls) (phones))
    (if (string-matches dcword "[a-z]*")
	(begin
	  (set! lts_pos feats)
	  (set! phones (lts_predict dcword oald_lts_rules))
	  (set! syls (lex.syllabify.phstress phones))
	  )
	(set! syls nil))
    (list word nil syls)))




;; You may or may not be able to write a letter to sound rule set for
;; your language.  If its largely lexicon based learning a rule
;; set will be better and easier that writing one (probably).
(lts.ruleset
 akl_nz
 (  (Vowel WHATEVER) )
 (
  ;; LTS rules 
  ))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;; Postlexical Rules 
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (akl_nz::postlex_rule1 utt)
  "(akl_nz::postlex_rule1 utt)
A postlexical rule form correcting phenomena over word boundaries."
  (mapcar
   (lambda (s)
     ;; do something
     )
   (utt.relation.items utt 'Segment))
   utt)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;; Lexicon definition
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(lex.create "akl_nz")
(lex.set.phoneset "akl_nz")
(lex.set.lts.method 'akl_nz_lts_function) ;; Sophie comment
;;(lex.set.lts.method 'f2b_lts)
;;above added by Alex Igic
;;; If you have a compiled lexicon uncomment this
(lex.set.compile.file (path-append akl_nz_jdt_dir "festvox/nzlex.out"))
(akl_nz_addenda)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;; Lexicon setup
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (akl_nz_jdt::select_lexicon)
  "(akl_nz_jdt::select_lexicon)
Set up the lexicon for akl_nz."
  (lex.select "akl_nz")

  ;; Post lexical rules
  (set! postlex_rules_hooks (list akl_nz::postlex_rule1))
)

(define (akl_nz_jdt::reset_lexicon)
  "(akl_nz_jdt::reset_lexicon)
Reset lexicon information."
  t
)

(provide 'akl_nz_jdt_lexicon)

