/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Lenovo
 */
public class KingdominoServerFrame extends JFrame{
    
    private volatile KingdomGameThread kingdomGameThread;
    
    private volatile Game game;
    private volatile Deck deck;
    private volatile JPanel center;
    private volatile Box kingdominoCenter;
    
    private ClickListener clickListener = new ClickListener();
    private JMenu serverMenu;
    private JMenuItem activateMenuItem;
    private JMenuItem shutDownMenuItem;
    private JMenu helpMenu;
    private JMenuItem helpMenuItem;
    private JMenuItem aboutMenuItem;
    
    private List<PlayerIcon> playerIcon = new LinkedList<>();
    private List<Box> playersNumBoxPanel = new LinkedList<>();
    private List<JPanel> iconPanel = new LinkedList<>();
    private List<JPanel> playerNamePanel = new LinkedList<>();
    private List<JPanel> playerKingdomButtonPanel = new LinkedList<>();
    private List<JLabel> iconLabel = new LinkedList<>();
    private List<JLabel> playerNameLabel = new LinkedList<>();
    private List<JButton> playerKingdomButton = new LinkedList<>();
    private Box playersBoxPanel = new Box(BoxLayout.LINE_AXIS);
    
    private List<JLabel> currentDominoLabel = new LinkedList<>();
    private List<JLabel> nextDominoLabel = new LinkedList<>();
    private List<JPanel> currentDominoPanel = new LinkedList<>();
    private List<JPanel> nextDominoPanel = new LinkedList<>();
    private Box currentDominoBoxPanel = new Box(BoxLayout.PAGE_AXIS);
    private Box nextDominoBoxPanel = new Box(BoxLayout.PAGE_AXIS);
    
    
    private List<KingdominoServerKingdomDialog> kingdominoServerKingdomDialogList = new LinkedList<>();

    public KingdominoServerFrame(){
        
        this.setGame(new Game(this));
        this.setDeck(new Deck());
//        this.getDeck().shuffleDeck();
//        this.getDeck().setDeckfor_n_Players(0);

        this.setSize(1400, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Kingdom");
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.kingdominoCenter = new Box(BoxLayout.LINE_AXIS);
        this.add(kingdominoCenter,BorderLayout.CENTER);
        
        
        
        this.setMenuItemsFrame();
        
        //Τα υπόλοιπα στοιχεία διεπαφής
        this.setNewGameFrame();
        
        this.setCurrentDominoRow();
        
        this.setNextDominoRow();
        
        
        
        
    }

    public KingdomGameThread getKingdomGameThread() {
        return kingdomGameThread;
    }
    
    public Game getGame(){
        return this.game;
    }
    
    public Deck getDeck(){
        return this.deck;
    }

    public JMenuItem getActivateMenuItem() {
        return activateMenuItem;
    }

    public JMenuItem getShutDownMenuItem() {
        return shutDownMenuItem;
    }

    public List<PlayerIcon> getPlayerIcon() {
        return playerIcon;
    }

    public List<JLabel> getPlayerNameLabel() {
        return playerNameLabel;
    }
    
    public List<JLabel> getIconLabel() {
        return iconLabel;
    }

    public List<JButton> getPlayerKingdomButton() {
        return playerKingdomButton;
    }

    public List<KingdominoServerKingdomDialog> getKingdominoServerKingdomDialogList() {
        return kingdominoServerKingdomDialogList;
    }

    public void setKingdominoServerKingdomDialogList(List<KingdominoServerKingdomDialog> kingdominoServerKingdomDialogList) {
        this.kingdominoServerKingdomDialogList = kingdominoServerKingdomDialogList;
    }
    
    public void setGame(Game game){
        this.game=game;
    }
    
    public void setDeck(Deck deck){
        this.deck=deck;
    }

    public List<JLabel> getCurrentDominoLabel() {
        return currentDominoLabel;
    }

    public void setCurrentDominoLabel(List<JLabel> currentDominoLabel) {
        this.currentDominoLabel = currentDominoLabel;
    }

    public List<JLabel> getNextDominoLabel() {
        return nextDominoLabel;
    }

    public void setNextDominoLabel(List<JLabel> nextDominoLabel) {
        this.nextDominoLabel = nextDominoLabel;
    }

    public List<JPanel> getCurrentDominoPanel() {
        return currentDominoPanel;
    }

    public void setCurrentDominoPanel(List<JPanel> currentDominoPanel) {
        this.currentDominoPanel = currentDominoPanel;
    }

    public List<JPanel> getNextDominoPanel() {
        return nextDominoPanel;
    }

    public void setNextDominoPanel(List<JPanel> nextDominoPanel) {
        this.nextDominoPanel = nextDominoPanel;
    }

    public Box getCurrentDominoBoxPanel() {
        return currentDominoBoxPanel;
    }

    public void setCurrentDominoBoxPanel(Box currentDominoBoxPanel) {
        this.currentDominoBoxPanel = currentDominoBoxPanel;
    }

    public Box getNextDominoBoxPanel() {
        return nextDominoBoxPanel;
    }

    public void setNextDominoBoxPanel(Box nextDominoBoxPanel) {
        this.nextDominoBoxPanel = nextDominoBoxPanel;
    }
    
    public void setMenuItemsFrame(){
        
        activateMenuItem = new JMenuItem("Activate");
        activateMenuItem.addActionListener(clickListener);
        
        shutDownMenuItem = new JMenuItem("Shut Down");
        shutDownMenuItem.setEnabled(false);
        shutDownMenuItem.addActionListener(clickListener);

        serverMenu = new JMenu("Server");
        serverMenu.add(activateMenuItem);
        serverMenu.add(shutDownMenuItem);
        
        helpMenuItem = new JMenuItem("Help");
        helpMenuItem.addActionListener(clickListener);
        
        aboutMenuItem = new JMenuItem("About Kingdomino");
        aboutMenuItem.addActionListener(clickListener);
      
        helpMenu = new JMenu("Help");
        helpMenu.add(helpMenuItem);
        helpMenu.add(aboutMenuItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(serverMenu);
        menuBar.add(helpMenu);
        
        this.setJMenuBar(menuBar);
    }
    
    public void setNewGameFrame(){
        
        this.playerIcon.clear();
        this.iconLabel.clear();
        this.playerNameLabel.clear();
        this.playerKingdomButton.clear();
        this.iconPanel.clear();
        this.playerNamePanel.clear();
        this.playerKingdomButtonPanel.clear();
        this.playersNumBoxPanel.clear();
        
        
        this.playerIcon.add(new PlayerIcon("Default",true));
        this.playerIcon.add(new PlayerIcon("Default",true));
        this.playerIcon.add(new PlayerIcon("Default",true));
        this.playerIcon.add(new PlayerIcon("Default",true));
        
        this.playersNumBoxPanel.add(new Box(BoxLayout.PAGE_AXIS));
        this.playersNumBoxPanel.add(new Box(BoxLayout.PAGE_AXIS));
        this.playersNumBoxPanel.add(new Box(BoxLayout.PAGE_AXIS));
        this.playersNumBoxPanel.add(new Box(BoxLayout.PAGE_AXIS));
        
        this.iconPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.iconPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.iconPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.iconPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        
        this.playerNamePanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.playerNamePanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.playerNamePanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.playerNamePanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        
        this.playerKingdomButtonPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.playerKingdomButtonPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.playerKingdomButtonPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.playerKingdomButtonPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        
        this.playerNameLabel.add(new JLabel("Player 1"));
        this.playerNameLabel.add(new JLabel("Player 2"));
        this.playerNameLabel.add(new JLabel("Player 3"));
        this.playerNameLabel.add(new JLabel("Player 4"));
        
        this.playerKingdomButton.add(new JButton("Kingdom"));
        this.playerKingdomButton.add(new JButton("Kingdom"));
        this.playerKingdomButton.add(new JButton("Kingdom"));
        this.playerKingdomButton.add(new JButton("Kingdom"));
        
        for(int i=0;i<4;i++){
            this.iconLabel.add(new JLabel(this.playerIcon.get(i).getIconPlayer()));
            this.iconPanel.get(i).add(this.iconLabel.get(i));
            this.playersNumBoxPanel.get(i).add(this.iconPanel.get(i));
            this.playerNamePanel.get(i).add(this.playerNameLabel.get(i));
            this.playersNumBoxPanel.get(i).add(this.playerNamePanel.get(i));
            this.playerKingdomButtonPanel.get(i).add(this.playerKingdomButton.get(i));
            this.playersNumBoxPanel.get(i).add(this.playerKingdomButtonPanel.get(i));
        }
        
        for(int i=0;i<4;i++){
            this.playersBoxPanel.add(this.playersNumBoxPanel.get(i));
        }
        
        this.kingdominoCenter.add(playersBoxPanel,BorderLayout.CENTER);
    }
    
    public void setCurrentDominoRow(){
        
        this.currentDominoPanel.clear();
        this.currentDominoBoxPanel.removeAll();
        
        this.currentDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.currentDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.currentDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.currentDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.currentDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        
        this.currentDominoLabel.add(new JLabel("Current Dominos"));
        this.currentDominoLabel.add(new JLabel("Unselected"));
        this.currentDominoLabel.add(new JLabel("Unselected"));
        this.currentDominoLabel.add(new JLabel("Unselected"));
        this.currentDominoLabel.add(new JLabel("Unselected"));
        
        for(int i=0;i<this.currentDominoPanel.size();i++){
            this.currentDominoPanel.get(i).add(this.currentDominoLabel.get(i));
            this.currentDominoBoxPanel.add(this.currentDominoPanel.get(i));
        }
        
        this.currentDominoBoxPanel.setVisible(false);
        
        this.kingdominoCenter.add(this.currentDominoBoxPanel);
    }
    
    public void setNextDominoRow(){
        
        this.nextDominoPanel.clear();
        this.nextDominoBoxPanel.removeAll();
        
        this.nextDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.nextDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.nextDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.nextDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.nextDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        
        this.nextDominoLabel.add(new JLabel("Next Dominos"));
        this.nextDominoLabel.add(new JLabel("Unselected"));
        this.nextDominoLabel.add(new JLabel("Unselected"));
        this.nextDominoLabel.add(new JLabel("Unselected"));
        this.nextDominoLabel.add(new JLabel("Unselected"));
        
        for(int i=0;i<this.nextDominoPanel.size();i++){
            this.nextDominoPanel.get(i).add(this.nextDominoLabel.get(i));
            this.nextDominoBoxPanel.add(this.nextDominoPanel.get(i));
        }
        
        this.nextDominoBoxPanel.setVisible(false);
        
        this.kingdominoCenter.add(this.nextDominoBoxPanel);
    }
    
    public void upDateCurrentDominoRow(){
        
        if(this.getGame().getLastRow()){
            
            this.getGame().setEnd(true);
            
            this.getGame().getCurrentDominoRow().clear();

            this.currentDominoBoxPanel.setVisible(false);

            if(this.getGame().getLastRow()){
                this.currentDominoBoxPanel.setVisible(false);
                this.nextDominoBoxPanel.setVisible(false);
            }

            for(int i=1;i<this.currentDominoPanel.size();i++){
                this.currentDominoPanel.get(i).removeAll();
                this.currentDominoLabel.get(i).setIcon(null);
                this.currentDominoLabel.get(i).setHorizontalTextPosition(SwingConstants.LEFT);
                this.currentDominoLabel.get(i).setText("Empty");
                this.currentDominoPanel.get(i).add(this.currentDominoLabel.get(i));
            }
        }
        else{
            this.getGame().getCurrentDominoRow().clear();
        
            this.getGame().setCurrentDominoRow(this.getGame().getNextDominoRow());

            this.currentDominoBoxPanel.setVisible(false);

            if(this.getGame().getLastRow()){
                this.currentDominoBoxPanel.setVisible(false);
                this.nextDominoBoxPanel.setVisible(false);
            }

            for(int i=1;i<this.getGame().getCurrentDominoRow().size()+1;i++){
                this.currentDominoPanel.get(i).removeAll();
                this.currentDominoLabel.get(i).setIcon(this.getGame().getCurrentDominoRow().get(i-1).getDominoIcon());
                this.currentDominoLabel.get(i).setHorizontalTextPosition(SwingConstants.LEFT);
                this.currentDominoLabel.get(i).setText("Unselected");
                this.currentDominoPanel.get(i).add(this.currentDominoLabel.get(i));
            }

            this.currentDominoBoxPanel.setVisible(true);
        }
        
        
    }
    
    public void upDateNextDominoRow(){
        
        this.getGame().getNextDominoRow().clear();
        
        int size;
        List<Domino> nextDominos;
        
        if(this.getGame().getNumberOfPlayers()==2)
           size=4;
        else
           size=this.getGame().getNumberOfPlayers();
        
        nextDominos = game.getDeck().drawFromDeck(size);
        
        if(nextDominos!=null){
            
            this.getGame().setNextDominoRow(nextDominos);
            
            this.nextDominoBoxPanel.setVisible(false);
            for(int i=1;i<this.getGame().getNextDominoRow().size()+1;i++){
                this.nextDominoPanel.get(i).removeAll();
                this.getGame().getNextDominoRow().get(i-1).resizeDominoSquares(70, 70);
                this.nextDominoLabel.get(i).setIcon(this.getGame().getNextDominoRow().get(i-1).getDominoIcon());
                this.nextDominoLabel.get(i).setText("Next Domino");
                this.nextDominoPanel.get(i).add(this.nextDominoLabel.get(i));
            }
            this.nextDominoBoxPanel.setVisible(true);
        }
        else{
            this.game.setLastRow(true);
             for(int i=1;i<size+1;i++){
                this.nextDominoPanel.get(i).removeAll();
                this.nextDominoLabel.get(i).setIcon(null);
                this.nextDominoLabel.get(i).setText("Empty");
                this.nextDominoPanel.get(i).add(this.nextDominoLabel.get(i));
            }
        }
    }
    
    public void setTurnFrame(String nameTurn){
        
        if(nameTurn==null){
            this.playersBoxPanel.setVisible(false);
            for(int i=0;i<this.playersNumBoxPanel.size();i++){
                for(int j=0;j<((Box)this.playersNumBoxPanel.get(i)).getComponents().length;j++){
                    ((JPanel)((Box)this.playersNumBoxPanel.get(i)).getComponent(j)).setOpaque(false);  
                }
            }
            this.playersBoxPanel.setVisible(true);
        }
        else if(nameTurn!=null){
            this.playersBoxPanel.setVisible(false);
            for(int i=0;i<this.playersNumBoxPanel.size();i++){
                if(this.playerNameLabel.get(i).getText().compareToIgnoreCase(nameTurn)==0){
                    for(int j=0;j<((Box)this.playersNumBoxPanel.get(i)).getComponents().length;j++){
                        ((JPanel)((Box)this.playersNumBoxPanel.get(i)).getComponent(j)).setOpaque(true);
                        ((JPanel)((Box)this.playersNumBoxPanel.get(i)).getComponent(j)).setBackground(Color.green);
                    }
                }
                else{
                    for(int j=0;j<((Box)this.playersNumBoxPanel.get(i)).getComponents().length;j++){
                        ((JPanel)((Box)this.playersNumBoxPanel.get(i)).getComponent(j)).setOpaque(false);
                    }
                }
            }
            this.playersBoxPanel.setVisible(true);
        }
        
    }
    
    public void upDateSelectionAtFirstRowFrame(int index, String name){
        this.currentDominoLabel.get(index+1).setText("Selected by "+name);
    }
    
    public void upDatePlayersKingdom(){
        for(int i=0;i<this.kingdominoServerKingdomDialogList.size();i++){
            (this.kingdominoServerKingdomDialogList.get(i)).refreshKingdom();
        }
    }
    
    public void refreshFrame(){
        
        //clear frame and its items
        game.getKingdominoServerFrame().getKingdominoServerKingdomDialogList().clear();
        
        for(int i=0;i<this.playersNumBoxPanel.size();i++){
            this.game.getKingdominoServerFrame().getPlayerNameLabel().get(i).setText("Player "+(1+i));
            this.game.getKingdominoServerFrame().getPlayerIcon().get(i).setColor("Default", true);
            this.game.getKingdominoServerFrame().getIconLabel().get(i).setIcon(this.game.getKingdominoServerFrame().getPlayerIcon().get(i).getIconPlayer());
            
            ActionListener[] actionListener = this.game.getKingdominoServerFrame().getPlayerKingdomButton().get(i).getActionListeners();
            
            for(int j=0;j<actionListener.length;j++){
                this.game.getKingdominoServerFrame().getPlayerKingdomButton().get(i).removeActionListener(actionListener[j]);
            }
        }

        //inserting the remainings players' info
        for(int i=0;i<this.game.getPlayersThreadList().size();i++){
            this.game.getKingdominoServerFrame().getPlayerNameLabel().get(i).setText(this.game.getPlayerFromThreadList(i).getName());
            this.game.getKingdominoServerFrame().getPlayerIcon().get(i).setColor(this.game.getPlayerFromThreadList(i).getColor(), true);
            this.game.getKingdominoServerFrame().getIconLabel().get(i).setIcon(this.game.getKingdominoServerFrame().getPlayerIcon().get(i).getIconPlayer());
            ActionListener[] listener = this.game.getKingdominoServerFrame().getPlayerKingdomButton().get(i).getActionListeners();
            for(int j=0;j<listener.length;j++)
                this.game.getKingdominoServerFrame().getPlayerKingdomButton().get(i).removeActionListener(clickListener);
            
            this.game.getKingdominoServerFrame().getPlayerKingdomButton().get(i).addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    int index = game.getKingdominoServerFrame().getKingdominoServerKingdomDialogList().size();
                    game.getKingdominoServerFrame().getKingdominoServerKingdomDialogList().add(new KingdominoServerKingdomDialog(game.getKingdominoServerFrame(),game.getPlayerFromThreadList(index)));
                    
                    game.getKingdominoServerFrame().getKingdominoServerKingdomDialogList().get(index).setVisible(true);
                }
            });
        }
    }
    
    public void startGame(){
        
        this.getGame().setGameStarted(true);
    }
    
    public void endGame(){
        
        this.getGame().setGameStarted(false);
    }

    private class ClickListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String text = e.getActionCommand();
            
            if(text.equals("Activate")){
                
                kingdomGameThread = new KingdomGameThread(game);
                kingdomGameThread.activate();
                kingdomGameThread.start();  
                
            }
            else if(text.equals("Shut Down")){
                kingdomGameThread.shutDown();
                JOptionPane.showMessageDialog(rootPane, text);
            }
            else if(text.equals("Help")){
                JOptionPane.showMessageDialog(rootPane, "1.KingDomino is a 2-4 player game where you are a King "+
                                            "who seeks to expand his land (represented by the dominoes).\n" +
                                            "2.Players play a series of rounds.\n" +
                                            "3.Each round, players pick different patterns of dominoes and try to connect them in a 5 X 5 grid.\n" +
                                            "4.Players gain points by:\n" +
                                            "   i.planning and forming patterns and\n" +
                                            "   ii.connecting key dominoes.\n" +
                                            "5.The player with the highest score wins.",
                                            "Information Message",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(text.equals("About Kingdomino")){
                JOptionPane.showMessageDialog(rootPane, "The game \"Kingdomino\" was developed by the Student Benjamin Moussa\n"+
                                            "from The Department of Informatics and Telecomunications of The University of Peloponnese as a Project,\n"+
                                            "for the course Advanced Topics of Programming Languages, guided by the supervisor Assistant Professor Nikolaos Platis.",
                                            "Information Message",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
