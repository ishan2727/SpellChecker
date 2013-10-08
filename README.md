SpellChecker
============

Write a program that reads a large list of English words (e.g. from /usr/share/dict/words on a unix system) into memory, and then reads words from stdin, and prints either the best spelling suggestion, or "NO SUGGESTION" if no suggestion can be found. The program should print ">" as a prompt before reading each word, and should loop until killed.

Your solution should be faster than O(n) per word checked, where n is the length of the dictionary. That is to say, you can't scan the dictionary every time you want to spellcheck a word.

For example:

> sheeeeep
sheep
> peepple
people
> sheeple
NO SUGGESTION
The class of spelling mistakes to be corrected is as follows:

Case (upper/lower) errors: "inSIDE" => "inside"
Repeated letters: "jjoobbb" => "job"
Incorrect vowels: "weke" => "wake"
Any combination of the above types of error in a single word should be corrected (e.g. "CUNsperrICY" => "conspiracy").

If there are many possible corrections of an input word, your program can choose one in any way you like. It just has to be an English word that is a spelling correction of the input by the above rules.

Final step: Write a second program that *generates* words with spelling mistakes of the above form, starting with correctly spelled English words. Pipe its output into the first program and verify that there are no occurrences of "NO SUGGESTION" in the output


Description of the code -
---------------------------

SpellChecker.java

The code first prprocesses the dicionary of words and creates two hashmaps :-
1) rwDictionary - Reduced word Dictionary. Removes repeated letters and converts vowels to '*' e.g. "sh*p" => "Sheep"
2) lcDictionary - Lower-case Dictionary. e.g. "sheep" => "Sheep"
The process of creating these dictionaries takes O(n) time where n = number of words in the dictionary.

Once these dictionaries are created the input word is also lower cased and is checked to be present in lcDictionary. If found then the value for this key is returned. 
Otherwise the word is reduced (vowels changed to '*' and repetitive letters removed) and the reduced word is checked to be present in rwDictionary.If found then the value for 

this key is returned.
If no such word is found in lcDictionary or rwDictionary then "No Suggestion" is returned.

Functions to convert the input word in lowercase, replae vowels with '*' and remove repetitive letters work in O(m) time where m = length of input word.
The lookup for the word in dictionaries take O(1) time.


BadWordGenerator.java

The code reads the words from the dictionary and performs the following :-
1)Change Case - A random index is chosen using a random number generator within the renge 0 - length of the word and the case of the letter at that index is changed.
2)Change Vowels - Vowels in the given word are randomly changed using this function.
3)Repetitive Letters - Repetitive letters are added to the word. Count of repetition is calclated using a random number generator.

All these functions run in O(m) time where m = length of the input word.


Overall complexity of the spell checker is O(m) where m = length of input word. 


Running the spell checker individually -
>javac SpellChecker.java
>java SpellChecker

Running the bad word generator indivisually -
>javac BadWordGenerator.java
>java BadWordGenerator

Running both so that the output of BadWordGenerator.java becomes the input of SpellChecker.java -
>javac SpellChecker.java 
>javac BadWordGenerator.java
>java BadWordGenerator | java SpellChecker
