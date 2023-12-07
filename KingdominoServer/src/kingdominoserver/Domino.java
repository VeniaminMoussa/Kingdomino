/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoserver;

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
        if(this.align.compareToIgnoreCase("vertical")==0){
            this.align="horizontal";
            
            ImageIcon tmp=this.square1.getIcon();
            this.square1.setIcon(square2.getIcon());
            this.square2.setIcon(tmp);
        }
        else if(this.align.compareToIgnoreCase("horizontal")==0){
            this.align="vertical";
        }
        
        this.getSquareArea1().rotateIconLeft();
        this.getSquareArea2().rotateIconLeft();
    }
    
    public void rotateRight(){
        if(this.align.compareToIgnoreCase("vertical")==0){
            this.align="horizontal";
            
            ImageIcon tmp=this.square1.getIcon();
            this.square1.setIcon(square2.getIcon());
            this.square2.setIcon(tmp);
        }
        else if(this.align.compareToIgnoreCase("horizontal")==0){
            this.align="vertical";
        }
        
        this.getSquareArea1().rotateIconRight();
        this.getSquareArea2().rotateIconRight();
    }
    
    public void resizeDominoSquares(int h, int w){
        
        this.getSquareArea1().resizeIcon(h, w);
        this.getSquareArea2().resizeIcon(h, w);
    }
    
    public ImageIcon getDominoIcon(){
        ImageIcon icon1 = this.getIcon1();
        ImageIcon icon2 = this.getIcon2();
        
        BufferedImage image1 = new BufferedImage(icon1.getIconWidth(), icon1.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        icon1.paintIcon(null, image1.getGraphics(), 0, 0);
        BufferedImage image2 = new BufferedImage(icon2.getIconWidth(), icon2.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        icon2.paintIcon(null, image2.getGraphics(), 0, 0);
        final BufferedImage combinedImage = new BufferedImage( 
                icon2.getIconWidth()+icon2.getIconWidth(),
                icon2.getIconHeight(), 
                BufferedImage.TYPE_INT_ARGB );
        Graphics2D g = combinedImage.createGraphics();
        g.drawImage(image1,0,0,null);
        g.drawImage(image2,icon2.getIconWidth(),0,null);
        g.dispose();
        
        return new ImageIcon(combinedImage);
    }

    @Override
    public int compareTo(Domino o) {
        if(this.getNum()<o.getNum())
            return -1;
        else if(this.getNum()>o.getNum())
            return 1;
        else
            return 0;
    }
    
    @Override
    public String toString()
    {
        return this.getNum() + ", " + this.getName1() + ", " + this.getName2();
    }
}
