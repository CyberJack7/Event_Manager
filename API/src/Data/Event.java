package Data;

import java.util.HashMap;
import java.io.Serializable;
public class Event implements Serializable{

    private int eventId;
    private String eventName;
    private String subject;
    private String date;
    private String place;
    private String eventType;
    private String genre;
    private String description;
    private String program;

    public Event(int event_id, HashMap<String, String> event) {
        this.eventId = event_id;
        this.eventName = event.get("name");
        this.subject = event.get("subject");
        this.date = event.get("date");
        this.place = event.get("place");
        this.eventType = event.get("type");
        this.genre = event.get("genre");
        this.description = event.get("description");
        this.program = event.get("program");
    }

    public Event(String eventName, String subject, String date, String place, String eventType, String genre, String description, String program) {
        this.eventName = eventName;
        this.subject = subject;
        this.date = date;
        this.place = place;
        this.eventType = eventType;
        this.genre = genre;
        this.description = description;
        this.program = program;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
