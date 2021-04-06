#!/bin/bash
Org="rss-sre-1";
Repo="rss-evaluation-service";
URL="https://github.com/$Org/$Repo/commits/dev";
BaseFile="11111111112";
TempFile="11111111111";
Interval="1m";

rm $TempFile &> /dev/null;
rm $BaseFile &> /dev/null;

curl $URL -o $BaseFile &> /dev/null;
cat $BaseFile | grep "https://github.com/$Org/$Repo/commit/" > $BaseFile; # get all commits


stop=$(cat stop 2> /dev/null);

# The loop does not stop until there is a file named 'stop' with content in it.
while [ "$stop" == "" ]; do
  echo $(date +"%T") - ;
  curl $URL -o $TempFile &> /dev/null;
  cat $TempFile | grep "https://github.com/$Org/$Repo/commit/" > $TempFile; # get all current commits

  # Compare past and current commits
  if [ "$(diff $BaseFile $TempFile)" != "" ]; then
    echo "Change found, starting mini-pipeline.";
    mv $TempFile $BaseFile; # Update base commit list.
    ./MiniPipeline.sh;
  fi
  rm $TempFile &> /dev/null;
  stop=$(cat stop 2> /dev/null);
  echo " - ";
  sleep $Interval;
done

rm $BaseFile;
