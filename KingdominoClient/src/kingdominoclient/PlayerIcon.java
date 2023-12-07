 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoclient;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Lenovo
 */
public class PlayerIcon{
    
    private ImageIcon iconPlayer;
    private String color;
    private Boolean available;
    
    public PlayerIcon(){}
    
    public PlayerIcon(String color, Boolean available){
        this.setColor(color);
        this.setAvailable(available);
    }
        
    public ImageIcon getIconPlayer(){
        return this.iconPlayer;
    }
        
    public String getColor(){
        return this.color;
    }
    
    public Boolean isAvailable(){
        return this.available;
    }
        
    public void setIconPlayer(ImageIcon iconPlayer){
        this.iconPlayer=iconPlayer;
    }
    
    public void setColor(String color){
        this.color=color;
    }
    
    public void setColor(String color, Boolean available){
        
        color=color.trim().strip();
        this.color=color.substring(0, 1).toUpperCase()+color.substring(1).toLowerCase();
        
        if(this.isAvailable())
            this.setIconPlayer(new ImageIcon("players/"+this.getColor()+"_Player.png"));
        else
            this.setIconPlayer(new ImageIcon("players/"+this.getColor()+"_Player_Unavailable.png"));
    }
    
    public void setAvailable(boolean available){
        
        this.available=available;
        
        this.setColor(this.getColor(), this.isAvailable());
    }
    
    public void resizeIcon(int h, int w){

        this.setIconPlayer(new ImageIcon(this.getIconPlayer().getImage().getScaledInstance(h, w,Image.SCALE_DEFAULT)));
    }
    
}

