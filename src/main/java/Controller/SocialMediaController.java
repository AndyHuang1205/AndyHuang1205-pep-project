package Controller;
import Model.Message;
// import org.eclipse.jetty.http.HttpTester.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
    
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();

    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::register);
        app.post("/login",this::login);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}",this::getMessage);
        app.delete("/messages/{message_id}",this::deleteMessageByID);
        app.patch("/messages/{message_id}",this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);
        return app;
    }

    private void getMessagesByUser(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getMessagesByUser(id));
        
    }
    private void updateMessage(Context ctx){
        try{
            ObjectMapper om = new ObjectMapper();
            String messageText = om.readValue(ctx.body(), Message.class).getMessage_text();
            int id = Integer.parseInt(ctx.pathParam("message_id"));
            if(messageService.getMessageByID(id) != null && messageText.length()<256 && !messageText.isEmpty())
            {
                messageService.updateMessageByID(id, messageText);
                ctx.json(messageService.getMessageByID(id));
            }
            else ctx.status(400);
        }catch(JsonProcessingException j){
            j.printStackTrace();
        }
    }

    private void deleteMessageByID(Context ctx){
        
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageByID(id);
        if(messageService.deleteMessageByID(id)) ctx.json(message);
        else{
            ctx.status(200);
        }
    }
   
    private void getMessage(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        ctx.json(messageService.getMessageByID(id) == null ? "" : messageService.getMessageByID(id));
    }

    private void getAllMessages(Context ctx){
        ctx.json(messageService.getAllMessages());
    }

    private void createMessage(Context ctx){
        try{
            ObjectMapper om = new ObjectMapper();
            Message message = om.readValue(ctx.body(), Message.class);
            if (message.getMessage_text().length() < 256 && messageService.messagePostedByExist(message.getPosted_by()) && !message.getMessage_text().isBlank()){
                messageService.createMessage(message);
                int id = messageService.getIDByMessage(message.getMessage_text());
                message.setMessage_id(id);
                ctx.json(message);
            }else ctx.status(400);
            
        }catch(JsonProcessingException j){
            j.printStackTrace();
        }
       
        

    }
    private void login(Context ctx){
        try{
            ObjectMapper om = new ObjectMapper();
            String body = ctx.body();
            Account account = om.readValue(body, Account.class);
            String username = account.getUsername();
            String password = account.getPassword();
            if(accountService.verifyAccount(username, password)){
                int ID = accountService.getIDByUsername(username);
                account.setAccount_id(ID);
                ctx.json(account);
            }else ctx.status(401);
        }catch(JsonProcessingException j){
            j.printStackTrace();
        }
        

    }
    private void register(Context context) {
        try{
            ObjectMapper om = new ObjectMapper();
            Account account = om.readValue(context.body(),Account.class);
            String password = account.getPassword();
            if(accountService.getAccountByUsername(account.getUsername())==null && password.length()>3 && !account.getUsername().isEmpty()){ 
                accountService.createAccount(account);
                int id = accountService.getIDByUsername(account.getUsername());
                account.setAccount_id(id);
                context.json(account);
            }else context.status(400);
            

        }catch(JsonProcessingException j){
            j.printStackTrace();
        }
        

    }


}