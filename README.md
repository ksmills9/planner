# Planner
Manage your daily events with this program. Create an account, add events, set a timer or an alarm.

Download Repository from: [GitHub](https://github.com/ksmills9/planner)

## About
The program connects you to a MySQL database hosted on an AWS EC2 server. For Java to connect to the database, you need the JDBC driver for MySQL, [Connector/J](https://dev.mysql.com/downloads/connector/j/). For ease of access, `mysql-connector-java-8.0.15.jar` has been added to the project repository.

For now, only a console version of the program is available. In future, we will release the GUI version using JavaFX.

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
  for example,
  ```
  javac -cp .;"mysql-connector-java-8.0.15.jar" *.java
  ```

  - After the Java is done compiling, type in the following:
  ```
  java -cp .;"path\to\mysql-connector-java-X.X.XX.jar" Main console
  ```
  for example,
  ```
  java -cp .;"mysql-connector-java-8.0.15.jar" Main console
  ```
  - Make sure to add `console` at the end.
- **Linux/MacOS**
  - Open command prompt and move to the same directory where you kept the `.java` files.
  - Type in the following command 
  ```
  javac -cp .:"path\to\mysql-connector-java-X.X.XX.jar" *.java
  ```
  for example,
  ```
  javac -cp .:"mysql-connector-java-8.0.15.jar" *.java
  ```
  - After the Java is done compiling, type in the following:
  ```
  java -cp .:"path\to\mysql-connector-java-X.X.XX.jar" Main console
  ```
  for example,
  ```
  java -cp .:"mysql-connector-java-8.0.15.jar" Main console
  ```
  - Make sure to add `console` at the end.

- **Eclipse/IntelliJ**
  - Add `mysql-connector-java-X.X.XX.jar` to your project's External Library/Archive.
  - Go to Build → Edit Configurations and type `console` in 'Program arguments' field. (IntelliJ)
  - Go to Run → Run Configurations tab and type `console` in 'Program arguments' section. (Eclipse)
