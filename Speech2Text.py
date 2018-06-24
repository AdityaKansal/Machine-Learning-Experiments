import speech_recognition as sr
#print(sr.__version__)


r = sr.Recognizer()

###working with audio file
##audiofile = sr.AudioFile('C:\\Users\\akansal2\\Downloads\\Test_Audio1.wav')
##with audiofile as source:
##    audio = r.record(audiofile)
##    
##    
##print(type(audio))
##import sphinx
##a = r.recognize_sphinx(audio)
##print(a)


mic = sr.Microphone(device_index=0)
                    
#print(sr.Microphone.list_microphone_names())
with mic as source:
    print('speak')
    r.adjust_for_ambient_noise(source)
    audio = r.listen(source)
    print('Converting to text....')
                    
print(r.recognize_sphinx(audio))
