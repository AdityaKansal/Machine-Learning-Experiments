# -*- coding: utf-8 -*-
"""
Created on Sun Jun 10 12:55:03 2018

@author: akansal2
"""

#glob library
import glob
glob.glob('*.*')
for file in glob.iglob('[N]*.py'):
    print(file)



#os library
import os
os.getcwd()


#class objects
class Apollo:
    destination = 'moon'
    def print_mes(self):
        print('spaceship flying to - ')
    def print_dest(self):
        print(self.destination)

obj1 = Apollo()
obj2 = Apollo()


obj1.destination = 'mars'
obj1.print_mes()
obj1.print_dest()
obj2.print_mes()
obj2.print_dest()


# multiple functions
class animal:
    def legs(self,name,l):
        print('Its an animal')
        
    def legs(self,l):
        print('this is a bird')

animal1 = animal()
animal1.legs('Dog',4)
animal1.legs(0)



#constructor
class Example:
#    def __new__(self):
#        print('Constructor 1 called')
    def __init__(self,a):
        print('Constructor 2 called')
        
e = Example(10)
print(type(e))




























    

    
