#!/usr/bin/perl
#
# Usage: maketestcase.pl circleChance objectCount endMarker?

$c = shift;
$n = shift;
$f = shift;

while ( $n-- > 0 ) {
  if ( r( 1, 100 ) > $c ) {
    #line
    print( "L" );
    for ( $i = 0; $i < 4; $i++ ) {
      print( " ", r( 0, 100 ) );
    }
    print( "\n" );
  }
  else {
    #circle
    $x = r( 1, 99 );
    $y = r( 1, 99 );
    $r = r( 1, min( min( $x, 100 - $x ), min( $y, 100 - $y ) ) );
    print( "C $x $y $r\n" );
  }
}

print "*\n";
print "*\n" if $f eq "y";

sub r {
  $min = shift;
  $max = shift;
  return int ( rand( $max ) + $min );
}

sub min {
  $a = shift;
  $b = shift;
  return $a < $b ? $a : $b;
}