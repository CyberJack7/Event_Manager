package ru.etu.components;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Event {

    private int eventId;
    private String eventName;
    private String subject;
    private String date;
    private String place;
    private int eventTypeId;
    private int genreId;
    private String description;
    private String program;

    public Event(int event_id) throws SQLException {
        Connection conn = JdbcRunner.getConn();
        Statement stmt = conn.createStatement();
        ResultSet event_query = stmt.executeQuery( "SELECT * FROM public.event WHERE event_id = " + event_id);

        //id = name = subject = date = place = event_type_id = genre_id = description = program = null;
        while (event_query.next()) {
            //System.out.println(admin_query.getBytes("admin_id").getClass().getSimpleName());
            this.eventId = event_query.getInt("event_id");
            this.eventName = event_query.getString("event_name");
            this.subject = event_query.getString("subject");
            this.date = event_query.getString("date");
            this.place = event_query.getString("place");
            this.eventTypeId = event_query.getInt("event_type_id");
            this.genreId = event_query.getInt("genre_id");
            this.description = event_query.getString("description");
            this.program = event_query.getString("program");
        }
        event_query.close();
        stmt.close();
        conn.close();
    }

    public Event(String eventName, String subject, String date, String place, int eventTypeId, int genreId, String description, String program) {
        this.eventName = eventName;
        this.subject = subject;
        this.date = date;
        this.place = place;
        this.eventTypeId = eventTypeId;
        this.genreId = genreId;
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

    public int getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(int eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
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
