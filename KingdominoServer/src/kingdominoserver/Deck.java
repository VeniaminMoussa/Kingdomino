/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class Deck{
    
    private Scanner inputStream=null;
    private volatile List<Domino> deckList = new LinkedList<>();
    
    public Deck(){
        try{
            inputStream = new Scanner(new FileInputStream("tiles/dominos.txt")).useDelimiter("\\s*,\\s*");
        }
        catch(FileNotFoundException e){
                            
            JOptionPane.showMessageDialog(null,
            "File dominos.txt was not found\n"
            +"or could not be opened"
            ,"~Fatal Error~",
            JOptionPane.ERROR_MESSAGE);
        }

        try{
            while(inputStream.hasNext()){
                Domino dom=new Domino();
                if(inputStream.hasNextInt()){
                    dom.setNum(inputStream.nextInt());
                }
                
                if(inputStream.hasNext()){
                    dom.setName1(inputStream.next().trim());
                    dom.setName2(inputStream.nextLine().trim().substring(1));
                }
                
                deckList.add(dom);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,
            "Error loading the file dominos.txt", 
            "~Fatal Error~",
            JOptionPane.ERROR_MESSAGE);
                            
            inputStream.close();
            System.exit(0);
        }
                        
        inputStream.close();
    }
    
    public List<Domino> getDeck(){
        return deckList;
    }
    
    public Domino getDomino(int i){
        return deckList.get(i);
    }
    
    public synchronized void shuffleDeck(){
        Collections.shuffle(deckList);
    }
    
    public synchronized void setDeckfor_n_Players(int playersNum){
        switch(playersNum){
            case 2:
                this.removeDominos(24);
                break;
            case 3:
                this.removeDominos(12);
                break;
            case 4:
                break;
            default:
                this.removeDominos(40);
                break;
        }
    }
    
    public synchronized void removeDominos(int dominosRemove){

        for(int i=0;i<dominosRemove;i++){
            this.deckList.remove(0);
        }
    }
    
    public synchronized List<Domino> drawFromDeck(int playersNum){
        
        if(this.deckList.isEmpty()){
            return null;
        }
 
        switch(playersNum){
            case 2:
            case 4:
            {
                List<Domino> drawList = new LinkedList<>();
                
                for(int i=0;i<4;i++){
                    drawList.add(this.deckList.get(0));
                    this.removeDominos(1);
                }
                
                Collections.sort(drawList);
                
                return drawList;
            }
            case 3:
            {
                List<Domino> drawList = new LinkedList<>();
                
                for(int i=0;i<3;i++){
                    drawList.add(this.deckList.get(0));
                    this.removeDominos(1);
                }
                
                Collections.sort(drawList);
                
                return drawList;
            }
            default:
                return null;
        }
    }
    
}
