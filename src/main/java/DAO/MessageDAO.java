package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO{
    
    public List<Message> getAllMessages(){
        Connection con = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        Message message;
        try{
            String sql = "Select * from message";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                message = new Message(rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

        }catch(SQLException e) {System.out.println(e.getMessage());}
        return messages;
        
    }
    public Boolean messagePostedByExist(int posted_by){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "select * from message where posted_by = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,posted_by);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return true;
        }catch(SQLException e) {System.out.println(e.getMessage());}
        return false;

    }
    public void createMessage(Message message){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message(posted_by,message_text,time_posted_epoch) values(?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println();
        }
    }

    public int getIDByMessage(String messageText){
        try{
            Connection con = ConnectionUtil.getConnection();
            String sql = "select message_id from message where message_text = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, messageText);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return rs.getInt("message_id");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public boolean deleteMessageByID(int message_id){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "delete from message where message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, message_id);
            int res = ps.executeUpdate();
            if(res > 0) return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
        
    }

    public Message getMessageByID(int id){
        Connection con = ConnectionUtil.getConnection();
        try{
            
            String sql = "Select * from message where message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return new Message(rs.getInt("message_id"), 
                                            rs.getInt("posted_by"),
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch"));
            }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
        }
    

        public Boolean updateMessageByID(int id,String message){
            try(Connection con = ConnectionUtil.getConnection()){
                String sql = "update message set message_text = ? where message_id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,message);
                ps.setInt(2, id);
                return ps.executeUpdate() > 0;
            }catch(SQLException e){
                e.printStackTrace();
                return false;
            }
        }
    
        public List<Message> getMessagesByUser(int id){
            List<Message> messages = new ArrayList<>();
            try(Connection con = ConnectionUtil.getConnection()){
                String sql = "Select * from message where posted_by = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Message message = new Message(rs.getInt("message_id"),
                                                    rs.getInt("posted_by"),
                                                    rs.getString("message_text"),
                                                    rs.getLong("time_posted_epoch"));
                    messages.add(message);
                }
                return messages;
            }catch(SQLException e) {
                e.printStackTrace();
                return messages;
        }
        }
    
}