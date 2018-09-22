// 2007 ACM Mid-Central USA Regional Programming Contest
// Solution to Problem C: "The Seven Percent Slink" [hard]
// Ron Pachecho

import java.io.*;
import java.util.Scanner;
import java.util.*;

class slink {

    public static void main( String[] args ) throws Exception {
	String inFile = args.length > 0 ? args[0] : "slink.in";
	Scanner in = new Scanner( new File( inFile ) );
	int n = 1;
	int r = in.nextInt();
	while ( r > 0 ) {
	    System.out.println( n++ );
	    SlinkPuzzle s = new SlinkPuzzle( in, r, in.nextInt() );
	    s.solve();
	    s.print();
	    r = in.nextInt();
	}
    }

    private static class SlinkPuzzle {

	private int _rows, _cols;
	private char[][] _board;
	private boolean _isBoardModified;
	public final static char ANY = '\000', BLOCKED = 'X', EMPTY = '.', LINKED = '+';

	private class Position {
	    protected int __row, __col;
	    public Position() { __row = 0; __col = 0; }
	    public Position( Position pos ) { __row = pos.__row; __col = pos.__col; }
	    public Position( int row, int col ) { __row = row; __col = col; }
	    public void goTo( int row, int col ) { __row = row; __col = col; }
	    public Position north() { return new Position( __row - 1, __col ); }
	    public Position south() { return new Position( __row + 1, __col ); }
	    public Position west() { return new Position( __row, __col - 1 ); }
	    public Position east() { return new Position( __row, __col + 1 ); }
	    public boolean isValid() { return __row >= 0 && __col >= 0 && __row < _rows && __col < _cols; }
	    public char getState() { return ( isValid() ? _board[__row][__col] : BLOCKED ); }
	    public void setState( char state ) {
		if ( isValid() ) {
		    if ( _board[__row][__col] != state )
			_isBoardModified = true;
		    if ( state == LINKED && isHzEdge() ) {
			west().setState( state );
			east().setState( state );
		    }
		    else if ( state == LINKED && isVtEdge() ) {
			north().setState( state );
			south().setState( state );
		    }
		    _board[__row][__col] = state;
		}
	    }
	    public void setState( int state ) {
		setState( Character.forDigit( state, 10 ) );
	    }
	    public boolean isState( char state ) { return state == ANY || getState() == state; }
	    public boolean isHzEdge() { return __row % 2 == 0 && __col % 2 == 1; }
	    public boolean isVtEdge() { return __row % 2 == 1 && __col % 2 == 0; }
	    public boolean isVertex() { return __row % 2 == 0 && __col % 2 == 0; }
	    public boolean isCell() { return __row % 2 == 1 && __col % 2 == 1; }
	}

	private class PositionIterator extends Position {
	    private int __startRow, __startCol, __rows, __cols;
	    public PositionIterator() {
		__startRow = 0;
		__startCol = 0;
		__rows = _rows;
		__cols = _cols;
		reset();
	    }
	    public PositionIterator( int row, int col, int rows, int cols ) {
		__startRow = row;
		__startCol = col;
		__rows = rows;
		__cols = cols;
		reset();
	    }
	    public PositionIterator next() {
		__col += 1;
		__row += ( __col - __startCol ) / __cols;
		__col = ( __col - __startCol ) % __cols + __startCol;
		return this;
	    }
	    public void reset() { goTo( __startRow, __startCol -1 ); }
	    public boolean isLastCol() { return __col == __startCol + __cols - 1; }
	}

	private class CellIterator extends Position {
	    public CellIterator() { super( 1, -1 ); }
	    public CellIterator next() { __col += 2; __row += 2 * ( __col / _cols ); __col = __col % ( _cols - 1 ); return this; }
	    public void reset() { goTo( 1, -1 ); }
	}

	public SlinkPuzzle( Scanner in, int puzzleRows, int puzzleCols ) {
	    _rows = 2 * puzzleRows + 1;
	    _cols = 2 * puzzleCols + 1;
	    _board = new char[_rows][_cols];
	    PositionIterator pos = new PositionIterator();
	    while ( pos.next().isValid() )
		pos.setState( EMPTY );
	    CellIterator cell = new CellIterator();
	    while ( cell.next().isValid() )
		cell.setState( in.nextInt() );
	}

	public void solve() {
	    rules1();
	    do {
		do {
		    _isBoardModified = false;
		    rules2();
		} while ( _isBoardModified );
	    } while ( _isBoardModified );
	}

	// rules applied only once
	private void rules1() {
	    // block all edges around zeros
	    rule( 3, 3, 0, 0, 0, false, "...|.0.|...", ".X.|X.X|.X." );
	    // adjacent threes
	    rule( 5, 5, 1, 0, 1, false, ".....|.....|.3.3.|.....|.....", "..X..|.....|+.+.+|.....|..X.." );
	    // diagonal threes
	    rule( 5, 5, 0, 0, 1, false, ".....|.3...|.....|...3.|.....", "++...|+....|.....|....+|...++" );
	}

	// rules applied repeatedly
	private void rules2() {
	    // three blocked on one side
	    rule( 3, 3, 0, 0, 3, false, "...|X3.|...", "+++|..+|+++" );
	    // two blocked on two sides
	    rule( 3, 3, 0, 0, 3, false, ".X.|X2.|...", "..+|..+|+++" );
	    rule( 3, 3, 0, 0, 1, false, "...|X2X|...", "+++|...|+++" );
	    // one blocked on three sides
	    rule( 3, 3, 0, 0, 3, false, ".X.|X1X|...", "...|...|+++" );
	    // three with three edges
	    rule( 3, 3, 0, 0, 3, false, ".+.|+3+|...", "...|...|.X." );
	    // two with two edges
	    rule( 3, 3, 0, 0, 3, false, ".+.|+2.|...", "...|..X|.X." );
	    rule( 3, 3, 0, 0, 1, false, "...|+2+|...", ".X.|...|.X." );
	    // one with one edge
	    rule( 3, 3, 0, 0, 3, false, "...|+1.|...", ".X.|..X|.X." );
	    // corner through a vertex
	    rule( 3, 3, 1, 1, 3, false, "...|..+|.+.", ".X.|X..|..." );
	    // straight through a vertex
	    rule( 3, 3, 1, 1, 1, false, "...|+.+|...", ".X.|...|.X." );
	    // three at a corner
	    rule( 3, 3, 1, 1, 3, false, ".X.|X..|..3", "...|.++|.+." );
	    // two at a corner
	    rule( 5, 3, 1, 1, 3, true, ".X.|X..|..2|X..|...", "...|...|...|...|.+." );
	    // one at a corner
	    rule( 3, 3, 1, 1, 3, false, ".X.|X..|..1", "...|..X|.X." );
	    // line into corner of a three
	    rule( 4, 4, 1, 1, 3, true, ".+..|!...|..3.|....", "....|...+|...+|.+++" );
	    // line into a corner of a one
	    rule( 4, 4, 1, 1, 3, true, ".+..|X...|..1.|....|", "....|....|...X|..X." );
	    // line into a corner of a two
	    rule( 5, 5, 1, 1, 3, true, ".+...|X....|..2..|....X|.....", ".....|.....|.....|.....|...+." );
	    // cornered three diagonal to a one
	    rule( 5, 5, 0, 0, 3, false, ".+...|+3...|.....|...1.|.....", ".....|.....|.....|....X|...X." );
	    // vertex blocked on three sides
	    rule( 3, 3, 1, 1, 3, false, ".X.|X.X|...", "...|...|.X." );
	    // coming into a vertex and only one way out
	    rule( 3, 3, 1, 1, 3, true, "...|+.X|.X.", ".+.|.+.|..." );
	    rule( 3, 3, 1, 1, 3, false, ".X.|+..|.X.", "...|.++|..." );
	}

	private void rule ( int rows, int cols, int anchorRow, int anchorCol, int rotations, boolean doMirror, String patternStr, String ruleStr ) {
	    char[] pattern = new char[ rows * cols ], rule = new char[ rows * cols ];
	    StringBuffer patternBuf = new StringBuffer( patternStr ), ruleBuf = new StringBuffer( ruleStr );
	    for ( int n = cols; n < rows * cols; n += cols ) {
		patternBuf.deleteCharAt( n );
		ruleBuf.deleteCharAt( n );
	    }
	    patternBuf.getChars( 0, rows * cols, pattern, 0 );
	    ruleBuf.getChars( 0, rows * cols, rule, 0 );
	    int passes = ( 1 + rotations ) * ( doMirror ? 2 : 1 );
	    int mirrorAt = ( doMirror ? passes / 2 : 0 );
	    while ( passes > 0 ) {
		for ( int r = -anchorRow; r + rows <= _rows + 1; r += 2 )
		    for ( int c = -anchorCol; c + cols <= _cols + 1; c += 2 )
			applyRule( new PositionIterator( r, c, rows, cols ), rows, cols, pattern, rule );
		passes--;
		if ( passes > 0 ) {
		    if ( rotations != 0 ) {
			char[] rotatedPattern = new char[ rows * cols ];
			char[] rotatedRule = new char[ rows * cols ];
			for ( int n = 0, i = ( rows - 1 ) * cols; i < rows * cols; i++ )
			    for ( int j = i; j >= 0; j -= cols, n++ ) {
				rotatedPattern[n] = pattern[j];
				rotatedRule[n] = rule[j];
			    }
			pattern = rotatedPattern;
			rule = rotatedRule;
			int swap = rows; rows = cols; cols = swap;
			swap = anchorRow; anchorRow = anchorCol; anchorCol = ( cols - 1 - swap ) % 2;
		    }
		    if ( passes == mirrorAt ) {
			patternBuf.getChars( 0, rows * cols, pattern, 0 );
			ruleBuf.getChars( 0, rows * cols, rule, 0 );
			for ( int i = 0; i < rows * cols; i += cols )
			    for ( int a = i, b = i + cols - 1; a < b; a++, b-- ) {
				char swap = pattern[a]; pattern[a] = pattern[b]; pattern[b] = swap;
				swap = rule[a]; rule[a] = rule[b]; rule[b] = swap;
			    }
			anchorCol = ( anchorCol + ( 1 - cols % 2 ) ) % 2;
		    }
		}
	    }
	}

	private void applyRule ( PositionIterator block, int rows, int cols, char[] pattern, char[] rule ) {
	    for ( int n = 0, r = 0; r < rows; r++ )
		for ( int c = 0; c < cols; c++, n++ ) {
		    block.next();
		    switch ( pattern[n] ) {
		    case '.': // matches anything
			break;
		    case '!': // matches empty
			if ( !block.isState( EMPTY ) ) return;
			break;
		    case 'X': // matches blocked
			if ( !block.isState( BLOCKED ) ) return;
			break;
		    case '+': // matches linked
			if ( !block.isState( LINKED ) ) return;
			break;
		    case '#': // matches 1, 2, or 3
			switch ( block.getState() ) {
			case '1': case '2': case '3':
			    break;
			default:
			    return;
			}
			break;
		    default:  // require exact match by default
			if ( !block.isState( pattern[n] ) ) return;
			break;
		    }
		}
	    block.reset();
	    for ( int n = 0, r = 0; r < rows; r++ )
		for ( int c = 0; c < cols; c++, n++ ) {
		    block.next();
		    if ( rule[n] != '.' ) block.setState( rule[n] );
		}
	}

	public void print() {
	    rule( 1, 2, 0, 0, 0, false, ".+", ".-" );
	    rule( 2, 1, 0, 0, 0, false, "./+", "./|" );
	    rule( 1, 3, 0, 1, 0, false, "-.-", ".-." );
	    rule( 3, 1, 1, 0, 0, false, "|/./|", "./|/." );
	    rule( 2, 2, 0, 0, 0, false, ".!/..", ". /.." );
	    rule( 2, 2, 0, 0, 0, false, "../!.", "../ ." );
	    rule( 2, 2, 0, 0, 0, false, "../.!", "../. " );
	    rule( 1, 1, 0, 0, 0, false, "!", " " );
	    rule( 1, 2, 0, 0, 0, false, ".X", ". " );
	    rule( 2, 1, 0, 0, 0, false, "./X", "./ " );
	    for ( int i = 0; i < 2 * _cols + 3; i++ )
		System.out.print( '#' );
	    System.out.println();
	    System.out.print( "# " );
	    for ( int i = 0; i < 2 * _cols - 1; i++ )
		System.out.print( ' ' );
	    System.out.println( " #" );
	    System.out.print( "# " );
	    PositionIterator pos = new PositionIterator();
	    while ( pos.next().isValid() ) {
		char stateChar = pos.getState();
		if ( pos.isHzEdge() ) {
		    System.out.print( ( stateChar == BLOCKED ? ' ' : stateChar ) );
		    System.out.print( stateChar );
		    System.out.print( ( stateChar == BLOCKED ? ' ' : stateChar ) );
		}
		else if ( pos.isCell() ) {
		    System.out.print( ' ' );
		    System.out.print( stateChar );
		    System.out.print( ' ' );
		}
		else
		    System.out.print( stateChar );
		if ( pos.isLastCol() ) {
		    System.out.println( " #" );
		    System.out.print( "# " );
		}
	    }
	    for ( int i = 0; i < 2 * _cols - 1; i++ )
		System.out.print( ' ' );
	    System.out.println( " #" );
	    for ( int i = 0; i < 2 * _cols + 3; i++ )
		System.out.print( '#' );
	    System.out.println();
	}
    }
}
