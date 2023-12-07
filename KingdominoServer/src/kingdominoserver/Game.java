/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoserver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class Game {

    private volatile KingdominoServerFrame kingdominoServerFrame;
    private volatile Server server;
    private List<PlayerThread> playersThreadList;
    private List<Player> playersFirstPriorityList;
    private List<Player> playersSecondPriorityList;
    private volatile Deck deck;
    private volatile List<Domino> currentDominoRow;
    private volatile List<Domino> nextDominoRow;
    private Boolean gameStarted;
    private Boolean placeDomino;
    private Boolean selectDomino;
    private Boolean lastRow;
    private Boolean end;
    private volatile int numberOfPlayers;

    public Game(KingdominoServerFrame kingdominoServerFrame) {
        this.setKingdominoServerFrame(kingdominoServerFrame);
       
        this.setServer(new Server());
        this.setDeck(new Deck());
        this.setPlayersThreadList(this.createPlayersThreadList());
        this.setCurrentDominoRow(this.createCurrentDominoRow());
        this.setNextDominoRow(this.createNextDominoRow());
        this.setNumberOfPlayers(0);
        this.setGameStarted(false);
        this.setSelectDomino(true);
        this.setPlaceDomino(false);
        this.setLastRow(false);
        this.setEnd(false);
    }

    public Boolean getPlaceDomino() {
        return placeDomino;
    }

    public void setPlaceDomino(Boolean placeDomino) {
        this.placeDomino = placeDomino;
    }

    public Boolean getSelectDomino() {
        return selectDomino;
    }

    public void setSelectDomino(Boolean selectDomino) {
        this.selectDomino = selectDomino;
    }

    public Boolean getLastRow() {
        return lastRow;
    }

    public void setLastRow(Boolean lastRow) {
        this.lastRow = lastRow;
    }

    public KingdominoServerFrame getKingdominoServerFrame() {
        return this.kingdominoServerFrame;
    }

    public Server getServer() {
        return this.server;
    }

    public Boolean getEnd() {
        return end;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }

    public List<PlayerThread> getPlayersThreadList() {
        return this.playersThreadList;
    }

    public PlayerThread getPlayerThreadFromList(int playerNum) {
        return this.getPlayersThreadList().get(playerNum);
    }

    public Player getPlayerFromThreadList(int playerNum) {
        return this.getPlayersThreadList().get(playerNum).getPlayer();
    }

    public List<Player> getPlayersFirstPriorityList() {
        return playersFirstPriorityList;
    }

    public List<Player> getPlayersSecondPriorityList() {
        return playersSecondPriorityList;
    }
    
    public Player getPlayerFromFirstPriorityList(int playerNum){
        return this.getPlayersFirstPriorityList().get(playerNum);
    }
    
    public Player getPlayerFromSecondPriorityList(int playerNum){
        return this.getPlayersSecondPriorityList().get(playerNum);
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Domino> getCurrentDominoRow() {
        return currentDominoRow;
    }

    public List<Domino> getNextDominoRow() {
        return nextDominoRow;
    }

    public Boolean getGameStarted() {
        return gameStarted;
    }

    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    public void setKingdominoServerFrame(KingdominoServerFrame kingdominoServerFrame) {
        this.kingdominoServerFrame = kingdominoServerFrame;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setPlayersThreadList(List<PlayerThread> playersThreadList) {
        this.playersThreadList = playersThreadList;
    }
    
    public void setPlayersFirstPriorityList(List<Player> playersFirstPriorityList) {
        this.playersFirstPriorityList = playersFirstPriorityList;
    }

    public void setPlayersSecondPriorityList(List<Player> playersSecondPriorityList) {
        this.playersSecondPriorityList = playersSecondPriorityList;
    }
    
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setCurrentDominoRow(List<Domino> currentDominoRow) {
        this.currentDominoRow = currentDominoRow;
    }

    public void setNextDominoRow(List<Domino> nextDominoRow) {
        this.nextDominoRow = nextDominoRow;
    }

    public void setGameStarted(Boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
    
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public List<PlayerThread> createPlayersThreadList() {
        return this.playersThreadList = new LinkedList<>();
    }
    
    public void createPlayersFirstPriorityList() {
        
        this.setPlayersFirstPriorityList(new LinkedList<>());
    }
    
    public void createPlayersSecondPriorityList() {
        
        this.setPlayersSecondPriorityList(new LinkedList<>());
    }
    
    public List<Domino> createCurrentDominoRow(){
        return new LinkedList<>();
    }
    
    public List<Domino> createNextDominoRow(){
        return new LinkedList<>();
    }

    public PlayerThread addPlayerToGame() {
        if (this.getPlayersThreadList().size() < 4) {
            Player player = new Player(this, this.getServer().addPlayerSocket());
            this.getPlayersThreadList().add(new PlayerThread(player));
            return new PlayerThread(player);
        } else {
            return null;
        }
    }

    public void removePlayerFromGame(Player player){

        int i;

        for (i = 0; i < this.getPlayersThreadList().size(); i++){
            if (this.getPlayerFromThreadList(i).getColor().compareToIgnoreCase(player.getColor()) == 0) {
                if (this.getPlayerFromThreadList(i).getName().compareToIgnoreCase(player.getName()) == 0) {
                    break;
                }
            }
        }

        if (i == this.getPlayersThreadList().size()) {
            return;
        }
        
        this.getPlayerFromThreadList(i).exit();
        this.getPlayersThreadList().remove(i);
        
        this.setNumberOfPlayers(this.getPlayersThreadList().size());
    }
    
    public void startGame(){
        
        int size;
        
        if(this.getNumberOfPlayers()==2)
            size=4;
        else
            size=this.getNumberOfPlayers();
        
        this.setGameStarted(true);
        this.getServer().close();
        
        this.createPlayersFirstPriorityList();
        for(int i=0;i<this.getPlayersThreadList().size();i++){
            if(this.getPlayersThreadList().size()==2){
                this.getPlayersFirstPriorityList().add(this.getPlayerFromThreadList(0));
                this.getPlayersFirstPriorityList().add(this.getPlayerFromThreadList(1));
            }
            else{
                this.getPlayersFirstPriorityList().add(this.getPlayerFromThreadList(i));
            }
        }
        this.createPlayersSecondPriorityList();
        
        for(int i=0;i<size;i++){ 
            if(this.getPlayersThreadList().size()==2){
                this.getPlayersSecondPriorityList().add(new Player());
                this.getPlayersSecondPriorityList().add(new Player());
            }
            else{
                this.getPlayersSecondPriorityList().add(new Player());
            }
            
        }
        

        this.shufflePlayersPriorityList(this.getPlayersFirstPriorityList());
        
        
        this.getDeck().shuffleDeck();
        this.getDeck().setDeckfor_n_Players(this.getNumberOfPlayers());
        
        for(int i=0;i<(4-size);i++){
            this.getKingdominoServerFrame().getCurrentDominoBoxPanel().remove(1);
            this.getKingdominoServerFrame().getCurrentDominoPanel().remove(1);
            this.getKingdominoServerFrame().getCurrentDominoLabel().remove(1);
        }

        for(int i=0;i<(4-size);i++){   
            this.getKingdominoServerFrame().getNextDominoBoxPanel().remove(1);
            this.getKingdominoServerFrame().getNextDominoPanel().remove(1);
            this.getKingdominoServerFrame().getNextDominoLabel().remove(1);
        }

        this.getKingdominoServerFrame().upDateNextDominoRow();
        
        this.setGameStarted(true);
        
        for(int i=0;i<this.getPlayersThreadList().size();i++){
            this.getPlayerFromThreadList(i).sendObjectToPlayer("start");
            this.getPlayerFromThreadList(i).sendObjectToPlayer("Yes");
            this.getPlayerFromThreadList(i).sendNextRow();
        }
        
        this.getKingdominoServerFrame().upDateCurrentDominoRow();
        this.getKingdominoServerFrame().upDateNextDominoRow();
        
        for(int i=0;i<this.getPlayersThreadList().size();i++){
            this.getPlayerFromThreadList(i).sendNextRow();
        }
        
        this.sendPlayerTurn();
        
        this.getKingdominoServerFrame().getCurrentDominoBoxPanel().setVisible(true);
        this.getKingdominoServerFrame().getNextDominoBoxPanel().setVisible(true);
    }

    public void shufflePlayersPriorityList(List<Player> playersPriorityList) {
        Collections.shuffle(playersPriorityList);
    }
    
    public void sendPlayerTurn(){
        
        if(this.getPlayersFirstPriorityList().isEmpty()){
            if(this.getSelectDomino()){
                this.setPlaceDomino(true);
                this.setSelectDomino(false);
            }
            else if(this.getPlaceDomino()){
                this.setPlaceDomino(false);
                this.setSelectDomino(true);
                
                
                this.getKingdominoServerFrame().upDateCurrentDominoRow();
                this.getKingdominoServerFrame().upDateNextDominoRow();
                
                if(this.getLastRow()){
                    for(int i=0;i<this.getPlayersThreadList().size();i++){
                        this.getPlayerFromThreadList(i).sendObjectToPlayer("nextRow");
                        this.getPlayerFromThreadList(i).sendObjectToPlayer("No");
                    }
                    if(this.getEnd()){
                        this.getKingdominoServerFrame().setTurnFrame(null);
                        for(int i=0;i<this.getPlayersThreadList().size();i++){
                            this.getPlayerFromThreadList(i).sendObjectToPlayer("Score");
                            this.getPlayerFromThreadList(i).sendObjectToPlayer(this.getPlayersThreadList().size());
                            for(int j=0;j<this.getPlayersThreadList().size();j++){
                                this.getPlayerFromThreadList(i).sendObjectToPlayer(this.getPlayerFromThreadList(i).getName());
                                int points = this.getPlayerFromThreadList(i).getKingdom().getKingdomPoints();
                                this.getPlayerFromThreadList(i).sendObjectToPlayer(points);
                            }
                        }
                        
                        
                        
                        
                        
                        //end game
                        return;
                    }
                }
                else if(!this.getLastRow()){
                    for(int i=0;i<this.getPlayersThreadList().size();i++)
                        this.getPlayerFromThreadList(i).sendNextRow(); 
                }
            }
            
            int size;
            
            if(this.getNumberOfPlayers()==2)
                size=4;
            else
                size=this.getNumberOfPlayers();
            
            for(int i=0;i<size;i++){
                this.getPlayersFirstPriorityList().add(new Player());
            }
            for(int i=0;i<size;i++){
                this.getPlayersFirstPriorityList().get(i).setName(this.getPlayersSecondPriorityList().get(i).getName());
            }
        }
        
        for(int i=0;i<this.getNumberOfPlayers();i++){
            this.getPlayerFromThreadList(i).sendTurn();
        }
        
        this.getKingdominoServerFrame().setTurnFrame(this.getPlayersFirstPriorityList().get(0).getName());
        
        this.getPlayersFirstPriorityList().remove(0);
    }

    public synchronized void endGame(){
        this.setGameStarted(false);
    }
}
