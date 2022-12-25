package Server;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {

    //список id мероприятий
    public static ArrayList<Integer> getEventsId() throws SQLException {
        ArrayList<Integer> arEventsId = new ArrayList<>();

        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet event_id_query = stmt.executeQuery( "SELECT event_id FROM public.event");

        while (event_id_query.next()) {
            int event_id = event_id_query.getInt("event_id");
            arEventsId.add(event_id);
        }
        event_id_query.close();
        stmt.close();

        return arEventsId;
    }

    //получение списка жанров
    public static String[] getGenres() throws SQLException {
        ArrayList<String> arGenres = new ArrayList<>();

        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet genres_query = stmt.executeQuery( "SELECT genre_name FROM public.genre ORDER BY genre_name");

        while (genres_query.next()) {
            String genre_name = genres_query.getString("genre_name");
            arGenres.add(genre_name);
        }
        genres_query.close();
        stmt.close();
        int amount_genres = arGenres.size();
        String[] genres = new String[amount_genres + 1];
        genres[0] = "Не выбрано";
        int i = 1;
        for (String genre : arGenres) {
            genres[i] = genre;
            i++;
        }
        return genres;
    }

    //получение списка типов мероприятий
    public static String[] getEventTypes() throws SQLException {
        ArrayList<String> arEventTypes = new ArrayList<>();

        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet event_type_query = stmt.executeQuery( "SELECT type_name FROM public.event_type ORDER BY type_name");

        while (event_type_query.next()) {
            String type_name = event_type_query.getString("type_name");
            arEventTypes.add(type_name);
        }
        event_type_query.close();
        stmt.close();
        int amount_types = arEventTypes.size();
        String[] event_types = new String[amount_types + 1];
        event_types[0] = "Не выбрано";
        int i = 1;
        for (String event_type : arEventTypes) {
            event_types[i] = event_type;
            i++;
        }
        return event_types;
    }

    //получение id типа мероприятия по названию
    public static Integer getEventTypeId(String event_type_name) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet event_id_query = stmt.executeQuery( "SELECT event_type_id FROM public.event_type " +
                " WHERE type_name = '" + event_type_name + "'");

        Integer event_id = null;
        while (event_id_query.next()) {
            event_id = event_id_query.getInt("event_type_id");
        }
        event_id_query.close();
        stmt.close();
        return event_id;
    }

    //получение id жанра по названию
    public static Integer getGenreId(String genre_name) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet genre_id_query = stmt.executeQuery( "SELECT genre_id FROM public.genre " +
                " WHERE genre_name = '" + genre_name + "'");

        Integer genre_id = null;
        while (genre_id_query.next()) {
            genre_id = genre_id_query.getInt("genre_id");
        }
        genre_id_query.close();
        stmt.close();
        return genre_id;
    }

    //получение названия типа мероприятия по id
    public static String getEventTypeName(Integer event_type_id) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet event_name_query = stmt.executeQuery( "SELECT type_name FROM public.event_type " +
                "WHERE event_type_id = '" + event_type_id + "'");

        String event_type_name = null;
        while (event_name_query.next()) {
            event_type_name = event_name_query.getString("type_name");
        }
        event_name_query.close();
        stmt.close();
        return event_type_name;
    }

    //получение названия жанра по id
    public static String getGenreName(Integer genre_id) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet genre_name_query = stmt.executeQuery( "SELECT genre_name FROM public.genre " +
                "WHERE genre_id = '" + genre_id + "'");

        String genre_name = null;
        while (genre_name_query.next()) {
            genre_name = genre_name_query.getString("genre_name");
        }
        genre_name_query.close();
        stmt.close();
        return genre_name;
    }

    //список id сотрудников
    public static ArrayList<Integer> getStaffId() throws SQLException {
        ArrayList<Integer> arStaffId = new ArrayList<>();

        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet employee_id_query = stmt.executeQuery( "SELECT employee_id FROM public.employee");

        while (employee_id_query.next()) {
            int employee_id = employee_id_query.getInt("employee_id");
            arStaffId.add(employee_id);
        }
        employee_id_query.close();
        stmt.close();

        return arStaffId;
    }

    //получение названия должности по id
    public static String getPostName(Integer post_id) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet post_name_query = stmt.executeQuery( "SELECT post_name FROM public.post " +
                " WHERE post_id = '" + post_id + "'");

        String post_name = null;
        while (post_name_query.next()) {
            post_name = post_name_query.getString("post_name");
        }
        post_name_query.close();
        stmt.close();
        return post_name;
    }

    public static String[] getPosts() throws SQLException {
        ArrayList<String> arPosts = new ArrayList<>();

        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet posts_query = stmt.executeQuery( "SELECT post_name FROM public.post ORDER BY post_name");

        while (posts_query.next()) {
            String post_name = posts_query.getString("post_name");
            arPosts.add(post_name);
        }
        posts_query.close();
        stmt.close();
        int amount_posts = arPosts.size();
        String[] posts = new String[amount_posts + 1];
        posts[0] = "Не выбрано";
        int i = 1;
        for (String post : arPosts) {
            posts[i] = post;
            i++;
        }
        return posts;
    }

    public static Integer getPostId(String post) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet post_id_query = stmt.executeQuery( "SELECT post_id FROM public.post " +
                " WHERE post_name = '" + post + "'");

        Integer post_id = null;
        while (post_id_query.next()) {
            post_id = post_id_query.getInt("post_id");
        }
        post_id_query.close();
        stmt.close();
        return post_id;
    }

    public static ArrayList<Integer> getPostsId() throws SQLException {
        ArrayList<Integer> arPostsId = new ArrayList<>();

        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet posts_id_query = stmt.executeQuery( "SELECT post_id FROM public.post");

        while (posts_id_query.next()) {
            int post_id = posts_id_query.getInt("post_id");
            arPostsId.add(post_id);
        }
        posts_id_query.close();
        stmt.close();

        return arPostsId;
    }
}
