//numeral.java  2009 MCPC Problem G.  Andy Harrington
import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class numeral
{
  /*
  For easy comparison of optimality, candidates for the A-Z numeral
  will be kept as an array of two strings, the symbols counted
  positively and the symbols subtracted.  Each string will a keep
  non-increasing order of symbol magnitudes.

  The patterns data structure gives possible options merely optimizing
    combinations of 1 and 5 times a single power of 10,
    for cases with and without carry out:  You need both considered
    since a carry may or may not help in higher order digits.
  Note that any one character never needs to be subtracted
    more than twice, and that subtracting twice will only work
    when there are at least two higher order positive symbols.
  Alternatives to subtracting one character twice need to be considered, in 
    case there are not enough positive symbols later.  Hence the two carry 
    versions for 8.
  For each value 0 - 10 (10 included because of a possible carry in),
    there is a sequence of patterns with each pattern in the form
    {pos terms, neg terms, carry}.  Scale later by 10**power.
  */
    
  static final String[][][] patterns = {
      //[digit][seq #][0:pos terms, 1:neg terms, 2:carry]
     {{"", "", "0"}}, //  for 0-2 only subtractions would be 10's - next power
     {{"1", "",   "0"}}, //1
     {{"11", "",   "0"}}, //2
     {{"111", "",   "0"}, {"", "511", "1"}}, //3
     {{"5", "1",   "0"}, {"", "51", "1"}}, //4
     {{"5", "",   "0"}, {"", "5", "1"}}, //5
     {{"51", "",   "0"}, {"1", "5", "1"}}, //6
     {{"511", "",   "0"}, {"11", "5", "1"}}, //7
     {{"5111", "",   "0"}, {"", "11", "1"}, {"111", "5", "1"}}, //8
     {{"", "1", "1"}}, //9 carry always cheaper for 9-10, as in Roman Numerals
     {{"", "", "1"}} //10
   };

   /*
    Two algorithms are considered.

    1.  convertBruteForce:  The simpler is just to see that for each power
    of 10, subtractions can be none, -1, -2, -5, -6, and -7.  Then it is
    easy to recursively construct strings of all possible subtractions.
    For any such negative string, its magnitude can be added to the desired
    number and a representation of the larger number can be made easily
    with just positive terms.  Then an attempt can be made to intersperse
    the negative symbols to make a legal A to Z numeral.  If this
    succeeds, that numeral is tested to see if it is the most compact so
    far.  This algorithm is simple, but it has order at least 6 to the
    power of the number of digits in the number, too large for a complete
    solution with 18 digit numbers.  For testing purposes this code is 
    used as a reality check for small number, like not more than
    a million.  Keeping to small enough integers allow normal arithmetic
    notation, too, avoiding bigIntegers for arithmetic.

    The main algorithm below does not require arithmetic with large integers
    at all.  The *testing* code has been written with longs, so tests only
    work for numbers up to 18 digits.
      
    2.  convert: 
    Looking at the pattern lists above, you see the following
    possible implications/requirements for higher digits:
       carry  number of positive symbols needed (posNeeded)
           1  1
           1  2
           0  0
           0  1 (occurs after 48 with the double subtraction option)
    Thus a single best answer cannot be accumulated from the low powers
    up, but there are only 4 sets of conditions to defer satisfying, and
    hence only a maximum of four partial solutions need to be accumulated
    through increasing powers. For simplicity an array of options is
    kept indexed by
        [carry: the carry to the next place 0 or 1]
        [posNeeded: the number of higher order pos terms needed: 0-2]
    Only four of these are used, with posNeeded always 0 or 1 more than carry
    (making easy loop nesting)

    Each individual candidate option is kept internally as a pair of strings,
    {symbols counted positively, symbols counted negatively}
    The marker used for impossible options is {null, null}
    Hence the option array for all options is 3D with index meanings:
    [carry][posNeeded][0 for positive symbol string or 1 for negative]
  */

  private static boolean LASTDUP = false;  // TESTING only for multiple answers
      
  public static void main(String[] args) throws Exception {
    String file = (args.length > 0) ? args[0] : "numeral.in";
    Scanner in = new Scanner(new File(file));
    String n  = in.next();
    while (!n.equals("0")) {
      String ans = convert(n);
      if (args.length > 0) test(n, ans, 1000000);  // judge integrity check
      System.out.println(ans);
      n = in.next();
    }
  }

  /** Return the A-Z numeral for the digit string n.
    See the overview above.
   */
  public static String convert(String n) {
    if (n.length() < 26)
        n = "0" + n; // to allow final carry
    String[][][] option =  allImpossible();  // see format description above
    option[0][0][0] = ""; // no Carry,no Positive Needed option 
    option[0][0][1] = ""; //     made legal, with pos and neg parts empty
    for (int power = 0; power < n.length(); power++) {
        String[][][] prevOption = option;
        int digit = n.charAt(n.length()-1-power) - '0';
        option = allImpossible();
        for (int lastCarry = 0; lastCarry < 2; lastCarry++) 
            for (String[] pattern : patterns[digit+lastCarry]) {
                String pos = pattern[0], neg = pattern[1]; 
                int carry = pattern[2].charAt(0)- '0'; 
                int moreNeg, nPos = pattern[0].length();
                if (neg.endsWith("11"))
                    moreNeg = 2;
                else if (carry > 0)
                    moreNeg = 1;
                else
                    moreNeg = 0;
                for (int i = 0; i < 2; i++) {
                    int lastPosNeeded = i+lastCarry;
                    String oldPos = prevOption[lastCarry][lastPosNeeded][0];
                    String oldNeg = prevOption[lastCarry][lastPosNeeded][1];
                    if (oldPos != null) {
                        int posNeeded = max(lastPosNeeded - nPos, moreNeg);
                        betterOption(option[carry][posNeeded],
                                     oldPos, oldNeg, pos, neg, power);
                    }
               }
            }
    }
    return formatAZNumeral(option[0][0]); // at end, no carry, no more pos syms
  }

  /** returns a marker for an impossible option for the symbols. */
  public static String[] impossible(){ // null indicates impossible
    return new String[2];
  }

  /**return options structure, with impossible() for all combinations of
    [carry][positive symbols needed].
  */
  public static String[][][] allImpossible() {
    String[][][] options = new String[2][3][0];
    for (String[][] a : options)
      for (int i = 0; i < 3; i++)
        a[i] = impossible();
    return options;
  }
  
  /** Option contains {posSeq, negSeq}.  Replace contents if new lengths better
    constructed from:
    oldPos, oldNeg are strings of symbols from a previous iteration
    pos, neg are sequeces of 1 and 5 to be used with the current power
    power is the current power of ten for the digit.
  */
  public static void betterOption(String[] option, String oldPos, String oldNeg, 
                                String pos, String neg, int power) {
    String[] pair = {syms(pos, power)+ oldPos, syms(neg, power) + oldNeg};
    int comp = compareNumerals(option, pair);
    if (0 > comp  || (LASTDUP && comp == 0)){ // Latter condition for testing
        option[0] = pair[0];
        option[1] = pair[1];
    }
  }

  /**Return A-Z numeral symbols for the proper power,
    converting 1's and 5's to symbols for 10**power as much. */
  public static String syms(String s, int power) {
    s = s.replace('1', (char)('a' + power));
    return s.replace('5', (char)('A' + power));
  }

  /** Return >0 if orig better, 0 same, <0 new better. */
  public static int compareNumerals(String[] origPosNeg, String[] posNeg) {
    if (origPosNeg[0] == null) 
        return -1; // original was impossible
    int origPosLen = origPosNeg[0].length(),
        origTotLen = origPosLen + origPosNeg[1].length(),
        newPosLen = posNeg[0].length(),
        newTotLen = newPosLen + posNeg[1].length();
    if (origTotLen != newTotLen)
        return newTotLen - origTotLen;
    return origPosLen - newPosLen;
  }

  /** compare magnitudes of single character A-Z numeral symbols. */
  public static Boolean isSmaller(char A, char B) {
    char a = Character.toLowerCase(A);
    char b = Character.toLowerCase(B);
    return a < b || a == b && A > B; // AASCII capitals before lowercase
  }
    
  /** Given {pos, neg} strings of symbols, insert neg symbols at rightmost
    place possible in positives and return the result.
    For debugging, return null if illegal.
  */
  public static String formatAZNumeral(String[] posNeg) {
    String n = posNeg[0], neg = posNeg[1]; 
    // Positive part will be added to, creating full numeral n
    for (int j = 0; j < neg.length(); j++) {
        char ch = neg.charAt(j);  // will be most significant first
        int i;
        for (i = n.length()-1; i >= 0; i--) 
            if (isSmaller(ch, n.charAt(i)) && 
                (i == 0 || !isSmaller(n.charAt(i-1), n.charAt(i))))
                break;
        if (i < 0) // DEBUG:  no fit
            return null;
        n = n.substring(0,i) + ch + n.substring(i);
    }
    return n;
  }

  // the rest is judge's TESTING code only /////////////////////////////

  /** Convert A-Z numeral character to a number, and return. */
  public static long symToNum(char ch) {
    long n = (Character.isUpperCase(ch) ? 5 : 1);
    for (int i = 0; i < Character.toLowerCase(ch) - 'a'; i++)
        n *= 10;
    return n;  // can't use math.pow if result has 17 digits
  }  

  /** Return evaluation of an A-Z numeral. */
  public static long evalAZNumeral(String s) {
    char after = s.charAt(s.length() - 1);
    long n = symToNum(after);
    for (int i = s.length() - 2; i >= 0; i--) {
        char ch = s.charAt(i);
        if (isSmaller(ch, after))
            n -= symToNum(ch);
        else
            n += symToNum(ch);
        after = ch;
    }    
    return n;
  }    

  /** convert, allowing no neg, -1, -2, -5, -6, -7 for every power by brute
    force, only eliminating completed illegal and nonoptimal strings at the end,
    return list of best string pairs {posSymString, negSymString}.  This
    is used to check that there is only one best option and it matches 
    the more efficient and complicated algorithm.
    order of 6**(number of digits in n) !
  */
  public static List<String[]> convertBruteForce(String n) { 
    ArrayList<String[]> best = new ArrayList<String[]>();
    brute(n, "", n.length() - 1, best);
    return best;
  }  

  private static String[] NEG_OPTIONS = {"", "1", "11", "5", "51", "511"};      

  /** Recursive helper to return a list of the best representations
    using negative symbols in all possible ways.
    n is the number to represent
    neg is one possible list of higher order negative symbols
    power is the next power to add negative symbols for
    best contains the best representation so far.  It is a list, so if
         there are several of equivalent size, all are included.
  */
  public static void brute(String n, String neg, int power,
                           List<String[]> best) {
    if (power == -1) { // full neg string created - check it
        long nVal = Long.parseLong(n);
        for (int i = 0; i < neg.length(); i++)
            //offset neg terms by adding to positive
            nVal += symToNum(neg.charAt(i));
        n = "" + nVal;  // amount to handle with positive terms
        if (n.indexOf('9') >= 0 || n.indexOf('4') >= 0) 
            return;  // no positive representation
        String pos = "";
        int maxPow = n.length() - 1;
        for (int i = 0; i <= maxPow; i++) // accumulate positive symbols 
            // first pattern in list for digit is for all positive terms
            pos += syms(patterns[n.charAt(i)-'0'][0][0], maxPow - i);
        String[] posNeg = {pos, neg};
        // update best, comparing to posNeg
        if (formatAZNumeral(posNeg) != null) // only legal combinations 
            if (best.size() > 0) {
                int comp = compareNumerals(best.get(0), posNeg);
                if (comp == 0)
                    best.add(posNeg);
                else if (comp < 0) {
                    best.clear();
                    best.add(posNeg);
                }    
            }    
            else {
                best.clear();
                best.add(posNeg);
            }
    }                   
    else  // recurse further, making longer strings of negative symbols
        for (String opt : NEG_OPTIONS)
            brute(n, neg + syms(opt, power), power-1, best); 
  }

  /** check for correct answer, duplicate answers, and do a comparison
    to the brute force result if the int(val) < maxTest.
  */    
  public static void test(String val, String ans, int maxTest) {
    if (val.length() < 19) {  // uses longs - could change to BigIntegers
        long x = evalAZNumeral(ans);
        if (x >= 700000000000000000L) // test evaluation of result is correct
            System.err.format("Value beyond specied range: %s\n", x);
        String eval = "" + x;
        if (!eval.equals(val)) // test evaluation of result is correct
            System.err.format("Evaluation wrong: %s %s %s\n", val, eval, ans);
    }
    LASTDUP = !LASTDUP;
    /* Among the potential partial answers, there may be ones of equal length,
       but the assertion is that they will never lead to anything optimal.
       With LASTDUP true, rather than taking the first of two equivalent
       answers, the second is taken.
       Getting the same answer in the end this way confirms that
       there are never alternate correct answers.
    */
    String ans2 = convert(val);
    if (!ans2.equals(ans))
        System.err.format("!duplicate answer! %s %s %s\n",
                          val, ans, ans2);
    LASTDUP = !LASTDUP;
    if (val.length() < 11) {  // long size bigger than any int
        long numVal = Long.parseLong(val);
        if (numVal < maxTest) {
            List<String[]> best = convertBruteForce(val);
            if (best.size() != 1 || !formatAZNumeral(best.get(0)).equals(ans))
            {
                System.err.println("Best does not match:");
                for (String[] posNeg : best) {
                    String num = formatAZNumeral(posNeg);
                    System.err.format("??: %s - %l\n", num, evalAZNumeral(num));
                }    
            }    
        }    
    }
  }  
}
