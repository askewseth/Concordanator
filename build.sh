#!/bin/bash

#Simple script to build Concordanator commandline tool.
#Author Cory Sabol

# Get the rows and cols of the term
cols=`tput cols`
lines=`tput lines`
build_dir="./build/"
flag=0

#Export the cols and lines
export cols=$cols
export lines=$lines

echo $cols
echo $lines
printenv

#Now check to make sure build/ doesn't already exist
if [ ! -d "$build_dir" ]; then
	#Dir didn't exist so create it
	mkdir build/
fi

# Now actually build the application
echo "Building java source code"
exec javac -sourcepath src/ src/Concordanator.java -d build/ | echo &&

# Now move the books into build 
echo ""
echo "Moving files"
cp -r "src/books" "build/"
# Now copy commonwords.txt where it belongs 
cp "src/ClassLibrary/commonwords.txt" "build/ClassLibrary/"

# Generate run.sh
echo ""
echo "Generating run.sh"
touch run.sh
echo "export cols=$cols; export lines=$lines; cd build/ && java Concordanator" > run.sh 
cat run.sh
chmod +x run.sh
echo "DONE"
echo ""
echo "To run application simply execute ./run.sh from the command line."
echo "To run application with GUI execute 'cd build/' and then 'java Concordanator gui'"
