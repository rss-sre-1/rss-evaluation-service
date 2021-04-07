#!/bin/bash
Org="rss-sre-1";
Repo="rss-evaluation-service";
URL="https://github.com/$Org/$Repo/commits/dev";
GrepURL="https://github.com/$Org/$Repo/commit/";
BaseFile="11111111112";
TempFile="11111111111";
GatherFile="11111111113";
Interval="1m";

rm $TempFile &> /dev/null;
rm $BaseFile &> /dev/null;
echo "" > $TempFile;
echo "" > $BaseFile;

curl $URL -s -o $BaseFile; # 2> /dev/null;
cat $BaseFile | grep $GrepURL > $GatherFile; # get all commits
mv $GatherFile $BaseFile;

stop=$(cat stop 2> /dev/null);

# The loop does not stop until there is a file named 'stop' with content in it.
# Or it will stop when you enter stop="stop" in another terminal.
while [ "$stop" == "" ]; do
  echo $(date +"%T") - ;
  curl $URL -s -o $TempFile; # 2> /dev/null;
  cat $TempFile | grep $GrepURL > $GatherFile; # get all current commits
  mv $GatherFile $TempFile;

#  echo "-- $(cat $BaseFile) --";
#  echo "-- $(cat $TempFile) --";
  # Compare past and current commits

#read -p "continue?"

  if [ "$(diff $BaseFile $TempFile)" != "" ]; then
    echo "Change found, starting mini-pipeline.";
    mv $TempFile $BaseFile; # Update base commit list.
    ./MiniPipeline.sh;
  fi
  stop=$(cat stop 2> /dev/null);
  echo " - ";
  sleep $Interval;
done
rm $TempFile &> /dev/null;
rm $BaseFile &> /dev/null;
