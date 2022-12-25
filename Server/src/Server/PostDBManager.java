package Server;

import Data.Post;

import java.sql.*;
import java.util.ArrayList;

public class PostDBManager {
    public static Post getPostById(int post_id) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet post_query = stmt.executeQuery( "SELECT * FROM public.post WHERE post_id = " + post_id);

        String name = null;
        Integer wage = null;
        while (post_query.next()) {
            name = post_query.getString("post_name");
            wage = post_query.getInt("wage");
        }
        Post post = new Post(post_id, name, wage);
        post_query.close();
        stmt.close();
        return post;
    }

    public static void deletePostsFromDB(ArrayList<Post> deleted_posts) throws SQLException {
        for (Post post : deleted_posts) {
            Connection conn = DBManager.getInstance().getConn();
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM public.post WHERE post_id = " + post.getPostId();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public static void updatePostName(String name, int postId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE public.post SET post_name = '" + name + "' WHERE post_id = " + postId;
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static void updatePostWage(Integer wage, int postId) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE public.post SET wage = " + wage + " WHERE post_id = " + postId;
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static int addPostInDB(Post post) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        String sql = "INSERT INTO public.post (post_name,wage) VALUES (?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, post.getName());
        pstmt.setInt(2, post.getWage());
        pstmt.executeUpdate();
        ResultSet keys_query = pstmt.getGeneratedKeys();
        int post_id = 0;
        while (keys_query.next()) {
            post_id = keys_query.getInt("post_id");
        }
        pstmt.close();
        return post_id;
    }
}
