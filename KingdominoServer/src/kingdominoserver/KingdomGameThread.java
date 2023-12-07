/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kingdominoserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KingdomGameThread extends Thread
{
    private volatile Game game;
    private volatile Boolean turn;
    
    public KingdomGameThread(Game game)
    {
        this.game = game;
        this.turn=false;
    }
    
    
    @Override
    public void run()
    {
        while(true){
            
            while(this.game.getPlayersThreadList().size()<4){
                if(this.game.getServer().getServerSocket().isClosed())
                    this.activate();
                
                this.listenForPlayerInfo(this.game.addPlayerToGame());
            }

            if(!this.game.getServer().getServerSocket().isClosed())
                this.game.getServer().close();

            //actual game
            while(this.game.getGameStarted()){}
            
            if(this.game.getGameStarted()){
                //show results
                this.game.getKingdominoServerFrame().getActivateMenuItem().setEnabled(true);
                this.game.getKingdominoServerFrame().getShutDownMenuItem().setEnabled(false);
                break;
            }
        }
    }
    
    public void activate(){
        this.game.getServer().activate();
        this.game.getKingdominoServerFrame().getActivateMenuItem().setEnabled(false);
        this.game.getKingdominoServerFrame().getShutDownMenuItem().setEnabled(true);
        this.game.setNumberOfPlayers(0);
        this.game.setGameStarted(false);
        this.game.setSelectDomino(true);
        this.game.setPlaceDomino(false);
        this.game.setLastRow(false);
        this.game.setEnd(false);
    }
    
    public void shutDown(){
        this.game.getServer().close();
        this.game.getKingdominoServerFrame().getActivateMenuItem().setEnabled(true);
        this.game.getKingdominoServerFrame().getShutDownMenuItem().setEnabled(false);
    }
    
    public void listenForPlayerInfo(PlayerThread playerThread){
        do{
            if(playerThread.getPlayer().listenToPlayer()){
                this.game.setNumberOfPlayers(this.game.getPlayersThreadList().size());
                this.game.getKingdominoServerFrame().getPlayerNameLabel().get(this.game.getPlayersThreadList().size()-1).setText(this.game.getPlayerFromThreadList(this.game.getPlayersThreadList().size()-1).getName());
                this.game.getKingdominoServerFrame().getPlayerIcon().get(this.game.getPlayersThreadList().size()-1).setColor(this.game.getPlayerFromThreadList(this.game.getPlayersThreadList().size()-1).getColor(), true);
                this.game.getKingdominoServerFrame().getIconLabel().get(this.game.getPlayersThreadList().size()-1).setIcon(this.game.getKingdominoServerFrame().getPlayerIcon().get(this.game.getPlayersThreadList().size()-1).getIconPlayer());
                this.game.getKingdominoServerFrame().getPlayerKingdomButton().get(this.game.getPlayersThreadList().size()-1).addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        game.getKingdominoServerFrame().getKingdominoServerKingdomDialogList().add(new KingdominoServerKingdomDialog(game.getKingdominoServerFrame(),playerThread.getPlayer()));
                        game.getKingdominoServerFrame().getKingdominoServerKingdomDialogList().get(game.getKingdominoServerFrame().getKingdominoServerKingdomDialogList().size()-1).setVisible(true);
                    }
                });
                
                this.game.getPlayerThreadFromList(this.game.getPlayersThreadList().size()-1).start();
                this.game.getPlayerThreadFromList(this.game.getNumberOfPlayers()-1).getListenToPlayerThread().start();
                
                break;
            }
                        
        }while(true);
    }
        
    
}
