package Server;

import Data.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EventDBManager {
    //получение информации о мероприятии по id
    public static HashMap<String,String> getEventById(int event_id) throws SQLException {
        HashMap<String,String> event = new HashMap<>();
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet event_query = stmt.executeQuery( "SELECT * FROM public.event WHERE event_id = " + event_id);

        while (event_query.next()) {
            event.put("name", event_query.getString("event_name"));
            event.put("subject", event_query.getString("subject"));
            event.put("date", event_query.getString("date"));
            event.put("place", event_query.getString("place"));
            event.put("type", Queries.getEventTypeName(event_query.getInt("event_type_id")));
            event.put("genre", Queries.getGenreName(event_query.getInt("genre_id")));
            event.put("description", event_query.getString("description"));
            event.put("program", event_query.getString("program"));
        }
        event_query.close();
        stmt.close();
        return event;
    }

    //добавление мероприятия в БД
    public static int addEventInDB(Event event) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        String sql = "INSERT INTO public.event (event_name,subject,date,place,event_type_id,genre_id,description,program) "
                + "VALUES (?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
        return event_id;
    }

    //удаление мероприятий из БД
    public static void deleteEventFromDB(ArrayList<Event> deletedEvents) throws SQLException {
        for (Event event : deletedEvents) {
            Connection conn = DBManager.getInstance().getConn();
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM public.event WHERE event_id = " + event.getEventId();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public static void updateEventName(String eventName, int eventId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE public.event SET event_name = '" + eventName + "' WHERE event_id = " + eventId;
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updateSubject(String subject, int eventId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql;
        if (!Objects.equals(subject, "")) {
            sql = "UPDATE public.event SET subject = '" + subject + "' WHERE event_id = " + eventId;
        } else {
            sql = "UPDATE public.event SET subject = NULL WHERE event_id = " + eventId;
        }
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updateDate(String date, int eventId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql;
        if (!Objects.equals(date, "")) {
            sql = "UPDATE public.event SET date = '" + date + "' WHERE event_id = " + eventId;
        } else {
            sql = "UPDATE public.event SET date = NULL WHERE event_id = " + eventId;
        }
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updatePlace(String place, int eventId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql;
        if (!Objects.equals(place, "")) {
            sql = "UPDATE public.event SET place = '" + place + "' WHERE event_id = " + eventId;
        } else {
            sql = "UPDATE public.event SET place = NULL WHERE event_id = " + eventId;
        }
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updateEventType(String eventType, int eventId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql;
        if (!Objects.equals(eventType, "Не выбрано")) {
            sql = "UPDATE public.event SET event_type_id = '" + Queries.getEventTypeId(eventType) + "' WHERE event_id = " + eventId;
        } else {
            sql = "UPDATE public.event SET event_type_id = NULL WHERE event_id = " + eventId;
        }
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updateGenre(String genre, int eventId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql;
        if (!Objects.equals(genre, "Не выбрано")) {
            sql = "UPDATE public.event SET genre_id = '" + Queries.getGenreId(genre) + "' WHERE event_id = " + eventId;
        } else {
            sql = "UPDATE public.event SET genre_id = NULL WHERE event_id = " + eventId;
        }
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updateDescription(String description, int eventId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql;
        if (!Objects.equals(description, "")) {
            sql = "UPDATE public.event SET description = '" + description + "' WHERE event_id = " + eventId;
        } else {
            sql = "UPDATE public.event SET description = NULL WHERE event_id = " + eventId;
        }
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updateProgram(String program, int eventId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql;
        if (!Objects.equals(program, "")) {
            sql = "UPDATE public.event SET program = '" + program + "' WHERE event_id = " + eventId;
        } else {
            sql = "UPDATE public.event SET program = NULL WHERE event_id = " + eventId;
        }
        stmt.executeUpdate(sql);
        stmt.close();
    }
}
