sub usage() {
    CORE::say "Please provide consumer_key, consumer_secret, access_token, and access_token_secret";
}
use Getopt::Long;
use Net::Twitter::Lite::WithAPIv1_1;
use Scalar::Util 'blessed';
#use LWP::UserAgent 6;
use warnings;
use strict;
use Data::Dumper;

my $consumer_key;
my $consumer_secret;
my $access_token;
my $access_token_secret;
my $search_term;
my $tot_file;
GetOptions ('tot_file=s' => \$tot_file, 'search=s' => \$search_term, 'consumer_key=s' => \$consumer_key, 'consumer_secret=s' => \$consumer_secret, 'access_token=s' => \$access_token, 'access_token_secret=s' => \$access_token_secret) or die usage();

open TOT_FILE, ">>$tot_file" or die $!;
open TWIT_FILE, "<.last_id";
my $last_id="";
if(tell(TWIT_FILE) != -1)
{
while (<TWIT_FILE>) {
	$last_id=$_;
  }
}
close TWIT_FILE;
print "last id: $last_id";
  my $nt = Net::Twitter::Lite::WithAPIv1_1->new(
      consumer_key        => $consumer_key,
      consumer_secret     => $consumer_secret,
      access_token        => $access_token,
      access_token_secret => $access_token_secret,
      ssl                 => 1,
      traits		  => "API::Search"
  );

  my $r = $nt->search({ q => $search_term, authenticate => 1, since_id => $last_id});
#  print Dumper($r);
  $last_id = $r->{search_metadata}->{max_id_str};
  for my $status ( @{$r->{statuses}} ) {
	my $text = $status->{text};
	my $baretext="";
	my $tags="";
	my $urls="";
	my $persons = "\@$status->{user}->{screen_name}";
	while ($text =~ /(\@{1}\w+)/g) {
        	$persons .= " $1";
	}
	while ($text =~ /(\#{1}\w+)/g) {
		$tags .= "$1 ";
     	}
	while ($text =~ /(http\w+)/g) {
		$urls .= "$1 ";
	}
	#while ($text =~ /(\@|\#|http)\w+/g) {
	#	print $1;
	#}
        print TOT_FILE "$text - $persons,$urls,$tags\n";
  }
open TWIT_FILE, ">.last_id" or die $!;
  print TWIT_FILE $last_id;
close TOT_FILE;
close TWIT_FILE;

  if ( my $err = $@ ) {
      die $@ unless blessed $err && $err->isa('Net::Twitter::Lite::Error');

      warn "HTTP Response Code: ", $err->code, "\n",
           "HTTP Message......: ", $err->message, "\n",
           "Twitter error.....: ", $err->error, "\n";
  }


