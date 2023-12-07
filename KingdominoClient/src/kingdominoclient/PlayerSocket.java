/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoclient; 

/**
 *
 * @author Lenovo
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class PlayerSocket {
    
    private Socket serverSocket;
    private ObjectOutputStream outputObjectStream;
    private ObjectInputStream inputObjectStream;
    
    public PlayerSocket(){
        
        this.connect();
    }
    
    public Socket getServerSocket(){
        return this.serverSocket;
    }
    
    public ObjectOutputStream getOutputObjectStream(){
        return this.outputObjectStream;
    }
    
    public ObjectInputStream getInputObjectStream(){
        return this.inputObjectStream;
    }
    
    public void setServerSocket(Socket serverSocket){
        this.serverSocket=serverSocket;
    }
    
    public void setOutputObjectStream(ObjectOutputStream outputObjectStream){
        this.outputObjectStream=outputObjectStream;
    }
    
    public void setInputObjectStream(ObjectInputStream inputObjectStream){
        this.inputObjectStream=inputObjectStream;
    }
    
    public Socket createServerSocket(){
        try{
            return new Socket("localhost", 7777);
        }
        catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error Message",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public ObjectOutputStream createOutputObjectStream(){
        try{
            return new ObjectOutputStream(this.getServerSocket().getOutputStream());
        }
        catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error Message",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public ObjectInputStream createInputObjectStream(){
        try{
            return new ObjectInputStream(this.getServerSocket().getInputStream());
        }
        catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error Message",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public void connect(){
        this.setServerSocket(this.createServerSocket());
        this.setOutputObjectStream(this.createOutputObjectStream());
        this.setInputObjectStream(this.createInputObjectStream());    
    }
    
    public Object receiveObject(){
        try {
            return this.getInputObjectStream().readObject();
        }catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean sendObject(Object objectSend){
        try {
            this.getOutputObjectStream().writeObject(objectSend);
            this.getOutputObjectStream().flush();
            return true;
        }catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean close(){
        try {
            this.getInputObjectStream().close();
            this.getOutputObjectStream().close();
            this.getServerSocket().close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    
    
    
    
    
    
    
}
