# troo.ly

This repository contains code that solves the problems posed in the Word Proximity section of the Coding page on troo.ly's website.

In order to run this code, you have to have java 1.8 and mvn (3.0.4 or greater) installed on your computer.

Problem 1: Given a document D and two words w1 and w2, write a program that finds the fewest number of characters that separate some appearances of w1 and w2 in D respectively. Your program should output the positions of w1, w2, and the distance between them.
Solution: This problem is solved by the class FindClosestTokens. This class can be run with the following command line:

  run.sh -ct source word word

where "source" is a source of the document D (supported sources are: a file name or a url) and w1 and w2 are the two "word" arguments.

Problem 2: Given a document D and given a set W of n words {w1, ..., wn}, write a program that finds the fewest number of characters that separate ANY two distinct words from w. Your program should output the two words, their positions and the distance between them.
Solution: This problem is also solved by the class FindClosestTokens, since Problem 2 is merely a generalization of Problem 1. This class can be run with the following command line:

  run.sh -ct source word word ...

where "source" is a source of the document D (supported sources are: a file name or a url) and {w1, ..., wn} are the "word" arguments.

Problem 3: Given a document D and given a set W of n words {w1, ..., wn}, and a number K (at most n), write a program that finds the shortest portion of the document that contains at least K distinct words from W. Your program should output the portion of the document and the words contained in it. 
Solution: This problem is solved by the class FindShortestPassage. This class can be run with the following command line:

  run.sh -sp source k word word ...

where "source" is a source of the document D (supported sources are: a file name or a url), k is K, and {w1, ..., wn} are the "word" arguments.
