/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoserver;

import javax.swing.ImageIcon;

/**
 *
 * @author Lenovo
 */
public class Player {
    
    private volatile Game game;
    private volatile String name;
    private volatile PlayerIcon playerIcon;
    private volatile Kingdom kingdom;
    private volatile int numberOfPlayers;
    private Boolean placeDomino;
    private int score;
    
    
    private PlayerSocket socket;
    
    public Player(){
        this.setName("");
        this.setPlayerIcon(new PlayerIcon());
        this.setKingdom(new Kingdom());
        this.setScore(0);
    }
    
    public Player(PlayerSocket socket){
        this.setName("");
        this.setPlayerIcon(new PlayerIcon());
        this.setSocket(socket);
        this.setKingdom(new Kingdom());
        this.sendStatusOfPlayers();
        this.setScore(0);
    }
    
    public Player(Game game, PlayerSocket socket){ 
        this.setGame(game);
        this.setName(null);
        this.setPlayerIcon(new PlayerIcon());
        this.setSocket(socket);
        this.setKingdom(new Kingdom());
        this.sendStatusOfPlayers();
        this.setNumberOfPlayers(this.getGame().getNumberOfPlayers());
        this.setScore(0);
    }
    
    public Game getGame(){
        return this.game;
    }
    
    public String getName(){
        return this.name;
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
    
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public PlayerSocket getSocket(){
        return this.socket;
    }
    
    public void setGame(Game game){
        this.game=game;
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
    
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
    
    public void setSocket(PlayerSocket socket){
        this.socket=socket;
    }
    
    public boolean sendObjectToPlayer(Object objectSend){
        return this.getSocket().sendObject(objectSend);
    }
    
    public Object receiveObjectFromPlayer(){
        return this.getSocket().receiveObject();
    }
    
        public boolean listenToPlayer(){

        String result = (String)this.receiveObjectFromPlayer();
        
        if(result.compareToIgnoreCase("checkName")==0){
            this.checkName();
            return false;
        }
        else if(result.compareToIgnoreCase("Start")==0){
            if(this.getGame().getNumberOfPlayers()>=2){
                this.getGame().startGame();
                return true;
            }
            else{
                this.sendObjectToPlayer("No");
                return false;
            }
            
        }
        else if(result.compareToIgnoreCase("rowSelected")==0){
            this.upDateSecondPriorityList();
            this.getGame().sendPlayerTurn();
            return false;
        }
        else if(result.compareToIgnoreCase("upDateKingdom")==0){
            this.upDateKingdom();
            this.getGame().getKingdominoServerFrame().upDatePlayersKingdom();
            this.upDateSecondPriorityList();
            this.sendStatusOfPlayers();
            this.getGame().sendPlayerTurn();
            
            return false;
        }
        else if(result.compareToIgnoreCase("CompletedInfo")==0){
            this.setPlayerInfo();
            this.getGame().getServer().activate();
            return true;
        }
        else if(result.compareToIgnoreCase("Pause")==0){
            return true;
        } 
        else if(result.compareToIgnoreCase("Exit")==0){
            if(this.getGame().getGameStarted()){
                this.removePlayerFromGame();
            }
            else{
                this.removePlayerFromLoby();
            }
            return true;
        }
        else{
            return true;
        }
    }
    
    public void sendPlayerSelection(int index, String name){
  
        for(int i=0;i<this.getNumberOfPlayers();i++){
            this.getGame().getPlayerFromThreadList(i).sendObjectToPlayer("PlayerSelected");
            this.getGame().getPlayerFromThreadList(i).sendObjectToPlayer(index);
            this.getGame().getPlayerFromThreadList(i).sendObjectToPlayer(name);
        }
    }
    
    public void checkName(){
        String checkName = (String)this.receiveObjectFromPlayer();
        
        for(int i=0;i<(this.getGame().getPlayersThreadList().size());i++){
            if(this.getGame().getPlayerFromThreadList(i).getName()!=null)
                if(this.getGame().getPlayerFromThreadList(i).getName().compareToIgnoreCase(checkName)==0){
                    this.sendObjectToPlayer(false);
                    return;
                }
        }
        
        this.sendObjectToPlayer(true);
    }
    
    public void upDateSecondPriorityList(){
        if(this.getGame().getSelectDomino()){
            int index = (int)this.receiveObjectFromPlayer();
            this.getGame().getPlayersSecondPriorityList().get(index).setName(this.getName());
            this.getGame().getKingdominoServerFrame().upDateSelectionAtFirstRowFrame(index, this.getName());
            this.sendPlayerSelection(index, this.getName());
        }
        else if(this.getGame().getPlaceDomino()){
            int size;
            
            if(this.getGame().getNumberOfPlayers()==2){
                size = 4;
            }
            else{
                size = this.getGame().getNumberOfPlayers();
            }

            this.getGame().getKingdominoServerFrame().getCurrentDominoLabel().get(size-this.getGame().getPlayersFirstPriorityList().size()).setText("Domino Placed/Discard");
        }
    }
    
    public void sendTurn(){
        this.sendObjectToPlayer("Turn");
        
        if(this.getGame().getPlaceDomino()){
            this.sendObjectToPlayer("Play");
        }
        else if(this.getGame().getSelectDomino()){
            this.sendObjectToPlayer("Pick");
        }
        
        this.sendObjectToPlayer(this.getGame().getPlayersFirstPriorityList().get(0).getName());
    }
    
    public void sendCurrentRow(){
        
        this.sendObjectToPlayer("currentRow");
        
        int size, num;
        String name1, name2;
        
        if(this.getGame().getNumberOfPlayers()==2)
           size=4;
        else
            size=this.getGame().getNumberOfPlayers();
        
        this.sendObjectToPlayer(size);
        
        for(int i=0;i<size;i++){
            
            num = this.getGame().getCurrentDominoRow().get(i).getNum();
            name1 = this.getGame().getCurrentDominoRow().get(i).getName1();
            name2 = this.getGame().getCurrentDominoRow().get(i).getName2();
            this.sendObjectToPlayer(num);
            this.sendObjectToPlayer(name1);
            this.sendObjectToPlayer(name2);
        }
    }
    
    public void sendNextRow(){
        
        this.sendObjectToPlayer("nextRow");
        this.sendObjectToPlayer("Yes");
        
        int size;
        
        if(this.getGame().getNumberOfPlayers()==2)
           size=4;
        else
            size=this.getGame().getNumberOfPlayers();
        
        this.sendObjectToPlayer(size);
        
        for(int i=0;i<size;i++){
            
            this.sendObjectToPlayer(this.getGame().getNextDominoRow().get(i).getNum());
            this.sendObjectToPlayer(this.getGame().getNextDominoRow().get(i).getName1());
            this.sendObjectToPlayer(this.getGame().getNextDominoRow().get(i).getName2());
        }
    }
    
    public void sendStatusOfKingdom(Player player){
        
        for(int i=0;i<player.getKingdom().getRows();i++){
            for(int j=0;j<player.getKingdom().getCols();j++){
                this.sendObjectToPlayer(player.getKingdom().getSquareAreaName(i, j));
            }
        }
    }
    
    public void upDateKingdom(){
        
        String result = ((String)this.receiveObjectFromPlayer());
        
        if(result.compareToIgnoreCase("No")==0){
            return;
        }
        
        String align = ((String)this.receiveObjectFromPlayer());
      
        if(align.compareToIgnoreCase("horizontal")==0){
            int i = (Integer)this.receiveObjectFromPlayer();
            int j = (Integer)this.receiveObjectFromPlayer();
            String name1 = (String)this.receiveObjectFromPlayer();
            ImageIcon icon1 = (ImageIcon)this.receiveObjectFromPlayer();
            String name2 = (String)this.receiveObjectFromPlayer();
            ImageIcon icon2 = (ImageIcon)this.receiveObjectFromPlayer();
            
            this.getKingdom().setSquareArea(i, j, name1, icon1);
            this.getKingdom().setSquareArea(i, j+1, name2, icon2);
        }
        else if(align.compareToIgnoreCase("vertical")==0){
            int i = (Integer)this.receiveObjectFromPlayer();
            int j = (Integer)this.receiveObjectFromPlayer();
            String name1 = (String)this.receiveObjectFromPlayer();
            ImageIcon icon1 = (ImageIcon)this.receiveObjectFromPlayer();
            String name2 = (String)this.receiveObjectFromPlayer();
            ImageIcon icon2 = (ImageIcon)this.receiveObjectFromPlayer();

            this.getKingdom().setSquareArea(i, j, name1, icon1);
            this.getKingdom().setSquareArea(i+1, j, name2, icon2);
        }
        
        this.setScore(this.getKingdom().getKingdomPoints());
    }
        
    public void setPlayerInfo(){
        
        this.setName((String)this.receiveObjectFromPlayer());
        this.setColor((String)this.receiveObjectFromPlayer());
        int row = (int)this.receiveObjectFromPlayer();
        int column = (int)this.receiveObjectFromPlayer();
        this.getKingdom().setSquareArea(row, column, "Kingdom", new ImageIcon("tiles/"+this.getColor()+"_Kingdom.png"));
    }
    
    public void sendStatusOfPlayers(){
        
        Player player;
        
        this.sendObjectToPlayer("upDatePlayers");
        
        this.sendObjectToPlayer(this.getGame().getPlayersThreadList().size());
        
        for(int i=0;i<this.getGame().getPlayersThreadList().size();i++){
            
            player=this.getGame().getPlayerFromThreadList(i);
            
            this.sendObjectToPlayer(player.getName());
            this.sendObjectToPlayer(player.getColor());
            this.sendStatusOfKingdom(player);
        }
        
    }
    
    public void removePlayerFromLoby(){
        this.sendObjectToPlayer("Exit");
        this.sendObjectToPlayer("Yes");
    }
    
    public void removePlayerFromGame(){
        this.sendObjectToPlayer("Exit");
        this.sendObjectToPlayer("No");
    }
    
    public void exit(){
        
        this.getSocket().close();
    }
    
    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if(this==o){
            return true;
        }
        if((o==null)||(getClass()!=o.getClass())){
            return false;
        }
        Player player = (Player)o;
        return ((this.getName().compareTo(player.getName())==0)&&(this.getColor().compareTo(player.getColor())==0));
    }

}
