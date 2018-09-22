#include <iostream>
#include <fstream>
#include <math.h>

using namespace std;

struct point
{
    int x, y;
      point (int xin, int yin)
    {
	x = xin;
	y = yin;
    };
    point () {
	x = 0;
	y = 0;
    };
};

struct vector
{
    double dx, dy;
};

struct line
{
    vector norm;
    double D;
};

vector p2v (point & p1, point & p2)
{
    vector v;
    v.dx = p2.x - p1.x;
    v.dy = p2.y - p1.y;
    return v;
}

double
dot (const vector v1, const vector v2)
{
    return (v1.dx * v2.dx + v1.dy * v2.dy);
}

void
pointnormal (const point p1, const point p2, vector & n, line & l)
{
    n.dx = p2.y - p1.y;
    n.dy = p1.x - p2.x;

    l.norm = n;
    l.D = n.dx * p1.x + n.dy * p1.y;
}

double
distance (point p1, point p2)
{
    return sqrt ((p1.x - p2.x) * (p1.x - p2.x) +
		 (p1.y - p2.y) * (p1.y - p2.y));
}

int
main ()
{
    line l;
    vector n;

    const int MAXPOINTS = 150;
    point center;
    double radius;
    point test_points[MAXPOINTS];
    int num_points;
    int ok_points;
    int i;

    ifstream infile ("transmit.in");
    if (!infile) {
	cerr << "Can't open transmit.in" << endl;
	exit (2);
    }

    ofstream outfile ("transmit.out");
    if (!outfile) {
	cerr << "Can't open transmit.out" << endl;
	exit (2);
    }



    while ((infile >> center.x >> center.y >> radius) && radius > 0.0) {
	infile >> num_points;
	ok_points = 0;
	for (i = 0; i < num_points; i++) {
	    infile >> test_points[ok_points].x >> test_points[ok_points].y;
	    if (distance (test_points[ok_points], center) <= radius)
		ok_points++;
	}

	int max_in_semi = 0;
	int pos = 0;
	int neg = 0;
	int zero = 0;

	for (i = 0; i < ok_points; i++) {
	    pointnormal (center, test_points[i], n, l);
	    pos = neg = zero = 0;

	    for (int j = 0; j < ok_points; j++) {
		double thisdot;
		thisdot = dot (n, p2v (center, test_points[j]));
		if (fabs (thisdot) < 0.000005)
		    zero++;
		else if (thisdot < 0)
		    neg++;
		else
		    pos++;

	    }
	    if ((neg + zero) > max_in_semi)
		max_in_semi = neg + zero;
	    if ((pos + zero) > max_in_semi)
		max_in_semi = pos + zero;

	}
	outfile << max_in_semi << endl;
    }
    return 0;
}
