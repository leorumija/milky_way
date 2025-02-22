package academy.courseinfo.repository;

import academy.courseinfo.domain.Course;

import java.util.List;

public interface CourseRepository {

    void saveCourse(Course course);

    List<Course> getAllCourses();

    void addNotes(String id, String notes);

    /// very good approach how to seal implmentation.
    static CourseRepository openCourseRepository(String databaseFile) {
        return new CourseJdbcRepository(databaseFile);

    }
}
