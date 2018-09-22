Information for Problem Authors
===============================

The Regional Chief Judges need your help! They are good at blending easy, medium, and hard problems together to make a problem set that will provide challenges for teams of all skill levels, but they need your ideas and assistance to keep the problems fresh and interesting.

Unlike in past years, the biggest need is for hard problems. Ideally, the winning team members should triumph not only because they know all the standard classroom algorithms and are efficient programmers, but also because they are highly creative problem solvers who can tackle novel problems. Novel problems are not just "wrappers" around a standard algorithm, even if from an advanced algorithms class. They should involve inventing new, non-obvious algorithms, or combining standard algorithms in clever ways. They should not be difficult just by being long and tedious; in fact, novel problems often have short, clear explanations and can be solved by programs of modest size.

So if you have an idea for any novel problem, but especially a hard one, please contact any of the Regional Chief Judges, and they will tell you how to proceed. (Don't send unencrypted problem information by email!) It will be most helpful if you can then write a complete problem including the description, solution, and test cases, but a good idea is valuable by itself, so don't let it go to waste - participate at whatever level you can.

The regional programming contest has two primary goals: to select the strongest teams to represent our region at the World Finals, and to provide a valuable educational experience for all teams. To ensure that these primary goals are met, we have the following requirements.

All teams can solve at least one problem, but few teams can solve them all.
All problem specifications are clear and unambiguous.
All problems can be graded quickly by machine.
The rest of this announcement provides some guidelines for meeting these requirements.

General Guidelines

Problems must be clear, concise, and unambiguous. We will not accept ambiguous problems. All necessary information must either be stated explicitly in the problem, or must be inferrable from what is stated. Try to fit the problem description (including sample input and output) on a single page.

If possible, make sure that there is only one nontrivial problem to solve; eliminate all other details. For example, if you write a maze-search problem, then you're testing a team's ability to implement a search algorithm. That's the essence of the problem. If, in addition, you require elaborate input and output formats, then you're also testing a team's ability to parse complex input and generate complex output. In effect, you're writing three problems. Decide what the essence of the problem is, and simplify the remaining details as much as possible.

Writing Clear and Unambiguous Problems

Here are some things to keep in mind.

Define limits. How big can the maze be? Are negative numbers allowed? What happens when the string is empty? How large can the result of that calculation be? Are characters case-sensitive?

Don't write 'one-shot' problems. The 1992 contest included a problem called Puttin' on the Hex that defined a large hexagonal maze and required teams to write a program to find a path through the maze. There was no input (teams had to hard-code the maze in their program) and there was only a single output. Problems like this make it tempting for teams to try to solve the problem by hand and then simply write a program that outputs the answer without performing any computations. Even if the problem seems too difficult to solve by hand, some team might get lucky. We want to avoid this situation, so in general we will not accept such problems.

If the obvious solution won't work, say so. A classic problem is coin changing, in which you are supposed to calculate the fewest number of coins necessary to make a certain amount of change. For example, the minimum number of U.S. coins needed to make 87 cents is 6 (3 quarters, 1 dime, and 2 pennies). It's easy to calculate this using a greedy strategy; use as many quarters as possible, then as many dimes as possible, then as many nickels as possible, and then as many pennies as possible. The trick is that the value of the coins is part of the input. If quarters are worth 23 cents, dimes are worth 11 cents, nickels are worth 4 cents, and pennies are worth 1 cent, then the greedy strategy will not work; it will use 5 coins to make 33 cents (23+4+4+1+1), but the actual minimum needed is 3 coins (11+11+11). One of us has used this problem in practice sessions, and most students fail to realize that the greedy strategy won't work in general. Moral: if you write a seemingly simple problem in which the obvious strategy will not work, say so, and provide sample input and output to illustrate it. (Of course, you shouldn't give them the correct strategy.)

If the obvious solution is too slow, say so. One of us once wrote a problem that, given n, required students to find all integers x and y such that (1) x - y = n, and (2) x and y together used all ten digits once each. An efficient solution must use the magnitude of n to constrain x and y. For example, if n < 1000 then x and y must both be 5-digit numbers whose first digits differ by 1. Some students tried to solve this problem by brute force, in effect trying all possible values of x (from 0 to 987654321). This example is extreme, but there are many problems that have simple but wildly inefficient solutions. If yours is one of them, explicitly state that the obvious brute-force solution is too slow.

State any window dressing concisely. It is traditional to 'dress up' a problem to make it interesting, and we heartily approve of this. We don't want all the problems to sound like exercises from a data structures textbook. While you have unlimited artistic license, don't let problem descriptions drag on too long, and don't provide irrelevant facts if they may mislead a team.

Writing Machine-Gradable Problems

To ensure that programs can be graded quickly and accurately, all problems must be machine gradable. Specifically,

output must be unique, so that it can be compared to the correct output using a file-comparison utility,
programs must complete all test cases in less than one minute, and
programs must be gradable using a single input file.
To ensure that output is unique, you must pay particular attention to spacing, spelling, punctuation, and format of floating-point output. For some problems you may need to require that output be sorted in some fashion. If you require output in the form of sentences, pay attention to plurals: will you require Barney needs 4 bandersnatches but Barney needs 1 bandersnatch, or will you use a neutral Barney needs 1 bandersnatch(es)? In general, select output formats that make life easy for the programmer. Avoid elaborate tabular formats that require programmers to count spaces, unless that is the point of the problem.

Be careful if your problem requires case-insensitive string sorting. There are two reasonable ways to perform such a sort if the language does not provide case-insensitive comparisons (and not all do). One way is to sort based on the lowercased versions of each string, and the other is to sort based on the uppercased versions of each string. These two methods may give different results if the strings contain any of the characters ('[', '\', ']', '^', '_', '`'), whose ASCII codes are between those of the uppercase and lowercase letters. So either avoid those characters, or specify the exact sorting method to use.

Ensure that correct programs require less than 1 minute to process all of the judge's input data.

Problems should be designed to work with a single input file that may contain any number of test cases. We will not accept problems that require each test case to be in a separate file.

Writing "Portable" Problems

Teams may be using a different compiler and/or operating system than they're used to. To minimize potential errors, use the following guidelines.

Use only ASCII characters. In C/C++, characters are one byte long, can be signed (-128..127) or unsigned (0-255), and the character set is locale-dependent. Java uses Unicode and characters are two bytes long. To avoid problems, only use printable ASCII characters in the range 32..126 (other than end-of-line character(s)).

Use sentinels to signal the end of the input. End-of-file handling differs between languages and sometimes between different compilers for the same language. It can cause problems for teams using tools that they're not used to.

Don't use whitespace other than blanks. The only whitespace in input and output is blanks and newlines. The general rule is that only single blanks appear, and only after the beginning of a line and before the end of a line, and there are no blank lines. In some cases, for instance with output in columns, you may want to break this rule and allow multiple blanks and blanks at the beginning of some lines. In this case mention the rule change explicitly.

Don't use NaNs and infinities. The floating-point units in modern processors support NaNs (Not-a-Number) and infinities. These are wonderful but are only directly supported by Java. (Support for the other languages is compiler-dependent.) Don't use them.

Eliminate end-of-line issues. The problem solution should not explicitly depend on the newline character sequence of a particular operating system. The judges will make sure data files are available both in Windows and Unix formats.

