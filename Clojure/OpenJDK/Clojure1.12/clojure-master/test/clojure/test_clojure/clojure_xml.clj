;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

;;Author: Frantisek Sodomka


(ns clojure.test-clojure.clojure-xml
  (:use clojure.test)
  (:require [clojure.xml :as xml])
  (:import [java.io ByteArrayInputStream]))

(deftest CLJ-2611-avoid-XXE
  (let [xml-str "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>
<!DOCTYPE foo [
  <!ELEMENT foo ANY >
  <!ENTITY xxe SYSTEM \"file:///etc/hostname\" >]>
<foo>&xxe;</foo>"]
    (is (= {:tag :foo, :attrs nil, :content nil}
           (with-open [input (ByteArrayInputStream. (.getBytes xml-str))]
             (xml/parse input))))))
; parse

; emit-element
; emit

