/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoclient;
 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author nplatis
 */
public class KingdominoClientKingdomDialog extends JDialog
{   
    private Player player;
    private Box kingdomBoxPanel = new Box(BoxLayout.PAGE_AXIS);
    private JPanel kingdomPanel = new JPanel(new GridLayout(5, 5));
    private JScrollPane kingdomScrollPane = new JScrollPane(kingdomPanel);
    
    public KingdominoClientKingdomDialog(KingdominoClientFrame parent, Player player)
    {
        super(parent, true);
        this.player=player;
        this.setTitle(player.getName()+"'s Kingdom");
        this.setSize(1400,800);
        this.setMinimumSize(new Dimension(1200,700));
        
        
        this.setLayout(new BorderLayout());
        
        kingdomPanel.setPreferredSize(new Dimension(700, 700));
        
        kingdomPanel.setMinimumSize(new Dimension(600, 600));
        
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(player.getKingdom().getSquareArea(i, j).getName().compareTo("Empty")==0){
                    kingdomPanel.add(new JButton("["+i+"]["+j+"]"));
                }
                else{
                    player.getKingdom().getSquareArea(i, j).resizeIcon(140,140);
                    kingdomPanel.add(new JLabel(player.getKingdom().getSquareAreaIcon(i, j)));
                }
            }
        }
        
        kingdomScrollPane.setPreferredSize(new Dimension(720, 720));
        
        kingdomScrollPane.setMaximumSize(new Dimension(740,740));
        
        kingdomBoxPanel.add(kingdomScrollPane);

        this.add(kingdomBoxPanel, BorderLayout.CENTER);
    }
    
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public void refreshKingdom(){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(player.getKingdom().getSquareArea(i, j).getName().compareTo("Empty")==0){
                    kingdomPanel.add(new JButton("["+i+"]["+j+"]"));
                }
                else{
                    kingdomPanel.add(new JLabel(player.getKingdom().getSquareAreaIcon(i, j)));
                }
            }
        }
    }
}
