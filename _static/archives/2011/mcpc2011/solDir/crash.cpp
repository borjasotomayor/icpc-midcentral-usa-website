/* Crash and Go(relians), MCPC 2011 Problem H, C++ solution by Gabriel Foust */
#include <cmath>
#include <fstream>
#include <iostream>
#include <list>
using namespace std;

ifstream fin( "crash.in" );

struct Point { double x, y, r; };
list<Point> points;

bool InRange( Point one, Point two )
{
    double dx= one.x - two.x;
    double dy= one.y - two.y;
    double distance= sqrt( dx*dx + dy*dy );

    // This is just to ensure that our test cases stay far away from rounding error
    if (abs( distance - one.r  ) < 1 || abs( distance - two.r ) < 1)
        cerr << "Warning: cutting it a little close\n";

    return (round( distance ) <= round( one.r ) || round( distance ) <= round( two.r ));
}

void AddPoint( Point s )
{
    list<Point> group;
    group.push_back( s );

    list<Point>::iterator i= points.begin();
    while (i != points.end())
    {
        if (InRange( s, *i ))
        {
            group.push_back( *i );
            points.erase( i++ );
        }
        else
            ++i;
    }

    if (group.size() == 1)
        points.push_back( s );
    else
    {
        Point combined;
        combined.x= combined.y= combined.r= 0.0;
        for (i= group.begin(); i != group.end(); ++i)
        {
            combined.x+= i->x;
            combined.y+= i->y;
            combined.r+= i->r*i->r;
        }
        combined.x/= group.size();
        combined.y/= group.size();
        combined.r= sqrt( combined.r );
        AddPoint( combined );
    }
}

int main( int argc, char** argv )
{
    int point_count;
    while (fin >> point_count && point_count > 0)
    {
        for (int i= 0; i < point_count; ++i)
        {
            Point s;
            fin >> s.x >> s.y >> s.r;
            AddPoint( s );
        }

        cout << points.size() << endl;
        points.clear();
    }

    return 0;
}

