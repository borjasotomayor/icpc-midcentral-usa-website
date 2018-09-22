/*
// 2006 ACM Mid-Central USA Regional Programming Contest
// Solution to "Quicksum" [easy]
// Ron Pacheco
*/
#include <stdio.h>

int main() {
  FILE *in = fopen( "quicksum.in", "r" );
  char c;
  int n = 0, q = 0;
  while ( ( c = fgetc( in ) ) != '#' ) {
    if ( c == '\n' ) {
      printf( "%d\n", q );
      n = 0;
      q = 0;
    }
    else
      q += ++n * ( ( c == ' ' ) ? 0 : ( c - 'A' + 1 ) );
  }
  fclose( in );
}
