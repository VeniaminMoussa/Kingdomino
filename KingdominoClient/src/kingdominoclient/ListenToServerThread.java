/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kingdominoclient;
 
public class ListenToServerThread extends Thread
{
    private PlayerThread playerThread;
    private Boolean exit;
    
    public ListenToServerThread(PlayerThread playerThread)
    {
        this.setExit(false);
        this.playerThread = playerThread;
    }
    
    public PlayerThread getPlayerThread(){
        return this.playerThread;
    }
    
    public Player getPlayer(){
        return this.playerThread.getPlayer();
    }
    
    public Game getGame(){
        return this.playerThread.getGame();
    }
    
    public Boolean getExit() {
        return exit;
    }

    public void setExit(Boolean exit){
        this.exit = exit;
    }
    
    @Override
    public void run()
    {
        while(true){
            if(this.getExit()){
                this.getGame().getKingdominoClientFrame().newGame();
                break;
            }
            
            this.getPlayer().listenToServer();
            
        }
    }
}
