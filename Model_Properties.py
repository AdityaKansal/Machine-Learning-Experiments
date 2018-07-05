# -*- coding: utf-8 -*-
"""
Created on Thu Jul  5 10:01:34 2018

@author: akansal2
"""


def break_string(model):
    
    str1 = re.findall(r'([\w]+)(?:\(|\[)',model)
    str1 = str(str1[0])
    str2 = re.findall(r'(?:[\w]+)(?:\(|\[)([\w,]+)(?:\)|\])',model)
    str2 = str(str2[0]).split(',')
    
    str3 = re.findall(r'(?:\)|\])(?:\(|\[|)([\w,]+)',model)
    str3 = str(str3[0]).split(',')
    return str1,str2,str3

#combinations

input_str = "CPL40AU B EM" 

model_list = [A,B,C,D]
#repeat below for loop for every row and using that row number retrieve the model properties
for row,model in enumerate(model_list): 
    found = False
    str1,str2,str3 = break_string(model)
    for i in str2:
        for j in str3:
            temp = str1 + " " + str(i) + " " + str(j)
            if temp == input_str:
                found = True
                break
    if(found):
        print(row)