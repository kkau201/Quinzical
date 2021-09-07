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
;;; Tokenizer for nz
;;;
;;;  To share this among voices you need to promote this file to
;;;  to say festival/lib/akl_nz/ so others can use it.
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;; Load any other required files

;;; Voice/nz token_to_word rules 
;;(define (akl_nz_jdt::token_to_words token name)
;;  "(akl_nz_jdt::token_to_words token name)
;;Specific token to word rules for the voice akl_nz_jdt.  Returns a list
;;of words that expand given token with name."
;;  (cond
;;   ((string-matches name "[1-9][0-9]+")
;;    (akl_nz::number token name))
;;   (t ;; when no specific rules apply do the general ones
;;    (list name))))
;;


(define (akl_nz::number token name)
  "(akl_nz::number token name)
Return list of words that pronounce this number in nz."

  (error "akl_nz::number to be written\n")

)

(define (akl_nz_jdt::select_tokenizer)
  "(akl_nz_jdt::select_tokenizer)
Set up tokenizer for nz."
  (Parameter.set 'Language 'akl_nz)

  ;;(set! token_to_words akl_nz_jdt::token_to_words)
  ;;(CW 14/6/2012) THis is the token rules from the main directory - as used by kal etc voices
  (set! token_to_words english_token_to_words)
)

(define (akl_nz_jdt::reset_tokenizer)
  "(akl_nz_jdt::reset_tokenizer)
Reset any globals modified for this voice.  Called by 
(akl_nz_jdt::voice_reset)."
  ;; None

  t
)

(provide 'akl_nz_jdt_tokenizer)
