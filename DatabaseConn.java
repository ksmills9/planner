import java.sql.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;


public class DatabaseConn {
    private Connection connection;

    public void startConnection(){
        try {
            this.connection = new DatabaseConn().getMySqlConnection();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Connection getMySqlConnection() {
        Connection ret = null;
        try
        {
            String mysqlUserName = "root";
            String mysqlPassword = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            String mysqlConnUrl = "jdbc:mysql://localhost:3306/planner";

            ret = DriverManager.getConnection(mysqlConnUrl, mysqlUserName , mysqlPassword);

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    int insertInDB(PreparedStatement update){
        try {
            return update.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
            return 0;
        }
    }

    ResultSet queryFromDB(PreparedStatement query){
        try {
            return query.executeQuery();
        }
        catch (SQLException ex){
            return null;
        }
    }

    public int addAccount(Account toAdd, String pass){
        PreparedStatement preStat1= null, preStat2 = null, preStat3 = null;
        String mainQuery = "INSERT INTO `accounts`(`account_name`, `account_timezone`) VALUES (?,?)";
        String idQuery = "SELECT LAST_INSERT_ID()";
        String otherQuery = "INSERT INTO `login`(`account_ID`, `password`, `salt`) VALUES (?,?,?)";
        ArrayList<byte[]> passHash = hashPass(pass);
        try {
            preStat1 = connection.prepareStatement(mainQuery);
            preStat1.setString(1, toAdd.getName());
            preStat1.setString(2, toAdd.getTimeZone());
            insertInDB(preStat1);

            preStat3 = connection.prepareStatement(idQuery);
            ResultSet rs = queryFromDB(preStat3);
            rs.next();
            int assignedID = rs.getInt("LAST_INSERT_ID()");
            rs.close();

            preStat2 = connection.prepareStatement(otherQuery);
            preStat2.setInt(1, assignedID);
            preStat2.setBytes(2, passHash.get(1));
            preStat2.setBytes(3, passHash.get(0));
            insertInDB(preStat2);
            return assignedID;
        } catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return -1;
    }

    public Account login(String accName, String password) throws SQLException {
        PreparedStatement preStat1 = null, preStat3 = null; ResultSet rs1 = null, rs3 = null;
        String initialQuery = "SELECT login.password, login.salt FROM login JOIN accounts on " +
                "accounts.ID = login.account_ID WHERE accounts.account_name = ?";
        try {
            preStat1 = connection.prepareStatement(initialQuery);
            preStat1.setString(1, accName);
            rs1 = queryFromDB(preStat1);
            if(rs1.next()){
                if(Arrays.equals(rs1.getBytes("password"), verifyHash(password, rs1.getBytes("salt")))){
                    String loadQuery = "SELECT * FROM `accounts` WHERE `account_name`= ?";
                    preStat3 = connection.prepareStatement(loadQuery);
                    preStat3.setString(1,accName);
                    rs3 = queryFromDB(preStat3); rs3.next();
                    return new Account(rs3.getInt("ID"), rs3.getString("account_name"),
                            rs3.getString("account_timezone"));
                }
            }
        } catch (SQLException ex){
            throw ex;
        }
//        finally {
//            preStat1.close(); preStat3.close();
//            rs1.close(); rs3.close();
//        }
        return null;
    }

    public boolean changeUser(String accName, String newStr, String field) throws SQLException{
        PreparedStatement preStat = null;
        String update = "UPDATE accounts SET "+ field +" = ? WHERE account_name = ?";
        try {
            preStat = connection.prepareStatement(update);
            preStat.setString(1, newStr);
            preStat.setString(2, accName);
            int rs = insertInDB(preStat);
            if(rs == 1) return true;
            else return false;
        } catch (SQLException ex){
            throw ex;
        } finally {
            preStat.close();
        }
    }

    public boolean changeUserPass(String accName, String oldPass, String newpass) throws SQLException{
        PreparedStatement preStat = null; ResultSet rs = null;
        String initialQuery = "SELECT login.account_ID, login.password, login.salt FROM login JOIN accounts on " +
                "accounts.ID = login.account_ID WHERE accounts.account_name = ?";
        try {
            preStat = connection.prepareStatement(initialQuery);
            preStat.setString(1, accName);

            rs = queryFromDB(preStat);
            rs.next();
            if(Arrays.equals(rs.getBytes("password"), verifyHash(oldPass, rs.getBytes("salt")))){
                ArrayList<byte[]> newPassHash = hashPass(newpass);
                String updateDB = "UPDATE login SET login.password = ?, login.salt = ? WHERE account_ID = ?";
                PreparedStatement preStat2 = connection.prepareStatement(updateDB);
                preStat2.setBytes(1, newPassHash.get(1));
                preStat2.setBytes(2, newPassHash.get(0));
                preStat2.setInt(3, rs.getInt("account_ID"));
                insertInDB(preStat2);
                return true;
            }
        } catch (SQLException ex){
            throw ex;
        } finally {
            rs.close(); preStat.close();
        }
        return false;
    }

    public int addEvent(Event toAdd, Account user){
        PreparedStatement preStat = null, preStat2 = null;
        String insert = "INSERT INTO `events`(`account_ID`, `event_name`, `event_desc`, `start_time`, `end_time`, " +
                "`event_location`) VALUES(?,?,?,?,?,?)";
        try {
            preStat = connection.prepareStatement(insert);
            preStat.setInt(1, user.getID());
            preStat.setString(2, toAdd.getName());
            preStat.setString(3, toAdd.getDescription());
            preStat.setString(4, toAdd.getStartTimeString());
            preStat.setString(5, toAdd.getEndTimeString());
            preStat.setString(6, toAdd.getLocation());
            insertInDB(preStat);

            preStat2 = connection.prepareStatement("SELECT LAST_INSERT_ID()\n");
            ResultSet rs = queryFromDB(preStat2);
            rs.next();
            int event_ID = rs.getInt("LAST_INSERT_ID()");
            rs.close();
            return event_ID;
        } catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            try {
                preStat.close(); preStat2.close();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return -1;
    }

    AllEvents loadEvents(Account ofUser){
        try {
            PreparedStatement preState = connection.prepareStatement("SELECT * FROM events WHERE account_ID = ?");
            preState.setInt(1, ofUser.getID());

            ResultSet rs = queryFromDB(preState);
            AllEvents eventList = new AllEvents();
            while (rs.next()){
                Event newEvent = new Event(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                eventList.addEvent(newEvent);
            }
            return eventList;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    ArrayList<byte[]> hashPass(String password){
        ArrayList<byte[]> retval = new ArrayList<>();
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            retval.add(salt);

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            retval.add(factory.generateSecret(spec).getEncoded());
            return retval;

        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    byte[] verifyHash(String givenPassword, byte[] givenSalt){
        try {
            KeySpec spec = new PBEKeySpec(givenPassword.toCharArray(), givenSalt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        } catch (Exception ex){
            return null;
        }
    }

    public void closeConnection() throws SQLException{
        connection.close();
    }
}
