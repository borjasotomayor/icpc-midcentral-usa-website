/* ACM '98 Mid-Central Regional Programming Contest
 * Problem 4: Self Numbers
 * by Eric Shade, SMSU
 */

#include <stdio.h>

/* took 19015 steps (iterations of while loop in generate_sequence */

#define N 10000

unsigned generator[N];

unsigned sum_of_digits (unsigned n)
{
  if (n < 10)
    return n;
  else
    return (n % 10) + sum_of_digits (n / 10);
}


void generate_sequence (unsigned n)
{
  while (n < N)
  {
    unsigned next = n + sum_of_digits (n);
    if (next >= N || generator[next] != next)
      return;
    generator[next] = n;
    n = next;
  }
}


void main ()
{
  FILE *out;
  unsigned n;

  for (n = 1; n < N; ++n)
    generator[n] = n;

  for (n = 1; n < N; ++n)
    generate_sequence (n);

  out = fopen ("self.out", "w");
  for (n = 1; n < N; ++n)
    if (generator[n] == n)
      fprintf (out, "%u\n", n);
  fclose (out);
}
