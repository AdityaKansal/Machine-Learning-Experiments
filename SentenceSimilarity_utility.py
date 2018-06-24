# -*- coding: utf-8 -*-
"""
Created on Fri Jun  8 07:51:45 2018

@author: akansal2
"""


# Designing the container and Input output setup
import tkinter
master = tkinter.Tk()
master.title('Word Similarity')
master.geometry("870x220")
from nltk.stem import WordNetLemmatizer
#from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
import numpy as np
from PIL import ImageTk, Image
from nltk.tokenize import word_tokenize
from nltk.tag import pos_tag

heading = tkinter.Label(master, text="Check the context!")
Blank1 = tkinter.Label(master)
Blank2 = tkinter.Label(master)
label_message = tkinter.Label(master)
#path = 'C:\A_stuff\Learning\Machine Learning\ML_Experimental_Learning\Sentence_Similarity\Images\ExactMatch.jpg'
#img = ImageTk.PhotoImage(Image.open(path))
#img = img.subsample(320,320)
#img = Image.open(path)
#img = img.resize((100,50),Image.ANTIALIAS)
#img1 = ImageTk.PhotoImage(img)

#Photo = tkinter.Label(master, image = img)


label_s1 = tkinter.Label(master, text="Sentence 1 ")
label_s2 = tkinter.Label(master, text="Sentence 2 ")
e1 = tkinter.Entry(master)
e2 = tkinter.Entry(master)




def recieve_input():
    perform_operation(e1.get(),e2.get())
    
    
def perform_operation(a,b):
    a = Lemmatizewords(a)
    b = Lemmatizewords(b)
    #cv = CountVectorizer()
    cv = TfidfVectorizer(stop_words = ['is','are','was','were','am','has','have','had','been','the'])
    sparse_matrix = cv.fit_transform([a,b]).toarray()
    score = Cosine_Similarity(sparse_matrix[0],sparse_matrix[1])
    match_result(score)

   
def Lemmatizewords(a):
    a = str(a)
    a = a.split(' ')
    temp = []
    for i in a:
        i = i.lower()
        lem = WordNetLemmatizer()
        word = word_tokenize(i)
        pos1 = pos_tag(word)
        if pos1[0][1] == 'RBR':
            temp.append(lem.lemmatize(i,pos ='a')) 
        elif pos1[0][1] == 'VBG':
            temp.append(lem.lemmatize(i,pos ='v'))
        else:
            temp.append(lem.lemmatize(i,pos ='n'))
            
            
    a = ' '.join(temp)  
    return a
      
    
def Cosine_Similarity(X,Y):
    dot_product = np.dot(X,Y)
    norm_X = np.linalg.norm(X)
    norm_Y = np.linalg.norm(Y)
    return dot_product*100/(norm_X*norm_Y)
              
def match_result(score): 
    if score == 0:
        display('Both sentences are out of context with each other')
    elif 0 < score <= 30:
        display('A very little similarity exists ')
    elif 30 < score <= 60:
        display(' Somewhat Similar')
    elif 60 < score <= 99:
        display(' Almost similar')
    else:
        display('Both sentennces are exactly in same context')
        
        

      
def display(result):
    #label_message.configure(text = '')
    label_message.configure(text = result)
    label_message.grid(row = 6,column = 3)
    label_message.configure(font=("Times New Roman", 12, "italic"))
#    messageVar = tkinter.Message(master, text = result)
#    messageVar.configure()
#    messageVar.grid(row = 6,column = 3)
    
    
Button_Check = tkinter.Button(master,text = 'Verify Similarity', command = recieve_input)

heading.grid(row = 0,column=2)
heading.configure(font=("Times New Roman", 12, "bold"))
Blank1.grid(row = 1,column=2)
label_s1.grid(row = 2,column=0)
#Photo.grid(row = 2,column=3)
label_s2.grid(row = 3,column=0)
e1.grid(row=2, column=1)
e2.grid(row=3, column=1)
Blank2.grid(row = 4,column=2)
Button_Check.grid(row=5, column=1)


master.mainloop()

