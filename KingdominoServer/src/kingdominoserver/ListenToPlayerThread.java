/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kingdominoserver;

public class ListenToPlayerThread extends Thread
{
    private PlayerThread playerThread;
    private Boolean exit;
    
    public ListenToPlayerThread(PlayerThread playerThread)
    {
        this.setExit(false);
        this.playerThread = playerThread;
    }
    
    public PlayerThread getPlayerThread(){
        return this.playerThread;
    }
    
    public Player getPlayer(){
        return this.getPlayerThread().getPlayer();
    }

    public Boolean getExit() {
        return exit;
    }

    public void setExit(Boolean exit) {
        this.exit = exit;
    }
    
    @Override
    public void run()
    {
        while(!this.getExit()){
            
            this.getPlayer().listenToPlayer();
        }
    }
    
}
