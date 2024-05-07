package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService{
    public MessageDAO messageDAO;
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public void createMessage(Message message){
        messageDAO.createMessage(message);
    }

    public boolean messagePostedByExist(int posted_by){
        return messageDAO.messagePostedByExist(posted_by);
    }

    public int getIDByMessage(String message){
        return messageDAO.getIDByMessage(message);
    }

    public boolean deleteMessageByID(int id){
        return messageDAO.deleteMessageByID(id);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int id){
        return messageDAO.getMessageByID(id);
    }
    
    public boolean updateMessageByID(int id, String message){
        return messageDAO.updateMessageByID(id, message);
    }

    public List<Message> getMessagesByUser(int id){
        return messageDAO.getMessagesByUser(id);
    }
}