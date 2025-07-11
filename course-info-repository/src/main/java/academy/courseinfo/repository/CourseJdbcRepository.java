package academy.courseinfo.repository;

import academy.courseinfo.domain.Course;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


class CourseJdbcRepository implements CourseRepository {


    private static final String db_init = "./db/db_init.sql";

    private static final String H2_DATABASE_URL = "jdbc:h2:file:%s;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM '" + db_init+ "'";

    private static final String INSERT_COURSE = """
            MERGE INTO Courses (id, name, length, url)
             VALUES (?, ?, ?, ?)
            """;

    private final DataSource dataSource;

    public CourseJdbcRepository(String databaseFile) {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(H2_DATABASE_URL.formatted(databaseFile));
        this.dataSource = jdbcDataSource;
    }

    @Override
    public void saveCourse(Course course) {
        executeStatement(INSERT_COURSE, statement -> {
            statement.setString(1, course.getId());
            statement.setString(2, course.getName());
            statement.setLong(3, course.getLength());
            statement.setString(4, course.getUrl());
            statement.execute();
        }, "Failed to insert " + course);
    }

    @Override
    public List<Course> getAllCourses() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
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

    private void executeStatement(String sql, PreparedStatementConfigurer configurer, String errorMsg) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            configurer.configure(statement);
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException(errorMsg, e);
        }
    }

    @FunctionalInterface
    interface PreparedStatementConfigurer {
        void configure(PreparedStatement statement) throws SQLException;
    }

}
