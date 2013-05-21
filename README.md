Simple Gradle JavaFX project.

To run it install [Gradle 1.6](http://www.gradle.org/) and the last [Java SE Development Kit 7](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

Clone the project:

    > git clone https://github.com/dahuapp/JavaFXGradleDummy.git
    > cd JavaFXGradleDummy

Change the following environment variables in your current shell (please adapt the solution to your plateform and install settings):

    export JAVA_HOME=/usr/lib/jvm/jdk1.7.0_21
    export JAVAFX_HOME=/usr/lib/jvm/jdk1.7.0_21
    export JFXRT_HOME=/usr/lib/jvm/jdk1.7.0_21

Run the project:

    > gradle run

To bundle the app for distribution do:

    > gradle build

This will create a .dmg on Max OSx, .exe on Windows and .deb on Ubuntu
