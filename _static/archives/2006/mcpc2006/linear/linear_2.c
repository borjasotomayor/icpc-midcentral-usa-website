/* Alternative solution, by Ron Pacheco */

#include <stdio.h>

int n;        // number of symbols in machine specification
char m[100];  // the pachinko machine

// p(i,d): probability that ball will fall through at
//         position i moving in direction d

float p( int i, int d ) {
  if ( i < 0 || i >= n || m[i] == '.' )
    return 1.0;
  switch ( m[i] ) {
  case '_':
    if ( d == 0 )
      return 0.0;
    else
      return p( i + d, d );
    break;
  case '|':
    if ( d == 0 )
      return ( p( i - 1, -1 ) + p( i + 1, +1 ) ) / 2.0;
    else
      return 0.0;
    break;
  case '/':
    if ( d == 0 )
      return p( i - 1, -1 );
    else
      return 0.0;
    break;
  case '\\':
    if ( d == 0 )
      return p( i + 1, +1 );
    else
      return 0.0;
    break;
  }
}

float solve() {
  int i;
  float f = 0.0;
  for ( i = 0; i < n; i++ )
    f += p( i, 0 );
  return 100.0 * f / n;
}

int main() {
  FILE *in = fopen( "linear.in", "r" );
  char c;
  n = 0;
  while ( ( c = fgetc( in ) ) != '#' ) {
    if ( c == '\n' ) {
      printf( "%d\n", (int) solve() );
      n = 0;
    }
    else
      m[n++] = c;
  }
  fclose( in );
}
