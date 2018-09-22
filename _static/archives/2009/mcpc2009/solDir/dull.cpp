/*  DuLL MCPC 2009 Problem C, by Gabriel Foust
*/

#include <iostream>
#include <fstream>
using namespace std;

ifstream fin;
int numDlls;
int numPrograms;
int numTransitions;
int dllSizes[20];
int dllReferences[20];
int programSizes[9];
string programDlls[9];

bool GetNextInputSet()
{
	numDlls= 0;
	fin >> numDlls;
	if (numDlls <= 0)
		return false;

	fin >> numPrograms >> numTransitions;

	for (int i= 0; i < numDlls; i++)
		fin >> dllSizes[i];

	for (int i= 0; i < numPrograms; i++)
	{
		fin >> programSizes[i];
		fin >> programDlls[i];
                for (int j = 0; j < programDlls[i].length(); j++) {//judge check
                    char ch = programDlls[i][j];
                    if (ch < 'A' || ch - 'A' >= numDlls)
               		cerr << "letter out of range" << endl;
                }
	}

	return true;
}

int AddReferences( int prog )
{
	string dlls= programDlls[prog - 1];

	int adjustment= 0;

	for (size_t i= 0; i < dlls.length(); i++)
	{
		int d= dlls[i] - 'A';
		if (dllReferences[d] == 0)
			adjustment+= dllSizes[d];
		dllReferences[d]++;
	}

	return adjustment;
}

int RemoveReferences( int prog )
{
	string dlls= programDlls[prog - 1];

	int adjustment= 0;

	for (size_t i= 0; i < dlls.length(); i++)
	{
		int d= dlls[i] - 'A';
		dllReferences[d]--;
		if (dllReferences[d] == 0)
			adjustment+= dllSizes[d];
	}

	return adjustment;
}

int MaxMemory()
{
	for (int d= 0; d < numDlls; d++)
		dllReferences[d]= 0;

	int memory= 0;
	int maxMemory= 0;

	for (int i= 0; i < numTransitions; i++)
	{
		int t;
		fin >> t;

		if (t > 0)
		{
			memory+= programSizes[t - 1];
			memory+= AddReferences( t );
		}
		else
		{
			memory-= programSizes[-t - 1];
			memory-= RemoveReferences( -t );
		}

		cerr << memory << endl;
		if (memory > maxMemory)
			maxMemory= memory;
	}

	return maxMemory;
}

int main()
{
	int count= 1;
	fin.open( "dull.in" );
	while (GetNextInputSet())
	{
		cerr << "Test #" << count++ << endl;
		cout << MaxMemory() << endl;
	}
	fin.close();

	return 0;
}
