/* Image Compression, MCPC 2010 Problem H by Michael Goldwasser */

#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

// love globals
fstream fin("compress.in");
string image[64];     // use array of strings in place of 2d array
int W,T;

// ranges are half-open as [xStart,xEnd)
void compress(int xStart, int xEnd, int yStart, int yEnd) {
  // first count current region
  int ones = 0;
  for (int x=xStart; x < xEnd; x++)
    for (int y=yStart; y< yEnd; y++)
      if (image[y][x] == '1')
        ones++;

  int total = (xEnd-xStart)*(yEnd-yStart);
  int majority = max(ones, total-ones);

  if (majority*100  >= T * total) {
    // base case
    char charval = (ones > total-ones) ? '1' : '0';

    for (int x=xStart; x < xEnd; x++)
      for (int y=yStart; y< yEnd; y++)
        image[y][x] = charval;
  } else {
    // four quadrants in proper order
    int xMid = (xStart + xEnd) / 2;
    int yMid = (yStart + yEnd) / 2;
    compress(xMid, xEnd, yStart, yMid);    // top-right
    compress(xStart, xMid, yStart, yMid);  // top-left
    compress(xStart, xMid, yMid, yEnd);    // bottom-left
    compress(xMid, xEnd, yMid, yEnd);      // bottom-right
  }
}

int main() {
  int count=0;
  fin >> W;
  while (W > 0) {
    count++;
    fin >> T;
    for (int i=0; i<W; i++)
      fin >> image[i];

    compress(0, W, 0, W);
    cout << "Image " << count << ":" << endl;
    for (int i=0; i<W; i++)
      cout << image[i] << endl;

    fin >> W;
  }
}
