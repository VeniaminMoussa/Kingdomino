/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoclient;
  
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;

/**
 *
 * @author nplatis
 */
public class KingdominoClientInfoKingdomDialog extends JDialog
{   
    private int column=-1;
    private int row=-1;
    private int i=-1, j=-1;
    
    private SquareArea icon = new SquareArea("Kingdom");
    private ImageIcon kingdomIcon;
    private ImageIcon emptyIcon = new ImageIcon("tiles/Empty.png");
    
    private Box mainBoxPanel = new Box(BoxLayout.PAGE_AXIS);
    
    private ButtonGroup placeKingdomButtonGroup = new ButtonGroup();
    private Box kingdomBoxPanel = new Box(BoxLayout.PAGE_AXIS);
    private JPanel kingdomPanel = new JPanel(new GridLayout(5, 5));
    private JScrollPane kingdomScrollPane = new JScrollPane(kingdomPanel);
    private RadioButtonListener radioButtonListener = new RadioButtonListener();
    
    private Box textBoxPanel = new Box(BoxLayout.PAGE_AXIS);
    private JLabel textLabel = new JLabel("Place your Kingdom");
    private JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    
    private Box placeKingdomBoxPanel = new Box(BoxLayout.PAGE_AXIS);
    private JButton placeKingdomButton = new JButton("Place");
    private JPanel placeKingdomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    
    public KingdominoClientInfoKingdomDialog(KingdominoClientInfoDialog parent, Player player)
    {
        super(parent, true);
        this.setTitle(player.getName()+"'s Kingdom");
        this.setSize(800,700);
        this.setMinimumSize(new Dimension(800,800));
        
        this.kingdomIcon = new ImageIcon("tiles/"+player.getColor()+"_Kingdom.png");
        
        this.textPanel.add(this.textLabel);
        this.textBoxPanel.add(this.textPanel);
        
        this.placeKingdomButtonPanel.add(this.placeKingdomButton);
        this.placeKingdomBoxPanel.add(this.placeKingdomButtonPanel);
        
        this.placeKingdomButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if((column<0) || (row<0)){
                    JOptionPane.showMessageDialog(rootPane, "Choose a place For your Kingdom!!!",
                                            "Information Message",JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    player.getKingdom().setSquareArea(getRow(), getColumn(),"Kingdom", icon.getIcon());
                    player.getGame().getKingdominoClientFrame().upDateKingdomFrame();
                    player.sendPlayerInfo();
                    dispose();
                }
            }
        });
        
        this.setLayout(new BorderLayout());
        
        this.kingdomPanel.setPreferredSize(new Dimension(600, 600));
        
        this.kingdomPanel.setMinimumSize(new Dimension(500, 500));
        
        for(i=0;i<5;i++){
            for(j=0;j<5;j++){
                this.kingdomPanel.add(new JRadioButton(emptyIcon));
                ((JRadioButton)kingdomPanel.getComponent(this.kingdomPanel.getComponentCount()-1)).setBorderPainted(true);
                ((JRadioButton)kingdomPanel.getComponent(this.kingdomPanel.getComponentCount()-1)).addItemListener(radioButtonListener);
                ((JRadioButton)kingdomPanel.getComponent(this.kingdomPanel.getComponentCount()-1)).setName(i+" "+j);
                this.placeKingdomButtonGroup.add(((JRadioButton)this.kingdomPanel.getComponent(this.kingdomPanel.getComponentCount()-1)));
            }
        }
        
        this.kingdomScrollPane.setPreferredSize(new Dimension(720, 720));
        
        this.kingdomScrollPane.setMaximumSize(new Dimension(740,740));
        
        this.kingdomBoxPanel.add(this.kingdomScrollPane);
        
        this.mainBoxPanel.add(this.textBoxPanel);
        this.mainBoxPanel.add(this.kingdomBoxPanel);
        this.mainBoxPanel.add(this.placeKingdomBoxPanel);

        this.add(mainBoxPanel, BorderLayout.CENTER);
        
        addWindowListener(new WindowAdapter()  {

            @Override
            public void windowClosing(WindowEvent e)
            {
                JOptionPane.showMessageDialog(KingdominoClientInfoKingdomDialog.this, "You can not leave the game,\nwhile you registre your info!!!","Warning Message",JOptionPane.WARNING_MESSAGE);
            }
            
        });
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
    
    private class RadioButtonListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent ie)
        {
            if(ie.getStateChange() == ItemEvent.SELECTED){
                
                icon.setIcon(kingdomIcon);
                icon.resizeIcon(140, 140);
                
                ((JRadioButton)ie.getItem()).setIcon(icon.getIcon());
                
                setRow(Character.getNumericValue(((JRadioButton)ie.getItem()).getName().charAt(0)));
                setColumn(Character.getNumericValue(((JRadioButton)ie.getItem()).getName().charAt(2)));
            }
            else {
                icon.setIcon(kingdomIcon);
                icon.resizeIcon(140, 140);
                ((JRadioButton)ie.getItem()).setIcon(emptyIcon);
            }
        }
    }
    
}
