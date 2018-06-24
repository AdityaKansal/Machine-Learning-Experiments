# -*- coding: utf-8 -*-
"""
Created on Sat Apr  7 16:48:53 2018

@author: akansal2
"""

#importing libraries
import pandas as pd
import numpy as np
display(Dataset)

import matplotlib.pyplot as plt


#getting dataset
Dataset = pd.read_csv('C:\\A_stuff\\Learning\\Machine Learning\\ML_Experimental_Learning\\Test.csv')




#label encoder verification
from sklearn.preprocessing import LabelEncoder
le1 = LabelEncoder()
le1.fit(Dataset.iloc[:,1])
Dataset.iloc[:,1] = le1.transform(Dataset.iloc[:,1])
le2 = LabelEncoder()
le2.fit(Dataset.iloc[:,2])
Dataset.iloc[:,2] = le2.transform(Dataset.iloc[:,2])
#Dataset.iloc[:,0] = le.inverse_transform(Dataset.iloc[:,0])


##One hot encoder
#from sklearn.preprocessing import OneHotEncoder
#ohe = OneHotEncoder(categorical_features = [1,2])
#B = ohe.fit_transform(Dataset.iloc[:,:]).toarray()
#


#using get dummy variables
Dataset = pd.get_dummies(Dataset,columns = ['X2','X3'],drop_first = True)





#dividingg  data set into matrices
X = Dataset.iloc[:,0:2].values
y= Dataset.iloc[:,2].values



#Visualizing the dataset
plt.scatter(X[:,0],X[:,1])
plt.show()

import nltk
nltk.download()
from textblob import TextBlob
wiki = TextBlob('not a very great calculation')
wiki.sentiment


from sklearn.feature_extraction.text import TfidfVectorizer
cv = TfidfVectorizer()
A = "i love u take with u all the time in urð±!!! ðððð
ð¦ð¦ð¦"
import re
B = cv.fit_transform(A).toarray()
cv.get_feature_names()

df = pd.Series({0:'abc',1:'bcd',2:'def'})
C = np.array([[0,1,2],[2,3,4],[3,4,45]])
C = np.concatenate((C,df.iloc[:].values),axis =0)
C = np.array([C,df.iloc[:].values])
df.iloc[:]

A = np.array([[1,2,3],[2,3,4]])
B = np.array([10,20]).reshape(2,1)
C = np.concatenate((A,B),axis = 1)



#LDA
from sklearn.decomposition import LatentDirichletAllocation
import keras
import tensorflow as tf