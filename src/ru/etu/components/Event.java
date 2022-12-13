package ru.etu.components;

import java.sql.*;
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
        ResultSet event_query = stmt.executeQuery( "SELECT * FROM public.event " +
                "JOIN public.event_type USING(event_type_id) JOIN public.genre USING(genre_id) WHERE event_id = " + event_id);

        //id = name = subject = date = place = event_type_id = genre_id = description = program = null;
        while (event_query.next()) {
            //System.out.println(admin_query.getBytes("admin_id").getClass().getSimpleName());
            this.eventId = event_query.getInt("event_id");
            this.eventName = event_query.getString("event_name");
            this.subject = event_query.getString("subject");
            this.date = event_query.getString("date");
            this.place = event_query.getString("place");
            this.eventType = event_query.getString("type_name");
            this.genre = event_query.getString("genre_name");
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
        /*Connection conn = JdbcRunner.getConn();
        Statement stmt = conn.createStatement();
        ResultSet event_type_id_query = stmt.executeQuery( "SELECT event_type_id FROM public.event_type WHERE type_name = '" + eventType + "'");
        while (event_type_id_query.next()) {
            this.eventTypeId = event_type_id_query.getInt("event_type_id");
        }
        event_type_id_query.close();

        ResultSet genre_id_query = stmt.executeQuery( "SELECT genre_id FROM public.genre WHERE genre_name = '" + genre + "'");
        while (genre_id_query.next()) {
            this.genreId = genre_id_query.getInt("genre_id");
        }
        genre_id_query.close();
        stmt.close();
        conn.close();*/
    }

    public static void addEventInDB(Event event) throws SQLException {
        Connection conn = JdbcRunner.getConn();
        Statement stmt = conn.createStatement();
        String sql = "INSERT INTO event (event_name,subject,date,place,event_type_id,genre_id,description,program) "
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
