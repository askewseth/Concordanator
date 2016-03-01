## Concordanator
A Java based applicaton/library for creating concordances of texts and searching said concordances.

#### What is a concordance might, you ask?
A concordance is a list of words appearing in a work, with line numbers for each occurrence. That's basically it.

#### Why is this useful?
Ask me that question again later and I might have a good answer for you.

#### Team Members:
Tony Ratlif
Cory Sebastain Sabol
Seth Askew

### This project is built using java 1.8 however it `should` be backwards compatible.

#### Using with netbeans:
1. Open NetBeans and create a new project from existing sources.
2. Follow the prompts, selecting the src folder for sources and the test folder for tests.
3. Let NB do it's thing.
4. You should now be able to run and compile the code from inside NB.

## Building and running from the command line, like a real man/woman >:^)
1. `cd` into the root dir of the project.
2. `mkdir build`
3. execute `javac -sourcepath src src/Concordanator.java -d build/`
4. Once that's done execute `java -cp build Concordanator`
5. Enjoy your concordances.

## Things that don't work
Most of the commands don't work. The functionality is in the library, however testing is not complete and
the command line interface doesn't tie everything together just yet.

#### General Todo:
1. Document all the things.
2. Come up with more todos.

#### If you have questions, shoot them at:
1. Cory Sabol   -  cssabol@uncg.edu
2. Tony Ratliff -  arratlif@uncg.edu
3. Seth Askew   -  tsaskew@uncg.edu
