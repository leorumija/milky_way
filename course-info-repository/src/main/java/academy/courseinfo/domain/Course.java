package academy.courseinfo.domain;

import java.io.Serializable;
import java.util.Optional;

public class Course implements Serializable {
    private String id;
    private String name;
    private long length;
    private String url;
    Optional<String> notes ;

    public Course(String id, String name, long length, String url, Optional<String> notes) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.url = url;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Optional<String> getNotes() {
        return notes;
    }

    public void setNotes(Optional<String> notes) {
        this.notes = notes;
    }

    private static void filled(String s) {
        if(s == null || s.isBlank()) {
            throw new IllegalArgumentException("No value present!");
        }
    }
}
