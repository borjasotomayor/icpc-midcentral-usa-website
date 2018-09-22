// 2001 ACM Mid-Central Regional Programming Contest
// Problem F: Double Vision
// Solution by Dr. Eric Shade, Southwest Missouri State University

#include <fstream>

// Some terminology for this solution: a "glyph" is the image of a single
// symbol, and a "picture" contains all the glyphs.  So each test case 
// contains exactly one picture but one or more glyphs.

const int MAX_ROWS = 10;
const int MAX_COLS = 79;

ifstream in ("double.in");
ofstream out ("double.out");

int glyphs;         /* number of glyphs in test case */
int rows;           /* height of glyph, in rows */
int cols;           /* width of glyph, in columns */

char picture[MAX_ROWS][MAX_COLS + 1];

#define pixel(g,r,c) (picture[r][(g) * ((cols) + 1) + (c)])
#define lit(g,r,c)   (pixel(g,r,c) != '.')



// Does at least one other glyph match g at pixel (r,c)?

bool match1 (int g, int r, int c)
{
    for (int h = 0; h < glyphs; ++h)
        if (h != g && lit (h,r,c))
            return true;

    return false;
}



// Does at least one other glyph match g at pixels (r,c) AND (r2,c2)?

bool match2 (int g, int r, int c, int r2, int c2)
{
    for (int h = 0; h < glyphs; ++h)
        if (h != g && lit (h,r,c) && lit (h,r2,c2))
            return true;

    return false;
}



bool solve (int g)
{
    for (int r = 0; r < rows; ++r)
        for (int c = 0; c < cols; ++c)
        {
            cout << g << ": 1 (" << r << ", " << c << ")\r";
            if (lit (g,r,c) && ! match1 (g,r,c))
            {
                pixel (g,r,c) = '#';
                return true;
            }
        }

    for (int r = 0; r < rows; ++r)
        for (int c = 0; c < cols; ++c)
        {
            cout << g << ": 2 (" << r << ", " << c << ")\r";
            if (lit (g,r,c))
            {
                // check the rest of the current row
                for (int c2 = c+1; c2 < cols; ++c2)
                    if (lit (g,r,c2) && ! match2 (g,r,c,r,c2))
                    {
                        pixel (g,r,c) = pixel (g,r,c2) = '#';
                        return true;
                    }
                    
                for (int r2 = r+1; r2 < rows; ++r2)
                    for (int c2 = 0; c2 < cols; ++c2)
                        if (lit (g,r2,c2) && ! match2 (g,r,c,r2,c2))
                        {
                            pixel (g,r,c) = pixel (g,r2,c2) = '#';
                            return true;
                        }
            }
        }

    return false;
}



int main (int argc, char* argv[])
{
    for (int test = 1; ; ++test)
    {
        in >> glyphs >> rows >> cols;
        in.ignore ();   // eat eol
        
        if (glyphs == 0) break;

        for (int r = 0; r < rows; ++r)
            in.getline (picture[r], MAX_COLS+1);

        out << "Test " << test << endl;

        bool impossible = false;
        
        for (int g = 0; ! impossible && g < glyphs; ++g)
            if (! solve (g))
            {
                cout << "Test " << test << ": can't solve glyph " << g << endl;
                impossible = true;
            }

        if (impossible)
            out << "impossible\n";
        else 
            for (int r = 0; r < rows; ++r)
                out << picture[r] << endl;
    }
        
    in.close ();
    out.close ();
}
