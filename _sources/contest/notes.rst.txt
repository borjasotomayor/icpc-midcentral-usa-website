Notes to Teams
==============

Scoring
-------

Teams are ranked by the number of problems solved, with teams solving the same number of problems ranked by least total time. Teams solving the same number of problems with the same total time are ranked by the smallest elapsed time of their last accepted solution (not counting penalties for rejected runs). See the official `ICPC Regional Rules <http://icpc.baylor.edu/regionals/rules>`_ for more details.

Any ties that remain, even after applying the official rules, are left unbroken unless they affect the regional winners, in which case they are broken by a coin flip.

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


Using Kattis
------------

Your site will provide you with login credentials and the Kattis
competition web URL.  Your must write your code in Java, C++, C, Python 2, or
Python 3 and submit the code to Kattis. Kattis will then compile your
code and run it on some secret input. After some careful deliberation,
you will get a judgement informing you whether your code behaved as
expected or not. 

Finding problems and sample data
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

You will find the problems both in the Kattis web page under the
heading PROBLEMS and on paper copies provided to you at your site. 
The end of the online version of a problem includes a link to files
containing the sample data.  These files are a good
start for generating your more robust sets of testing data.

Program structure
~~~~~~~~~~~~~~~~~

-  Input is obtained from *standard input*:

   -  In Java, use ``System.in``.
   -  In C++, use the ``cin`` input stream.
   -  In C, this is the default in ``scanf``. When using functions like
      ``fgets``, ``fscanf``, etc., use file ``stdin``.
   -  In Python, read from ``sys.stdin``
-  All test cases used in judging will conform to the input
   specifications. It is not necessary for you to detect invalid input.
   If, for example, the problem statement specifies that an integer is
   positive, you do not need to check for or handle non-positive
   integers (or non-integers). Always read the specification carefully.
-  Output is sent to standard output:

   -  In Java, use ``System.out``.
   -  In C++, use the ``cout`` output stream.
   -  In C, this is the default in ``printf``. When using functions like
      ``fprintf`` use file ``stdout``.
   -  In Python, this is the default in ``print``
- Kattis ignores all output to standard error (``System.err`` in
  Java, ``cerr`` in C++, ``stderr`` in C, ``sys.stderr`` in C), so you
  can use this for debugging notes (though this will slow your program
  down).
- Your program should refer to no files, either for input or output.
  Trying to read from an input file is likely to cause a run-time error.
- Kattis uses specific compiler flags (see :ref:`Compilation <compilation>` below.).
  You should use these same flags when compiling on your machine.  Your
  site should have the IDE's that they provide set up to use them by
  default.  If you test from the command line, be sure to include the
  flags listed below.
- If your program’s exit status is not zero, Kattis will judge it as a
  runtime error. C11, C++, Java, and Python all default to returning
  zero if you do not specify an explicit exit code.
- Java programmers: Do not put your code in a
  package (i.e., do not put a line like "package hello;" at the top of your source
  file). Be careful, by default an IDE may add a package line
  automatically.  A package declaration unnecessarily complicates
  submission (below) or causes a run-time error. 

Submitting in the browser
~~~~~~~~~~~~~~~~~~~~~~~~~

Choose the Submit button at the top of the Kattis web interface.   A
form appears with places to select the Problem ID and Language and a
place to drag or choose files to upload.  Typically you upload just a
single source file that you have thoroughly tested, though multiple
files are allowed.  Unlike past years, the root of the file name does
not have to match the problem ID.

Java users: You must also enter the Main Class - the name of the class
containing the ``main`` method.  Caution:  This is not the file name
but the class name.  If your file hello.java has the class ``hello``
containing your ``main`` method, you indicate that the Main Class is
just ``hello``.  If you misspell this, you will get a compiler error
saying you have an "Unknown mainclass".

How does Kattis handle a submitted program?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

First, Kattis will compile your program. If the compiler fails to
compile your program, or does not complete in a reasonable amount of
time, Kattis will judge it as Compile Error. Otherwise, Kattis will
execute the compiled binary on the first set of secret judge's input
(there may be several). If the execution takes too long it will be
judged as Time Limit Exceeded. If it produces way too much output, it
will be judged as Output Limit Exceeded.  If it crashes, terminates
with non-zero exit status, or uses too much memory, it will be judged
as a Run Time Error. If the execution terminates correctly (exit
status 0), Kattis will inspect the output produced to verify that it
is correct. If it is incorrect, Kattis will judge the submission as
Wrong Answer. 

Note that your submission's output is only inspected if it
successfully terminates in time, thus a Time Limit Exceeded or Run
Time Error does not mean that output was correct up until the point
where the program crashed or time ran out. Note that if your program
should, for instance, divide by zero after the time limit has been
reached, it will be judged as Time Limit Exceeded rather than Run Time
Error.

If there are multiple test files, Kattis will continue with the same
procedure for the next set of judge's test data, as long as no error
has been found. As soon as an error is detected Kattis will stop and
report that error. Each test is run with a new invocation of your
program, so your program does not need to be adapted in any way to
handle multiple test files. Each test file will follow the input
specification for the problem.

If your program passes all test files successfully, it will be judged
as Accepted.

Tracking your submissions
~~~~~~~~~~~~~~~~~~~~~~~~~

You can track the status of your submission in the web interface by
choosing your team name from the top right menu.  On this page you
will see a list of all submissions you have made, in reverse
chronological order. As the submission proceeds through the judgement
process your submissions page will reflect this. The states a
submission will pass through while it is being judged are:

#. New
#. Waiting for Compile
#. Compiling
#. Waiting for Run
#. Running
#. Final Judgement (see Possible Judgements below).

Clarification requests
~~~~~~~~~~~~~~~~~~~~~~

During the contest you can submit requests for clarifications to the
judges. To do so, click on Clarifications. The clarifications page has
three sections:

-  submitted clarification requests from your team that have not yet
   been answered
-  a form for submitting a clarification request
-  clarification requests with answers from the judges.

When you submit a clarification request, please select a subject
(either one of the problems or "general'') and write your request in
English. The third section contains answers to your requests and
sometimes answers to other teams' requests, in case the judges choose
to reveal the question and the answer to all teams. Notifications that
there are new clarification replies are displayed on all Kattis web
pages, but not on other pages (e.g., API documentation and
scoreboards).

Possible Judgements
~~~~~~~~~~~~~~~~~~~

When Kattis has judged your submission, you will get a reply telling you
the status of your submission. The following judgements are possible 
(these are described in more detail below):

-  Accepted
-  Compile Error
-  Run Time Error
-  Time Limit Exceeded
-  Output Limit Exceeded
-  Wrong Answer

For compile errors, there will be extra information apart from the
judgement itself, to help you debug your code syntax. This information
will be available on the page for the respective submission (available
by clicking on the corresponding submission ID number in your list of
submissions).

If your program compiles successfully, Kattis starts on a sequence of
runs with secret judges' data.  Kattis stops and reports the first
error found.  If a run completes normally, the output for that run is
tested.  Kattis only goes on to further test runs if the last run's
output was correct.

Accepted
~~~~~~~~

Accepted means that we were very happy with your program, and that it
(as far as Kattis could tell) solved the problem correctly.
Congratulations!

Compile Error
~~~~~~~~~~~~~

Compile Error means that Kattis failed to compile your source code. In
order to help you debug the error, the compiler output will be available
as extra information. This error does not cause a 20 minute time
penalty. (You do waste the time spent submitting it!)  Information about
what compilers and flags are used can be found below. 

Common errors in Java submisisons come from misnaming the Main Class,
leading to an error "Unknown mainclass".  You must specifiy the name
of the class that contains the starting main method.  The class name
contains no ".java"! If you enter the plain class name and you have 
put your class in a package, then you also get the error "Unknown
mainclass". 

Run Time Error
~~~~~~~~~~~~~~

Run Time Error means your program crashed during execution with our
secret test input. More precisely it means that it terminated with a
non-zero exit code, or with an uncaught exception.  Common examples:

-  array out-of-bounds references
-  stack overflows (likely infinite recursion)
-  using too much memory
-  invalid memory references (*e.g.*, dereferencing a null pointer)
-  trying to open a file

You may make this last error if you mistakenly use the previous years'
Mid-Central format:  trying to open an input or output file.  Remember
to just use standard input and output as discussed earlier.

Note that since the exit code is used to determine normal termination,
it is important that your main function in a C or C++ program does not
return a non-zero value.

Time Limit Exceeded
~~~~~~~~~~~~~~~~~~~

Time Limit Exceeded means that your program ran for too long. When the
time limit is exceeded, the program is terminated. The output produced
is not inspected until your program has finished successfully, so
getting Time Limit Exceeded does *not* mean that the output you had
produced so far was correct.  The time limit is applied separately to
each run with judges secret test data.  Usually there are multiple test
runs before a submisison is accepted.

Each individual problem lists a time limit, that is the limit on the
web server, not your local machine. You should be given a list of
approximate time limits appropriate for your tests on the hardware
used at your site.

Output Limit Exceeded
~~~~~~~~~~~~~~~~~~~~~

Output Limit Exceeded means that your program has produced too much
output and we decided to shoot it down before it flooded our hard
drive. Check to make sure that you don't get stuck in an infinite loop
where you print something and that you handle input termination
correctly.

Wrong Answer
~~~~~~~~~~~~

Wrong Answer means that your program finished within the time limit, but
that the answer produced was incorrect. This error is usually the most
frustrating one, since typically no extra information will be given.
Sometimes, the only way around it is to try to find bugs in your code by
constructing tricky test data yourself.

Please note that it is possible to technically have a correct solution,
but get a Wrong Answer because you formatted the output incorrectly.
While problems are designed to minimize the possibility of such
formatting errors, please note the the following:

-  Only if text formatting is explicitly given as a part of the problem,
   then formatting variations cause a Wrong Answer.
-  Where formatting is not a part of the problem (the most common case),
   then the system allows variations in white space, and problems are
   not likely to require much canned text in an answer.  The output
   validator looks at the output as a sequence of white-space separated
   tokens.
-  Instructions describing floating point answers are likely to have an
   error bound noted, so a rounded answer with an exact textual match of
   digits is not required in your output.

All the errors that come after successful compilation do cost a 20
minute time penalty if a later submission of the problem is accepted. 

.. _compilation:

Compilation and Execution details
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Please see the Kattis documentation for the exact compiler flags
that will be used to compile your submission, as well as how it will
be run:

* `C <https://open.kattis.com/help/c>`__
* `C++ <https://open.kattis.com/help/cpp>`__
* `Java <https://open.kattis.com/help/java>`__
* `Python 2 <https://open.kattis.com/help/python2>`__
* `Python 3 <https://open.kattis.com/help/python3>`__

System libraries
~~~~~~~~~~~~~~~~

You are allowed to use all standard libraries included with the
language you are working in.

Please note that, even if your site's computers have a given library
installed on them, that does not mean it will be available on Kattis.
Most notably, Kattis does *not* support Python libraries like Scipy, Numpy,
and Pandas.
