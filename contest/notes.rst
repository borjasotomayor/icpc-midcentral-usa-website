Notes to Teams
==============

Programs must be in a single source file with the name specified in the problem description; failure to meet this requirement is a Submission Error. Java programmers, note that you can include additional top-level classes in a single source file as long as they are unqualified (just "class", without "public").  Java programmers, note as well that your source file must not be declared as a part of a named package. That means you should not have a line at the top of your source code like: package someName;
All output will be judged using a file comparison utility, so output must be exactly as shown in the examples. This applies to all problems, whether explicitly stated in the problem description or not. Spelling, punctuation, spacing, and case (uppercase/lowercase) are all significant.
Make sure you know your site's specific time limit for program execution.  That may affect how you revise the efficiency of some programs.
Programs must write their results to standard output (usually stdout in C, cout in C++, and System.out in Java). The judges will ignore all output to standard error (usually stderr in C, cerr/clog in C++, and System.err in Java), so you can write as much debugging information to standard error as you want, subject to your site's time limit.
Your program cannot require any intervention by the user. For example, if you pause the program and ask the user to press a key to continue, you will be flagged with a Submission Error. If you pause the program without any prompting at all, you may be flagged with a Time Limit Exceeded error.
Do not use drive and/or path specifications when naming input files. If a problem indicates that the input file is named file.in, then you must open file.in and not a:file.in or c:\stuff\file.in or anything else. Violating this rule will result in a Submission Error.
All test cases used in judging will conform to the input specifications. It is not necessary for you to detect invalid input.
Input data and correct output data will obey the following rules.
Other than end-of-line characters, spaces are the only whitespace that appear.
Two or more consecutive spaces do not appear, unless specifically mentioned in the problem statement.
Spaces do not appear at the end of lines.
Spaces do not appear at the beginning of lines, unless specifically mentioned in the problem statement.
Blank lines do not appear.
All lines, including the last line, end with the standard end-of-line marker.
This applies only to Java programmers. Counter to Java conventions, the name of your source file and main class must be in lowercase for this competition. For example, if a problem states that your program must be called compute, then you would create a file called compute.java that begins like this:
public class compute {
    public static void main(String args[]) {
        ...
    }
    ....
}
Teams are ranked by the number of problems solved, with teams solving the same number of problems ranked by least total time (see Regional Scoring). Teams solving the same number of problems with the same total time are ranked by the smallest elapsed time of their last accepted solution (not counting penalties for rejected runs). Any remaining ties are left unbroken unless they affect the regional winners, in which case they are broken by a coin flip.
Any team that jeopardizes the integrity of the contest or violates the rules of the contest will be disqualified and the team members may be banned permanently from competing in the Mid-Central Region. Some examples of such actions are:
accessing the Internet in any way,
disrupting power to computers,
corrupting judging materials or the judging process,
collaborating with anyone not on the team (this includes using a portable phone),
disobeying site officials' instructions regarding appropriate conduct.