package ru.etu.test;

import ru.etu.components.JdbcRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class FirstTest {
    public static void main(String[] args) throws SQLException {
        Student student1 = new Student("name1", "111111");
        student1.setCourse((byte)3);
        System.out.println(student1.getCourse());
//        Vector<String> vector = new Vector<String>();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("123");
        arrayList.add("456");
        arrayList.add("789");
        arrayList.remove(0);
        int i = arrayList.indexOf("456");
        System.out.println(i);

        for (String s : arrayList) {
            System.out.println(s);
        }

        HashMap<String, Student> map = new HashMap<>();
        map.put("123", student1);
        map.put("456", new Student("name2", "111112"));
        map.put("789", new Student("name3", "111113"));
        Student student = map.get("789");
        map.remove("789");
//        map.clear();
        Set<String> strings = map.keySet();
        for (String next : strings) {
            System.out.println(next + " - " + map.get(next).getName());
        }

        try {
            System.out.println(arrayList.get(100));
        }catch(IndexOutOfBoundsException e){
            System.out.println("IndexOut");
        }

        /*ArrayList<Integer> arEventsId = new ArrayList<>();
        System.out.println(arEventsId);

        Connection conn = JdbcRunner.getConn();
        Statement stmt = conn.createStatement();
        ResultSet event_id_query = stmt.executeQuery( "SELECT event_id FROM public.event");

        while (event_id_query.next()) {
            int event_id = event_id_query.getInt("event_id");
            arEventsId.add(event_id);
        }
        event_id_query.close();
        stmt.close();
        conn.close();*/
    }
}