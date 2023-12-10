package org.kukuking.database;

import org.kukuking.entity.Message;
import org.kukuking.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class MyDatabaseUtil {
    private static final DataSource dataSource = MyDruid.getDataSource();
    private static final Logger LOGGER = MyDruid.getLogger();

//    下面俩是查询 和 插入/更新的模板，第二个的result不能回传，因为在statement.close()后resultset就是空集了

    public static void databaseInserter(String sql) {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
    }

    private static ResultSet databaseSearcher(String sql) {
        ResultSet result = null;
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return result;
    }

    /*________________________________________________________________________________________________*/
    //用户登陆
    public static User login(String userID, String password) {
        User result = null;
        if (userID == null || password == null) {
            return null;
        }
        String sql = "select userID, userName, password, groupID from user where userID = '{userID}'";
        sql = sql.replace("{userID}", userID);
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next() && rs.getString("password").equals(password)) {
                result = new User(rs.getString("userName"), rs.getString("password"), rs.getString("userID"), rs.getInt("groupID"));
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return result;
    }

    public static void initDB() {
        String sql = """
                """;
    }

    public static String getUserID(String userName) {
        String result = null;
        if (userName == null) {
            return null;
        }
        String sql = "select userID from user where userName = '{userName}'";
        sql = sql.replace("{userName}", userName);
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                result = rs.getString("userID");
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return result;
    }

    //用户注册
    public static void signUp(String userName, String password, String userID) {
        if (getUserID(userName) == null) {
            String sql = "insert into user(userID, userName, password, groupID) values ('{userID}', '{userName}', '{password}', '{groupID}')";
            sql = sql.replace("{userID}", userID);
            sql = sql.replace("{userName}", userName);
            sql = sql.replace("{password}", password);
            sql = sql.replace("{groupID}", "2");
            databaseInserter(sql);
        }
//      groupID: 0=root 1=manager 2=user
    }

    public static void reset(String userID, String password) {
        String sql = "update user set password = '{password}' where userID = '{userID}'";
        sql = sql.replace("{userID}", userID);
        sql = sql.replace("{password}", password);
        databaseInserter(sql);
    }

    public static int indentify(String userID) {
        int result = 2;
        String sql = "select groupID from user where userID = '{userID}'";
        sql = sql.replace("{userID}", userID);
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                result = Integer.parseInt(rs.getString("groupID"));
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return result;
    }

    public static void saveMessageToDB(Message message) {
        String sql = "insert into message(messageID, parentID, publisherID, changeTime, support, messageText) VALUES ('{messageID}','{parentID}','{publisherID}','{changeTime}','{support}','{messageText}')";
        sql = sql.replace("{messageID}", String.valueOf(message.getMessageID()));
        sql = sql.replace("{parentID}", String.valueOf(message.getParentID()));
        sql = sql.replace("{publisherID}", String.valueOf(message.getPublisherID()));
        sql = sql.replace("{changeTime}", String.valueOf(message.getChangeTime()));
        sql = sql.replace("{support}", String.valueOf(message.getSupport()));
        sql = sql.replace("{messageText}", message.getMessageText());
        databaseInserter(sql);
    }

    public static List<Message> randomMessageFromDB(int limit) {
        List<Message> result = new ArrayList<>();
        String sql = "select * from message where parentID = -1 order by rand() limit " + limit;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                Message message = new Message();
                message.setMessageID(rs.getInt("messageID"));
                message.setParentID(-1);
                message.setPublisherID(rs.getInt("publisherID"));
                message.setChangeTime(rs.getLong("changeTime"));
                message.setSupport(rs.getInt("support"));
                message.setMessageText(rs.getString("messageText"));
                result.add(message);
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return result;
    }
}