#!/bin/bash
#For this program to run the pipeline, a MiniPipeline.sh file must be present in the same directory as this file.

Org="rss-sre-1";
Repo="rss-evaluation-service";
Interval="1m";

#Only change if you already have files with these names
GatherFile="11111111113";
BaseFile="11111111112";
TempFile="11111111111";

#Do not change the following variables
URL="https://github.com/$Org/$Repo/commits/dev";
GrepURL="https://github.com/$Org/$Repo/commit/";

# CHECKING FOR BASE FILE STORING OLD COMMIT
rm $TempFile &> /dev/null;
if [ "$(ls | grep $BaseFile 2> /dev/null)" != "$BaseFile" ]; then
#rm $BaseFile &> /dev/null;
  echo "" > $BaseFile;
  curl $URL -s -o $BaseFile; # 2> /dev/null;
  cat $BaseFile | grep $GrepURL > $GatherFile; # get initial commit
  mv $GatherFile $BaseFile;

else
  echo "Previous commit found.";
fi
echo "" > $TempFile;

# CHECKING FOR STOP FILE
if [ "$(ls | grep stop 2> /dev/null)" == "stop" ]; then
  read -p "File 'stop' found. Remove and continue? (y/n): ";
  if [ "$REPLY" != "y" ]; then
    exit;
  else
    rm stop &> /dev/null;
  fi
fi
stop=$(cat stop 2> /dev/null);

# The loop does not stop until there is a file named 'stop' with content in it.
while [ "$stop" == "" ]; do
  echo $(date +"%T") - ;

  # GET CURRENT COMMIT
  curl $URL -s -o $TempFile; # 2> /dev/null;
  cat $TempFile | grep $GrepURL > $GatherFile;
  mv $GatherFile $TempFile;

  # CHECK IF NEW COMMIT DOES NOT MATCH OLD COMMIT
  if [ "$(diff $BaseFile $TempFile)" != "" ]; then
    #read -p "continue?"
    echo "Change found, starting mini-pipeline.";
    mv $TempFile $BaseFile;
    ./MiniPipeline.sh;
  fi

  echo " - ";
  sleep $Interval;
  stop=$(cat stop 2> /dev/null);
done

rm $TempFile &> /dev/null;
