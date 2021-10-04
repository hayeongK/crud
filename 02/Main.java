package jdbc_crud;

import java.sql.*;

class User{
    private int id;
    private String name;
    private int age;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}

public class Main {
    private static String className = "com.mysql.jdbc.Driver";
    private static String dbUrl = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Seoul";
    private static String dbUser = "root";
    private static String DbPassword = "mysqlpassword";

    public static void main(String[] args){
        try {
            Class.forName(className);
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, DbPassword);
            User[] users = new User[5];
            users[0] = new User(1, "이익준", 25);
            users[1] = new User(2, "양석형", 27);
            users[2] = new User(3, "추민하", 25);
            users[3] = new User(4, "전미도", 26);
            users[4] = new User(2, "양석형", 25);

            //CREATE
            System.out.println("create");
            String sqlInsert = "INSERT INTO users2 VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            for(int i=0; i<4; i++) {
                ps.setInt(1, users[i].getId());
                ps.setString(2, users[i].getName());
                ps.setInt(3, users[i].getAge());
                int insert = ps.executeUpdate();
            }
            //READ
            read();

            //UPDATE
            System.out.println("update");
            String sqlUpdate = "UPDATE users2 SET age=? WHERE id=?";
            PreparedStatement psu = conn.prepareStatement(sqlUpdate);
            psu.setInt(1, users[4].getAge());
            psu.setInt(2, users[4].getId());
            int rsu = psu.executeUpdate();
            read();

            //DELETE
            System.out.println("delete");
            String sqld = "DELETE FROM users2 WHERE id = ?";
            PreparedStatement psd = conn.prepareStatement(sqld);
            psd.setInt(1, 1);
            int rsd = psd.executeUpdate();

            read();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void read(){
        System.out.println("read");
        try {
            Class.forName(className);
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, DbPassword);
            String sqlRead = "SELECT * FROM users2";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sqlRead);

            while(rs.next()){
                String name = rs.getString("name");
                String id = rs.getString("id");
                String age = rs.getString("age");
                System.out.println("Name:"+name+" age:"+age);
            }
            System.out.println("-------------------------");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
