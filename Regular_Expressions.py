# -*- coding: utf-8 -*-
"""
Created on Wed Jun  6 10:04:32 2018

@author: akansal2
"""

import re

A = 'fine'
B = 'fine it is fine and fine'
result = re.match(r'fine',B)
print(result.end())

#re.search
result = re.search("good","This good  person is very good")
if result:
    print("found")
else:
    print("not found")
    
#re.findall
result_all = re.findall("good","This good  person is very good")   
print(result_all)


#metacharacters
A = '''aditya is 28  123445 123   Years old @#_modinagar

'''
print(re.findall('[a-z]',A))
print(re.findall('[@]',A))
print(re.findall('[0-9]',A))
print(re.findall('[a-z0-9A-Z]',A))
print(re.findall('[a]',A))
print(re.findall('a',A))
print(re.findall('[^a]',A))
print(re.findall('[^ad0-9]',A))
print(re.findall('\w',A))
print(re.findall('\W',A))
print(re.findall('\w+',A))
print(re.findall('\W+',A))
print(re.findall('\s',A))
print(re.findall('\S',A))
print(re.findall('\n',A))
print(re.findall('\D',A))
print(re.findall('\d',A))
print(re.findall('\d{0,2}',A))




A = 'Cats Dre. We. Cats are smarter Cats than dogs j@gmail.com.'
r1 = re.match('Cats',A)
print(r1.group(1))
print(r1.groups())
print(re.sub(r'[a-zA-Z]','',A))
print(re.findall(r'[a-z]*\.',A))


pattern = re.compile(r'@gmail\.com')
print(re.findall(pattern,A))

matches = pattern.finditer(A)
for match in matches:
    print(match)


a = '+00.5'
result = re.search(r'^(\+|\-|)?\d*\.\d+$',a)
print(result)

i = '+0.2'
result = re.search(r'(\+|\-)?\d*\.\d*',i)
print(result)

###############################################################
'''
pattern = [r'[a]'] OR pattern = ['a-z']
. Any character except new line
\d digit(0-9)
\D  not a digit(0-9)
\w wordcharacter a-z,A-Z,0-9,_ (no space,new line or tab)
\W not a word character
\s whitespace space tab, newline
\S not a white space
\b wordboundary (starting or ending of word)
\B not a wordboundary
^ beginning of string
$ end of string
[] matches characters in brackets
[^ ]  matches character Not in brackets
| either or
() Groups

Quantifiers
* 0 or more
+ 1 or More
? 0 or one
{3}  exact number
{3,4} range of numbers (min, max)

Commands
findter
findall
sub
match
search
group
groups
'''


#implementation
##############################################
#pattern = [r'[a]'] OR pattern = ['a-z']
# r stands for raw string and it indicates that after r, this stands for Reg exp
A = ''' hello! This is aditya

I am good '''
print(re.findall('a',A))    # same result
print(re.findall(r'a',A))   # same result

#but see this case,both are different
# 'r' means the the following is a "raw string", ie. backslash characters are treated literally instead of signifying special treatment of the following character
print(re.findall('is\b',A))
print(re.findall(r'is\b',A))

#############################################

#. Any character except new line
A = ''' hello! This is aditya

test

I am good '''
pattern = re.compile(r'.')
matches = pattern.finditer(A)
for match in matches:
    print(match)


##############################################

#\d digit(0-9)
A = ''' hello! This is aditya17
I am good '''
pattern = re.compile(r'\d')
matches = pattern.finditer(A)
for match in matches:
    print(match)



##############################################

#\D  not a digit(0-9)
A = ''' hello! This is aditya17
I am good '''
pattern = re.compile(r'\D')
matches = pattern.finditer(A)
for match in matches:
    print(match)


##############################################

#\w wordcharacter a-z,A-Z,0-9,_ (no space,new line or tab)

A = ''' hello! This is aditya_17@
I am good '''
pattern = re.compile(r'\w')
matches = pattern.finditer(A)
for match in matches:
    print(match)




##############################################

#\W not a word character
A = ''' hello! This is aditya_17@
I am good '''
pattern = re.compile(r'\W')
matches = pattern.finditer(A)
for match in matches:
    print(match)


##############################################

#\s whitespace space tab, newline

A = ''' hello! This is aditya_17@
I am good '''
pattern = re.compile(r'\s')
matches = pattern.finditer(A)
for match in matches:
    print(match)


##############################################

#\S not a white space

A = ''' hello! This is aditya_17@
I am good '''
pattern = re.compile(r'\S')
matches = pattern.finditer(A)
for match in matches:
    print(match)



##############################################

#\b wordboundary (starting or ending of word)

A = ''' hello! This is aditya_17@
I am good '''
pattern = re.compile(r'is\b')
matches = pattern.finditer(A)
for match in matches:
    print(match)





##############################################

#\B not a wordboundary

A = ''' hello! This is aditya_17@
I am good '''
pattern = re.compile(r'it\B')
matches = pattern.finditer(A)
for match in matches:
    print(match)


##############################################

#^ beginning of string
A = '''hello! This is aditya_17@
I am good '''
pattern = re.compile(r'^hello')
matches = pattern.finditer(A)
for match in matches:
    print(match)


##############################################

#$ end of string


A = '''hello! This is aditya_17@
I am good'''
pattern = re.compile(r'good$')
matches = pattern.finditer(A)
for match in matches:
    print(match)

##############################################

#[] matches characters in brackets

A = '''hello! This is aditya_17@
I am good'''
pattern = re.compile(r'[a-e]')
matches = pattern.finditer(A)
for match in matches:
    print(match)



##############################################

#[^ ]  matches character Not in brackets

A = '''hello! This is aditya_17@
I am good'''
pattern = re.compile(r'[^a-e]')
matches = pattern.finditer(A)
for match in matches:
    print(match)


##############################################
#| either or

A = '''hello! This is aditya_17@
I am good'''
pattern = re.compile(r'he|lo|ad')
matches = pattern.finditer(A)
for match in matches:
    print(match)


##############################################
#() Groups

A = '''aditya@gmail.com
kansal@yahoo.com
17@monster.com
I am good'''
pattern = re.compile(r'\w+@(gmail|yahoo)\.com')
matches = pattern.finditer(A)
for match in matches:
    print(match)
    
    

##############################################

#Quantifiers
#* 0 or more


A = '''aditya@gmail.com
kansal@gmail...com
kansal1@gmailcom
I am good'''
pattern = re.compile(r'\w+@gmail\.*com')
matches = pattern.finditer(A)
for match in matches:
    print(match)
    
    

##############################################

#+ 1 or More
A = '''aaditya Kansal
aditya@gmailcom
I am good'''
pattern = re.compile(r'a+ditya')
matches = pattern.finditer(A)
for match in matches:
    print(match)

##############################################

#? 0 or one

A = '''aditya@gmail.com
kansal@gmailcom
I am good'''
pattern = re.compile(r'\w+@gmail\.com')
matches = pattern.finditer(A)
for match in matches:
    print(match)

##############################################
#{3}  exact number

A = '''Helloo worlds'''
pattern = re.compile(r'o{2}')
matches = pattern.finditer(A)
for match in matches:
    print(match)
    


##############################################
#{3,4} range of numbers (min, max)
A = '''Helloooo worlds'''
pattern = re.compile(r'o{1,3}')
matches = pattern.finditer(A)
for match in matches:
    print(match)






##############################################
#Commands
#findter
#findall - fina all meatching character
A = '''Hello worlds Hello'''
pattern = re.compile(r'Hello')
print(re.findall(pattern,A))
#sub - substitute the matched character in the string
#match - search in starting of string
#search - search in all string
A = '''Hello worlds Hello'''
pattern = re.compile(r'Hello')
result = re.search(pattern,A)
print(result.groups())
#group  - first group
#groups - all groups



















    






























