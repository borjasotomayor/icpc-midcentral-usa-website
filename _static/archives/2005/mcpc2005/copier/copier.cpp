// 2005 ACM Mid-Central Regional Programming Contest
// Problem D: Copier Reduction
// Posed by John Cigas, Rockhurst University
// Written and Coded by Eric Shade, Missouri State University

#include <iostream>
#include <fstream>
#include <cmath>
using namespace std;


int main ()
{
    ifstream in("copier.in");

    int imageX, imageY, paperX, paperY;

    for (;;) {
        in >> imageX >> imageY >> paperX >> paperY;
        
        if (imageX == 0) break;
        
        // rotate image so that X <= Y
        if (imageX > imageY) {
            int temp = imageX;
            imageX = imageY;
            imageY = temp;
        }

        // rotate paper so that X <= Y
        if (paperX > paperY) {
            int temp = paperX;
            paperX = paperY;
            paperY = temp;
        }

        double scaleX = static_cast<double>(paperX) / imageX;
        double scaleY = static_cast<double>(paperY) / imageY;

        double scale = scaleX < scaleY ? scaleX : scaleY;
        
        scale = floor(scale * 100.0);
        if (scale > 100.0) scale = 100.0;
        
        cout << static_cast<int>(scale) << "%" << endl;
    }

    in.close();
}
