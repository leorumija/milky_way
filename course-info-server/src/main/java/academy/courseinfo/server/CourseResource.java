package academy.courseinfo.server;

import academy.courseinfo.domain.Course;
import academy.courseinfo.repository.CourseRepository;
import academy.courseinfo.repository.RepositoryException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

@Path("/courses")
public class CourseResource {
    private static final Logger LOG = LoggerFactory.getLogger(CourseResource.class);

    private CourseRepository courseRepository;

    public CourseResource(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    public String getCourses() {
        try {
           List<Course> courses = courseRepository
                    .getAllCourses()
                    .stream()
                    .sorted(comparing(Course::id)).collect(Collectors.toList());
            return courses.stream().map(Course::toString).collect(Collectors.joining("\n"));
        } catch (RepositoryException e) {
            LOG.error("Could not retrieve courses from the database", e);
            throw new NotFoundException();
        }
    }

    @POST
    @Path("/{id}/notes")
    //@Consumes(MediaType.TEXT_PLAIN)
    public void addNotes(@PathParam("id") String id, String notes) {
        courseRepository.addNotes(id, notes);
    }
}
