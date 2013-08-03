** CodeSample **

1. Launch sbt:

        cd CodeSample
        ./sbt (or sbt.bat for Windows)
        
  This downloads all the dependencies for the project.

2. To run your program, in SBT:
   
        >run        
        
  This searches all objects for a 'main' method. If it finds more than one, it asks you to chose which you want to run.

3. To load the project into Eclipse, at the SBT prompt:

        > eclipse with-sources=true
        
   Then from within Eclipse, select File->Import->General->Existing Projects Into Workspace, and select your project directory. 
   Install the Eclipse Scala IDE plugin from [here](http://scala-ide.org/download/current.html).

4. Or, to load the project into Intellij IDEA, at the SBT prompt:
   
        > gen-idea
        
   Then from within IDEA, select File -> Open Project, and select your project directory.

2. Put your Scala class files in src/main/scala. An example has been created in:
src/main/scala/com/myapp/App.scala. SBT uses the same folder structure as Maven. 
For more information see [here](https://github.com/harrah/xsbt/wiki/Getting-Started-Directories).

3. Write your tests in src/test/scala (some examples have been included). To run all your tests:

        > test
        
4. To run just one test:

        > test-only com.myapp.AppTest
        
5. To launch the Scala interpreter, at the sbt prompt:

        > console
        
   This allows you to 'play around' with Scala commands, with full access to all the libraries defined in your project. 
   Ctrl-D returns you to SBT.
   
6. To view / modify project configuration settings, see build.sbt in the project root directory. If you change anything, run

        >reload
        
   from within SBT for your changes to take effect.


