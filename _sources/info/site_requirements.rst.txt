Site Requirements
=================

Sites must meet three types of requirements:

* :ref:`Software <requirements_software>`
* :ref:`Network Security <requirements_network>` (including access to the central judging server)
* :ref:`Physical Configuration <requirements_physical>`

.. _requirements_software:

Software
--------

Operating Systems
~~~~~~~~~~~~~~~~~

One of the following is required: Windows XP/Vista or higher, Mac OS 10.4 or higher, 
or any \*NIX variant that will fully support the compilers specified below.

Compilers
~~~~~~~~~

Sites must provide the following freely-available compilers. These are the same
compilers used by the Kattis site, and will ensure that students do not run into
situations where code compiles fine on the site, but then doesn't compile when
submitted to the Kattis site.

-  **C/C++**

   A compiler (and corresponding debugger) supporting C++14 is required.

   We recommend using GNU’s gcc 5.4 or higher plus a compatible version
   of GNU’s gdb debugger. Windows sites should use the MinGW port. Mac
   OS 10.4 and higher have a suitable gcc and gdb available as part of
   the developer tools, which ship with the OS but are not installed by
   default.

-  **Java**

   The Java 8 SDK or higher is required (make sure you have the
   ``java``, ``javac``, and ``jdb`` commands). Either the Sun or OpenJDK
   version is acceptable, but take into account that Kattis uses the
   OpenJDK compiler. Java 7 can be used if using Java 8 is not possible.

-  **Python**

   The official Python 2.7 and 3.5 interpreters (as available on Python.org) must
   be installed. For Python 2.7, the Pypy interpreter may be used, as that is
   the one used on Kattis.

If your site includes multiple version of a given compiler, your site-specific documentation
must specify how to invoke the correct compiler.

The exact compiler flags are specified in the :doc:`notes to teams <../contest/notes>`.
Although the teams are told to use these commands, you may optionally want to
provide command aliases (e.g., ``icpc-gcc``, ``icpc-javac``, etc.) that invoke
the correct compiler with all the correct flags.

Integrated Development Environments (IDEs)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

GNU's gcc and Sun's javac (from the JDK) are command-line compilers, 
and gdb and jdb are command-line debuggers, so graphical IDEs are not
required. It is sufficient to provide one or more good text editors and 
a command line. Emacs, Vim, and jEdit are powerful editors that support 
all required languages and work on virtually all operating systems.

However, sites are encouraged to provide IDEs if possible. C/C++ IDEs
must work with gcc, and Java IDEs must work with the provided JDK. 
In addition, IDEs must be freely available so teams can practice 
with them in advance before the contest. The following IDEs meet 
these requirements, and are also used in the World Finals:

* Eclipse 4.7 (Java, C/C++, and Python)
* IntelliJ (Java)
* CLion (C/C++)
* Pycharm Community Edition (Python)
* Code::Blocks (C/C++ and Java)

Note: IDEs must ideally be configured to use the same compilers and the same
compiler flags specified in the :doc:`notes to teams <../contest/notes>`.
If that is not possible, please make sure you inform teams in the site-specific
instructions, so they know to test their code with the right flags from
the command line.

Computers
~~~~~~~~~

Computers must support the software required above, and must be configured 
so that they are all functionally identical. If possible, they should also 
be physically identical, although it is sufficient to have the same CPU and RAM.


.. _requirements_network:

Network Security
----------------

We use a central judging system called
`Kattis <https://open.kattis.com/>`__, the same system used at the World
Finals. With Kattis, there is a single central server that does all the
judging. Teams make their submissions via a web interface, so it is
important that the machines are configured to access only that web
interface, but not the rest of the Internet.

To do this, your site must limit Internet access to just the Kattis
site, as well as a regional site where we will post reference
documentation.

There are two mechanisms to accomplish this:

-  **Using the regional proxy**

   We will be running an HTTP proxy for the region that will ensure that
   teams can only access the Kattis site. On the site, you will only
   need to limit IP access to that HTTP proxy. The web browsers on the
   contest machines must also be configured to use this proxy by default
   (or you must give teams instructions on how to setup the browsers
   accordingly).

   This is our preferred mechanism for limiting access to Kattis.

-  **Running your own proxy**

   It is also possible for your site to limit access to Kattis by
   setting up your own web proxy. However, doing this requires a
   non-trivial setup. If this option is preferable, please :doc:`Contact <../contact>`
   the Director of Systems and we'll send you more details (we can also
   share the regional web proxy's configuration file; we use Squid so,
   if your site does too, it may make your setup easier).

Network Security
~~~~~~~~~~~~~~~~

In the weeks before the contest, we will be running a number of dry runs
using the exact same configuration we will use during the actual
contest. We encourage you to make use of these dry runs to ensure that
everything is working correctly on your end. In particular, you should
check that:

-  You can access the regional Kattis site but not any other kattis.com
   sites (you can test ``open.kattis.com`` and ``uchicago.kattis.com``).
-  You cannot access any other hosts on the Internet.
-  You can log into Kattis and submit solutions to problems.
-  You can submit clarifications, and receive responses (someone will be
   supervising the dry run, and will respond to clarification requests).

Please note that, at any other time, the regional proxy will be left
running so you can run tests. However, there will be no one actively
monitoring the proxy or the Kattis site if any issues arise.


.. _requirements_physical:

Physical Configuration
----------------------

Teams must have a workstation (which may consist of more than one desk) large enough to allow all three team members to work simultaneously, one using the computer while the other two work on paper. Teams must be separated from each other far enough so that team members may talk quietly among themselves without risk of being overheard by nearby teams. Many configurations are possible. Some sites put each team in a classroom by themselves, and at the World Finals all teams are together in a large hall.

If teams do not have a room to themselves, it is desirable to provide one or more additional large common rooms with several large whiteboards or chalkboards where team members may go to discuss ideas.

Regardless of physical configuration, sites must take reasonable steps to ensure that there is no physical communication between a contestant and anyone but his or her team members.

