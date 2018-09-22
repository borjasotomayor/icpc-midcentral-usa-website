// 1998 ACM Mid-Central Regional Programming Contest
// Problem 1: Booklet Printing
// by John Cigas, Rockhurst

#include <iostream.h>
#include <fstream.h>
#include <stdlib.h>

const int blank = -1;
const int MAXPAGE = 100;

void printpage (int pgnum, ostream & s)
{
	if (pgnum == blank)
		s << "Blank";
	else
		s << pgnum;
}

int main ()
{
	int numpages;
	int pages [MAXPAGE];
	ifstream infile("booklet.in");
	if (!infile) {
		cerr << "Can't open booklet.in" << endl; exit(2);
	}

	ofstream outfile("booklet.out");
	if (!outfile) {
		cerr << "Can't open booklet.out" << endl; exit(2);
	}
	cout = outfile;

	infile >> numpages;
	while (numpages != 0)
	{
		int maxpages = numpages;
		if (numpages % 4 != 0)
			maxpages += (4-numpages % 4);
		for (int i=0; i<numpages; i++)
			pages[i] = i+1;
		for (int i=numpages; i<maxpages; i++)
			pages[i] = blank;

		int min = 0; int max = maxpages-1; int thissheet = 1;
		cout << "Printing order for " << numpages << " pages:" << endl;
		for (int i=0; i<maxpages; i+=4)
		{
			cout << "Sheet " << thissheet << ", front: ";
			printpage(pages[max--],cout);
			cout << ", ";
			printpage(pages[min++],cout);
			cout << endl;
			if (numpages == 1) break;
			cout << "Sheet " << thissheet << ", back : ";
			printpage(pages[min++],cout);
			cout << ", ";
			printpage(pages[max--],cout);
			cout << endl;
			thissheet++;
		}
		infile >> numpages;
	}
	return 0;
}

