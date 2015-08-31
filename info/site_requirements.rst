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

* **C/C++**

  GNU's gcc 4.8 or higher is required, plus a compatible version of GNU's gdb debugger. 
  Windows sites should use the MinGW port. Mac OS 10.4 and higher have a suitable gcc 
  and gdb available as part of the developer tools, which ship with the OS but are not 
  installed by default. Solaris sites should visit sunfreeware.com for current packages.
  Other \*NIX distributions should have a suitable gcc and gdb preinstalled.

* **Java**

  The Java 7 SDK or higher is required (make sure you have the ``java``, ``javac``, and
  ``jdb`` commands). Either the Sun or OpenJDK version is acceptable, but take
  into account that Kattis uses the OpenJDK compiler. 

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
these requirements. Eclipse and jGRASP have the advantage of working 
with all three contest languages. In addition, Eclipse is used at the World Finals.

=================== ============ ====================
IDE                 Language(s)  Operating System(s)
=================== ============ ====================
BlueJ               Java         all
Dev-C++             C, C++       Windows
DrJava              Java         all
Eclipse (plus CDT)  C, C++, Java all
Turbo JBuilder      Java         all
JCreator LE         Java         Windows
jGRASP              C, C++, Java all
NetBeans            Java         all
V IDE               C, C++, Java Windows, Linux
Xcode               C, C++, Java Mac OS X
=================== ============ ====================

Note: IDEs must be configured to use the same compilers and the same
compiler flags specified in the :doc:`notes to teams <../contest/notes>`.
If teams use different flags for optimization, debugging, etc., 
a program that works correctly for a team when using the IDE may 
fail when judged. This has happened in past contests.

Computers
~~~~~~~~~

Computers must support the software required above, and must be configured 
so that they are all functionally identical. If possible, they should also 
be physically identical, although it is sufficient to have the same CPU and RAM.


.. _requirements_network:

Network Security
----------------

We use a central judging system called `Kattis <https://open.kattis.com/>`_, the
same system used at the World Finals. With Kattis, there is a single central
server that does all the judging. Teams make their submissions via a web interface,
so it is important that the machines are configured to access only that web
interface, but not the rest of the Internet.

To do this, you must limit access to IP address **192.36.103.42** *and* hostname
**mcpc2015.kattis.com**.  

NOTE: It is not enough to limit access by IP, since other
Kattis sites are on that same IP, including Open Kattis, where teams could access
problems they have solved on that site.

There are, broadly, two mechanisms to accomplish this:

* **Limiting DNS access**

  Besides limiting IP access to 192.36.103.42, you should ensure that machines do
  not have access to your local DNS server. Then, modify the computers hosts file
  (``/etc/hosts`` in UNIX systems) to only include an entry for 192.36.103.42/mcpc2015.kattis.com.
  
* **Setting up an HTTP proxy**

  If you can only limit access by IP at the host level, the next option is to set up
  an HTTP proxy on the network, configured in such a way that only URLs to mcpc2015.kattis.com
  will be served by the proxy. The web browsers on the contest machines must be configured
  to use this proxy by default (or you must give teams instructions on how to setup the browsers
  accordingly).

Our region will also have a separate server containing
programming language documentation and other resources. However, that site will
only require IP-level filtering. The IP for this server will be announced
a few weeks before the contest.

Testing Network Security
~~~~~~~~~~~~~~~~~~~~~~~~

In the weeks before the contest, we will be running a number of dry runs using the exact same
configuration we will use during the actual contest. We encourage you to make use of these
dry runs to ensure that everything is working correctly on your end. In particular, you should
check that:

* You can access ``mcpc2015.kattis.com`` but not any other kattis.com sites (you can test
  ``open.kattis.com`` and ``uchicago.kattis.com``).
* You cannot access any other hosts on the Internet.
* You can log into Kattis (we will supply you with test accounts), and submit solutions to
  problems.
* You can submit clarifications, and receive responses (someone will be supervising the dry
  run, and will respond to clarification requests).
  
Please note that, at any other time, you can also test your network configuration by limiting
access to another Kattis site and checking that you can't
access other kattis.com sites. We recommend you use ``open.kattis.com`` for this purpose, as
you can submit solutions to problems there in a manner similar (but not identical to) the
actual regional contest. 

.. _requirements_physical:

Physical Configuration
----------------------

Teams must have a workstation (which may consist of more than one desk) large enough 
to allow all three team members to work simultaneously, one using the computer while 
the other two work on paper. Teams must be separated from each other far enough so 
that team members may talk quietly among themselves without risk of being overheard 
by nearby teams. Many configurations are possible. Some sites put each team in a 
classroom by themselves, and at the World Finals all teams are together in a large hall.

If teams do not have a room to themselves, it is desirable to provide one or more 
additional large common rooms with several large whiteboards or chalkboards where 
team members may go to discuss ideas.

Regardless of physical configuration, sites must take reasonable steps to ensure that 
there is no physical communication between a contestant and anyone but his or her team members.

