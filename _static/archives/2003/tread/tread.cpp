// 2003 ACM Mid-Central Regional Programming Contest
// Problem D: Treadmill
// by Eugene Styer, Eastern Kentucky University
//
// tread.cpp - extract treadmill chirp commands from an audio file
#include<iostream>
#include<fstream>
#include<iomanip>

using namespace std;

const int SamplesPerSecond = 3;

// sample test functions
bool IsLow(int x)  { return (x < 2); }
bool IsMid(int x)  { return (x==4 || x==5); }
bool IsHigh(int x) { return (x > 7); }

long SampleCount=0;

// get one sample and count it off
int GetSample( istream &i )
{
    int t;

    i >> t;
    SampleCount++;
    return t;
}

// get one bit from the input
// will check for samples that don't represent valid bits ("3", or "0 0", etc.)
// return -1 for illegal value, or the bit (0/1)
int GetBit( istream &i )
{
    int CurSample;

    // get first sample for bit
    CurSample = GetSample( i );
    if ( IsLow( CurSample ) )
    {
	// expect L H
	if ( IsHigh( GetSample( i ) ) )
	{
	    // got L H = 1 bit
	    //cout << "GetBit returning 1" << endl;
	    return 1;
	}
    }
    else if ( IsMid( CurSample ) )
    {
	// expect M M
	if ( IsMid( GetSample( i ) ) )
	{
	    // got M M = 0 bit
	    //cout << "GetBit returning 0" << endl;
	    return 0;
	}
    }

    // result not a valid 0 or 1 bit
    cout << "GetBit error" << endl;
    return -1;
}

// get one value from the input
// return value or -1 for illegal item
int GetValue( istream &i )
{
    int val;

    // get the samples and assemble them into a number
    val =
	   GetBit(i)		// least significant bit
	| (GetBit(i)<<1)	// middle bit
	| (GetBit(i)<<2);	// most significant bit

    // do error check (assumes 2's complement numbers)
    if ( val < 0 )
	val = -1;		// we had a problem
    //cout << "GetValue returning " << val << endl;
    return val;
} // GetValue

// output "Bad Chirp message)
void OutBad( ostream &o, int Minutes, int Seconds )
{
    o << setfill('0') << setw(2) << Minutes << ":"
	 << setfill('0') << setw(2) << Seconds
	 << " Bad Chirp" << endl;
    cout << setfill('0') << setw(2) << Minutes << ":"
	 << setfill('0') << setw(2) << Seconds
	 << " Bad Chirp" << endl;
} // OutBad

int main()
{
    ifstream inaudio;		// input audio file
    ofstream outwork;		// output workout

    // open files
    inaudio.open("tread.in");
    outwork.open("tread.out");

    int NumProgram;
    inaudio >> NumProgram;
   for (int z=0; z<NumProgram; z++) { 
    int NewSpeed, NewInc, Checksum;	// values from chirp
    int CurSample=1;
    int Past1=3, Past2=3, Past3=3;	// recent past samples
    int StartTime;			// starting time in seconds
    int Minutes, Seconds;		// starting time as mm:ss

    SampleCount = 0;
    outwork << "Program " << z+1 << endl;


    while( CurSample < 10 )
    {
	// shift our last samples, get the next one
	Past3 = Past2;
	Past2 = Past1;
	Past1 = CurSample;
	CurSample = GetSample( inaudio );

	// start sequence??
	if ( IsMid( Past3 ) && IsMid( Past2 )
		&& IsLow( Past1 ) && IsHigh( CurSample ) )
	{
		// have M M L H - start bit sequence is finished

		// when did the chirp start?
		StartTime = (SampleCount-4)/3;
		Minutes = StartTime / 60;
		Seconds = StartTime % 60;
		cout << "Chirp at count " << (SampleCount-4) << endl;

		// get speed, inclination, checksum
		NewSpeed = GetValue( inaudio );
		if ( NewSpeed < 0 )
		{   // had an error
		    cout << "Bad Speed: " << NewSpeed << endl;
		    OutBad( outwork, Minutes, Seconds );
		    continue;
		}
		NewInc   = GetValue( inaudio );
		if ( NewInc < 0 )
		{   // had an error
		    cout << "Bad Inclination: " << NewInc << endl;
		    OutBad( outwork, Minutes, Seconds );
		    continue;
		}
		// output time and speed/inclination
		outwork << setfill('0') << setw(2) << Minutes << ":"
		        << setfill('0') << setw(2) << Seconds
			<< " Speed " << NewSpeed
			<< " Inclination " << NewInc << endl;
		cout << setfill('0') << setw(2) << Minutes << ":"
		     << setfill('0') << setw(2) << Seconds
		     << " Speed " << NewSpeed
		     << " Inclination " << NewInc << endl;
	} // end - have valid start sequence
    } // end While
  }
  inaudio.close();
  outwork.close();
}
