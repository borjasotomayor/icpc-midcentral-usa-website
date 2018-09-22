/* 2002 ACM Mid-Central Regional Programming Contest */
/* Problem A: Programmer, Rank Thyself */
/* by Dr. Eric Shade, SMSU */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define round(x) ((int) (x + 0.5))

#define MAX_TEAMS 20
#define MAX_NAME  10
#define SCORES     7

FILE *in;
FILE *out;

struct team
{
    char name[MAX_NAME + 1];
    int score[SCORES];
    int solved;
    int total;
    int gmean;
} team[MAX_TEAMS];

int contest = 0;
int teams;



void read_scores (void)
{
    int t;

    for (t = 0; t < teams; t++)
    {
        int i;
        float log_sum_f = 0.0;
        double log_sum_g = 0.0;
        float f = 0.0;
        double g = 0.0;

        fscanf (in, "%s ", team[t].name);

        team[t].solved = 0;
        team[t].total = 0;

        for (i = 0; i < SCORES; i++)
        {
            fscanf (in, "%d ", &team[t].score[i]);
            if (team[t].score[i] > 0)
            {
                team[t].solved++;
                team[t].total += team[t].score[i];
                log_sum_f += log (team[t].score[i]);
                log_sum_g += log (team[t].score[i]);
            }
        }

        if (team[t].solved > 0)
        {
            f = exp (log_sum_f / team[t].solved);
            g = exp (log_sum_g / team[t].solved);
        }

        if (round (f) != round (g))
            printf ("rounding error: float %.10g, double %.10g\n", f, g);

        team[t].gmean = round (f);
    }
}



int compare_teams (const void *x, const void *y)
{
    const struct team *p = x;
    const struct team *q = y;

    if (p->solved != q->solved)
        return q->solved - p->solved;
    else if (p->total != q->total)
        return p->total - q->total;
    else if (p->gmean != q->gmean)
        return p->gmean - q->gmean;
    else
        return strcmp (p->name, q->name);
}



void sort_scores (void)
{
    qsort (team, teams, sizeof (struct team), compare_teams);
}



int tied (int t, int u)
{
    return team[t].solved == team[u].solved
        && team[t].total == team[u].total
        && team[t].gmean == team[u].gmean;
}



void write_scores (void)
{
    int t;
    int rank = 1;

    for (t = 0; t < teams; t++)
    {
        int i;

        if (t > 0 && ! tied (t-1, t)) rank = t + 1;

        fprintf (out, "%02d %*s %1d %4d %3d",
                 rank, -MAX_NAME, team[t].name, team[t].solved,
                 team[t].total, team[t].gmean);

        for (i = 0; i < SCORES; i++)
            fprintf (out, "%4d", team[t].score[i]);

        fputc ('\n', out);
    }
}



int main (void)
{
    in = fopen ("rank.in", "r");
    out = fopen ("rank.out", "w");

    for (;;)
    {
        fscanf (in, "%d\n", &teams);

        if (teams == 0) break;

        fprintf (out, "CONTEST %d\n", ++contest);
        read_scores ();
        sort_scores ();
        write_scores ();
    }

    fclose (in);
    fclose (out);

    return 0;
}






