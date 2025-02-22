package academy.courseinfo.cli.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CourseRetrievalService {

    private static final String PS_URI = "https://app.pluralsight.com/profile/data/author/%s/all-content";
    private static final HttpClient CLIENT = HttpClient
            .newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    public List<PluralsightCourse> getCourseFor(String courseId) {

        HttpRequest requet = HttpRequest
                .newBuilder(URI.create(String.format(PS_URI, courseId)))
                .GET()
                .build();

        try {
            HttpResponse<String> response = CLIENT.send(requet, HttpResponse.BodyHandlers.ofString());

            return switch (response.statusCode()){
                case 200 -> null;
                case 404 -> List.of();
                default -> throw new RuntimeException("Pluralsight API call failed with status code " + response.statusCode());
            };

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Could not call Pluralsight API", e);
            }

    }
}
