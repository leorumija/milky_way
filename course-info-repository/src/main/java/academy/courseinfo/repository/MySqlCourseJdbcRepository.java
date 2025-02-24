package academy.courseinfo.repository;

import academy.courseinfo.domain.Course;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MySqlCourseJdbcRepository implements CourseRepository {
    private final Connection connection;

   private static final String url="jdbc:mysql://localhost:3306/ps_course";
   private static final String user="root";
   private static final String password="tartabiq";

    private static final String INSERT_COURSE = """
            INSERT INTO Courses (id, name, length, url)
             VALUES (?, ?, ?, ?)
            """;

    public MySqlCourseJdbcRepository(String schema) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void saveCourse(Course course) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_COURSE);
            statement.setString(1, course.getId());
            statement.setString(2, course.getName());
            statement.setLong(3, course.getLength());
            statement.setString(4, course.getUrl());
            statement.executeUpdate();
        } catch (SQLException e) {

            System.out.println(e);

        }finally {
            DBUtil.closePreparedStatement(statement);

        }
    }

    @Override
    public List<Course> getAllCourses() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM COURSES");

            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                Course course = new Course(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getLong(3),
                        resultSet.getString(4),
                        Optional.ofNullable(resultSet.getString(5)));
                courses.add(course);
            }
            return Collections.unmodifiableList(courses);
        } catch (SQLException e) {
            throw new RepositoryException("Failed to retrieve courses", e);
        }
    }

    @Override
    public void addNotes(String id, String notes) {

    }
}
