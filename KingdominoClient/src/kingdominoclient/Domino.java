/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoclient;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon; 
/**
 *
 * @author Lenovo
 */
public class Domino implements Comparable<Domino>{
    
    private int num;
    private SquareArea square1;
    private SquareArea square2;
    private String align;
    
    public Domino(){
        this.square1 = new SquareArea();
        this.square2 = new SquareArea();
        this.align="horizontal";
    }
    
    public Domino(int num, String name1, String name2){
        this.num=num;
        this.square1 = new SquareArea(name1);
        this.square2 = new SquareArea(name2);
        
        this.align="horizontal";
    }
    
    public void setDomino(Domino domino){
        this.setAlign(domino.getAlign());
        this.setNum(domino.getNum());
        this.setName1(domino.getName1());
        this.setName2(domino.getName2());
        this.setIcon1(domino.getIcon1());
        this.setIcon2(domino.getIcon2());
    }
    
    public Integer getNum(){
        return this.num;
    }
    
    public SquareArea getSquareArea1(){
        return this.square1;
    }
    
    public String getName1(){
        return this.square1.getName();
    }
    
    public ImageIcon getIcon1(){
        return this.square1.getIcon();
    }
    
    public SquareArea getSquareArea2(){
        return this.square2;
    }
    
    public String getName2(){
        return this.square2.getName();
    }
    
    public ImageIcon getIcon2(){
        return this.square2.getIcon();
    }
    
    public String getAlign(){
        return this.align;
    }
    
    public void setNum(int num){
        this.num=num;
    }
    
    public void setName1(String name1){
        this.square1.setName(name1);
    }
    
    public void setIcon1(ImageIcon icon1){
        this.square1.setIcon(icon1);
    }
    
    public void setName2(String name2){
        this.square2.setName(name2);
    }
    
    public void setIcon2(ImageIcon icon2){
        this.square2.setIcon(icon2);
    }
    
    public void setAlign(String align){
        this.align=align;
    }
    
    public void rotateLeft(){
        
        SquareArea tmp = new SquareArea();
        
        if(this.align.compareToIgnoreCase("vertical")==0){
            this.align="horizontal";
        }
        else if(this.align.compareToIgnoreCase("horizontal")==0){
            this.align="vertical";
            
            tmp.setSquareArea(this.getSquareArea1());
            this.getSquareArea1().setSquareArea(this.getSquareArea2());
            this.getSquareArea2().setSquareArea(tmp);
        }
        
        this.getSquareArea1().rotateIconLeft();
        this.getSquareArea2().rotateIconLeft();
    }
    
    public void rotateRight(){
        
        SquareArea tmp = new SquareArea();
        
        if(this.align.compareToIgnoreCase("vertical")==0){
            this.align="horizontal";
            
            tmp.setSquareArea(this.getSquareArea1());
            this.getSquareArea1().setSquareArea(this.getSquareArea2());
            this.getSquareArea2().setSquareArea(tmp);
        }
        else if(this.align.compareToIgnoreCase("horizontal")==0){
            this.align="vertical";
        }
        
        this.getSquareArea1().rotateIconRight();
        this.getSquareArea2().rotateIconRight();
    }
    
    public void resizeDomino(int h, int w){
        
        this.getSquareArea1().resizeIcon(h, w);
        this.getSquareArea2().resizeIcon(h, w);
    }
    
    public ImageIcon getDominoIcon(){
        
        if(this.getAlign().compareToIgnoreCase("horizontal")==0){
        
            ImageIcon icon1 = this.getIcon1();
            ImageIcon icon2 = this.getIcon2();

            BufferedImage image1 = new BufferedImage(icon1.getIconWidth(), icon1.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            icon1.paintIcon(null, image1.getGraphics(), 0, 0);
            BufferedImage image2 = new BufferedImage(icon2.getIconWidth(), icon2.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            icon2.paintIcon(null, image2.getGraphics(), 0, 0);
            final BufferedImage combinedImage = new BufferedImage( 
                    icon1.getIconWidth()+icon2.getIconWidth(),
                    icon1.getIconHeight(), 
                    BufferedImage.TYPE_INT_ARGB );
            Graphics2D g = combinedImage.createGraphics();
            g.drawImage(image1,0,0,null);
            g.drawImage(image2,icon1.getIconWidth(),0,null);
            g.dispose();

            return new ImageIcon(combinedImage);
        }
        else if(this.getAlign().compareToIgnoreCase("vertical")==0){
        
            ImageIcon icon1 = this.getIcon1();
            ImageIcon icon2 = this.getIcon2();

            BufferedImage image1 = new BufferedImage(icon1.getIconWidth(), icon1.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            icon1.paintIcon(null, image1.getGraphics(), 0, 0);
            BufferedImage image2 = new BufferedImage(icon2.getIconWidth(), icon2.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            icon2.paintIcon(null, image2.getGraphics(), 0, 0);
            final BufferedImage combinedImage = new BufferedImage( 
                    icon1.getIconWidth(),
                    icon1.getIconHeight()+icon2.getIconHeight(), 
                    BufferedImage.TYPE_INT_ARGB );
            Graphics2D g = combinedImage.createGraphics();
            g.drawImage(image1,0,0,null);
            g.drawImage(image2,0,icon1.getIconHeight(),null);
            g.dispose();

            return new ImageIcon(combinedImage);
        }
        else
            return null;
    }

    @Override
    public int compareTo(Domino o) {
            return (this.num - o.num);
    }
    
    @Override
    public String toString()
    {
        return this.getNum() + ", " + this.getName1() + ", " + this.getName2();
    }
}
