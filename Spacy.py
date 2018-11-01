# -*- coding: utf-8 -*-
"""
Created on Sun Aug  5 11:39:11 2018

@author: akansal2
"""

#installing libraires
import spacy
import en_core_web_sm


#loading enlgish language
nlp = en_core_web_sm.load()


#examples of reading data
docx = nlp('Spacy is a cool tool')
doc1 = nlp('Spacy is an amazing tool better than nltk')
myfile = open('C:/A_stuff/Learning/Machine Learning/ML_Experimental_Learning/News.txt').read()
doc2 = nlp(myfile)



#finding out sentences
doc1 = nlp('hello!! how are you? Where are you now a days?')
for sentence in doc1.sents:
    print(sentence)

    
for num,sentence in enumerate(doc1.sents):
    print(num ,': ',sentence)
    
################################################    
#creating tokens
doc1 = nlp('Spacy is a good tool!')    
for token in doc1:
    print(token.text)
    
print([token.text for token in doc1])
print(doc1.text)
print(doc1.text.split())


####################################################
#shape of tokens
doc1 = nlp('SpaCy is a good tool! 123 ') 
for word in doc1:
    print(word.text,word.shape)     #will print in int form
    
for word in doc1:
    print(word.text,word.shape_)   #x is for lower case , X is for upper case  # it will print in unicode form
    
    
for word in doc1:
    print(word.text,' : ',word.is_alpha)  #checks for aplhabets




######################################################
#parts of speech
ex1 = nlp('he drinks a drink')
for word in ex1:
    print(word.text,word.pos)    #pos for integer output



ex1 = nlp('he drinks a drink')
for word in ex1:
    print(word.text,word.pos_)   #pos_ for unicode output


ex2 = nlp('I fish a fish')    #another tricky example
for word in ex2:
    print(word.text,word.pos_)
    


#pos_tag good for traning model
ex2 = nlp('I fish a fish')    #another tricky example
for word in ex2:
    print(word.text,word.pos_,word.tag_)  #tag gives short name of pos and used in traning the model

##############################################################
#explaning spacy abbreviations
print(spacy.explain('DET'))
print(spacy.explain('DT'))
print(spacy.explain('VBP'))
print(spacy.explain('PRP'))



#########################################################
#another tricky example
exercise1 = nlp('all the faith he had had had had no effect on the outcome of his life')
for word in exercise1:
    print(word.text,word.pos_,word.tag_)   #notice tags for had had had had
 

###########################################################
#Syntactic dependency
ex3 = nlp('Sally likes Sam')
for word in ex3:
    print(word.text,word.tag_,word.pos_,word.dep_)   #word.dep_ for finding dependency

print(spacy.explain('advmod'))
print(spacy.explain('root'))
print(spacy.explain('dobj'))



##############################################################
from spacy import displacy
ex3 = nlp('Tajmahal is an iconic monument in India')
displacy.serve(ex3,style = 'dep')  #dependency
displacy.serve(ex3,style = 'ent')  # named entity recognization


displacy.serve(ex3,style = 'ent',options = {'compact' : True,'bg':'cornflowerblue','font': 'Sans-Serif'}) 


##################################################################
#Lemmatization
docx = nlp('study studying studious studio student')
for word in docx:
    print(word.text,word.lemma_,word.pos_)


docx = nlp('walks walking walker walked good better best')
for word in docx:
    print(word.text,word.lemma_,word.pos_)




#####################################################################
#name entity recogniation
wikitext = nlp('John went to playground at Delhi in 1920 and came back on monday afternoon')
for word in wikitext.ents:
    print(word.text,word.label_)   #notice here label_
    
print(spacy.explain('GPE'))

wikitext = nlp('I ate turkey in Turkey')
for word in wikitext.ents:
    print(word.text,word.label_)
    

wikitext = nlp('I worked in Acro and later i joined Nokia')
for word in wikitext.ents:
    print(word.text,word.label_)  
displacy.serve(wikitext,style = 'dep')



###############################################################################
#Similarity 
doc1 = nlp('dog')
doc2 = nlp('cat')
doc3 = nlp('water')
print(doc1.similarity(doc2))
print(doc1.similarity(doc3))


doc1 = nlp('smart')
doc2=nlp('clever')
doc3 = nlp('poor')
print(doc1.similarity(doc2))
print(doc1.similarity(doc3))


doc1 = nlp('I am good')
doc2 = nlp('We are bad')
print(doc1.similarity(doc2))   # for a sentence


ex1 = nlp('wolf dog cat fish bird')
for token1 in ex1:
    for token2 in ex1:
        print(token1.text,token2.text,'similarity : ',token1.similarity(token2))

#another way
mylist = [(token1.text,token2.text,token1.similarity(token2)) for token2 in ex1 for token1 in ex1]

#putting it in dataframe
import pandas as pd
df = pd.DataFrame(mylist)
df.head()
df.corr()    #correlation
df.columns = ['Token1','Token2','Similarity']
df_vis = df.replace({'wolf':0,'dog':1,'cat':2,'fish':3,'bird':4})


#visualizing it
import matplotlib.pyplot as plt
import seaborn as sns
#%matplotlib inline

plt.figure(figsize = (20,10))
sns.heatmap(df_vis,annot = True)
plt.show()


#############################################################################################
#stopword
from spacy.lang.en.stop_words import STOP_WORDS
print(STOP_WORDS)
print(len(STOP_WORDS))

#checking if word is a stopword
nlp.vocab['the'].is_stop  #will return True
nlp.vocab['better'].is_stop  #will return False


#filtering the stopwords
ex1 = nlp("How do I keep looping through until the len(new_list) = len(data_list) (i.e. all the numbers are in the new list) with everything sorted without using the built in max, min, sort functions? I'm not sure if it's necessary to create a new list either.")
for word in ex1:
    if word.is_stop:
        print(word)
        
        
#another way
mylist = [word for word in ex1 if word.is_stop]

#adding/removing stopwords
print(nlp.vocab['lamao'].is_stop)
STOP_WORDS.add('lol')
print(nlp.vocab['lol'].is_stop)
STOP_WORDS.remove('lol')
print(nlp.vocab['lol'].is_stop)




########################################################
docs = nlp('Aditya went to the Tajmahal in the Agra and ate icecream there')
for token in docs.noun_chunks:
    print(token.text)  #it wll print 'the'
    

for token in docs.noun_chunks:
    print(token.root.text) #it will print with the
    
for token in docs.noun_chunks:
    print(token.root.text,' connected to ',token.root.head.text)

#############################################################
docs = nlp('Hello how are you in India?')
for token in docs.cats:
    print(token.text)










































































































































































































    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    










    
    





























    



























