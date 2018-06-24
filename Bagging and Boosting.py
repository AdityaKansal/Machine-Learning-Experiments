# -*- coding: utf-8 -*-
"""
Created on Sat Jun  9 07:49:27 2018

@author: akansal2
"""
#importing libraries
import pandas as pd
import numpy as np
from sklearn.preprocessing import LabelEncoder
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score
from sklearn.ensemble import BaggingClassifier,RandomForestClassifier,VotingClassifier,AdaBoostClassifier
from sklearn.svm import SVC
from sklearn.naive_bayes import GaussianNB





#importing data set
data = pd.read_csv('C:\\A_stuff\\Learning\\Machine Learning\\ML_Experimental_Learning\\abalone.csv',header = None)

#data cleansing
le = LabelEncoder()
data[0] = le.fit_transform(data.iloc[:,0])
data = pd.concat([data,pd.get_dummies(data.iloc[:,0],drop_first = True,)],axis = 1)
data = data.drop([0],axis = 1)
data.columns = [0,1,2,3,4,5,6,7,8,9]
data[7] = data[7].apply(lambda x : 0 if 0<x<10 else (1 if 10<x<20 else 2 ))

# X and y set
X = data.iloc[:,[0,1,2,3,4,5,6,8,9]].values
y = data.iloc[:,7].values

#dividing data into X_train and y_train data set
X_train,X_test,y_train,y_test = train_test_split(X,y,test_size = 0.2,random_state = 1)


#apply one decision tree
clf = DecisionTreeClassifier()
clf.fit(X_train,y_train)
y_pred = clf.predict(X_test)
scoring = 'accuracy'


print(accuracy_score(y_test,y_pred))



#apply ensembling bagging SVC
cl3 = SVC(kernel = 'poly')
clf = BaggingClassifier(base_estimator = cl3,n_estimators = 20,max_samples = 0.8 )
clf.fit(X_train,y_train)
y_pred = clf.predict(X_test)
print(accuracy_score(y_test,y_pred))


#apply random forest
clf = RandomForestClassifier(n_estimators = 20)
clf.fit(X_train,y_train)
y_pred = clf.predict(X_test)
print(accuracy_score(y_test,y_pred))



#applying voting classifier
cl1 = GaussianNB()
cl2 = DecisionTreeClassifier()
cl3 = SVC(kernel = 'poly')
clf = VotingClassifier(estimators = [('svc',cl3),('gnb',cl1),('dt',cl2)],voting = 'hard')
clf.fit(X_train,y_train)
y_pred = clf.predict(X_test)
print(accuracy_score(y_test,y_pred))



#applying boosting
clf = AdaBoostClassifier(DecisionTreeClassifier(),n_estimators= 10,learning_rate =1)
clf.fit(X_train,y_train)
y_pred = clf.predict(X_test)
print(accuracy_score(y_test,y_pred))








