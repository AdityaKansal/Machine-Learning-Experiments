# -*- coding: utf-8 -*-
"""
Created on Thu May 31 17:16:09 2018

@author: akansal2
"""

#Count Vectorizer

l = [3,4,5,7]
t = (3,5,6,8)
d = {1:3,4:5,6:7}


Data = {1:['He is a good boy',  'She dances well'],0: 'I dont like Horses'}

import nltk


#tokenizer
from nltk.tokenize import TweetTokenizer
s = '   I am a good boy and wake up at 4::00 < > *& AM daily'
s1 = s.split(' ')
twt = TweetTokenizer(reduce_len=False,strip_handles=True,preserve_case=True)

s2 = twt.tokenize(s) 






#Sparse/Dense matrix to CSR(Compressed Sparse Row)
import numpy as np
A = np.array([[0,0,0,0,1],[1,0,0,0,0],[0,1,0,1,0],[2,0,0,0,1],[0,0,0,8,2]])
from scipy.sparse import csr_matrix
B = csr_matrix(A)
print(type(B))
print(B.todense())
print(B.toarray())


#Cosine Similarity between texts
from sklearn.feature_extraction.text import TfidfVectorizer
tv = TfidfVectorizer(min_df = 0,stop_words = 'english')
from sklearn.feature_extraction.text import CountVectorizer
tv = CountVectorizer()
Data = ['He is a good boy',  'She dances well','I dont like Horses']
Sparse_matrix = tv.fit_transform(Data).toarray()
Sparse_matrix = Sparse_matrix()
print(type(tv.get_feature_names()))

print(tv.vocabulary_)
import pandas as pd
dataset = pd.DataFrame(Sparse_matrix,columns = tv.get_feature_names())


#cosine similarity
from sklearn.metrics.pairwise import cosine_similarity
print(cosine_similarity(Sparse_matrix,Sparse_matrix,dense_output = False))


def cos_sim(X,Y):
    dot_product = np.dot(X,Y)
    norm_X = np.linalg.norm(X)
    norm_Y = np.linalg.norm(Y)
    return dot_product/(norm_X*norm_Y)    


Data = ['My name is abhishek kansal ',  'abhishek kansal is my name']
Sparse_matrix = tv.fit_transform(Data).toarray()


print(type(int(cos_sim(Sparse_matrix[1],Sparse_matrix[0]))))



A = 'Abhishek kansal is a good boy'
B = 'Aditya Kansal is a good boy'
C = 'I run fast'

Data = [A,B,C]
a = 0
for i in Data:
    for j in Data:
        if(i!=j): 
            Sparse_matrix = tv.fit_transform([i,j]).toarray()
            print(i+ ' & '+ j + ' = ' + str(cos_sim(Sparse_matrix[0],Sparse_matrix[1])))
            #print(temp)
            #if(temp > a):
             #   a = temp
                
Data = [A,B]
Sparse_matrix = tv.fit_transform(Data).toarray()
print(Sparse_matrix)
print(cos_sim(Sparse_matrix[0],Sparse_matrix[1]))
           
   
    
    

#reading files using python
file = open('C:\\Users\\akansal2\\Desktop\\LinkstoUHG.txt')
a = file.readlines()
for i in a:
    print(i)
file.close()

path = 'C:\A_stuff\SRs/Agile Medicaid'
import glob
files = glob.glob(path)

for i in files:
    f = open(i)
    print(f)
    f.close



#paragraph to sentence

a = '''In the third category he included those Brothers (the majority) who saw nothing in Freemasonry but the external forms and ceremonies, and prized the strict performance of these forms without troubling about their purport or significance. Such were Willarski and even the Grand Master of the principal lodge. Finally, to the fourth category also a great many Brothers belonged, particularly those who had lately joined. These according to Pierre's observations were men who had no belief in anything, nor desire for anything, but joined the Freemasons merely to associate with the wealthy young Brothers who were influential through their connections or rank, and of whom there were very many in the lodge.Pierre began to feel dissatisfied with what he was doing. Freemasonry, at any rate as he saw it here, sometimes seemed to him based merely on externals. He did not think of doubting Freemasonry itself, but suspected that Russian Masonry had taken a wrong path and deviated from its original principles. And so toward the end of the year he went abroad to be initiated into the higher secrets of the order.What is to be done in these circumstances? To favor revolutions, overthrow everything, repel force by force?No! We are very far from that. Every violent reform deserves censure, for it quite fails to remedy evil while men remain what they are, and also because wisdom needs no violence. "But what is there in running across it like that?" said Ilagin's groom. "Once she had missed it and turned it away, any mongrel could take it," Ilagin was saying at the same time, breathless from his gallop and his excitement. '''
print(a)


a = a.split(' ')
   






#lemmatize

import nltk
nltk.download('wordnet')
nltk.download('punkt')
nltk.download('averaged_perceptron_tagger')
nltk.download('punkt')
nltk.download('maxent_treebank_pos_tagger')
from nltk.tokenize import word_tokenize

from nltk.stem import WordNetLemmatizer

from nltk.tag import pos_tag

lem = WordNetLemmatizer()
word = word_tokenize('ran')
pos1 = pos_tag(word)
print(pos1)
print(lem.lemmatize('ran','n'))

#tkinter
#import tkinter
#m = tkinter.Tk()
##m.title('Counting')
##
##button = tkinter.Button(m,text = 'Stop',width = 25, command = m.destroy)
##button.pack()
##m.mainloop()
##take input
#m.title('Input and output')
#inputfirstname = tkinter.Entry()
#inputlstname = tkinter.Entry()
#inputfirstname.pack()
#inputlstname.pack()
#m.mainloop()
#
#
#
#from tkinter import *
#
#root = Tk()
#
#
#label1 = Label( root, text="Month(MM)")
#E1 = Entry(root, bd =5)
#
#label2 = Label( root, text="Day(DD)")
#E2 = Entry(root, bd =5)
#
#label3 = Label( root, text="Year(YYYY)")
#E3 = Entry(root, bd =5)
#
#
#def getDate(E1,E2,E3):
#    print('test')
#    print(E1.get())
#    print(E2.get())
#    print(E3.get())
#label1.pack()
#E1.pack()
#label2.pack()
#E2.pack()
#label3.pack()
#E3.pack()
#submit.pack(side =BOTTOM) 
#root.mainloop(




#count vectorizer
A = ' Good boy hello hello'
B =  'She She she dances good'
C = 'Hello Mr India'
Data = [A,B,C]
from sklearn.feature_extraction.text import CountVectorizer
tv = CountVectorizer()
tv = CountVectorizer(lowercase = True,analyzer = 'word',ngram_range = (1,1),stop_words = None ,max_features = None)
Sparse_matrix = tv.fit_transform(Data).toarray()
import pandas as pd
Data = pd.DataFrame(Sparse_matrix,columns = tv.get_feature_names())
print(tv.vocabulary)










#tfidf
from sklearn.feature_extraction.text import TfidfVectorizer
tv = TfidfVectorizer(min_df = 0,stop_words = 'english')




#speech to text
import speech_recognition as sr
sr.__version__

r = sr.Recognizer()

#working with audio file
audiofile = sr.AudioFile('C:\\Users\\akansal2\\Downloads\\Test_Audio1.wav')
with audiofile as source:
    audio = r.record(audiofile)
    
    
type(audio)
import sphinx
a = r.recognize_sphinx(audio)


import re
pattern = re.compile(r'[^AEIOUaeiou][AEIOUaeiou][AEIOUaeiou][^AEIOUaeiou]')
print(re.findall(pattern,'heaheal'))

#converting to exe file
#pyinstaller SentenceSimilarity_utility.py



#Lesk algorithm
import nltk
from nltk.wsd import lesk
from nltk.tokenize import word_tokenize
sentence = 'The river bank was full of dead fishes'
sent = nltk.word_tokenize(sentence)
print(lesk(sent,'bank','n'))


import re
A ='Helllo  AdityaKansal    '
re.findall(r'\t',A)    
    
























