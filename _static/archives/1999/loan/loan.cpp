#include <iostream.h>
#include <fstream.h>
#include <stdlib.h>


int main ()
{
	const int max_month = 100;
	int duration, depcount, this_month, dep_index, dep_month[max_month+1];
	int i;
	double down, initloan, current_value, monthly_payment,
	current_owe, depreciation, dep_amount[max_month+1];

	ifstream infile("loan.in");
	if (!infile) {
		cerr << "Can't open loan.in" << endl; exit(2);
	}

	ofstream outfile("loan.out");
	if (!outfile) {
		cerr << "Can't open loan.out" << endl; exit(2);
	}

	infile >> duration >> down >> initloan >> depcount;

	while (duration >= 0) 
	{
	   for (i=0; i<depcount; i++) 
		infile >> dep_month[i] >> dep_amount[i];

	   this_month = 0;
	   dep_index = 0;
	   current_value = down + initloan;
	   monthly_payment = initloan / duration;
	   current_owe = initloan;

	   depreciation = dep_amount[dep_index];
	   current_value *= (1-depreciation);
	   dep_index = 1;

	   while (current_value < current_owe)
	   {
		current_owe -= monthly_payment;

		this_month++;
		if (dep_index < depcount && this_month >= dep_month[dep_index])
		{
		   depreciation = dep_amount[dep_index];
		   dep_index++;
		}
	   	current_value *= (1-depreciation);
	   }
	
	   outfile << this_month << " month" << (this_month==1?"":"s") << endl;
	   infile >> duration >> down >> initloan >> depcount;
	}
	return 0;
}

