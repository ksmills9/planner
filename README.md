# Planner
Manage your daily events with this program. Create an account, add events, set a timer or an alarm.

Download Repository from: [GitHub](https://github.com/ksmills9/planner)

## About
The program connects you to a MySQL database hosted on an AWS EC2 server. For Java to connect to the database, you need the JDBC driver for MySQL, [Connector/J](https://dev.mysql.com/downloads/connector/j/). For ease of access, `mysql-connector-java-8.0.15.jar` has been added to the project repository.

## Setting up the program

- Download the repository and keep all the .java file in the same directory.

- Download [Connector/J](https://dev.mysql.com/downloads/connector/j/) and put the `mysql-connector-java-X.X.XX.jar` in the same directory as the project. 
  - You can also install it to classpath by following the instructions [here](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-installing.html).
  - You can actually keep the `mysql-connector-java-X.X.XX.jar` file anywhere in your computer. Just remember where you kept it.

## Running the program (Console)
- **Windows**
  - Open command prompt and move to the same directory where you kept the `.java` files.
  - Type in the following command 
  ```
  javac -cp .;"path\to\mysql-connector-java-X.X.XX.jar" *.java
  ```
  - After the Java is done compiling, type in the following:
  ```
  java -cp .;"path\to\mysql-connector-java-X.X.XX.jar" Main console
  ```
  - Make sure to add `console` at the end.
- **Linux/MacOS**
  - Open command prompt and move to the same directory where you kept the `.java` files.
  - Type in the following command 
  ```
  javac -cp .:"path\to\mysql-connector-java-X.X.XX.jar" *.java
  ```
  - After the Java is done compiling, type in the following:
  ```
  java -cp .:"path\to\mysql-connector-java-X.X.XX.jar" Main console
  ```
  - Make sure to add `console` at the end.

- **Eclipse/IntelliJ**
  - Add `mysql-connector-java-X.X.XX.jar` to your project's External Library/Archive.
  - Go to Build → Edit Configurations and type `console` in 'Program arguments' field. (IntelliJ)
  - Go to Run → Run Configurations tab and type `console` in 'Program arguments' section. (Eclipse)

## Running the program (GUI | Java 11)
To run the GUI version with Java 11, you will need to download [JavaFX 11](https://openjfx.io/) separately. After downloading it, follow the following steps. 
- **Windows**
  - Open command prompt and move to the same directory where you kept the `.java` files.
  - Type in the following command 
  ```
  javac --module-path "path\to\javafx-sdk-11.X.X\lib" --add-modules=javafx.controls,javafx.fxml -cp .;"path\to\mysql-connector-java-X.X.XX.jar" *.java
  ```
  - After the Java is done compiling, type in the following:
  ```
  java --module-path "path\to\javafx-sdk-11.X.X\lib" --add-modules=javafx.controls,javafx.fxml -cp .;"path\to\mysql-connector-java-X.X.XX.jar" Main gui
  ```
  - Make sure to add `gui` at the end.
- **Linux/MacOS**
  - Open command prompt and move to the same directory where you kept the `.java` files.
  - Type in the following command 
  ```
  javac --module-path "path\to\javafx-sdk-11.X.X\lib" --add-modules=javafx.controls,javafx.fxml -cp .:"path\to\mysql-connector-java-X.X.XX.jar" *.java
  ```
  - After the Java is done compiling, type in the following:
  ```
  java --module-path "path\to\javafx-sdk-11.X.X\lib" --add-modules=javafx.controls,javafx.fxml -cp .:"path\to\mysql-connector-java-X.X.XX.jar" Main gui
  ```
  - Make sure to add `gui` at the end.

- **Eclipse/IntelliJ**
  - Add `mysql-connector-java-X.X.XX.jar` to your project's External Library/Archive.
  - Go to Build → Edit Configurations and type `gui` in 'Program arguments' field. (IntelliJ)
  - Go to Run → Run Configurations tab and type `gui` in 'Program arguments' section. (Eclipse)
  - Add the following to the VM option field `--module-path "path\to\javafx-sdk-11.X.X\lib" --add-modules=javafx.controls,javafx.fxml`

## Running the program (GUI | Java 10 and earlier)
Javafx comes bundled with earlier versions of JDK. Using similar command as the console version and replacing `console` with `gui` as command line argument should suffice.

Example:
```
javac -cp .;"path\to\mysql-connector-java-X.X.XX.jar" *.java
```

If nothing works, use the `jfxrt.jar` and add it to classpath when compiling and running the program.

Example:
  ```
  javac -cp .;"path\to\mysql-connector-java-X.X.XX.jar";"path/to/jfxrt.jar" *.java
  ```
  And,
  ```
  java -cp .;"path\to\mysql-connector-java-X.X.XX.jar";"path/to/jfxrt.jar" Main gui
  ```

## References
  1. https://stackoverflow.com/a/37276108
  2. https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
  3. https://stackoverflow.com/a/26169947
  4. https://stackoverflow.com/questions/10121991/javafx-application-icon
