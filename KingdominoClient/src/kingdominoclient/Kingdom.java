/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoclient;
 
import javax.swing.ImageIcon;

/**
 *
 * @author Lenovo
 */
public class Kingdom{
    
    private int rows;
    private int cols;
    private SquareArea[][] kingdom;
    private int points;
    private String property;
    private int propertySize;
    
    public Kingdom(){
        
        this.rows=5;
        this.cols=5;
        
        this.points=0;
        this.propertySize=0;
        
        kingdom = new SquareArea[this.getRows()][this.getCols()];
        
        for(int i=0;i<this.getRows();i++){
            for(int j=0;j<this.getCols();j++){
                kingdom[i][j]= new SquareArea("Empty");
            }
        }
    }
    
    public SquareArea[][] getKingdom(){
        return kingdom;
    }
    
    public int getRows(){
        return this.rows;
    }
    
    public int getCols(){
        return this.cols;
    }
    
    public int getPoints(){
        return this.points;
    }
    
    public String getProperty(){
        return this.property;
    }
    
    public int getPropertySize(){
        return this.propertySize;
    }
    
    public int getCrowns(){
        
        int crowns = 0;
        
        for(int i=0;i<this.getRows();i++){
            for(int j=0;j<this.getCols();j++){
                crowns+=this.getSquareAreaCrowns(i, j);
            }
        }
        return crowns;
    }
    
    public SquareArea getSquareArea(int i, int j){
        
        if((i<0)||(i>=this.getRows())){
            return null;
        }
        else if((j<0)||(j>=this.getCols())){
            return null;
        }
        
        return kingdom[i][j];
    }
    
    public String getSquareAreaKind(int i, int j){
        
        if((i<0)||(i>=this.getRows())){
            return null;
        }
        else if((j<0)||(j>=this.getCols())){
            return null;
        }
        
        
        if(this.getSquareAreaName(i, j).contains("farm"))
            return "farm";
        else if(this.getSquareAreaName(i, j).contains("land"))
            return "land";
        else if(this.getSquareAreaName(i, j).contains("forest"))
            return "forest";
        else if(this.getSquareAreaName(i, j).contains("lake"))
            return "lake";
        else if(this.getSquareAreaName(i, j).contains("rocks"))
            return "rocks";
        else if(this.getSquareAreaName(i, j).contains("field"))
            return "field";
        else if(this.getSquareAreaName(i, j).contains("Kingdom"))
            return "Kingdom";
        else if(this.getSquareAreaName(i, j).contains("Empty"))
            return "Empty";
        else
            return null;
    }
    
    public String getSquareAreaName(int i, int j){
        
        if((i<0)||(i>=this.getRows())){
            return null;
        }
        else if((j<0)||(j>=this.getCols())){
            return null;
        }
        
        return this.getSquareArea(i, j).getName();
    }
    
    public ImageIcon getSquareAreaIcon(int i, int j){
        
        if((i<0)||(i>=this.getRows())){
            return null;
        }
        else if((j<0)||(j>=this.getCols())){
            return null;
        }
        
        return this.getSquareArea(i, j).getIcon();
    }
    
    public int getSquareAreaCrowns(int i, int j){
        
        if((i<0)||(i>=this.getRows())){
            return -1;
        }
        else if((j<0)||(j>=this.getCols())){
            return -1;
        }
        
        return this.getSquareArea(i, j).getCrowns();
    }
    
    public boolean getSquareAreaVisits(int i, int j){
        
        if((i<0)||(i>=this.getRows())){
            return false;
        }
        else if((j<0)||(j>=this.getCols())){
            return false;
        }
        
        return this.getSquareArea(i, j).getVisits();
    }
    
    public void setSquareArea(int i, int j, SquareArea squareArea){
        
        if((i<0)||(i>=this.getRows())){
            return;
        }
        else if((j<0)||(j>=this.getCols())){
            return;
        }
        
        this.getSquareArea(i, j).setSquareArea(squareArea);
    }
    
    public void setSquareArea(int i, int j, String name, ImageIcon icon){
        
        if((i<0)||(i>=this.getRows())){
            return;
        }
        else if((j<0)||(j>=this.getCols())){
            return;
        }
        
        this.getSquareArea(i, j).setSquareArea(name, icon);
    }
    
    public void setHeight(int rows){
        this.rows=rows;
    }
    
    public void setWidth(int cols){
        this.cols=cols;
    }
    
    public void setPoints(int points){
        this.points=points;
    }
    
    public void setPropertySize(String property){
        this.property=property;
    }
    
    public void setPropertySize(int propertySize){
        this.propertySize=propertySize;
    }
    
    public void setSquareAreaName(int i, int j, String name){
        
        if((i<0)||(i>=this.getRows())){
            return;
        }
        else if((j<0)||(j>=this.getCols())){
            return;
        }
        
        this.getSquareArea(i, j).setName(name);
    }
    
    public void setSquareAreaIcon(int i, int j, ImageIcon icon){
        
        if((i<0)||(i>=this.getRows())){
            return;
        }
        else if((j<0)||(j>=this.getCols())){
            return;
        }
        
        this.getSquareArea(i, j).setIcon(icon);
    }
    
    public void setSquareAreacCrowns(int i, int j, int crowns){
        
        if((i<0)||(i>=this.getRows())){
            return;
        }
        else if((j<0)||(j>=this.getCols())){
            return;
        }
        
        this.getSquareArea(i, j).setCrowns(crowns);
    }
    
    public void setSquareAreaVisits(int i, int j, boolean visits){
        
        if((i<0)||(i>=this.getRows())){
            return;
        }
        else if((j<0)||(j>=this.getCols())){
            return;
        }
        
        this.getSquareArea(i, j).setVisits(visits);
    }
    
    public String findKingdom(){
        for(int i=0;i<this.rows;i++){
            for(int j=0;j<this.cols;j++){
                if(this.getSquareArea(i, j).getName().compareToIgnoreCase("Kingdom")==0){
                    return i+" "+j;
                }
            }
        }
        return null;
    }
    
    public int getKingdomPoints(){
        
        int[] points_n_squares = new int[2];
        int points=0;
    
        for(int i=0;i<this.getRows();i++){
            for(int j=0;j<this.getCols();j++){
                this.inspectForProperty(i,j,this.getSquareAreaKind(i, j),points_n_squares);
                points+=(points_n_squares[0]*points_n_squares[1]);
                points_n_squares[0]=0;
                points_n_squares[1]=0;
            }
        }
    
        return points;
    }
    
    public void inspectForProperty(int row, int col, String kind, int[] points_n_squares){
        
        if(this.getSquareAreaVisits(row, col)){
            return;
        }
        else{
            this.setSquareAreaVisits(row, col, true);
        }
        
        points_n_squares[0]+=this.getSquareAreaCrowns(row, col);
        points_n_squares[1]+=1;
        
        if(this.getSquareArea(row+1, col)!=null){
            if(this.getSquareAreaKind(row+1, col).compareToIgnoreCase(kind)==0){
                inspectForProperty(row+1,col, kind, points_n_squares);
            }
        }
        
        if(this.getSquareArea(row-1, col)!=null){
            if(this.getSquareAreaKind(row-1, col).compareToIgnoreCase(kind)==0){
                inspectForProperty(row-1,col, kind, points_n_squares);
            }
        }
        
        if(this.getSquareArea(row, col+1)!=null){
            if(this.getSquareAreaKind(row, col+1).compareToIgnoreCase(kind)==0){
                inspectForProperty(row,col+1, kind, points_n_squares);
            }
        }
        
        if(this.getSquareArea(row, col-1)!=null){
            if(this.getSquareAreaKind(row, col-1).compareToIgnoreCase(kind)==0){
                inspectForProperty(row,col-1, kind, points_n_squares);
            }
        }
    }
}
