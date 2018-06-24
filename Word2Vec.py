# -*- coding: utf-8 -*-
"""
Created on Sun Jun 10 05:44:23 2018

@author: akansal2
"""

import gensim
from gensim import corpora,models,similarities
import pandas as pd
import nltk



df = pd.read_csv('C:\\A_stuff\\Learning\\Machine Learning\\ML_Experimental_Learning\\tweets.csv')
corpus = df['tweet'].values.tolist()
tok_corpus = [nltk.word_tokenize(sent.encode().decode('utf-8')) for sent in corpus]
model = gensim.models.Word2Vec(tok_corpus,min_count =1,size = 32)
model.save('tweets_model')
model1 = gensim.models.Word2Vec.load('tweets_model')


