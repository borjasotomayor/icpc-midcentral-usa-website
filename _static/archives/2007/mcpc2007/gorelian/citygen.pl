#!/usr/bin/perl

$rows = $ARGV[0] + 1;
$cols = $ARGV[1] + 1;
$pctclosed = $ARGV[2];
$pctoneway = $ARGV[3];

print "$ARGV[0] $ARGV[1]\n";

for ( $i = 0; $i < 2 * $rows - 1; $i++ ) {

  $r = int( $i / 2 );
  $s = '';

  # e-w row
  if ( $i % 2 == 0 ) {
    for ( $c = 0; $c < $cols - 1; $c++ ) {
      if ( chance( $pctclosed ) ) {
	$s .= '0 * ';
      }
      else {
	$n = int( rand( 9 ) ) + 1;
	$s .= "$n ";
	if ( chance( $pctoneway ) ) {
	  $s .= chance( 50 ) ? '< ' : '> ';
	}
	else {
	  $s .= '* ';
	}
      }
    }
    ( $s ) = $s =~ /(.*) /;
    print "$s\n";
  }

  # n-s row
  else {
    for ( $c = 0; $c < $cols; $c++ ) {
      if ( chance( $pctclosed ) ) {
	$s .= '0 * ';
      }
      else {
	$n = int( rand( 9 ) ) + 1;
	$s .= "$n ";
	if ( chance( $pctoneway ) ) {
	  $s .= chance( 50 ) ? 'v ' : '^ ';
	}
	else {
	  $s .= '* ';
	}
      }
    }
    ( $s ) = $s =~ /(.*) /;
    print "$s\n";
  }

}

print "0 0\n";

sub chance() {
  $p = shift;
  $r = int( rand( 100 ) );
  return $r < $p ? 1 : 0;
}
