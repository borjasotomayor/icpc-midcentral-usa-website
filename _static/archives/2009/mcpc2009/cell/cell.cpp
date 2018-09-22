/* Cell Towers, MCPC 2009 Problem I, by Gabriel Foust
 *
 * Check each mile post to see which is the strongest tower
 * Report each time it changes
 */

#include <cmath>
#include <fstream>
#include <iostream>
#include <limits>
using namespace std;

ifstream fin( "cell.in" );

struct Point
{
	double x, y;
	Point() :
		x( 0 ), y( 0 )
	{
	}
	Point( double x, double y ) :
		x( x ), y( y )
	{
	}
};

int T;
Point towers[10];
double powers[10];
int R;
Point roads[11];

double ptDistance( Point p, Point q )
{
	double d1= p.x - q.x;
	double d2= p.y - q.y;
	return sqrt( d1 * d1 + d2 * d2 );
}

int dataset= 0;
bool getDataSet()
{
	fin >> T;
	if (T <= 0)
		return false;
	fin >> R;

	for (int i= 0; i < T; i++)
		fin >> towers[i].x >> towers[i].y >> powers[i];

	for (int i= 0; i < R + 1; i++)
		fin >> roads[i].x >> roads[i].y;

	cerr << "Data Set " << ++dataset << endl;
	return true;
}

int towerForPoint( Point p )
{
	int tower= 0;
	double d= ptDistance( towers[0], p );
	double realS= powers[0] / d / d;
	int s= round( realS );
	int strength= s;

	// realStrength at nextStrength are only used to keep track of the difference
	// between the winner and the runner up - wanted to watch out for close calls
	// Not relevant to the solution
	double realStrength= realS;
	double nextStrength= -1e6;

	for (int t= 1; t < T; t++)
	{
		d= ptDistance( towers[t], p );
		realS= powers[t] / d / d;
		s= round( realS );
		if (s == strength)
		{
			if (t < tower)
			{
				tower= t;
				strength= s;
			}

			if (round( realStrength ) == round( nextStrength ))
			{
				if (realS > realStrength)
					realStrength= realS;
				else if (realS < nextStrength)
					nextStrength= realS;
			}
			else
			{
				if (realS > realStrength)
				{
					nextStrength= realStrength;
					realStrength= realS;
				}
				else
				{
					nextStrength= realS;
				}
			}
		}
		else if (s > strength)
		{
			tower= t;
			strength= s;
			nextStrength= realStrength;
			realStrength= realS;
		}
		else if (realS > nextStrength)
		{
			nextStrength= realS;
		}
	}

	double realDiff= realStrength - nextStrength;
	if (realDiff < 2.5 && realDiff > 0.1)
	{
		cerr << '(' << p.x << ',' << p.y << ") = " << realStrength << " vs. " << nextStrength << endl;
	}

	return tower;
}

void tracePath()
{
	int tower= towerForPoint( roads[0] );
	cout << "(0," << (char)('A' + tower) << ")";

	int mile= 0;
	double distance= 0;

	for (int r= 0; r < R; r++)
	{
		double length= ptDistance( roads[r], roads[r + 1] );
		double dx= roads[r + 1].x - roads[r].x;
		double dy= roads[r + 1].y - roads[r].y;

		while (distance < length)
		{
			Point p;
			if (dx == 0)
			{
				p.x= roads[r].x;
				p.y= roads[r].y + (dy >= 0 ? distance : -distance);
			}
			else if (dy == 0)
			{
				p.x= roads[r].x + (dx >= 0 ? distance : -distance);
				p.y= roads[r].y;
			}
			else
			{
				double m= fabs( dx / dy );

				double yside= sqrt( distance * distance / (m * m + 1) );
				double xside= m * yside;

				p.x= roads[r].x + (dx >= 0 ? xside : -xside);
				p.y= roads[r].y + (dy >= 0 ? yside : -yside);
			}

			int t= towerForPoint( p );
			if (t != tower)
			{
				tower= t;
				cout << " (" << mile << "," << (char)('A' + tower) << ")";
			}

			mile++;
			distance++;
		}

		distance-= length;
	}

	if (distance < .5)
	{
		int t= towerForPoint( roads[R] );
		if (t != tower)
		{
			tower= t;
			cout << " (" << mile << "," << (char)('A' + tower) << ")";
		}
	}

	cout << endl;
}

int main()
{
	while (getDataSet())
		tracePath();
	return 0;
}
