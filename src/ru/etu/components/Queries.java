package ru.etu.components;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Queries {

    //список id мероприятий
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

    //ассоциативный массив жанров genre_id => genre_name
    public static String[] getGenresInfo() throws SQLException {
        ArrayList<String> arGenres = new ArrayList<>();

        Connection conn = JdbcRunner.getConn();
        Statement stmt = conn.createStatement();
        ResultSet genres_query = stmt.executeQuery( "SELECT genre_name FROM public.genre ORDER BY genre_name");

        while (genres_query.next()) {
            String genre_name = genres_query.getString("genre_name");
            arGenres.add(genre_name);
        }
        genres_query.close();
        stmt.close();
        conn.close();
        int amount_genres = arGenres.size();
        String[] genres = new String[amount_genres];
        int i = 0;
        for (String genre : arGenres) {
            genres[i] = genre;
            i++;
        }

        return genres;
    }

    public static String[] getEventTypesInfo() throws SQLException {
        ArrayList<String> arEventTypes = new ArrayList<>();

        Connection conn = JdbcRunner.getConn();
        Statement stmt = conn.createStatement();
        ResultSet event_type_query = stmt.executeQuery( "SELECT type_name FROM public.event_type ORDER BY type_name");

        while (event_type_query.next()) {
            String type_name = event_type_query.getString("type_name");
            arEventTypes.add(type_name);
        }
        event_type_query.close();
        stmt.close();
        conn.close();
        int amount_types = arEventTypes.size();
        String[] event_types = new String[amount_types];
        int i = 0;
        for (String event_type : arEventTypes) {
            event_types[i] = event_type;
            i++;
        }

        return event_types;
    }
}
