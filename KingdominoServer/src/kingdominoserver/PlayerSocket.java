/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class PlayerSocket {
    
    private Socket playerSocket;
    
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    
    
    public PlayerSocket(ServerSocket serverclient){
        this.connect(serverclient);
    }
    
    public Socket getPlayerSocket(){ 
        return this.playerSocket;
    }
    
    public ObjectInputStream getPlayerObjectInputStream(){
        return this.objectInputStream;
    }
    
    public ObjectOutputStream getPlayerObjectOutputStream(){
        return this.objectOutputStream;
    }
    
    public void setPlayerSocket(Socket playerSocket){
        this.playerSocket=playerSocket;
    }
    
    public void setPlayerObjectInputStream(ObjectInputStream objectInputStream){
        this.objectInputStream=objectInputStream;
    }
    
    public void setPlayerObjectOutputStream(ObjectOutputStream objectOutputStream){
        this.objectOutputStream=objectOutputStream;
    }
    
    public Object receiveObject(){
        try {
            return this.getPlayerObjectInputStream().readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean sendObject(Object objectSend){
        try {
            this.getPlayerObjectOutputStream().writeObject(objectSend);
            this.getPlayerObjectOutputStream().flush();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean connect(ServerSocket serverclient){
        try {
            this.setPlayerSocket(serverclient.accept());
            this.setPlayerObjectOutputStream(this.createPlayerObjectOutputStream());
            this.setPlayerObjectInputStream(this.createPlayerObjectInputStream());
            serverclient.close();
            
            return true;
            
        }catch (IOException ex){
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ObjectOutputStream createPlayerObjectOutputStream(){
        try {
            return new ObjectOutputStream(this.getPlayerSocket().getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ObjectInputStream createPlayerObjectInputStream(){
        try {
            return new ObjectInputStream(this.getPlayerSocket().getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean close(){
        try {
            this.getPlayerObjectInputStream().close();
            this.getPlayerObjectOutputStream().close();
            this.getPlayerSocket().close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
}
