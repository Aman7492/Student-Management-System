import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {

    static final String JDBC_URL = "jdbc:mysql://localhost:3306/student_db";
    static final String USER = "root";
    static final String PASSWORD = "1809";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            createDatabase(connection);
            createTableInfo(connection);
            createTablePer(connection);
            createTableMarks(connection);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nStudent Management System");
                System.out.println("1. Add Student");
                System.out.println("2. Update Student");
                System.out.println("3. Display Students");
                System.out.println("4. Exit");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addStudent(connection, scanner);
                        break;
                    case 2:
                        updateStudent(connection, scanner);
                        break;
                    case 3:
                        displayStudents(connection);
                        break;
                    case 4:
                        System.out.println("Exiting the program. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS student_db");
        }
    }

    private static void createTableInfo(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS std_info (" +
                    "apl_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "dob DATE," +
                    "course VARCHAR(50)," +
                    "sec VARCHAR(5)," +
                    "c_roll INT(12)" +
                    ")");
        }
    }

    private static void createTablePer(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS std_per (" +
                    "c_roll INT(12) PRIMARY KEY," +
                    "address VARCHAR(255)," +
                    "mob INT(10)," +
                    "gender VARCHAR(5)," +
                    "blood VARCHAR(5)" +
                    ")");
        }
    }

    private static void createTableMarks(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS std_marks (" +
                    "c_roll INT(12) PRIMARY KEY," +
                    "sem1 INT(5)," +
                    "sem2 INT(5)," +
                    "sem3 INT(5)" +
                    ")");
        }
    }

    private static void addStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\n**ADDING A NEW STUDENT INFORMATION");

        System.out.print("Enter student name: ");
        String name = scanner.next();

        System.out.print("Enter student dob (YYYY-MM-DD): ");
        String dob = scanner.next();

        System.out.print("Enter student course: ");
        String course = scanner.next();

        System.out.print("Enter student sec: ");
        String sec = scanner.next();

        System.out.print("Enter student c_roll: ");
        int c_roll = scanner.nextInt();

        System.out.print("Enter student address: ");
        String address = scanner.next();

        System.out.print("Enter student mob: ");
        int mob = scanner.nextInt();

        System.out.print("Enter student gender: ");
        String gender = scanner.next();

        System.out.print("Enter student blood: ");
        String blood = scanner.next();

        System.out.print("Enter student sem1 marks: ");
        int sem1 = scanner.nextInt();

        System.out.print("Enter student sem2 marks: ");
        int sem2 = scanner.nextInt();

        System.out.print("Enter student sem3 marks: ");
        int sem3 = scanner.nextInt();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO std_info (name, dob, course, sec, c_roll) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, dob);
            preparedStatement.setString(3, course);
            preparedStatement.setString(4, sec);
            preparedStatement.setInt(5, c_roll);
            preparedStatement.executeUpdate();
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO std_per (c_roll, address, mob, gender, blood) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, c_roll);
            preparedStatement.setString(2, address);
            preparedStatement.setInt(3, mob);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, blood);
            preparedStatement.executeUpdate();

        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO std_marks (c_roll, sem1, sem2, sem3) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setInt(1, c_roll);
            preparedStatement.setInt(2, sem1);
            preparedStatement.setInt(3, sem2);
            preparedStatement.setInt(4, sem3);
            preparedStatement.executeUpdate();
            System.out.println("Student added successfully!");
        }
    }

    private static void updateStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\nUpdating student information:");

        System.out.print("Enter student c_roll to update: ");
        int c_roll = scanner.nextInt();

        System.out.print("Enter new student name: ");
        String name = scanner.next();

        System.out.print("Enter new student dob (YYYY-MM-DD): ");
        String dob = scanner.next();

        System.out.print("Enter new student course: ");
        String course = scanner.next();

        System.out.print("Enter new student sec: ");
        String sec = scanner.next();

        System.out.print("Enter new student address: ");
        String address = scanner.next();

        System.out.print("Enter new student mob: ");
        int mob = scanner.nextInt();

        System.out.print("Enter new student gender: ");
        String gender = scanner.next();

        System.out.print("Enter new student blood: ");
        String blood = scanner.next();

        System.out.print("Enter new student sem1 marks: ");
        int sem1 = scanner.nextInt();

        System.out.print("Enter new student sem2 marks: ");
        int sem2 = scanner.nextInt();

        System.out.print("Enter new student sem3 marks: ");
        int sem3 = scanner.nextInt();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE std_info SET name = ?, dob = ?, course = ?, sec = ? WHERE c_roll = ?")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, dob);
            preparedStatement.setString(3, course);
            preparedStatement.setString(4, sec);
            preparedStatement.setInt(5, c_roll);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("No student found with the given c_roll.");
            }
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE std_per SET address = ?, mob = ?, gender = ?, blood = ? WHERE c_roll = ?")) {
            preparedStatement.setString(1, address);
            preparedStatement.setInt(2, mob);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, blood);
            preparedStatement.setInt(5, c_roll);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("No student found with the given c_roll.");
            }
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE std_marks SET sem1 = ?, sem2 = ?, sem3 = ? WHERE c_roll = ?")) {
            preparedStatement.setInt(1, sem1);
            preparedStatement.setInt(2, sem2);
            preparedStatement.setInt(3, sem3);
            preparedStatement.setInt(4, c_roll);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("No student found with the given c_roll.");
            }
        }
    }

    private static void displayStudents(Connection connection) throws SQLException {
        System.out.println("\nDisplaying all students:");

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM std_info INNER JOIN std_per ON std_info.c_roll = std_per.c_roll INNER JOIN std_marks ON std_info.c_roll = std_marks.c_roll");

            while (resultSet.next()) {
                int c_roll = resultSet.getInt("c_roll");
                String name = resultSet.getString("name");
                String dob = resultSet.getString("dob");
                String course = resultSet.getString("course");
                String sec = resultSet.getString("sec");
                String address = resultSet.getString("address");
                int mob = resultSet.getInt("mob");
                String gender = resultSet.getString("gender");
                String blood = resultSet.getString("blood");
                int sem1 = resultSet.getInt("sem1");
                int sem2 = resultSet.getInt("sem2");
                int sem3 = resultSet.getInt("sem3");

                System.out.println("C_Roll: " + c_roll + ", Name: " + name + ", DOB: " + dob + ", Course: " + course + ", Sec: " + sec +
                        ", Address: " + address + ", Mob: " + mob + ", Gender: " + gender + ", Blood: " + blood +
                        ", Sem1: " + sem1 + ", Sem2: " + sem2 + ", Sem3: " + sem3);
            }
        }
    }
}