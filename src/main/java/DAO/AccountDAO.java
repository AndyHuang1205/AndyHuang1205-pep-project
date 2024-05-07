package DAO;

import Util.ConnectionUtil;
import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO{
    
    public List<Account> getAllAccounts(){
        Connection con = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        Account account;
        try{
            String sql = "Select * from account";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                account = new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e) {System.out.println(e.getMessage());}
        return accounts;
        
    }
    public Account getAccountByUsername(String username){
        Connection con = ConnectionUtil.getConnection();
        Account account;
        try{
            String sql = "select * from account where username = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                account = new Account(rs.getString("username"), rs.getString("password"));
                return account;
            }
        }catch(SQLException e) {System.out.println(e.getMessage());}
        return null;

    }

    public boolean verifyAccount(String username, String password){
        try{
            Connection con = ConnectionUtil.getConnection();
            String sql = "Select * from account where username = ? and password = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public int getIDByUsername(String username){
        try{
            Connection con = ConnectionUtil.getConnection();
            String sql = "select account_id from account where username = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt("account_id");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
        

    }
    public Account createAccount(Account account){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account(username,password) values(?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            return account;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    
}