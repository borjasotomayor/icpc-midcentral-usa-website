Information for Site Directors
==============================

The first rule is to contact me whenever you have a question or problem. That's why I'm here.

Look at the programming environment requirements, the schedule, and last year's contest materials. Also look at all the current documents, including the notes to judges.

Document Preparation. You must provide the following documents for the contest, in the quantities shown. You must provide Site-Specific Instructions if necessary, as explained in the Notes to Sites. All other documents are available on this web site except the problems themselves.

Per Team. Have more Clarification Request forms ready if teams need them, and fill out the team number and problem letter for each Judging Request form in advance.

Clarification Request:  3
Error Messages:   1
Judging Request:  1 per problem
Notes to Teams:   1
Problem Set:   3
Site-Specific Instructions (if any):   1
For The Judges As A Group. These are minimum quantities. Be ready to make more if the judges ask for them. Some documents may be unnecessary if you are using PC^2; use your own judgment.

Accepted Solution Receipts:   10
Error Messages:   3
Judging Log Sheet:   1 per team
Sample Judging Log Sheet:  1
Notes to Judges:  3
Notes to Sites:   3
Notes to Teams:   3
Site Log Sheet:   1
Using the Judging Utilities:  3
Problem Set:   3
Site-Specific Instructions (if any):   3



Notes to Sites Last modified Mon Oct 27 12:58:32 2014
Most of these notes are for the site director, but some apply to the judges as well. There are notes for before, during, and after the contest.

Contest-Day Contact (Site Director and Judges)

Each year one of the regional chief judges will act as the contest-day point of contact to handle all questions and corrections. This year your contact is Andy Harrington.

Before the Day of the Contest (Site Director)

1. Figure out how to restrict access to the Internet. Ideally, teams should work on computers that are not attached to the Internet (local networks are OK). Consider running unnetworked computers as a possibility. If you can't isolate your computers from the Internet, disable as many services (email, web browsers, irc, etc.) as possible and plan on monitoring teams throughout the competition. Anyone found to have accessed anything outside the local network should be disqualified from the contest and reported to the Regional Director for other possible sanctions.

2. Make sure that the IDE and command-line versions of your compilers are the same and use the same options for code generation, debugging information, etc. The students will probably use the IDE, but the judges will probably use the command-line compilers. If you are using a Windows compiler, make sure it is set to generate a text-mode application, not a GUI application. See Programming Environment Requirements for more information.

3. The judging utilities assume that the command-line versions of the compilers are on the path. See Using the Judging Utilities for more information.

4. Site-specific time limits are being used and are based on the speed of the local judging machines on benchmark tests. Calculate your site's time limit as follows:

Download the benchmark utilitities, utilTest2010.zip, which are described in Using the Judging Utilities. If you use the standard judging utilities, further instructions can be found in the README.txt file of utilTest2010. If you are using some other judging mechanism, all you need are the source code benchmarks/benchmarks.cpp and benchmarks/benchmarks.java; these programs do not accept any input and they report profiling information to standard output.
Compile and run both benchmark.cpp and benchmark.java on a machine that will be used as a judging station for the contest. Make sure that the machine is configured as it will be during the contest, and with the same compiler settings as you will be using on contest day.
Each program displays a "total" time. Your site's time limit is
0.3 * (total java time + total c++ time)
rounded to the nearest second (that is, 30% of that sum).
Be sure to record this time limit in any automated judging system; let the judges know the limit, and let the teams know the limit before the contest starts.
Email your benchmark results and site time limit to David.
You are encouraged to compute the time limit before the day of the competition, as there will be many other things to attend to that day!
5. If your compiler has multiple options for creating a project and there is a chance that a team might use the wrong one, prepare a hand-out for each team explaining how to use the compiler.

6. Contact the Regional Director for instructions on retrieving the contest materials

Day of the Contest (Site Director and Judges)

1. The site director will have a sealed envelope that must be opened in the presence of at least two other coaches from two different schools. This envelope will contain the passwords for a) the contest materials, and b) the scoring website (see below). The contest materials contain PDFs of each of the problems. The site director must then make three copies of the problem set per team, plus copies for the coaches and judges as needed. He or she may delegate that task to two of the coaches, if necessary. Under no circumstances should any student be allowed access to the problem set before the contest starts, whether a contestant or not.

2. If you prepared a hand-out for teams in Step 5 above, distribute it and discuss it at the pre-contest meeting, and post a copy on the notice board. Warn teams about immediate disqualification for accessing anything outside the local computing environment.

3. You will need to appoint a local Head Judge, and in addition you will need two Witnesses (described below). All three must be team coaches from different schools.

4. Make sure all of your judges are aware of the calculated site-specific time limit.  Even if you are using an automated system, the judges are there as a double-check.  If using an automated system, make sure that the site's Head Judge confirms that the Site Director has entered the right value into the system configuration.
5. All contest-day administration (entering scores, viewing the standings, and receiving problem updates) will occur via the web site at http://mcicpc.cs.atu.edu. A link to the scoring system will be posted by noon. The Web page is fairly self-explanatory, but if you're having trouble, call or email. Problem clarifications and corrections will be displayed at the top of the standings pages, so be sure to check the standings regularly, even if you have no scores to enter.

6. If your teams will use removable media (USB flash drives, etc.) for problem submission, do not allow teams access to those media until they are in their rooms at 12:15; if possible, your runners should deliver the media to the rooms. Fill out the judging request forms in advance with the team number and problem, so that teams cannot collaborate and submit problems for a different team.

7. Once the contest begins, if there are any teams that did not show up, call or email David and tell him which ones. He will leave those teams in the rankings (in case they show up late), but will mark them with {X} to indicate that they are no-shows.

Immediately After the Contest (Site Director and Judges)

5:30 Finish Entering Scores
The web scoring system will allow updates until approximately 5:50. There will be people running around everywhere at 5:30, so make sure that no one other than the judges has access to the machines you are using to enter scores. Either lock the door (and perhaps post a sign) or have someone block the entrance until you are done. Once you have entered all scores, carefully double-check the scores for any of your teams that are ranked in the top 5 in the region. If possible, delete all cookies from the web browsers you used, so that no one can access the scorer without knowing the password.

5:50 Email Solutions for Top-ranked Teams
The Head Judge and two Witnesses will need to send David solutions from all teams that are unofficially ranked in the top 5 for the region. All three should work together on this so that there are no mistakes and no foul play. If you have any teams ranked in the top 5 in the region, send David an email message with the following information:

name of site,
name of each ranked team,
a .tar file for each team containing the source files for their correct solutions (the name of the file should be the name of the team, like rockhurst-a.tar), and
the name, school, email address, and phone number of the Head Judge and the two Witnesses, who certify that the entered scores are correct.
The tar program is a command-line utility for creating archives and is preinstalled on all Unix variants and Mac OS X; a tar.exe for Windows is included in the \mcpc2014\judge directory of the CD-ROM. On all systems, tar has the following syntax: "tar cf name.tar file ...", where you can list the names of one or more files and/or directories to be included in the archive. Since both PC^2 and the judging utilities maintain copies of all submissions, be sure to send the correct version and not an old copy from an incorrect run. David will reply to your email with a confirmation that he got everything OK. Don't assume your email got through OK until you get a response from David. Several universities have been silently dropping email with certain attachments (like .zip files). If in doubt, send him another email with no attachments, or call. The chief judges will then use this information to verify the final rankings. Official final rankings will be posted by Monday. 

If you do not have a team ranked in the top 5 in the region, you do not need to send anything.

After the Contest (Site Director)

Keep all contest materials (all judging sheets and time logs, copies of correct solutions, and all of the backups created by the judging utilities) at your site for at least a month in case any problems arise.
