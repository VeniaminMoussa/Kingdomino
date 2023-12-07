/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoserver;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author Lenovo
 */
public class SquareArea{
    
    private String name;
    private ImageIcon icon;
    private int crowns;
    private boolean visits;

    public SquareArea(){
        this.name="Empty";
        this.icon=null;
        this.visits=false;
        this.crowns=0;
    }
    public SquareArea(String name){
        this.name=name;
        this.visits=false;
        
        if(this.name.contains("1c")){
            this.crowns=1;
        }
        else if(this.name.contains("2c")){
            this.crowns=2;
        }
        else if(this.name.contains("3c")){
            this.crowns=3;
        }
        else{
            this.crowns=0;
        }
        
        if(name.compareTo("Empty")==0){
            this.setIcon(null);
        }
        if(name.compareTo("Kingdom")==0){
            this.setIcon(null);
        }
        else{
            this.setIcon(new ImageIcon("tiles/"+name+".png"));
            this.resizeIcon(150, 150);
        }
    }
    
    public SquareArea(SquareArea squareArea){
        
        
        this.setName(squareArea.getName());
        this.visits=false;
        
        if(this.name.contains("1c")){
            this.setCrowns(1);
        }
        else if(this.name.contains("2c")){
            this.setCrowns(2);
        }
        else if(this.name.contains("3c")){
            this.setCrowns(3);
        }
        else{
            this.setCrowns(0);
        }
        
        if(name.compareTo("Empty")==0){
            this.setIcon(null);
        }
        else if(name.compareTo("Kingdom")==0){
            this.setIcon(squareArea.getIcon());
            this.resizeIcon(150, 150);
        }
        else{
            this.setIcon(squareArea.getIcon());
            this.resizeIcon(150, 150);
        }
    }
    
    public String getName(){
        return this.name;
    }
    
    public ImageIcon getIcon(){
        return this.icon;
    }
    
    public int getCrowns(){
        return this.crowns;
    }
    
    public boolean getVisits(){
        return this.visits;
    }
    
    public void setSquareArea(SquareArea squareArea){
        this.setName(squareArea.getName());
        this.visits=false;
        
        if(this.name.contains("1c")){
            this.setCrowns(1);
        }
        else if(this.name.contains("2c")){
            this.setCrowns(2);
        }
        else if(this.name.contains("3c")){
            this.setCrowns(3);
        }
        else{
            this.setCrowns(0);
        }
        
        if(name.compareTo("Empty")==0){
            this.setIcon(null);
        }
        else if(name.compareTo("Kingdom")==0){
            this.setIcon(squareArea.getIcon());
            this.resizeIcon(150, 150);
        }
        else{
            this.setIcon(squareArea.getIcon());
            this.resizeIcon(150, 150);
        }
    }
    
    public void setSquareArea(String name, ImageIcon icon){
        this.setName(name);
        this.visits=false;
        
        if(this.name.contains("1c")){
            this.setCrowns(1);
        }
        else if(this.name.contains("2c")){
            this.setCrowns(2);
        }
        else if(this.name.contains("3c")){
            this.setCrowns(3);
        }
        else{
            this.setCrowns(0);
        }
        
        if(name.compareTo("Empty")==0){
            this.setIcon(null);
        }
        else if(name.compareTo("Kingdom")==0){
            this.setIcon(icon);
            this.resizeIcon(150, 150);
        }
        else{
            this.setIcon(icon);
            this.resizeIcon(150, 150);
        }
    }
    
    public void setName(String name){
        this.name=name;
        
        if(name.contains("1c")){
            this.crowns=1;
        }
        else if(name.contains("2c")){
            this.crowns=2;
        }
        else if(name.contains("3c")){
            this.crowns=3;
        }
        else{
            this.crowns=0;
        }
        
        this.setVisits(false);
        
        if(name.compareTo("Empty")==0){
            this.setIcon(null);
        }
        else if(name.compareTo("Kingdom")==0){
            this.setIcon(null);
        }
        else{
            this.setIcon(new ImageIcon("tiles/"+name+".png"));
            this.resizeIcon(150, 150);
        }
    }
    
    public void setIcon(ImageIcon icon){
        this.icon=icon;
    }
    
    public void setCrowns(int crowns){
        this.crowns=crowns;
    }
    
    public void setVisits(boolean visits){
        this.visits=visits;
    }
    
    public void rotateIconRight(){
        int w = this.getIcon().getIconWidth();
        int h = this.getIcon().getIconHeight();
        int type = BufferedImage.TYPE_INT_RGB;  // other options, see api
        BufferedImage image = new BufferedImage(h, w, type);
        Graphics2D g = image.createGraphics();
        double x = (h - w)/2.0;
        double y = (w - h)/2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(Math.toRadians(-90), w/2.0, h/2.0);
        g.drawImage(this.getIcon().getImage(), at, null);
        g.dispose();
        this.setIcon(new ImageIcon(image));
    }
    
    public void rotateIconLeft(){
        int w = this.getIcon().getIconWidth();
        int h = this.getIcon().getIconHeight();
        int type = BufferedImage.TYPE_INT_RGB;  // other options, see api
        BufferedImage image = new BufferedImage(h, w, type);
        Graphics2D g = image.createGraphics();
        double x = (h - w)/2.0;
        double y = (w - h)/2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(Math.toRadians(90), w/2.0, h/2.0);
        g.drawImage(this.getIcon().getImage(), at, null);
        g.dispose();
        this.setIcon(new ImageIcon(image));
    }
    
    public void resizeIcon(int h, int w){

        this.setIcon(new ImageIcon(this.getIcon().getImage().getScaledInstance(h, w,Image.SCALE_DEFAULT)));
    }
}
