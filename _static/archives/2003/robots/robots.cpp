// 2003 ACM Mid-Central Regional Programming Contest
// Problem B: Robots
// by Greg Eastman, Drury University
//
//This thing works because locations are read in row 
//major order.  Otherwise we need something more 
//sophisticated.
#include <iostream>
#include <fstream>
#include <vector>
using namespace std;

const int MAXROWS = 24;
typedef vector<int> ROW;

/////////////////////////////////////////////////////////////////////////
//An array of row vectors.
class fieldmap
{
public:
	fieldmap();
	////////////////////////////////////////////////////////////////////
	//poke()
	//Used to populate a field map
	//Postcondition - A column is appended to the appropriate row vector.  
	void poke(int row, int col);

	////////////////////////////////////////////////////////////////////
	//clean()
	//Precondition - The garbage locations must have been added in row
	//major order.
	//Postcondition - All garbage deleted from row vectors
	//returns the minimum number of robots needed to clean the field
	int clean();

	///////////////////////////////////////////////////////////////////
	//Used for console output
	int get_garbage_count() { return m_garbage_count; }
private:
	////////////////////////////////////////////////////////////////////
	//peel()
	//A "peel" is the path a single robot takes accross a map.
	//It removes a layer of the leftmost columns much like
	//peeling the skin from an onion.  The optimal solution
	//involves repeated calls to peel until the map is empty.
	//Precondition - The map is not empty.
	//Postcondition - The leftmost layer of column nodes is
	//removed from the map.
	void peel();

	///////////////////////////////////////////////////////////////////
	//pluck_stuff()
	//Postcondition - all columns in row (if any) representing columns less
	//than or equal to the anchor are removed.  This function returns
	//the value of the lowest column removed or the oringinal value
	//of the anchor if none are removed.
	int pluck_stuff(int row, int anchor);

	//Just for convenience
	int m_last_row;
	int m_garbage_count;
	//An array of row vectors.
	ROW m_fieldrow[MAXROWS];
};

/////////////////////////////////////////////////////////////////////////
//Field Map Implementation
fieldmap::fieldmap()
{
	m_garbage_count = 0;
	m_last_row = -1;
}

void fieldmap::poke(int row, int col)
{
	m_fieldrow[row - 1].push_back(col);
	m_last_row = row - 1;
	m_garbage_count++;
}

int fieldmap::pluck_stuff(int row, int anchor)
{
	int ret_anchor;

	//Anchor is smallest of current column head on this row.
	//or anchor passed in.
	ret_anchor = anchor;
	if( m_fieldrow[row].front() < anchor)
		ret_anchor = m_fieldrow[row].front();

	//Pull stuff off until the column is greater than the anchor
	//value (not part of this peel) or until the row is empty.
	while(m_fieldrow[row].size() > 0 && m_fieldrow[row].front() <= anchor)
	{
		m_fieldrow[row].erase(m_fieldrow[row].begin());
		m_garbage_count--;
	}
	return ret_anchor;
}

void fieldmap::peel()
{
	int anchor;
	int row;

	//Find the last populated row
	while(m_fieldrow[m_last_row].empty())
		m_last_row--;

	row = m_last_row;
	//Initialize anchor and then strip the last row.
	anchor = m_fieldrow[m_last_row].front();
	m_garbage_count = m_garbage_count - (int)m_fieldrow[m_last_row].size();
	m_fieldrow[m_last_row].clear();
	row--;
	while( row >= 0)
	{
		//pluck some stuff
		if(!m_fieldrow[row].empty())
			anchor = pluck_stuff(row, anchor);
		row--;
	}
}

int fieldmap::clean()
{
	int anchor = 0;
	int robot_count = 0;

	while(m_garbage_count != 0)
	{
		//Each robot peels a layer
		peel();
		robot_count++;
	}
	m_last_row = -1;
	return robot_count;
}

int main()
{
	ifstream in;
	ofstream out;
	int row, col;
	fieldmap field;
	int rcount;
	int fieldcount = 0;

	//Open input adn output
	in.open("robots.in");
	if(in.fail())
	{
		cout << "Unable to open input." << endl;
		exit( 1 );
	}

	out.open("robots.out");
	if(out.fail())
	{
		cout << "Unable to open output." << endl;
		in.close();
		exit( 1 );
	}

	//I can do this because I guarantee there is at least one field map in an input file
	in >> row >> col;
	//While not EOF
	while ( row >= 0 )
	{
		//While not end of field
		while (row > 0)
		{
			field.poke(row, col);
			in >> row >> col;
		}
		fieldcount++;
		//////////////////////////////////////////////////////////////////
		//Console output to monitor progress.
		cout << "Field " << fieldcount << " Garbage count " << field.get_garbage_count();
		///////////////////////////////////////////////////////////////////
		//Contest output
		rcount = field.clean();
		out << rcount << endl;
		///////////////////////////////////////////////////////////////////
		//Console output to monitor progress.
		cout << " Robot count " << rcount << endl;
		//Read in next pair
		in >> row >> col;
	}
	out.close();
	in.close();

	return 0;
}
