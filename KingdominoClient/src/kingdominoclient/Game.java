/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoclient;
 
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class Game{
    
    private KingdominoClientFrame kingdominoClientFrame;
    private PlayerThread playerThread;
    private List<Player> playersList;
    private List<PlayerIcon> playersIconList;
    private List<Domino> currentDominoRow;
    private List<Domino> nextDominoRow;
    private int selectedRow;
    private Boolean gameStarted;
    private Boolean lastRow;
    private int row;
    private int column;
    
    public Game(KingdominoClientFrame kingdominoClientFrame){
        
        this.setKingdominoClientFrame(kingdominoClientFrame);
        this.setPlayerThread(new Player(this));
        this.setPlayersList(this.createPlayersList());
        this.setCurrentDominoRow(this.createCurrentDominoRow());
        this.setNextDominoRow(this.createNextDominoRow());
        this.setGameStarted(false);
        this.setLastRow(false);
    }
    
    public KingdominoClientFrame getKingdominoClientFrame(){
        return this.kingdominoClientFrame;
    }
    
    public PlayerThread getPlayerThread(){
        return this.playerThread;
    }
    
    public Player getPlayer(){
        return this.playerThread.getPlayer();
    }
    
    public List<Player> getPlayersList(){
        return this.playersList;
    }
    
    public List<PlayerIcon> getPlayersIconList(){
        return this.playersIconList;
    }

    public Boolean getLastRow() {
        return lastRow;
    }

    public void setLastRow(Boolean lastRow) {
        this.lastRow = lastRow;
    }
    
    public Player getPlayerFromList(int playerNum){
        return this.getPlayersList().get(playerNum);
    }
    
    public List<Domino> getCurrentDominoRow() {
        return currentDominoRow;
    }

    public List<Domino> getNextDominoRow() {
        return nextDominoRow;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(int selectedRow) {
        this.selectedRow = selectedRow;
    }

    public Boolean getGameStarted() {
        return gameStarted;
    }
    
    public void setKingdominoClientFrame(KingdominoClientFrame kingdominoClientFrame){
        this.kingdominoClientFrame=kingdominoClientFrame;
    }
    
    public void setPlayerThread(Player player){
        this.playerThread = new PlayerThread(player);
    }

    public void setPlayersList(List<Player> playersList){
        this.playersList=playersList;
    }
    
    public void setPlayersIconList(List<PlayerIcon> playersIconList){
        this.playersIconList=playersIconList;
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
    public List<Player> createPlayersList(){
        return new LinkedList<>();
    }
    
    public List<PlayerIcon> createPlayersIconList(){
        return this.fillPlayersIconList(new LinkedList<>());
    }
    
    public List<Domino> createCurrentDominoRow(){
        return new LinkedList<>();
    }
    
    public List<Domino> createNextDominoRow(){
        return new LinkedList<>();
    }
    
    public List<PlayerIcon> fillPlayersIconList(List<PlayerIcon> playersIconList){
        
        playersIconList.add(new PlayerIcon("Blue",true));
        playersIconList.add(new PlayerIcon("Red",true));
        playersIconList.add(new PlayerIcon("Green",true));
        playersIconList.add(new PlayerIcon("Yellow",true));
        playersIconList.add(new PlayerIcon("Brown",true));
        playersIconList.add(new PlayerIcon("Purple",true));
        
        return playersIconList;
    }
    
    public void removePlayerIconFromList(String color){//sets the PlayerIcon Unavailable, doesn't really removes it
        
        for(int i=0;i<this.getPlayersIconList().size();i++){
            if(this.getPlayersIconList().get(i).getColor().compareToIgnoreCase(color)==0){
                this.getPlayersIconList().get(i).setAvailable(false);
                return;
            }
        }
    }
    
    public Player addPlayersToList(){
        Player newPlayer = new Player();
        this.getPlayersList().add(newPlayer);
        return newPlayer;
    }
}
