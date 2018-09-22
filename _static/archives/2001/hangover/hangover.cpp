#include <fstream>


std::ifstream in ("hangover.in");
std::ofstream out ("hangover.out");


int main (int argc, char* argv[])
{
    for (;;)
    {
        float c;

        in >> c;

        if (c == 0.0) break;

        float sum = 0.0;
        int n;

        for (n = 0; sum < c; ++n)
            sum += 1.0f / (n + 2);

        out << n << " card(s)\n";
    }

    in.close ();
    out.close ();
}
