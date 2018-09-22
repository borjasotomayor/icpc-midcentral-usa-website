/* 2002 ACM Mid-Central Regional Programming Contest */
/* Problem D: Safecracker */
/* by Eric Shade, using an idea from Andy Harrington */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#define MAX_LETTERS 12

FILE *in;
FILE *out;

int key;                /* target key */
int codes;              /* number of letters/codes */

int K[MAX_LETTERS];     /* the numeric codes */

char best[6];           /* best combination found so far */


int is_solution (int u, int v, int w, int x, int y)
{
    return u - v*v + w*w*w - x*x*x*x + y*y*y*y*y == key;
}



void test_permutations (int start)
{
    int i;

    if (start == 5)
    {
        if (is_solution (K[0], K[1], K[2], K[3], K[4]))
        {
            char s[6];

            s[0] = K[0] + 'A' - 1;
            s[1] = K[1] + 'A' - 1;
            s[2] = K[2] + 'A' - 1;
            s[3] = K[3] + 'A' - 1;
            s[4] = K[4] + 'A' - 1;
            s[5] = '\0';

            printf ("\tFound solution %s.\n", s);

            if (strcmp (s, best) > 0) strcpy (best, s);
        }
    }
    else
    {
        for (i = start; i < codes; i++)
        {
            /* swap K[start] and K[i] */
            int temp = K[start];
            K[start] = K[i];
            K[i] = temp;

            test_permutations (start + 1);

            /* swap back */
            K[i] = K[start];
            K[start] = temp;
        }
    }
}



int main (void)
{
    in = fopen ("safe.in", "r");
    out = fopen ("safe.out", "w");

    for (;;)
    {
        int i;
        char s[MAX_LETTERS + 1];

        fscanf (in, "%d %s\n", &key, s);

        if (key == 0) break;

        codes = strlen (s);
        printf ("Key %d, letters %s (%d).\n", key, s, codes);
        for (i = 0; i < codes; i++)
            K[i] = s[i] - 'A' + 1;
        strcpy (best, "@@@@@");

        test_permutations (0);

        printf ("\tBest is %s.\n", best);
        if (strcmp (best, "@@@@@") != 0)
            fprintf (out, "%s\n", best);
        else
            fprintf (out, "no solution\n");
    }

    fclose (in);
    fclose (out);

    return 0;
}
