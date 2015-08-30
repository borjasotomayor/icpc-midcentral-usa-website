
Programming Environment
Requirements   Last modified: May 20, 2009
Operating Systems

One of the following is required: Windows XP/Vista or higher, Mac OS 10.4 or higher, or any *NIX variant that will fully support Sun's 6.x Java Development Kit either natively or via Linux emulation.

Compilers

In the past we allowed sites to choose any compilers that met certain minimum criteria. We are now requiring specific freely available compilers region-wide for three reasons: (1) consistency, (2) since the compilers are free, all teams will be able to practice with the contest compilers in advance, and (3) we will eventually move to region-wide automated judging, at which time it will be mandatory for the compilers used at the sites to be the same as the ones used by the regional judges.

C/C++
GNU's gcc 3.3 or higher is required, plus a compatible version of GNU's gdb debugger. Windows sites should use the MinGW port. Mac OS 10.4 and higher have a suitable gcc and gdb available as part of the developer tools, which ship with the OS but are not installed by default. Solaris sites should visit sunfreeware.com for current packages. Other *NIX distributions should have a suitable gcc and gdb preinstalled.

Java
Sun's Java SE Development Kit (JDK) 6.0 or higher is required. It will include the jdb debugger.

Note. Having additional compilers beyond those listed above is prohibited. Sometimes code that works fine with one compiler will not work with a different compiler. This has actually happened during past contests.

Integrated Development Environments (IDEs)

GNU's gcc and Sun's javac (from the JDK) are command-line compilers, and gdb and jdb are command-line debuggers, so graphical IDEs are not required. It is sufficient to provide one or more good text editors and a command line. Emacs, Vim, and jEdit are powerful editors that support all required languages and work on virtually all operating systems.

However, sites are encouraged to provide IDEs if possible. C/C++ IDEs must work with gcc, and Java IDEs must work with Sun's JDK. In addition, IDEs must be freely available so teams can practice with them in advance before the contest. The following IDEs meet these requirements. Eclipse and jGRASP have the advantage of working with all three contest languages. In addition, Eclipse is used at the World Finals.

IDE   Language(s) Operating System(s)
BlueJ Java  all
Dev-C++  C, C++   Windows
DrJava   Java  all
Eclipse (plus CDT)   C, C++, Java   all
Turbo JBuilder Java  all
JCreator LE Java  Windows
jGRASP   C, C++, Java   all
NetBeans Java  all
V IDE C, C++, Java   Windows, Linux
Xcode C, C++, Java   Mac OS X
Note. IDEs must be configured to use the same compiler settings as the judges (who will use gcc and javac directly). If they use different settings for optimization, debugging, etc., a program that works correctly for a team when using the IDE may fail when the judges test it. This has happened in a past contest.

Computers

Computers must support the software required above, and must be configured so that they are all functionally identical. If possible, they should also be physically identical, although it is sufficient to have the same CPU and RAM.

Physical Configuration

Teams must have a workstation (which may consist of more than one desk) large enough to allow all three team members to work simultaneously, one using the computer while the other two work on paper. Teams must be separated from each other far enough so that team members may talk quietly among themselves without risk of being overheard by nearby teams. Many configurations are possible. Some sites put each team in a classroom by themselves, and at the World Finals all teams are together in a large hall.

If teams do not have a room to themselves, it is desirable to provide one or more additional large common rooms with several large whiteboards or chalkboards where team members may go to discuss ideas.

Regardless of physical configuration, sites must take reasonable steps to ensure that there is no physical communication between a contestant and anyone but his or her team members.

Network Security

If team computers are connected to a network, they must be configured so that they can only access those network resources necessary to run the contest, and no more. Teams must not be able to communicate over the network with anyone except the judges, and of course general Internet access must be blocked.