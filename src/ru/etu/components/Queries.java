package ru.etu.components;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {

    public static ArrayList<Integer> getEventsId() throws SQLException {
        ArrayList<Integer> arEventsId = new ArrayList<>();

        Connection conn = JdbcRunner.getConn();
        Statement stmt = conn.createStatement();
        ResultSet event_id_query = stmt.executeQuery( "SELECT event_id FROM public.event");

        while (event_id_query.next()) {
            int event_id = event_id_query.getInt("event_id");
            arEventsId.add(event_id);
        }
        event_id_query.close();
        stmt.close();
        conn.close();

        return arEventsId;
    }
}
