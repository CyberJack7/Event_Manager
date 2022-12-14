package ru.etu.components;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class Event {

    private int eventId;
    private String eventName;
    private String subject;
    private String date;
    private String place;
    private String eventType;
    private String genre;
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
            this.eventType = Queries.getEventTypeName(event_query.getInt("event_type_id"));
            this.genre = Queries.getGenreName(event_query.getInt("genre_id"));
            this.description = event_query.getString("description");
            this.program = event_query.getString("program");
        }
        event_query.close();
        stmt.close();
        conn.close();
    }

    public Event(String eventName, String subject, String date, String place, String eventType, String genre, String description, String program) throws SQLException {
        this.eventName = eventName;
        this.subject = subject;
        this.date = date;
        this.place = place;
        this.eventType = eventType;
        this.genre = genre;
        this.description = description;
        this.program = program;
    }

    public static int addEventInDB(Event event) throws SQLException {
        Connection conn = JdbcRunner.getConn();
        //Statement stmt = conn.createStatement();
        String sql = "INSERT INTO public.event (event_name,subject,date,place,event_type_id,genre_id,description,program) "
                + "VALUES (?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, event.getEventName());
        if (!Objects.equals(event.getSubject(), "")) {
            pstmt.setString(2, event.getSubject());
        } else {
            pstmt.setNull(2, java.sql.Types.NULL);
        }
        if (!Objects.equals(event.getDate(), "")) {
            pstmt.setString(3, event.getDate());
        } else {
            pstmt.setNull(3, java.sql.Types.NULL);
        }
        if (!Objects.equals(event.getPlace(), "")) {
            pstmt.setString(4, event.getPlace());
        } else {
            pstmt.setNull(4, java.sql.Types.NULL);
        }
        if (!Objects.equals(event.getEventType(), "Не выбрано")) {
            pstmt.setInt(5, Queries.getEventTypeId(event.getEventType()));
        } else {
            pstmt.setNull(5, java.sql.Types.NULL);
        }
        if (!Objects.equals(event.getGenre(), "Не выбрано")) {
            pstmt.setInt(6, Queries.getGenreId(event.getGenre()));
        } else {
            pstmt.setNull(6, java.sql.Types.NULL);
        }
        if (!Objects.equals(event.getDescription(), "")) {
            pstmt.setString(7, event.getDescription());
        } else {
            pstmt.setNull(7, java.sql.Types.NULL);
        }
        if (!Objects.equals(event.getProgram(), "")) {
            pstmt.setString(8, event.getProgram());
        } else {
            pstmt.setNull(8, java.sql.Types.NULL);
        }
        pstmt.executeUpdate();
        ResultSet keys_query = pstmt.getGeneratedKeys();
        int event_id = 0;
        while (keys_query.next()) {
            event_id = keys_query.getInt("event_id");
        }
        pstmt.close();
        conn.close();
        return event_id;
    }

    public static void deleteEventFromDB(ArrayList<Event> deletedEvents) throws SQLException {
        for (Event event : deletedEvents) {
            int event_id = event.getEventId();
            Connection conn = JdbcRunner.getConn();
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM public.event WHERE event_id = " + event_id;
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        }
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
        /*if (eventType == null) {
            return eventType = "-";
        }*/
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getGenre() {
        /*if (genre == null) {
            return genre = "-";
        }*/
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
