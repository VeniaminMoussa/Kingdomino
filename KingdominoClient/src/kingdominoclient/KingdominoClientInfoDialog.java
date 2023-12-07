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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author nplatis
 */
public class KingdominoClientInfoDialog extends JDialog
{
    private PlayerIcon playerIcon;
    
    private Boolean validationName=true;
    private Boolean validationIconPlayer=true;
    
    private JPanel iconPlayerPanel = new JPanel(new GridLayout(1,3));
    private JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel namePlayerPanel = new JPanel(new GridLayout(2,1));
    private JPanel nameFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel okPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    
    private JLabel  nameLabel = new JLabel("Name: ");
    private JTextField nameField = new  JTextField(20);
    private JButton okButton = new JButton("OK");
    private JLabel  iconLabel = new JLabel();
    private JButton leftButton = new JButton("Left");
    private JButton rightButton = new JButton("Right");
    
    private Box leftBoxPanel = new Box(BoxLayout.LINE_AXIS);
    private Box rightBoxPanel = new Box(BoxLayout.LINE_AXIS);
    
    private KingdominoClientInfoKingdomDialog kingdominoClientInfoKingdomDialog;
    
    public KingdominoClientInfoDialog(KingdominoClientFrame parent)
    {
        
        super(parent, "Player's Information", true);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setPreferredSize(new Dimension(540, 590));
        this.setMinimumSize(new Dimension(740, 790));
        
        this.playerIcon = ((KingdominoClientFrame)(getParent())).getGame().getPlayersIconList().get(0);
        this.iconLabel.setIcon(this.playerIcon.getIconPlayer());
        this.validationIconPlayer=playerIcon.isAvailable();
        
        this.setLayout(new BorderLayout());
        
        addWindowListener(new WindowAdapter()  {

            @Override
            public void windowClosing(WindowEvent e)
            {
                JOptionPane.showMessageDialog(KingdominoClientInfoDialog.this, "You can not leave the game,\nwhile you register your info!!!","Warning Message",JOptionPane.WARNING_MESSAGE);
            }
            
        });

//The Icon of the Player Field
        this.iconPanel.add(this.iconLabel);
        
        this.leftBoxPanel.add(Box.createHorizontalGlue());
        this.leftBoxPanel.add(this.leftButton);
        this.leftBoxPanel.add(Box.createHorizontalGlue());
        this.rightBoxPanel.add(Box.createHorizontalGlue());
        this.rightBoxPanel.add(this.rightButton);
        this.rightBoxPanel.add(Box.createHorizontalGlue());
        
        this.iconPlayerPanel.add(this.leftBoxPanel);
        this.iconPlayerPanel.add(this.iconPanel);
        this.iconPlayerPanel.add(this.rightBoxPanel);
        
//The Name Field
        this.nameFieldPanel.add(this.nameLabel);
        this.nameFieldPanel.add(this.nameField);
        this.okPanel.add(this.okButton);
        this.namePlayerPanel.add(this.nameFieldPanel);
        this.namePlayerPanel.add(this.okPanel);

        this.add(this.iconPlayerPanel, BorderLayout.CENTER);
        this.add(this.namePlayerPanel, BorderLayout.SOUTH);   
        
        nameField.getDocument().addDocumentListener(new DocumentListener() {

            // Η μέθοδος αυτή καλείται κάθε φορά που προστίθεται κείμενο στην text area
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                validationName=nameCheck(nameField.getText());
            }

            // Η μέθοδος αυτή καλείται κάθε φορά που διαγράφεται κείμενο από την text area
            @Override
            public void removeUpdate(DocumentEvent e)
            {
                validationName=nameCheck(nameField.getText());
            }

            // Η μέθοδος αυτή καλείται κάθε φορά που αλλάζει μορφοποίηση το 
            // κείμενο της text area (κάτι που εδώ δεν συμβαίνει ποτέ, γιαυτό την
            // αφήνουμε κενή)
            @Override
            public void changedUpdate(DocumentEvent e)
            {
                
            }
            public Boolean nameCheck(String name){
                
                if(!((KingdominoClientFrame)(getParent())).getPlayer().checkName(name)){
                    JOptionPane.showMessageDialog(rootPane, "The name "+ name + " is allready taken!!!",
                                                "Information Message",JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                return true;
            }
            
        });
        
        this.okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(validationName&&validationIconPlayer){
                    ((KingdominoClientFrame)(getParent())).getPlayer().setName(nameField.getText());
                    ((KingdominoClientFrame)(getParent())).getPlayer().setColor(playerIcon.getColor());
                    ((KingdominoClientFrame)(getParent())).setTitle(((KingdominoClientFrame)(getParent())).getPlayer().getName()+"'s Kingdom");
                    
                    kingdominoClientInfoKingdomDialog=new KingdominoClientInfoKingdomDialog(((KingdominoClientFrame)(getParent())).getKingdominoClientInfoDialog(),((KingdominoClientFrame)(getParent())).getPlayer());
                    kingdominoClientInfoKingdomDialog.setVisible(true);
                    
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "Invalid Information Datas!!!",
                                            "Information Message",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        this.leftButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int playerIconIndex = ((KingdominoClientFrame)(getParent())).getGame().getPlayersIconList().indexOf(playerIcon);
                int playerIconListSize = ((KingdominoClientFrame)(getParent())).getGame().getPlayersIconList().size();
                
                if(playerIconIndex == 0){
                    playerIcon = ((KingdominoClientFrame)(getParent())).getGame().getPlayersIconList().get(playerIconListSize-1);
                    iconLabel.setIcon(playerIcon.getIconPlayer());
                    validationIconPlayer=playerIcon.isAvailable();
                }
                else{
                    playerIcon = ((KingdominoClientFrame)(getParent())).getGame().getPlayersIconList().get(playerIconIndex-1);
                    iconLabel.setIcon(playerIcon.getIconPlayer());
                    validationIconPlayer=playerIcon.isAvailable();
                }
            }
        });
        
        this.rightButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int playerIconIndex = ((KingdominoClientFrame)(getParent())).getGame().getPlayersIconList().indexOf(playerIcon);
                int playerIconListSize = ((KingdominoClientFrame)(getParent())).getGame().getPlayersIconList().size();
                
                if(playerIconIndex == (playerIconListSize-1)){
                    playerIcon = ((KingdominoClientFrame)(getParent())).getGame().getPlayersIconList().get(0);
                    iconLabel.setIcon(playerIcon.getIconPlayer());
                    validationIconPlayer=playerIcon.isAvailable();
                }
                else{
                    playerIcon = ((KingdominoClientFrame)(getParent())).getGame().getPlayersIconList().get(playerIconIndex+1);
                    iconLabel.setIcon(playerIcon.getIconPlayer());
                    validationIconPlayer=playerIcon.isAvailable();
                }
            }
        });
        
    }

    public KingdominoClientInfoKingdomDialog getKingdominoClientInfoKingdomDialog() {
        return kingdominoClientInfoKingdomDialog;
    }

    public void setKingdominoClientInfoKingdomDialog(KingdominoClientInfoKingdomDialog kingdominoClientInfoKingdomDialog) {
        this.kingdominoClientInfoKingdomDialog = kingdominoClientInfoKingdomDialog;
    }
    
    
}
