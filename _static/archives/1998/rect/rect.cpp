// rect.cpp   Andy Harrington Loyola University Chicago 9/98
#include <fstream.h>
struct Point {
    int x, y;
    char label;
};
const int MAXSIZE = 26;
const int PERLINE = 10;

void main() {
    int nSet = 0, nRect, nPoints;
    Point pt[MAXSIZE];  
    int hiLt, hiRt, loRt, loLt;   // indices in pt for vertices in order
                           // high left, high right, low right, low left
    ifstream inf("rect.in");
    ofstream outf("rect.out");
    
    inf >> nPoints;
    while (nPoints > 0) {
        for(int i=0; i < nPoints; i++) 
            inf >> ws >> pt[i].label >> pt[i].x >> pt[i].y;
        nSet++;
        outf << "Point set " << nSet <<':';
        nRect = 0;
        for(hiLt = 0; hiLt < nPoints; hiLt++)
          for(hiRt = 0; hiRt < nPoints; hiRt++)
            if (pt[hiRt].y == pt[hiLt].y && pt[hiRt].x > pt[hiLt].x)
              for(loRt = 0; loRt < nPoints; loRt++)
                if (pt[hiRt].x == pt[loRt].x && pt[loRt].y < pt[hiRt].y)
                  for(loLt = 0; loLt < nPoints; loLt++)
                    if (pt[loLt].x==pt[hiLt].x && pt[loLt].y==pt[loRt].y){
                        if (nRect % PERLINE == 0) outf << endl;
                        nRect++;
                        outf << ' ' << pt[hiLt].label << pt[hiRt].label <<
                                       pt[loRt].label << pt[loLt].label;
                    }
        if (nRect == 0) outf << " No rectangles";
        outf << endl;
        inf >> nPoints;
    }
}

            