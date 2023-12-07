/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoclient;
 
import java.util.List;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class Player{
    
    private Game game;
    private List<Player> playersPriorityList;
    private String name;
    private PlayerIcon playerIcon;
    private Kingdom kingdom;
    private PlayerSocket socket;
    private Boolean turn;
    private Boolean play;
    private int score;
    
    public Player(){
        this.setSocket(null);
        this.setName("");
        this.setPlayerIcon(new PlayerIcon());
        this.setKingdom(new Kingdom());
        this.setTurn(false);
        this.setPlay(false);
        this.setScore(0);
    }
    
    public Player(Game game){
        this.setGame(game);
        this.setSocket(null);
        this.setName("");
        this.setPlayerIcon(new PlayerIcon());
        this.setKingdom(new Kingdom());
        this.setTurn(false);
        this.setPlay(false);
        this.setScore(0);
    }
    
    public Player(String name, String color){
        this.setName(name);
        this.setSocket(null);
        this.setColor(color);
        this.setKingdom(new Kingdom());
        this.setTurn(false);
        this.setPlay(false);
        this.setScore(0);
    }
    
    public Game getGame(){
        return this.game;
    }

    public List<Player> getPlayersPriorityList() {
        return playersPriorityList;
    }
    
    public String getName(){
        return this.name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public PlayerIcon getPlayerIcon(){
        return this.playerIcon;
    }
    
    public String getColor(){
        return this.getPlayerIcon().getColor();
    }
    
    public ImageIcon getIcon(){
        return this.getPlayerIcon().getIconPlayer();
    }
    
    public Kingdom getKingdom(){
        return this.kingdom;
    }

    public Boolean getPlay() {
        return play;
    }

    public void setPlay(Boolean play) {
        this.play = play;
    }
    
    public PlayerSocket getSocket(){
        return this.socket;
    }

    public Boolean getTurn() {
        return turn;
    }
    
    public void setGame(Game game){
        this.game=game;
    }
    
    public void setPlayersPriorityList(List<Player> playersPriorityList){
        this.playersPriorityList = playersPriorityList;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public void setPlayerIcon(PlayerIcon playerIcon){
        this.playerIcon=playerIcon;
    }
    
    public void setColor(String color){
        this.getPlayerIcon().setColor(color);
        this.getPlayerIcon().setAvailable(true);
        
    }
    
    public void setIcon(ImageIcon icon){
        this.getPlayerIcon().setIconPlayer(icon);
    }
    
    public void setKingdom(Kingdom kingdom){
        this.kingdom=kingdom;
    }
    
    public void setSocket(PlayerSocket socket){
        this.socket=socket;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }
    
    public boolean enterGame(){
        
        socket = new PlayerSocket();
        if(socket!=null){
            if(((String)this.receiveObjectFromServer()).compareToIgnoreCase("upDatePlayers")==0){
                this.receiveStatusOfPlayers();
            }
            this.setStatusOfPlayersIconItems();
            return true;
        }
        else
            return false;
    }
    
    public boolean sendObjectToServer(Object objectSend){
        return this.getSocket().sendObject(objectSend);
    }
    
    public Object receiveObjectFromServer(){
        return this.getSocket().receiveObject();
    }
    
    public Boolean listenToServer(){
        
        String result = (String)this.receiveObjectFromServer();
        
        if(result.compareToIgnoreCase("Start")==0){
            this.startGame();
            return true;
        }
        else if(result.compareToIgnoreCase("Turn")==0){
            this.setTurn();
            return true;
        }
        else if(result.compareToIgnoreCase("nextRow")==0){
            if(((String)this.receiveObjectFromServer()).compareToIgnoreCase("Yes")==0){
                this.getGame().getKingdominoClientFrame().upDateCurrentDominoRow();
                this.receiveNextRow(); 
                this.getGame().getKingdominoClientFrame().upDateNextDominoRow();
                return true;
            }
            else{
                if(this.getGame().getLastRow()){
                    this.getGame().getKingdominoClientFrame().upDateCurrentDominoRow();
                    this.getGame().getKingdominoClientFrame().setTurnFrame(null);
                    this.getGame().getKingdominoClientFrame().setCurrentDominoRow();
                    this.getGame().getKingdominoClientFrame().setNextDominoRow();
                    this.getGame().getKingdominoClientFrame().getPickBox().setVisible(false);
                    this.getGame().getKingdominoClientFrame().getStartButtonBoxPanel().setVisible(false);
                    this.getGame().getKingdominoClientFrame().getCurrentDominoBoxPanel().setVisible(false);
                    this.getGame().getKingdominoClientFrame().getNextDominoBoxPanel().setVisible(false);
                    int points = this.getKingdom().getKingdomPoints();
                    System.out.println(points+": by "+this.getName());
                    return false;
                }
                else{
                    this.getGame().getKingdominoClientFrame().upDateCurrentDominoRow();
                    this.getGame().setLastRow(true);
                    this.getGame().getKingdominoClientFrame().upDateNextDominoRow();
                    return false;
                }
            }
        }
        else if(result.compareToIgnoreCase("PlayerSelected")==0){
            this.upDateSelectedDomino();
            
            return false;
        }
        else if(result.compareToIgnoreCase("Exit")==0){
            if(((String)this.receiveObjectFromServer()).compareToIgnoreCase("Yes")==0){
                this.getGame().getPlayerThread().getListenToServerThread().setExit(true);
                return false;
            }
            else{
                this.getGame().getPlayerThread().getListenToServerThread().setExit(false);
                JOptionPane.showMessageDialog(null, "Cannot exit!!","Information Message",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        else if(result.compareToIgnoreCase("Score")==0){
            this.upDateScore();
            return false;
        }
        else if(result.compareToIgnoreCase("pause")==0){
            
            return false;
        }
        else if(result.compareToIgnoreCase("upDatePlayers")==0){
             this.receiveStatusOfPlayers();
             this.getGame().getKingdominoClientFrame().upDatePlayerToFrame();
             this.getGame().getKingdominoClientFrame().upDatePlayersKingdom();
            return false;
        }
        else{
            return false;
        }
    }
    
    public void upDateScore(){
        String name;
        int score, j, i;
        int size = (int)this.receiveObjectFromServer();
        for(i=0;i<size;i++){
            name = (String)this.receiveObjectFromServer();
            score = (int)this.receiveObjectFromServer();
            
            if(this.getName().compareToIgnoreCase(name)==0){
                this.setScore(score);
                continue;
            }
            for(j=0;j<this.getGame().getPlayersList().size();j++){
                if(this.getGame().getPlayerFromList(j).getName().compareToIgnoreCase(name)==0)
                    break;
            }
            
            this.getGame().getPlayerFromList(j).setScore(score);
        }
        
        int winnerScore1 = this.getScore();
        String winner1 = this.getName();
        int crowns1 = this.getKingdom().getCrowns();
        int winnerScore2 = this.getScore();
        String winner2 = this.getName();
        int crowns2 = this.getKingdom().getCrowns();
        
        for(i=0;i<this.getGame().getPlayersList().size();i++){
            if(this.getGame().getPlayerFromList(i).getScore()>winnerScore1){
                winnerScore1 = this.getGame().getPlayerFromList(i).getScore();
                winner1 = this.getGame().getPlayerFromList(i).getName();
                crowns1 = this.getGame().getPlayerFromList(i).getKingdom().getCrowns();
                
            }
            else if(this.getGame().getPlayerFromList(i).getScore()==winnerScore1){
                if(this.getGame().getPlayerFromList(i).getKingdom().getCrowns()>crowns1){
                    winnerScore1 = this.getGame().getPlayerFromList(i).getScore();
                    winner1 = this.getGame().getPlayerFromList(i).getName();
                    crowns1 = this.getGame().getPlayerFromList(i).getKingdom().getCrowns();
                }
                else if(this.getGame().getPlayerFromList(i).getKingdom().getCrowns()==crowns1){
                    winnerScore2 = winnerScore1;
                    winner2 = winner1;
                    crowns2 = crowns1;
                    
                    winnerScore1 = this.getGame().getPlayerFromList(i).getScore();
                    winner1 = this.getGame().getPlayerFromList(i).getName();
                    crowns1 = this.getGame().getPlayerFromList(i).getKingdom().getCrowns();
                }
            }
        }
        
        if((winnerScore2==winnerScore1)&&(crowns1==crowns2)){
            if(winner1.compareToIgnoreCase(winner2)!=0)
                JOptionPane.showMessageDialog(null, "The Winner is "+winner1+"!!! with score: "+winnerScore1+"\nand "+winner2+"!!! with score: "+winnerScore2,"Information Message",JOptionPane.PLAIN_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "The Winner is "+winner1+"!!!\nScore: "+winnerScore1,"Information Message",JOptionPane.PLAIN_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "The Winner is "+winner1+"!!!\nScore: "+winnerScore1,
                                            "Information Message",JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    public void upDateSelectedDomino(){
        
        int index = (int)this.receiveObjectFromServer();
        String name = (String)this.receiveObjectFromServer();
        
        this.getGame().getKingdominoClientFrame().upDateSelectionOfPlayer(index, name);
    }
    
    public void setTurn(){
        
        String result = (String)this.receiveObjectFromServer();
        
        String nameTurn = (String)this.receiveObjectFromServer();
        
        this.getGame().getKingdominoClientFrame().setTurnFrame(nameTurn);
        
        this.game.setColumn(-1);
        this.game.setRow(-1);
        
        if(nameTurn.compareToIgnoreCase(this.getName())==0){
            this.game.getKingdominoClientFrame().getPauseMenuItem().setEnabled(true);
            if(result.compareToIgnoreCase("Play")==0){
                this.game.getKingdominoClientFrame().getDominoSelection().setDomino(this.game.getKingdominoClientFrame().getDominoPicked().get(0));
                this.game.getKingdominoClientFrame().getPickLabel().setIcon(this.game.getKingdominoClientFrame().getDominoSelection().getDominoIcon());
                this.game.getKingdominoClientFrame().getPickLabel().setEnabled(true);
                this.game.getKingdominoClientFrame().getDominoPicked().remove(0);
                this.game.getKingdominoClientFrame().getPlaceDominoButton().setEnabled(true);
            
                this.getGame().getKingdominoClientFrame().getPickLabel().setEnabled(true);
                this.getGame().getKingdominoClientFrame().getDiscardDominoButton().setEnabled(true);
                this.getGame().getKingdominoClientFrame().getPlaceDominoButton().setEnabled(true);
                this.getGame().getKingdominoClientFrame().getSelectButton().setEnabled(false);
            }
            else if(result.compareToIgnoreCase("Pick")==0){
                this.getGame().getKingdominoClientFrame().getPickLabel().setEnabled(false);
                this.getGame().getKingdominoClientFrame().getDiscardDominoButton().setEnabled(false);
                this.getGame().getKingdominoClientFrame().getPlaceDominoButton().setEnabled(false);
                this.getGame().getKingdominoClientFrame().getSelectButton().setEnabled(true);
            }
            this.setTurn(true);
        }
        else{
            this.game.getKingdominoClientFrame().getPauseMenuItem().setEnabled(false);
            if(result.compareToIgnoreCase("Play")==0){
                this.getGame().getKingdominoClientFrame().getPickLabel().setEnabled(false);
                this.getGame().getKingdominoClientFrame().getDiscardDominoButton().setEnabled(false);
                this.getGame().getKingdominoClientFrame().getPlaceDominoButton().setEnabled(false);
                this.getGame().getKingdominoClientFrame().getSelectButton().setEnabled(false);
            }
            else if(result.compareToIgnoreCase("Pick")==0){
                this.getGame().getKingdominoClientFrame().getPickLabel().setEnabled(true);
                this.getGame().getKingdominoClientFrame().getDiscardDominoButton().setEnabled(false);
                this.getGame().getKingdominoClientFrame().getPlaceDominoButton().setEnabled(false);
                this.getGame().getKingdominoClientFrame().getSelectButton().setEnabled(false);
            }
            this.setTurn(false);
        }
        
        
        
    }
    
    public boolean checkName(String name){
   
        this.sendObjectToServer("checkName");
        this.sendObjectToServer(name);
        
        return (Boolean)this.receiveObjectFromServer();
    }
    
    public Boolean checkDominoPlace(int i,int j, Domino domino){
        
        int cols = this.getKingdom().getCols()-1;
        int rows = this.getKingdom().getRows()-1;
        
        if(domino.getAlign().compareTo("horizontal")==0){
            if(cols==j){
                JOptionPane.showMessageDialog(null,"Out of Border!!!\n","~Informative Message~",JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            else{
                //check if it is valid the first square
                if(this.getKingdom().getSquareArea(i, j-1)!=null){
                    if(domino.getName1().contains(this.getKingdom().getSquareAreaKind(i, j-1))){
                        return true;
                    }
                    else if(this.getKingdom().getSquareArea(i, j-1).getName().contains("Kingdom")){
                        return true;
                    }
                }
                if(this.getKingdom().getSquareArea(i-1, j)!=null){
                    if(domino.getName1().contains(this.getKingdom().getSquareAreaKind(i-1, j))){
                        return true;
                    }
                    else if(this.getKingdom().getSquareArea(i-1, j).getName().contains("Kingdom")){
                        return true;
                    }
                }
                if(this.getKingdom().getSquareArea(i+1, j)!=null){
                    if(domino.getName1().contains(this.getKingdom().getSquareAreaKind(i+1, j))){
                        return true;
                    }
                    else if(this.getKingdom().getSquareArea(i+1, j).getName().contains("Kingdom")){
                        return true;
                    }
                }
                //check if it is valid the second square
                if(this.getKingdom().getSquareArea(i, j+2)!=null){
                    if(domino.getName2().contains(this.getKingdom().getSquareAreaKind(i, j+2))){
                        return true;
                    }
                    else if(this.getKingdom().getSquareArea(i, j+2).getName().contains("Kingdom")){
                        return true;
                    }
                }
                if(this.getKingdom().getSquareArea(i-1, j+1)!=null){
                    if(domino.getName2().contains(this.getKingdom().getSquareAreaKind(i-1, j+1))){
                        return true;
                    }
                    else if(this.getKingdom().getSquareArea(i-1, j+1).getName().contains("Kingdom")){
                        return true;
                    }
                }
                if(this.getKingdom().getSquareArea(i+1, j+1)!=null){
                    if(domino.getName2().contains(this.getKingdom().getSquareAreaKind(i+1, j+1))){
                        return true;
                    }
                    else if(this.getKingdom().getSquareArea(i+1, j+1).getName().contains("Kingdom")){
                        return true;
                    }
                }

                JOptionPane.showMessageDialog(null,"No Matched!!!\n","~Informative Message~",JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            
        }
        else if(domino.getAlign().compareTo("vertical")==0){
            if(rows==i){
                JOptionPane.showMessageDialog(null,"Out of Border!!!\n","~Informative Message~",JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            else{
                //check if it is valid the first square
                if(this.getKingdom().getSquareArea(i-1, j)!=null){
                    if(domino.getName1().contains(this.getKingdom().getSquareAreaKind(i-1, j))){
                        return true;
                    }
                    else if(this.getKingdom().getSquareArea(i-1, j).getName().contains("Kingdom")){
                        return true;
                    }
                }
                if(this.getKingdom().getSquareArea(i, j+1)!=null){
                    if(domino.getName1().contains(this.getKingdom().getSquareAreaKind(i, j+1))){
                        return true;
                    }
                    else if(this.getKingdom().getSquareArea(i, j+1).getName().contains("Kingdom")){
                        return true;
                    }
                }
                if(this.getKingdom().getSquareArea(i, j-1)!=null){
                    if(domino.getName1().contains(this.getKingdom().getSquareAreaKind(i, j-1))){
                        return true;
                    }
                    else if(this.getKingdom().getSquareArea(i, j-1).getName().contains("Kingdom")){
                        return true;
                    }
                }
                //check if it is valid the second square
                if(this.getKingdom().getSquareArea(i+2, j)!=null){
                    if(domino.getName2().contains(this.getKingdom().getSquareAreaKind(i+2, j))){
                        return true;
                    }
                    else if(this.getKingdom().getSquareArea(i+2, j).getName().contains("Kingdom")){
                        return true;
                    }
                }
                if(this.getKingdom().getSquareArea(i+1, j-1)!=null){
                    if(domino.getName2().contains(this.getKingdom().getSquareAreaKind(i+1, j-1))){
                        return true;
                    }
                    else if(this.getKingdom().getSquareArea(i+1, j-1).getName().contains("Kingdom")){
                        return true;
                    }
                }
                if(this.getKingdom().getSquareArea(i+1, j+1)!=null){
                    if(domino.getName2().contains(this.getKingdom().getSquareAreaKind(i+1, j+1))){
                        return true;
                    }
                    else if(this.getKingdom().getSquareArea(i+1, j+1).getName().contains("Kingdom")){
                        return true;
                    }
                }

                JOptionPane.showMessageDialog(null,"No Matched!!!\n","~Informative Message~",JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        else
            return false;
    }
    
    public void receiveStatusOfKingdom(Player newPlayer){
        
        int i, j;
        String iconName;
        
        for(i=0;i<this.getKingdom().getRows();i++){
            for(j=0;j<this.getKingdom().getCols();j++){
                iconName = (String)this.receiveObjectFromServer();
                if(iconName!=null){
                    if(iconName.compareToIgnoreCase("Kingdom")==0){
                        newPlayer.getKingdom().setSquareAreaName(i, j, iconName);
                        newPlayer.getKingdom().setSquareAreaIcon(i, j, new ImageIcon("tiles/"+newPlayer.getColor()+"_Kingdom.png"));
                        
                    }
                    else{
                        newPlayer.getKingdom().setSquareAreaName(i, j, iconName);
                    }
                }
            }
        }
    }
    
    public void placeDominoAt(int i,int j, Domino domino){
        
        this.sendObjectToServer("upDateKingdom");
        
        if(domino==null){
            this.sendObjectToServer("No");
            return;
        }
        
        this.sendObjectToServer("Yes");
        
        if(domino.getAlign().compareTo("horizontal")==0){
            this.getKingdom().setSquareArea(i, j, domino.getName1(), domino.getIcon1());
            this.getKingdom().setSquareArea(i, j+1, domino.getName2(), domino.getIcon2());
            
            this.sendObjectToServer("horizontal");
            this.sendObjectToServer(i);
            this.sendObjectToServer(j);
            this.sendObjectToServer(domino.getName1());
            this.sendObjectToServer(domino.getIcon1());
            this.sendObjectToServer(domino.getName2());
            this.sendObjectToServer(domino.getIcon2());
        }
        else if(domino.getAlign().compareTo("vertical")==0){
            this.getKingdom().setSquareArea(i, j, domino.getName1(), domino.getIcon1());
            this.getKingdom().setSquareArea(i+1, j, domino.getName2(), domino.getIcon2());
            
            this.sendObjectToServer("vertical");
            this.sendObjectToServer(i);
            this.sendObjectToServer(j);
            this.sendObjectToServer(domino.getName1());
            this.sendObjectToServer(domino.getIcon1());
            this.sendObjectToServer(domino.getName2());
            this.sendObjectToServer(domino.getIcon2());
        }
        
        this.setScore(this.getKingdom().getKingdomPoints());
    }
    
    public void sendPlayerInfo(){
        
        String kingdom;
        int column=2, row=2;
        
        this.sendObjectToServer("CompletedInfo");
        
        this.sendObjectToServer(this.getName());
        this.sendObjectToServer(this.getColor());
        kingdom=this.getKingdom().findKingdom();
        if(kingdom!=null){
            row=Character.getNumericValue(kingdom.charAt(0));
            column=Character.getNumericValue(kingdom.charAt(2));
        }
        
        this.sendObjectToServer(row);
        this.sendObjectToServer(column);
    }
    
    public void receiveNextRow(){
        
        int num;
        String square1, square2;
        
        this.getGame().getNextDominoRow().clear();
        
        int size = (int)this.receiveObjectFromServer();
        
        for(int i=0;i<size;i++){
            
            num = (int)this.receiveObjectFromServer();
            square1 = (String)this.receiveObjectFromServer();
            square2 = (String)this.receiveObjectFromServer();

            this.getGame().getNextDominoRow().add(new Domino());
            this.getGame().getNextDominoRow().get(this.getGame().getNextDominoRow().size()-1).setNum(num);
            this.getGame().getNextDominoRow().get(this.getGame().getNextDominoRow().size()-1).setName1(square1);
            this.getGame().getNextDominoRow().get(this.getGame().getNextDominoRow().size()-1).setName2(square2);
        }
    }
    
    public void receiveStatusOfPlayers(){
        
        Player player = new Player();
        String playerName;
        String playerColor;
        
        this.getGame().getPlayersList().clear();
        
        int size = (Integer)this.receiveObjectFromServer();
        
        for(int i=0;i<size;i++){
            playerName = (String)this.receiveObjectFromServer();
            playerColor = (String)this.receiveObjectFromServer();
            player.setName(playerName);
            player.setColor(playerColor);
            
            this.receiveStatusOfKingdom(player);
            
            if(player.getName().compareToIgnoreCase(this.getName())==0){
                if(player.getColor().compareToIgnoreCase(this.getColor())==0){
                    continue;
                }
            }

            this.getGame().addPlayersToList();
            
            this.getGame().getPlayerFromList(this.getGame().getPlayersList().size()-1).setColor(playerColor);
            this.getGame().getPlayerFromList(this.getGame().getPlayersList().size()-1).setName(playerName);
            
            for(int j=0;j<player.getKingdom().getRows();j++){
                for(int l=0;l<player.getKingdom().getCols();l++){
                    this.getGame().getPlayerFromList(this.getGame().getPlayersList().size()-1).getKingdom().setSquareAreaName(j, l, player.getKingdom().getSquareAreaName(j,l));
                        this.getGame().getPlayerFromList(this.getGame().getPlayersList().size()-1).getKingdom().setSquareAreaIcon(j, l, player.getKingdom().getSquareAreaIcon(j, l));
                }
            }
            
            this.getGame().getPlayerFromList(this.getGame().getPlayersList().size()-1).setScore(this.getGame().getPlayerFromList(this.getGame().getPlayersList().size()-1).getKingdom().getKingdomPoints());
            this.getGame().getKingdominoClientFrame().upDatePlayersKingdom();
        }
    }
    
    public void setStatusOfPlayersIconItems(){
        
        this.getGame().setPlayersIconList((this.getGame().createPlayersIconList()));
        
        for(int i=0;i<this.getGame().getPlayersList().size();i++)
            this.getGame().removePlayerIconFromList(this.getGame().getPlayerFromList(i).getColor());
    }
    
    public void startGame(){
        
        String start = ((String)this.receiveObjectFromServer());
        
        if(start.compareToIgnoreCase("Yes")==0){

           int allPlayers;
           if((this.getGame().getPlayersList().size()+1)==2){
               allPlayers=4;
           }
           else{
               allPlayers=this.getGame().getPlayersList().size()+1;
           }
           for(int i=0;i<(4-allPlayers);i++){
               this.getGame().getKingdominoClientFrame().getCurrentDominoPanel().remove(1);
               this.getGame().getKingdominoClientFrame().getRadioButtonCurrentDomino().remove(0);
               this.getGame().getKingdominoClientFrame().getCurrentDominoBoxPanel().remove(1);

           }

           for(int i=0;i<(4-allPlayers);i++){
               this.getGame().getKingdominoClientFrame().getNextDominoLabel().remove(1);
               this.getGame().getKingdominoClientFrame().getNextDominoPanel().remove(1);
               this.getGame().getKingdominoClientFrame().getNextDominoBoxPanel().remove(1);
           }
           
           this.getGame().getKingdominoClientFrame().getCenter().getComponent(2).setVisible(false);
           this.getGame().getKingdominoClientFrame().getKingdominoCenter().getComponent(0).setVisible(true);
           this.getGame().getKingdominoClientFrame().getCurrentDominoBoxPanel().setVisible(true);
           this.getGame().getKingdominoClientFrame().getNextDominoBoxPanel().setVisible(true);
           this.getGame().getKingdominoClientFrame().getPickBox().setVisible(true);
           this.getGame().getKingdominoClientFrame().getPlaceDiscardDominoButtonBoxPanel().setVisible(true);
           this.getGame().getKingdominoClientFrame().getNextDominoBoxPanel().setVisible(true);
           this.getGame().getKingdominoClientFrame().getCurrentDominoBoxPanel().setVisible(true);
           
           
           this.receiveObjectFromServer();
           this.receiveObjectFromServer();
           this.receiveNextRow();
           this.getGame().getKingdominoClientFrame().upDateNextDominoRow();
           this.getGame().getKingdominoClientFrame().upDateCurrentDominoRow();
           this.receiveObjectFromServer();
           this.receiveObjectFromServer();
           this.receiveNextRow();
           this.getGame().getKingdominoClientFrame().upDateNextDominoRow();
           this.getGame().setGameStarted(true);
           this.receiveObjectFromServer();
           this.setTurn();
        }
        else if(start.compareToIgnoreCase("No")==0){
            JOptionPane.showMessageDialog(null, "Can not start Game!!!","Information Message",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void sendRowOfSelectedDomino(int row){
        this.sendObjectToServer("rowSelected");
        this.sendObjectToServer(row);
    }
    
    public void sendExit(){
        this.sendObjectToServer("exit");
    }
    
    public void exit(){
        this.getSocket().close();
    }
    
        /**
     *
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return false;
        }
        if((obj==null)){
            return false;
        }
        Player player = (Player)obj;
        return ((this.getName().compareTo(player.getName())==0)&&(this.getColor().compareTo(player.getColor())==0));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.playerIcon);
        return hash;
    }


}
