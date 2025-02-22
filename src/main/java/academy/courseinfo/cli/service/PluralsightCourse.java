package academy.courseinfo.cli.service;

import java.time.Duration;
import java.time.LocalTime;

public record PluralsightCourse(String id, String title, String duration, String contentUrl, boolean isRetired) {
    public long durationInMinutes() {
        return Duration.between(
                LocalTime.MIN,
                LocalTime.parse(duration())
        ).toMinutes();
    }
}
