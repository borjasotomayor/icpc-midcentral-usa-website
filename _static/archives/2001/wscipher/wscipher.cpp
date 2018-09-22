#include <iostream>
#include <algorithm>
#include <string>
#include <fstream>

using namespace std;

int
main ()
{

    const int MAXCHAR = 80;
    int k1, k2, k3;
    int s1[MAXCHAR], s2[MAXCHAR], s3[MAXCHAR];
    unsigned s1size, s2size, s3size;
    bool generate = false;

    ifstream infile ("wscipher.in");
    if (!infile) {
	cerr << "Can't open wscipher.in" << endl;
	exit (2);
    }

    ofstream outfile ("wscipher.out");
    if (!outfile) {
	cerr << "Can't open wscipher.out" << endl;
	exit (2);
    }


    while ((infile >> k1 >> k2 >> k3) && (k1 + k2 + k3 != 0)) {
	unsigned i;
	string instring;
	int target;

	infile.ignore (80, '\n');
	infile >> instring;     // ok, no spaces
	if (generate)
	    reverse (instring.begin (), instring.end ());
	string outstring (instring);

	s1size = s2size = s3size = 0;

	for (i = 0; i < instring.size (); i++) {
	    if (instring[i] >= 'a' && instring[i] <= 'i')
		s1[s1size++] = i;
	    else if (instring[i] >= 'j' && instring[i] <= 'r')
		s2[s2size++] = i;
	    else
		s3[s3size++] = i;
	}

	for (i = 0; i < s1size; i++) {
	    target = s1[(i + k1) % s1size];
	    outstring[target] = instring[s1[i]];
	}
	//cerr << "outstring = " << outstring << endl;

	for (i = 0; i < s2size; i++) {
	    target = s2[(i + k2) % s2size];
	    outstring[target] = instring[s2[i]];
	}
	//cerr << "outstring = " << outstring << endl;

	for (i = 0; i < s3size; i++) {
	    target = s3[(i + k3) % s3size];
	    outstring[target] = instring[s3[i]];
	}
	//cerr << "outstring = " << outstring << endl;

	if (generate) {
	    outfile << k1 << " " << k2 << " " << k3 << endl;
	    reverse (outstring.begin (), outstring.end ());
	}

	//cerr << "outstring = " << outstring << endl;

	outfile << outstring << endl;

    }
    return 0;
}
