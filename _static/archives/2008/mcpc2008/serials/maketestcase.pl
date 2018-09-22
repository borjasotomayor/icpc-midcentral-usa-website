#!/usr/bin/perl
#
# Usage: maketestcase.pl sMin sMax cMin cMax tMin tMax rows
#   sMin: Minumum serial number value
#   sMax: Maximum serial number value
#   cMin: Minimum status code
#   cMax: Maximum status code
#   tMin: Minimum transfer code
#   tMax: Maximum transfer code
#   rows: Number of transactions rows to generate

$sMin = shift;
$sMax = shift;
$cMin = shift;
$cMax = shift;
$tMin = shift;
$tMax = shift;
$rows = shift;

while ( $rows-- > 0 ) {
  $a = int( rand( $sMax ) + $sMin );
  $b = int( rand( $sMax ) + $sMin );
  $c = chr( ord( $cMin ) + int( rand( ord( $cMax ) - ord( $cMin ) + 1 ) ) );
  $t = int( rand( $tMax ) + $tMin );
  if ( $a < $ b ) {
    print( "$a $b " );
  }
  else {
    print( "$b $a " );
  }
  print " $c $t\n";
}
