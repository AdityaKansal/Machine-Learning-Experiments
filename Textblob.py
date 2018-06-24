# -*- coding: utf-8 -*-
"""
Created on Wed Jun 13 16:07:51 2018

@author: akansal2
"""
#importing libraies
from textblob import TextBlob


#TextBlob Strings
Str1 = TextBlob('Amazing')
Str2 = TextBlob('Spider Man')

#Textblob string operations
Str1.lower()
Str1.upper()
Str1[1:4]
Str1 + " " + Str2
Str1.detect_language()



#Paragraph and sentence operations
para = TextBlob("My name is aditya. \n I live is  Modinagar.\n My apples id is kansal_aditya@optum.com")
para.sentences  # distinguish sentences with combination of . and \n
para.sentences[0]
para.sentences[1]
para.sentences[2]
para.sentences[0].words
for n in para.sentences[1].noun_phrases:
    print(n)
for t in para.sentences[1].tags:
    print(t)
    
print(para.sentences[2].words[1].singularize())



#Word operation
from textblob import Word
Word('horsases').singularize()
w = Word('better')
w.singularize()
w.pluralize()
w.correct()
w.lemmatize('a')
w = Word('horss')
w.spellcheck()



#ngrams
blob = TextBlob('I live in Modinagar')
print(blob.ngrams(2))
print(blob.ngrams(3))



#sentiments
blob = TextBlob('you are wise man')
blob.sentiment




#translate
blob = TextBlob('how are you')
blob.translate(from_lang='en',to='ka')






















