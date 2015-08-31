Notes to Teams
==============

Judging system
--------------

During the Mid-Central Regional Contest, you will submit your solutions
using the Kattis system. You will be provided with login instructions
to access the Kattis system.

Everything you need to know about Kattis is
included in the `Kattis Team Guide <http://icpc.baylor.edu/worldfinals/programming-environment/Kattis-Team-Guide-V1.4.pdf>`_.
You will be provided with a copy of this document at the contest.
**Read it carefully**. It explains how to make submissions, 
input/output requirements, etc. The Team Guide also explains the
type of judgements you can receive from Kattis.

Just in case, here is a summary of the main things you should be
mindful of:

* Input is done on *standard input*.

  - In C, this is the default in ``scanf``. When using functions like ``fgets``, ``fscanf``, etc., use file ``stdin``.
  - In C++, use the ``cin`` input stream.
  - In Java, use ``System.in``.
   
* All test cases used in judging will conform to the input specifications. 
  It is not necessary for you to detect invalid input.
* Input is done on *standard output*.

  - In C, this is the default in ``printf``. When using functions like ``fprintf`` use file ``stdout``.
  - In C++, use the ``cout`` output stream.
  - In Java, use ``System.out``.

* Kattis ignores all output on standard error.
* Kattis uses specific compiler flags (see below). You should use these
  same flags when compiling on your machine.
* If your program's exit status is not zero, Kattis will judge it as a runtime
  error. C99, C++, and Java both default to returning zero if you do not specify
  an explicit exit code; even so, you should be mindful of this if your program
  includes explicit exit codes.
* Java programmers: Kattis does not handle Java files with package declarations.
  Although Kattis does support using multiple Java files, we encourage you to
  use a single Java file per submission.

Compiler flags
--------------

Source programs submitted to the Judges will be compiled using the following 
command line arguments for the respective languages:

C::

    gcc -g -O2 -std=gnu99 -static $FILES -lm

C++::

    g++ -g -O2 -std=gnu++11 -static $FILES

Java::

    javac -encoding UTF-8 -sourcepath . -d . $FILES

For C/C++, the resulting executable will be executed to generate the output of the submission.
For Java, the compiled code will be executed using the following command::

    java -client -Xss8m -Xmx$MEMORY $FILES
    
Where ``$MEMORY`` is the memory limit specified in the problem statement in the Kattis site.   

Scoring
-------

Teams are ranked by the number of problems solved, with teams solving the same number of problems 
ranked by least total time (see `Regional Scoring <http://icpc.baylor.edu/regionals/rules#HScoringofaRegionalContest>`_).
Teams solving the same number of problems with 
the same total time are ranked by the smallest elapsed time of their last accepted solution 
(not counting penalties for rejected runs). Any remaining ties are left unbroken unless they 
affect the regional winners, in which case they are broken by a coin flip.


Disqualification
----------------

Any team that jeopardizes the integrity of the contest or violates the rules of the 
contest will be disqualified and the team members may be banned permanently 
from competing in the Mid-Central Region. Some examples of such actions are:

* accessing the Internet in any way,
* disrupting power to computers,
* corrupting judging materials or the judging process,
* collaborating with anyone not on the team (this includes using a portable phone),
* disobeying site officials' instructions regarding appropriate conduct.