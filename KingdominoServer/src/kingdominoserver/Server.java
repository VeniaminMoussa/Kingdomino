/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class Server{

    private ServerSocket serverSocket;

    public Server(){}
    
    public void activate(){
        try {
            this.serverSocket = new ServerSocket(7777);
        }catch (IOException ex){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ServerSocket getServerSocket(){
        return this.serverSocket;
    }
    
    public ServerSocket setServerSocket(){
        return this.serverSocket;
    }
    
    public PlayerSocket addPlayerSocket(){
        return new PlayerSocket(this.getServerSocket());
    }

    public boolean close(){
        
        try {
            this.getServerSocket().close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
