/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kingdominoserver;

public class PlayerThread extends Thread
{
    private ListenToPlayerThread listenToPlayerThread;
    private volatile Player player;
    
    public PlayerThread(Player player)
    {
        this.setListenToPlayerThread(new ListenToPlayerThread(this));
        this.player = player;
    }
    
    public Player getPlayer(){
        return this.player;
    }
    
    public Game getGame(){
        return this.getPlayer().getGame();
    }

    public ListenToPlayerThread getListenToPlayerThread() {
        return listenToPlayerThread;
    }

    public void setListenToPlayerThread(ListenToPlayerThread listenToPlayerThread) {
        this.listenToPlayerThread = listenToPlayerThread;
    }
    
    public KingdominoServerFrame getKingdominoServerFrame(){
        return this.getGame().getKingdominoServerFrame();
    }
    
    
    @Override
    public void run()
    {
        do{
            if(!listenToPlayerThread.isAlive()){
                if(this.getPlayer()!=null)
                    this.getGame().removePlayerFromGame(this.getPlayer());
                
                this.getKingdominoServerFrame().refreshFrame();
                break;
            }
            
            //send status of other players in lobby
            if((this.getPlayer().getNumberOfPlayers())!=(this.getGame().getNumberOfPlayers())){
                this.getPlayer().setNumberOfPlayers(this.getGame().getNumberOfPlayers());
                this.getPlayer().sendStatusOfPlayers();
            }
                
        }while(true);
    }
    
    
        
    
}
