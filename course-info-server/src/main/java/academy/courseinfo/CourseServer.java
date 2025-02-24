package academy.courseinfo;

import academy.courseinfo.repository.CourseRepository;
import academy.courseinfo.server.CourseResource;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class CourseServer {
    private static final Logger LOG = LoggerFactory.getLogger(CourseServer.class);
    private static final String BASE_URI = "http://localhost:8080/";


    public static void main(String... args) {
      //  String databaseFilename = loadDatabaseFilename();
        LOG.info("Starting HTTP server with database {}", "databaseFilename");
        CourseRepository courseRepository = CourseRepository.openMySqlCourseRepository("./db/course.db");
        ResourceConfig config = new ResourceConfig().register(new CourseResource(courseRepository));

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }

   /* private static String loadDatabaseFilename() {
        try (InputStream propertiesStream = CourseServer.class.getResourceAsStream("/server.properties")) {
            Properties properties = new Properties();
            properties.load(propertiesStream);
            return properties.getProperty("course-info.database");
        } catch (IOException e) {
            throw new IllegalStateException("Could not load database filename");
        }
    }*/
}
