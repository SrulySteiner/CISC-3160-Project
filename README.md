# CISC-3160-Project
Repository for CISC 3160 Project at Brooklyn College

Solved with help from the following resources:
[Ohio State CSE 2231 Slides](https://web.cse.ohio-state.edu/software/2231/web-sw2/extras/slides/)

Based on the following prompt:

The following defines a simple language, in which a program consists of assignments and each variable is assumed to be of the integer type. For the sake of simplicity, only operators that give integer values are included. Write an interpreter for the language in a language of your choice. Your interpreter should be able to do the following for a given program: (1) detect syntax errors; (2) report uninitialized variables; and (3) perform the assignments if there is no error and print out the values of all the variables after all the assignments are done.

Program:
	Assignment*

Assignment:
	Identifier = Exp;

Exp: 
	Exp + Term | Exp - Term | Term

Term:
	Term * Fact  | Fact

Fact:
	( Exp ) | - Fact | + Fact | Literal | Identifier

Identifier:
     	Letter [Letter | Digit]*

Letter:
	a|...|z|A|...|Z|_

Literal:
	0 | NonZeroDigit Digit*
		
NonZeroDigit:
	1|...|9

Digit:
	0|1|...|9
