#include <fstream>
#include <iostream>
#include <string>
using namespace std;

const long long MAX = 1000000;

long long gcf( long long a, long long b )
{
  long long r;
  while (r= a % b)
    {
      a= b;
      b= r;
    }

  return b;
}

int main()
{
  ifstream fin( "egypt.in" );

  long long m, n;
  while (fin >> m >> n && m != 0)
    {
      bool first= true;
      while (m > 0)
        {
          long long denom= (n + m - 1) / m - 1;

          long long m2, n2;
          do
            {
              ++denom;
              long long f= gcf( n, denom );

              m2= (m * denom - n) / f;
              n2= n * denom / f;

              if (m2 > 0)
                {
                  f= gcf( n2, m2 );
                  m2/= f;
                  n2/= f;
                }
            }
          while (n2 > MAX);

          m= m2;
          n= n2;

          if (first)
            first= false;
          else
            cout << ' ';
          cout << denom;
        }
      cout << endl;
    }

  return 0;
}
