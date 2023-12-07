 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kingdominoclient;

public class PlayerThread extends Thread
{
    private ListenToServerThread listenToServerThread;
    private Player player;
    
    
    public PlayerThread(Player player)
    {
        this.setListenToServerThread(new ListenToServerThread(this));
        this.player = player;
    }

    public ListenToServerThread getListenToServerThread() {
        return listenToServerThread;
    }
    
    public Player getPlayer(){
        return this.player;
    }
    
    public Game getGame(){
        return this.getPlayer().getGame();
    }

    
    
    public void setListenToServerThread(ListenToServerThread listenToServerThread) {
        this.listenToServerThread = listenToServerThread;
    }
    
    public KingdominoClientFrame getKingdominoClientFrame(){
        return this.getGame().getKingdominoClientFrame();
    }
    
    
    @Override
    public void run()
    {
        while(true){
            //Wait for turn
            while(!this.getPlayer().getTurn()){
                if(!listenToServerThread.isAlive()){
                    if(!this.getPlayer().getSocket().getServerSocket().isClosed()){
                        this.getPlayer().exit();
                        this.getKingdominoClientFrame().newGame();
                        break;
                    }
                }
            }
                
            if(!listenToServerThread.isAlive()){
                if(!this.getPlayer().getSocket().getServerSocket().isClosed()){
                    this.getPlayer().exit();
                    this.getKingdominoClientFrame().newGame();
                    break;
                }
            }
            
            //Play 
        }
    }
        
    public Boolean connect(){
        
        Boolean connected = player.enterGame();
        
        if(connected){

            this.getKingdominoClientFrame().setKingdominoClientInfoDialog(new KingdominoClientInfoDialog(this.getKingdominoClientFrame()));
                                  
            this.getKingdominoClientFrame().getKingdominoClientInfoDialog().setVisible(true);
                    
            this.getKingdominoClientFrame().getEnterMenuItem().setEnabled(false);
            this.getKingdominoClientFrame().getExitMenuItem().setEnabled(true);
            
            this.getKingdominoClientFrame().newPlayerToFrame(player);
            
            this.getKingdominoClientFrame().upDatePlayerToFrame();
            
            this.getKingdominoClientFrame().upDatePlayersKingdom();
                    
            this.getKingdominoClientFrame().getStartButtonsBoxPanel().setVisible(true);
            
            this.getListenToServerThread().start();
            
        }
        return connected;
    }
}
