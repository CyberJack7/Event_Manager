package Server;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
    private static DBManager dbm;
    private Connection conn;

    private DBManager() {
        /*String DB_URL = "jdbc:postgresql://localhost/javaDB";
        String USER = "postgres";
        String PASS = "pass";*/

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String userHome = System.getProperty("user.home");
        String configPath = userHome + "/OOP_Project/database.xml";

        File file = new File(configPath);

        DBConf dbConf;
        XStream xStream = new XStream();
        xStream.alias("DBConf", DBConf.class);
        xStream.addPermission(AnyTypePermission.ANY); //снимаем ограничения, мешающие прочитать конфигурационный файл
        if (file.exists()) {
            dbConf = (DBConf) xStream.fromXML(file);
        } else {
            file.getParentFile().mkdirs();
            dbConf = new DBConf();
            try {
                xStream.toXML(dbConf, new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        Properties info = new Properties();
        info.setProperty("user", dbConf.getLogin());
        info.setProperty("password", dbConf.getPassword());
        info.setProperty("useUnicode", "true");
        info.setProperty("characterEncoding", "utf8");
        //URL к базе состоит из протокола:подпротокола://[хоста]:[порта_СУБД]/[БД] и других_сведений

        String DB_URL = "jdbc:postgresql://" + dbConf.getHost() + ":" + dbConf.getPort() + "/" + dbConf.getBase();
        try {
            //Создаём соединение
            conn = DriverManager.getConnection(DB_URL, info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DBManager getInstance() {
        if (dbm == null) {
            dbm = new DBManager();
        }
        return dbm;
    }

    public Connection getConn(){
        return conn;
    }
}
