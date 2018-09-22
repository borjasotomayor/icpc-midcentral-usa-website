// 2001 ACM Mid-Central Regional Programming Contest
// Problem D: Index Generation
// Solution by Dr. Eric Shade, Southwest Missouri State University

#include <fstream>
#include <string>
#include <cctype>
#include <vector>
#include <algorithm>
using namespace std;


ifstream in ("indexgen.in");
ofstream out ("indexgen.out");

int page;
int ch;
int token;
bool lookahead = false;

const int END_OF_DOCUMENT = -1;
const int END_OF_FILE = -2;

struct Entry
{
    string primary;
    string secondary;
    int page;

    Entry (string p, string s) 
        : primary(p), secondary(s), page(::page) {};
    
};

vector<Entry> entry;


int string_compare (const string& s, const string& t)
{
    int m = s.length ();
    int n = t.length ();
    int k = m <= n ? m : n;
    
    for (int i = 0; i < k; ++i)
    {
        int a = toupper (s[i]);
        int b = toupper (t[i]);

        if (a != b) return a - b;
    }

    return m == n ? 0 : m < n ? -1 : 1;
}



bool less_than (const Entry& a, const Entry& b)
{
    int cmp = string_compare (a.primary, b.primary);
    if (cmp < 0) return true;
    if (cmp > 0) return false;
    cmp = string_compare (a.secondary, b.secondary);
    if (cmp < 0) return true;
    if (cmp > 0) return false;
    return a.page < b.page;
}



inline int next_char ()
{
    if (lookahead) lookahead = false; else ch = in.get ();
    return ch;
}



int next_token ()
{
    switch (next_char ())
    {
    case '*':
        token = (next_char () == '*') ? END_OF_FILE : END_OF_DOCUMENT;
        break;

    case ' ': case '\n':
       next_char ();
       if (ch == '%' || ch == '$' || ch == '}')
           token = ch;
           // fall into next case
       else {
           token = ' ';
           lookahead = true;
           break;
       }

    case '{': case '%': case '$':
       token = ch;
       lookahead = ! isspace (next_char ());
       break;

    default:
       token = ch;
    }

    return token;
}



inline bool is_delimiter (int t)
{
    return t == '%' || t == '$' || t == '}';
}



void add_entry ()
{
    string primary, secondary;

    while (! is_delimiter (next_token ()))
        primary += token;

    if (token == '%')
    {
        primary.erase ();
        while (! is_delimiter (next_token ()))
            primary += token;        
    }

    if (token == '$')
        while (! is_delimiter (next_token ()))
            secondary += token;

    entry.push_back (Entry (primary, secondary));
}



int main (int argc, char* argv[])
{
    for (int document = 1; ; ++document)
    {
        if (next_token () == END_OF_FILE) break;
        
        out << "DOCUMENT " << document;

        page = 1;
        
        // add a sentinel entry at the beginning
        entry.clear ();
        entry.push_back (Entry ("", ""));
        
        do {
            if (token == '&')
                ++page;
            else if (token == '{')
                add_entry ();
        } while (next_token () != END_OF_DOCUMENT);

        sort (entry.begin (), entry.end (), less_than);

        for (int i = 1; i < entry.size(); ++i)
        {
            if (entry[i].primary == entry[i-1].primary)
                if (entry[i].secondary == entry[i-1].secondary)
                {
                    if (entry[i].page != entry[i-1].page)
                        out << ", " << entry[i].page;
                }
                else
                    out << "\n+ " << entry[i].secondary 
                        << ", " << entry[i].page;
            else
            {
                out << '\n' << entry[i].primary;
                if (entry[i].secondary == "")
                    out << ", " << entry[i].page;
                else
                    out << "\n+ " << entry[i].secondary 
                        << ", " << entry[i].page;
            }
        }

        out << endl;
    }
    
    in.close ();
    out.close ();
}
