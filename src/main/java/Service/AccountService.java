package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService{
    public AccountDAO accountDAO;
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }
    
    public Account getAccountByUsername(String username){
        return accountDAO.getAccountByUsername(username);
    }
    public Account createAccount(Account account){
        return accountDAO.createAccount(account);

    }

    public int getIDByUsername(String username){
        return accountDAO.getIDByUsername(username);
    }

    public boolean verifyAccount(String username, String password){
        return accountDAO.verifyAccount(username, password);
    }


    
    
}