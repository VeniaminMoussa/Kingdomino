/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingdominoclient;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

/**
 *
 * @author Lenovo
 */
public class KingdominoClientFrame extends JFrame{
    
    private Game game;
    private Player player;
    private JPanel center;
    private Box kingdominoCenter;
    private int i, j;
    
    //Menu Items
    private JMenuBar menuBar = new JMenuBar();
    private JMenu gameMenu = new JMenu("Game");
    private JMenuItem enterMenuItem = new JMenuItem("Enter");
    private JMenuItem pauseMenuItem = new JMenuItem("Pause");
    private JMenuItem exitMenuItem = new JMenuItem("Exit");
    private JMenu helpMenu = new JMenu("Help");

    private JMenuItem helpMenuItem = new JMenuItem("Help");
    private JMenuItem aboutMenuItem = new JMenuItem("About Kingdomino");
    private ClickListener clickListener = new ClickListener();
    
    //Kingdom Items
    private ButtonGroup KingdomRadioButtonGroup = new ButtonGroup();
    private JRadioButton[][] kingdomRadioButtons = new JRadioButton[5][5];
    private RadioButtonKingdomListener radioButtonKingdomListener = new RadioButtonKingdomListener();
    private Box kingdomBoxPanel = new Box(BoxLayout.PAGE_AXIS);
    private JPanel kingdomPanel = new JPanel(new GridLayout(5, 5));
    private JScrollPane kingdomScrollPane = new JScrollPane(kingdomPanel);
    private Box placeDiscardDominoButtonBoxPanel = new Box(BoxLayout.LINE_AXIS);
    private JPanel placeDominoButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JButton placeDominoButton = new JButton("Place Domino");
    private JPanel discardDominoButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JButton discardDominoButton = new JButton("Discard Domino");
    
    private SquareArea icon = new SquareArea("Empty");
    private ImageIcon emptyIcon = new ImageIcon("tiles/Empty.png");
    
    //Players' Info Items
    private List<PlayerIcon> playerIcon = new LinkedList<>();
    private List<Box> playersNumBoxPanel = new LinkedList<>();
    private List<JPanel> iconPanel = new LinkedList<>();
    private List<JPanel> playerNamePanel = new LinkedList<>();
    private List<JPanel> playerKingdomButtonPanel = new LinkedList<>();
    private List<JLabel> iconLabel = new LinkedList<>();
    private List<JLabel> playerNameLabel = new LinkedList<>();
    private List<JButton> playerKingdomButton = new LinkedList<>();
    private Box playersBoxPanel = new Box(BoxLayout.LINE_AXIS);
    
    //Start Button Items
    private Box startButtonBoxPanel = new Box(BoxLayout.PAGE_AXIS);
    private JPanel startButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JButton startButton = new JButton("Start Game");
    
    
    //Dominos Rows Items
    private ButtonGroup currentButtonGroup = new ButtonGroup();
    private List<JRadioButton> radioButtonCurrentDomino = new LinkedList<>();
    private List<JLabel> nextDominoLabel = new LinkedList<>();
    private List<JPanel> currentDominoPanel = new LinkedList<>();
    private List<JPanel> nextDominoPanel = new LinkedList<>();
    private Box currentDominoBoxPanel = new Box(BoxLayout.PAGE_AXIS);
    private Box nextDominoBoxPanel = new Box(BoxLayout.PAGE_AXIS);
    
    //Pick Domino Items
    private JLabel pickLabel = new JLabel();
    private JButton rotateLeftButton = new JButton("Rotate Left");
    private JButton rotateRightButton = new JButton("Rotate Right");
    private Domino dominoSelection = new Domino();
    private List<Domino> dominoPicked = new LinkedList<>();
    private List<JPanel> pickPanel = new LinkedList<>();
    private Box pickBox = new Box(BoxLayout.LINE_AXIS);
    private JButton selectButton = new JButton("Select");
    private RadioButtonListener radioButtonListener = new RadioButtonListener();
    
    //Popup windows
    private KingdominoClientInfoDialog kingdominoClientInfoDialog=null;
    private List<KingdominoClientKingdomDialog> kingdominoClientKingdomDialogList = new LinkedList<>();
    
    public KingdominoClientFrame(){
        
        this.setGame(new Game(this));
        this.setSize(1400,800);
        this.setMinimumSize(new Dimension(1200,700));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Kingdomino");
        this.setLayout(new BorderLayout());
        this.center = new JPanel(new BorderLayout());
        this.add(center,BorderLayout.CENTER);
        
        this.kingdominoCenter = new Box(BoxLayout.LINE_AXIS);
        this.center.add(kingdominoCenter,BorderLayout.CENTER);
        
        this.setMenuItemsFrame();
        
        this.setKingdomItemFrame();
        
        this.setCurrentDominoRow();
        
        this.setNextDominoRow();
        
        this.setPickedDominoItems();
        
        this.setStartButtonFrame();
        
        this.setPlayersItemsFrame();
        

        
        
        
    addWindowListener(new WindowAdapter()  {

        @Override
        public void windowClosing(WindowEvent e)
        {
//            if(game.getGameStarted()){
//                JOptionPane.showMessageDialog(KingdominoClientFrame.this, "Can not leave while Playing!!!",
//                                            "Information Message",JOptionPane.WARNING_MESSAGE);
//            }
//            else if(!game.getGameStarted()){
//                int answer = JOptionPane.showConfirmDialog(KingdominoClientFrame.this, "Are you sure \nyou want leave?", "Window closing", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//                if (answer == JOptionPane.YES_OPTION){
//                    if(game.getPlayerThread().isAlive())
//                        game.getPlayer().sendExit();
//                    else
//                        dispose();
//                }
//            }
        }
            
    });

    }
    public Game getGame(){
        return this.game;
    }
    
    public Player getPlayer(){
        return this.player;
    }
    
    public JMenuItem getEnterMenuItem() {
        return enterMenuItem;
    }

    public JMenuItem getPauseMenuItem() {
        return pauseMenuItem;
    }

    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public JButton getSelectButton() {
        return selectButton;
    }

    public JButton getDiscardDominoButton() {
        return discardDominoButton;
    }

    public void setDiscardDominoButton(JButton discardDominoButton) {
        this.discardDominoButton = discardDominoButton;
    }

    public void setSelectButton(JButton selectButton) {
        this.selectButton = selectButton;
    }

    public List<Domino> getDominoPicked() {
        return dominoPicked;
    }

    public void setDominoPicked(List<Domino> dominoPicked) {
        this.dominoPicked = dominoPicked;
    }

    public Box getPlaceDiscardDominoButtonBoxPanel() {
        return placeDiscardDominoButtonBoxPanel;
    }

    public void setPlaceDiscardDominoButtonBoxPanel(Box placeDiscardDominoButtonBoxPanel) {
        this.placeDiscardDominoButtonBoxPanel = placeDiscardDominoButtonBoxPanel;
    }

    public Box getStartButtonBoxPanel() {
        return startButtonBoxPanel;
    }

    public void setStartButtonBoxPanel(Box startButtonBoxPanel) {
        this.startButtonBoxPanel = startButtonBoxPanel;
    }

    public List<PlayerIcon> getPlayerIcon() {
        return playerIcon;
    }

    public List<JLabel> getIconLabel() {
        return iconLabel;
    }

    public List<JButton> getPlayerKingdomButton() {
        return playerKingdomButton;
    }

    public Box getStartButtonsBoxPanel() {
        return startButtonBoxPanel;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JPanel getCenter() {
        return center;
    }

    public JButton getPlaceDominoButton() {
        return placeDominoButton;
    }

    public void setPlaceDominoButton(JButton placeDominoButton) {
        this.placeDominoButton = placeDominoButton;
    }

    public Box getCurrentDominoBoxPanel() {
        return currentDominoBoxPanel;
    }

    public void setCurrentDominoBoxPanel(Box currentDominoBoxPanel) {
        this.currentDominoBoxPanel = currentDominoBoxPanel;
    }

    public List<JRadioButton> getRadioButtonCurrentDomino() {
        return radioButtonCurrentDomino;
    }

    public Domino getDominoSelection() {
        return dominoSelection;
    }

    public void setDominoSelection(Domino dominoSelection) {
        this.dominoSelection = dominoSelection;
    }

    public List<JLabel> getNextDominoLabel() {
        return nextDominoLabel;
    }

    public List<JPanel> getCurrentDominoPanel() {
        return currentDominoPanel;
    }

    public List<JPanel> getNextDominoPanel() {
        return nextDominoPanel;
    }

    public Box getNextDominoBoxPanel() {
        return nextDominoBoxPanel;
    }

    public JLabel getPickLabel() {
        return pickLabel;
    }

    public void setPickLabel(JLabel pickLabel) {
        this.pickLabel = pickLabel;
    }

    public void setNextDominoBoxPanel(Box nextDominoBoxPanel) {
        this.nextDominoBoxPanel = nextDominoBoxPanel;
    }

    public KingdominoClientInfoDialog getKingdominoClientInfoDialog() {
        return kingdominoClientInfoDialog;
    }

    public List<KingdominoClientKingdomDialog> getKingdominoClientKingdomDialogList() {
        return kingdominoClientKingdomDialogList;
    }
    
    public void setGame(Game game){
        this.game=game;
        this.setPlayer(this.getGame().getPlayer());
    }
    
    public void setPlayer(Player player){
        this.player=player;
    }
    
    public void setCenter(JPanel center) {
        this.center = center;
    }

    public Box getKingdominoCenter() {
        return kingdominoCenter;
    }

    public void setKingdominoCenter(Box kingdominoCenter) {
        this.kingdominoCenter = kingdominoCenter;
    }

    public Box getPickBox() {
        return pickBox;
    }

    public void setPickBox(Box pickBox) {
        this.pickBox = pickBox;
    }

    public void setKingdominoClientInfoDialog(KingdominoClientInfoDialog kingdominoClientInfoDialog) {
        this.kingdominoClientInfoDialog = kingdominoClientInfoDialog;
    }

    public void setKingdominoClientKingdomDialogList(List<KingdominoClientKingdomDialog> kingdominoClientKingdomDialogList) {
        this.kingdominoClientKingdomDialogList = kingdominoClientKingdomDialogList;
    }
    
    public void newGame(){
        //remove all Datas from game if ther is any
        this.getGame().getPlayersList().clear();
        this.getGame().setPlayersList(this.getGame().createPlayersList());
        this.getGame().getPlayersIconList().clear();
        this.getGame().setPlayersIconList(this.getGame().createPlayersIconList());
        this.getGame().getCurrentDominoRow().clear();
        this.getGame().setCurrentDominoRow(this.getGame().createCurrentDominoRow());
        this.getGame().getNextDominoRow().clear();
        this.getGame().setNextDominoRow(this.getGame().createNextDominoRow());
        this.getGame().setGameStarted(false);
        this.getGame().setLastRow(false);
        
        for(int i=0;i<this.kingdominoClientKingdomDialogList.size();i++){
            this.kingdominoClientKingdomDialogList.remove(i);
        }
        
        this.upDateKingdomFrame();
        this.setCurrentDominoRow();
        this.setNextDominoRow();
        //Set neutral frame
        this.setTitle("Kingdomino");
        this.kingdomBoxPanel.setVisible(false);
        this.pickBox.setVisible(false);
        this.startButtonBoxPanel.setVisible(false);
        this.currentDominoBoxPanel.setVisible(false);
        this.nextDominoBoxPanel.setVisible(false);
        
        for(int i=0;i<this.playerIcon.size();i++){
            this.playersNumBoxPanel.get(i).setVisible(false);
            this.playerIcon.get(i).setColor("Default",true);
            this.playerNameLabel.get(i).setText("Player "+(i+1));
            for(int j=0;j<this.getPlayerKingdomButton().get(i).getActionListeners().length;j++)
                this.getPlayerKingdomButton().get(i).removeActionListener(this.getPlayerKingdomButton().get(i).getActionListeners()[j]);
        }
        
        this.enterMenuItem.setEnabled(true);
        this.exitMenuItem.setEnabled(false);
        this.setStartButtonAction();
    }
    
    public void setMenuItemsFrame(){
        
        enterMenuItem.addActionListener(clickListener);

        pauseMenuItem.setEnabled(false);
        pauseMenuItem.addActionListener(clickListener);

        exitMenuItem.setEnabled(false);
        exitMenuItem.addActionListener(clickListener);

        gameMenu.add(enterMenuItem);
        gameMenu.add(pauseMenuItem);
        gameMenu.add(exitMenuItem);
        
        helpMenuItem.addActionListener(clickListener);
        aboutMenuItem.addActionListener(clickListener);

        helpMenu.add(helpMenuItem);
        helpMenu.add(aboutMenuItem);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }
    
    public void setStartButtonAction(){
        
        ActionListener[] actionListener = startButton.getActionListeners();

        for(int i=0;i<actionListener.length;i++)
            startButton.removeActionListener(startButton.getActionListeners()[i]);
        
        if(game.getPlayersList().size()<1){
            startButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JOptionPane.showMessageDialog(rootPane, "More players are required!!!","Information Message",JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        else{
            startButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    game.getPlayer().sendObjectToServer("Start");
                }
            });
        }
    }
    
    public void setStartButtonFrame(){
        
        this.setStartButtonAction();
        
        this.startButtonPanel.add(this.startButton);
        this.startButtonBoxPanel.add(this.startButtonPanel);

        this.center.add(this.startButtonBoxPanel,BorderLayout.NORTH);

        this.startButtonBoxPanel.setVisible(false);
    }
    
    public void setPlaceDominoButtonAction(){
        ActionListener[] actionListener = this.placeDominoButton.getActionListeners();

        for(int i=0;i<actionListener.length;i++)
            this.placeDominoButton.removeActionListener(this.placeDominoButton.getActionListeners()[i]);
        
        this.placeDominoButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if((game.getRow()<0) || (game.getColumn()<0)){
                    JOptionPane.showMessageDialog(rootPane, "Choose a place For your Kingdom!!!",
                                            "Information Message",JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    if(game.getPlayer().checkDominoPlace(game.getRow(), game.getColumn(), dominoSelection)){
                        
                        
                        game.getPlayer().placeDominoAt(game.getRow(), game.getColumn(), dominoSelection);
                        getPickLabel().setEnabled(false);
                        discardDominoButton.setEnabled(false);
                        getPlaceDominoButton().setEnabled(false);
                        
                        upDateKingdomFrame();
                    }
                }
                
            }
        });
    }
    
    public void setDiscardDominoButtonAction(){
        ActionListener[] actionListener = this.discardDominoButton.getActionListeners();

        for(int i=0;i<actionListener.length;i++)
            this.discardDominoButton.removeActionListener(this.discardDominoButton.getActionListeners()[i]);
        
        this.discardDominoButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.getPlayer().placeDominoAt(game.getRow(), game.getColumn(), null);
                getPickLabel().setEnabled(false);
                discardDominoButton.setEnabled(false);
                getPlaceDominoButton().setEnabled(false);
            }
        });
    }
    
    public void setKingdomItemFrame(){
        
        this.kingdomPanel.setPreferredSize(new Dimension(340, 340));
        this.kingdomPanel.setMinimumSize(new Dimension(320, 320));
        
        this.createKingdomFrame();
        
        this.kingdomScrollPane.setPreferredSize(new Dimension(420, 420));
        this.kingdomScrollPane.setMaximumSize(new Dimension(440,440));
        
        this.kingdomBoxPanel.add(this.kingdomScrollPane);
        this.kingdominoCenter.add(this.kingdomBoxPanel);
        
        this.setPlaceDominoButtonAction();
        this.setDiscardDominoButtonAction();
        
        this.placeDominoButtonPanel.add(this.placeDominoButton);
        this.placeDiscardDominoButtonBoxPanel.add(this.placeDominoButtonPanel);
        this.discardDominoButtonPanel.add(this.discardDominoButton);
        this.placeDiscardDominoButtonBoxPanel.add(this.discardDominoButtonPanel);
        this.placeDiscardDominoButtonBoxPanel.setVisible(false);
        
        this.add(this.placeDiscardDominoButtonBoxPanel,BorderLayout.SOUTH);

        this.placeDominoButton.setEnabled(false);

        this.discardDominoButton.setEnabled(false);
        this.kingdomBoxPanel.setVisible(false);
    }
    
    public void createKingdomFrame(){
        
        this.kingdomPanel.removeAll();
        
        this.icon.setIcon(emptyIcon);
          
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                kingdomRadioButtons[i][j]= new JRadioButton();
            }
        }
        
        for(i=0;i<5;i++){
            for(j=0;j<5;j++){
                
                if(this.getPlayer().getKingdom().getSquareArea(i, j).getName().compareTo("Empty")==0){
                    
                    this.kingdomPanel.add(kingdomRadioButtons[i][j]);
                    this.kingdomRadioButtons[i][j].setIcon(icon.getIcon());
                    this.kingdomRadioButtons[i][j].setBorderPainted(true);
                    this.kingdomRadioButtons[i][j].addItemListener(radioButtonKingdomListener);
                    this.kingdomRadioButtons[i][j].setName(i+" "+j);
                    this.KingdomRadioButtonGroup.add(this.kingdomRadioButtons[i][j]);
                }
                else{
                    
                    this.kingdomPanel.add(kingdomRadioButtons[i][j]);
                    this.kingdomRadioButtons[i][j].setIcon(this.getPlayer().getKingdom().getSquareAreaIcon(i, j));
                    this.kingdomRadioButtons[i][j].setBorderPainted(false);
                    this.kingdomRadioButtons[i][j].setName(i+" "+j);
                    this.KingdomRadioButtonGroup.add(this.kingdomRadioButtons[i][j]);
                }
            }
        }
    }
    
    public void upDateKingdomFrame(){
        
        for(i=0;i<5;i++){
            for(j=0;j<5;j++){
                
                if(this.getPlayer().getKingdom().getSquareArea(i, j).getName().compareTo("Empty")==0){
                    
                    ItemListener[] itemListener = this.kingdomRadioButtons[i][j].getItemListeners();
                    
                    for(int k=0;k<itemListener.length;k++)
                        this.kingdomRadioButtons[i][j].removeItemListener(itemListener[k]);
                    
                    this.icon.setIcon(emptyIcon);
                    this.kingdomRadioButtons[i][j].setBorderPainted(true);
                    this.kingdomRadioButtons[i][j].addItemListener(radioButtonKingdomListener);
                    this.kingdomRadioButtons[i][j].setName(i+" "+j);
                    this.kingdomRadioButtons[i][j].setIcon(this.icon.getIcon());
                }
                else{
                    ItemListener[] itemListener = this.kingdomRadioButtons[i][j].getItemListeners();
                    
                    for(int k=0;k<itemListener.length;k++)
                        this.kingdomRadioButtons[i][j].removeItemListener(itemListener[k]);
                    
                    this.kingdomRadioButtons[i][j].setBorderPainted(false);
                    this.kingdomRadioButtons[i][j].setName(i+" "+j);
                    this.kingdomRadioButtons[i][j].setIcon(this.getPlayer().getKingdom().getSquareAreaIcon(i, j));
                }
            }
        }
    }
    
    public void setPlayersItemsFrame(){
        
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
            if(i==0)
                this.playerIcon.get(i).resizeIcon(50, 80);
            else
                this.playerIcon.get(i).resizeIcon(35, 50);
            this.iconLabel.add(new JLabel(this.playerIcon.get(i).getIconPlayer()));
            this.iconPanel.get(i).add(this.iconLabel.get(i));
            this.playersNumBoxPanel.get(i).add(this.iconPanel.get(i));
            this.playerNamePanel.get(i).add(this.playerNameLabel.get(i));
            this.playersNumBoxPanel.get(i).add(this.playerNamePanel.get(i));
            if(i!=0){
                this.playerKingdomButtonPanel.get(i).add(this.playerKingdomButton.get(i));
                this.playersNumBoxPanel.get(i).add(this.playerKingdomButtonPanel.get(i));
            }
            this.playersNumBoxPanel.get(i).setVisible(false);
        }
        
        for(int i=0;i<4;i++){
            this.playersBoxPanel.add(this.playersNumBoxPanel.get(i));
        }
        
        this.add(playersBoxPanel,BorderLayout.NORTH);
    }
    
    public void newPlayerToFrame(Player newPlayer){
        
        int i;
        
        for(i=0;i<this.playerIcon.size();i++){
            if(this.playerIcon.get(i).getColor().compareToIgnoreCase("Default")==0)
                break;
        }
        
        this.playerIcon.get(i).setColor(newPlayer.getColor(),true);
        this.playerNameLabel.get(i).setText(newPlayer.getName());
        
        if(i==0)
                newPlayer.getPlayerIcon().resizeIcon(65, 100);
            else
                newPlayer.getPlayerIcon().resizeIcon(35, 50);
        
        this.iconLabel.get(i).setIcon(newPlayer.getPlayerIcon().getIconPlayer());
            
        if(i!=0){
            this.playerKingdomButton.get(i).addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    kingdominoClientKingdomDialogList.add(new KingdominoClientKingdomDialog(KingdominoClientFrame.this, newPlayer));
                    kingdominoClientKingdomDialogList.get(getKingdominoClientKingdomDialogList().size()-1).setVisible(true);
                }
            });
        }
        
        this.playersNumBoxPanel.get(i).setVisible(true);
    }
    
    public void upDatePlayerToFrame(){
        
        if(!kingdominoClientKingdomDialogList.isEmpty()){
            for(int i=1;i<kingdominoClientKingdomDialogList.size();i++)
            kingdominoClientKingdomDialogList.remove(i);
        }
        
        for(int i=1;i<this.playerIcon.size();i++){
            this.playerIcon.get(i).setColor("Default",true);
            this.playerIcon.get(i).resizeIcon(35, 50);
            this.iconLabel.get(i).setIcon(this.playerIcon.get(i).getIconPlayer());
            this.playerNameLabel.get(i).setText("Player "+(i+1));
            for(int j=0;j<this.getPlayerKingdomButton().get(i).getActionListeners().length;j++)
                this.getPlayerKingdomButton().get(i).removeActionListener(this.getPlayerKingdomButton().get(i).getActionListeners()[j]);
            
            this.playersNumBoxPanel.get(i).setVisible(false);
        }
        
        //here is the original
        for(int i=0, j;i<this.game.getPlayersList().size();i++){
            for(j=0;j<this.playerIcon.size();j++){
                if(this.playerIcon.get(j).getColor().compareToIgnoreCase(this.game.getPlayerFromList(i).getPlayerIcon().getColor())==0)
                    break;
            }
            if(j==this.playerIcon.size())
                this.newPlayerToFrame(this.game.getPlayerFromList(i));
        }
        
        this.setStartButtonAction();
    }
    
    public void setSelectButtonAction(){
        
        ActionListener[] actionListener = this.selectButton.getActionListeners();

        for(int i=0;i<actionListener.length;i++)
            this.selectButton.removeActionListener(this.selectButton.getActionListeners()[i]);
        
        this.selectButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(game.getPlayer().getTurn()){
                    
                    for(int i=0;i<radioButtonCurrentDomino.size();i++){
                        if(game.getCurrentDominoRow().get(i).getNum()==dominoSelection.getNum()){
                            if(!radioButtonCurrentDomino.get(i).isBorderPainted()){
                                JOptionPane.showMessageDialog(null,"Choose a Domino!!!\n","~Informative Message~",JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                        }
                    }
                    dominoPicked.add(new Domino());
                    dominoPicked.get(dominoPicked.size()-1).setDomino(dominoSelection);
                    Collections.sort(dominoPicked);
                    game.getPlayer().sendRowOfSelectedDomino(game.getSelectedRow());
                    
                    game.getKingdominoClientFrame().pickLabel.setEnabled(false);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Not your Turn!!!\n","~Informative Message~",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
    
    public void setCurrentDominoRow(){
        
        this.radioButtonCurrentDomino.clear();
        this.currentDominoPanel.clear();
        this.currentDominoBoxPanel.removeAll();
        
        this.radioButtonCurrentDomino.add(new JRadioButton());
        this.radioButtonCurrentDomino.add(new JRadioButton());
        this.radioButtonCurrentDomino.add(new JRadioButton());
        this.radioButtonCurrentDomino.add(new JRadioButton());
        
        for(int i=0;i<this.radioButtonCurrentDomino.size();i++)
            this.radioButtonCurrentDomino.get(i).setBorderPainted(true);
        
        this.currentDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.currentDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.currentDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.currentDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.currentDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.currentDominoPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        
        this.currentDominoPanel.get(0).add(new JLabel("Pick Domino"));
        
        this.currentDominoBoxPanel.add(this.currentDominoPanel.get(0));
        
        for(int i=1;i<this.currentDominoPanel.size()-1;i++){
            this.radioButtonCurrentDomino.get(i-1).setName(String.valueOf((i-1)));
            this.radioButtonCurrentDomino.get(i-1).addItemListener(radioButtonListener);
            this.currentButtonGroup.add(this.radioButtonCurrentDomino.get(i-1));
            this.currentDominoPanel.get(i).add(this.radioButtonCurrentDomino.get(i-1));
            this.currentDominoBoxPanel.add(this.currentDominoPanel.get(i));
        }
        
        this.currentDominoBoxPanel.add(new JPanel().add(this.selectButton));
        
        this.setSelectButtonAction();
        
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
        this.nextDominoLabel.add(new JLabel("Empty"));
        this.nextDominoLabel.add(new JLabel("Empty"));
        this.nextDominoLabel.add(new JLabel("Empty"));
        this.nextDominoLabel.add(new JLabel("Empty"));
        
        for(int i=0;i<this.nextDominoPanel.size();i++){
            this.nextDominoPanel.get(i).add(this.nextDominoLabel.get(i));
            this.nextDominoBoxPanel.add(this.nextDominoPanel.get(i));
        }
        
        this.nextDominoBoxPanel.setVisible(false);
        
        this.kingdominoCenter.add(this.nextDominoBoxPanel);
    }
    
    public void upDateCurrentDominoRow(){  
        
        if(this.getGame().getLastRow()){
            this.currentDominoBoxPanel.setVisible(false);
            this.nextDominoBoxPanel.setVisible(false);
        }
        else{
            
            this.getGame().getCurrentDominoRow().clear();
            
            for(int i=0;i<this.getGame().getNextDominoRow().size();i++){
            this.getGame().getCurrentDominoRow().add(this.getGame().getNextDominoRow().get(i));
            }

            for(int i=1;i<this.getGame().getNextDominoRow().size()+1;i++){

                this.radioButtonCurrentDomino.get(i-1).setBorderPainted(true);
                this.radioButtonCurrentDomino.get(i-1).setName(String.valueOf(i-1));
                this.radioButtonCurrentDomino.get(i-1).setIcon(this.getGame().getCurrentDominoRow().get(i-1).getDominoIcon());
                this.radioButtonCurrentDomino.get(i-1).setText("Select");

                ItemListener[] listener = this.radioButtonCurrentDomino.get(i-1).getItemListeners();

                for(int j=0;j<listener.length;j++)
                    this.radioButtonCurrentDomino.get(i-1).removeItemListener(listener[j]);

                this.radioButtonCurrentDomino.get(i-1).addItemListener(radioButtonListener);
            }
        }
        
        
        
    }
    
    public void upDateNextDominoRow(){
        
        if(!game.getLastRow()){
            for(int i=1;i<this.getGame().getNextDominoRow().size()+1;i++){
                ((JLabel)this.nextDominoPanel.get(i).getComponent(0)).setIcon(this.getGame().getNextDominoRow().get(i-1).getDominoIcon());
                ((JLabel)this.nextDominoPanel.get(i).getComponent(0)).setText("Next Domino");
            }
        }
        else{
            for(int i=1;i<this.getGame().getNextDominoRow().size()+1;i++){
                ((JLabel)this.nextDominoPanel.get(i).getComponent(0)).setIcon(null);
                ((JLabel)this.nextDominoPanel.get(i).getComponent(0)).setText("Empty");
            }
        }
        
    }

    public void upDateSelectionOfPlayer(int index, String name){
         this.radioButtonCurrentDomino.get(index).setText("Selected by "+name);
          
         this.radioButtonCurrentDomino.get(index).setBorderPainted(false);
           
         ItemListener[] listener = this.radioButtonCurrentDomino.get(index).getItemListeners();
            
         for(int j=0;j<listener.length;j++)
             this.radioButtonCurrentDomino.get(index).removeItemListener(listener[j]);
    }
    
    public void setPickedDominoItems(){
        this.pickPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.pickPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        this.pickPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER)));
        
        this.rotateLeftButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.setRow(-1);
                game.setColumn(-1);
                dominoSelection.rotateLeft();
                pickLabel.setIcon(dominoSelection.getDominoIcon());
            }
        });
        
        this.rotateRightButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.setRow(-1);
                game.setColumn(-1);
                dominoSelection.rotateRight();
                pickLabel.setIcon(dominoSelection.getDominoIcon());
            }
        });
        
        this.pickPanel.get(0).add(this.rotateLeftButton);
        this.pickPanel.get(1).add(this.pickLabel);
        this.pickPanel.get(2).add(this.rotateRightButton);
        
        
        for(int i=0;i<this.pickPanel.size();i++){
            this.pickBox.add(this.pickPanel.get(i));
            
        }
        
        this.pickBox.setVisible(false);

        this.center.add(pickBox,BorderLayout.SOUTH);
    }
    
    public void setTurnFrame(String nameTurn){
        
        if(nameTurn==null){
            for(int i=0;i<this.getGame().getPlayersList().size()+1;i++){
                this.playersNumBoxPanel.get(i).setVisible(false);

                for(int j=0;j<((Box)this.playersNumBoxPanel.get(i)).getComponents().length;j++){
                    ((JPanel)((Box)this.playersNumBoxPanel.get(i)).getComponent(j)).setOpaque(false);
                
                }
                this.playersNumBoxPanel.get(i).setVisible(true);
            }
        }
        else if(nameTurn!=null){
            for(int i=0;i<this.getGame().getPlayersList().size()+1;i++){
                this.playersNumBoxPanel.get(i).setVisible(false);
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
                this.playersNumBoxPanel.get(i).setVisible(true);
            }
        }
    }
    
    public void upDatePlayersKingdom(){
        for(int i=0;i<this.kingdominoClientKingdomDialogList.size();i++){
            (this.kingdominoClientKingdomDialogList.get(i)).refreshKingdom();
        }
    }
    
    private class RadioButtonListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent ie)
        {
            if(ie.getStateChange() == ItemEvent.SELECTED){
                
                ((JRadioButton)ie.getItem()).setText("Selected");
                if(((JRadioButton)ie.getItem()).getName().compareTo("0")==0){
                    game.setSelectedRow(0);
                    dominoSelection.setDomino(game.getCurrentDominoRow().get(0));
                    pickLabel.setIcon(dominoSelection.getDominoIcon());
                    pickLabel.setEnabled(false);
                }
                else if(((JRadioButton)ie.getItem()).getName().compareTo("1")==0){
                    game.setSelectedRow(1);
                    dominoSelection.setDomino(game.getCurrentDominoRow().get(1));
                    pickLabel.setIcon(dominoSelection.getDominoIcon());
                    pickLabel.setEnabled(false);
                }
                else if(((JRadioButton)ie.getItem()).getName().compareTo("2")==0){
                    game.setSelectedRow(2);
                    dominoSelection.setDomino(game.getCurrentDominoRow().get(2));
                    pickLabel.setIcon(dominoSelection.getDominoIcon());
                    pickLabel.setEnabled(false);
                }
                else if(((JRadioButton)ie.getItem()).getName().compareTo("3")==0){
                    game.setSelectedRow(3);
                    dominoSelection.setDomino(game.getCurrentDominoRow().get(3));
                    pickLabel.setIcon(dominoSelection.getDominoIcon());
                    pickLabel.setEnabled(false);
                }
            }
            else {
                ((JRadioButton)ie.getItem()).setText("Select");
            }
        }
    }
    
    private class RadioButtonKingdomListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent ie)
        {
            if(ie.getStateChange() == ItemEvent.SELECTED){
                
                game.setRow(Character.getNumericValue(((JRadioButton)ie.getItem()).getName().charAt(0)));
                game.setColumn(Character.getNumericValue(((JRadioButton)ie.getItem()).getName().charAt(2)));
                
                if(player.checkDominoPlace(game.getRow(), game.getColumn(), dominoSelection)){
                    if(dominoSelection.getAlign().compareToIgnoreCase("horizontal")==0){
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setVisible(false);
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setOpaque(true);
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setBackground(Color.green);
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setVisible(true);
                        if(game.getColumn()!=kingdomRadioButtons.length-1){
                            kingdomRadioButtons[game.getRow()][game.getColumn()+1].setVisible(false);
                            kingdomRadioButtons[game.getRow()][game.getColumn()+1].setOpaque(true);
                            kingdomRadioButtons[game.getRow()][game.getColumn()+1].setBackground(Color.green); 
                            kingdomRadioButtons[game.getRow()][game.getColumn()+1].setVisible(true);
                        }
                    }
                    else if(dominoSelection.getAlign().compareToIgnoreCase("vertical")==0){
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setVisible(false);
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setOpaque(true);
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setBackground(Color.green); 
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setVisible(true);
                        if(game.getRow()!=kingdomRadioButtons[game.getRow()].length-1){
                            kingdomRadioButtons[game.getRow()+1][game.getColumn()].setVisible(false);
                            kingdomRadioButtons[game.getRow()+1][game.getColumn()].setOpaque(true);
                            kingdomRadioButtons[game.getRow()+1][game.getColumn()].setBackground(Color.green); 
                            kingdomRadioButtons[game.getRow()+1][game.getColumn()].setVisible(true);
                        }
                        
                    }
                }
                else{
                    if(dominoSelection.getAlign().compareToIgnoreCase("horizontal")==0){
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setVisible(false);
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setOpaque(true);
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setBackground(Color.red);
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setVisible(true);
                        if(game.getColumn()!=kingdomRadioButtons.length-1){
                            kingdomRadioButtons[game.getRow()][game.getColumn()+1].setVisible(false);
                            kingdomRadioButtons[game.getRow()][game.getColumn()+1].setOpaque(true);
                            kingdomRadioButtons[game.getRow()][game.getColumn()+1].setBackground(Color.red);  
                            kingdomRadioButtons[game.getRow()][game.getColumn()+1].setVisible(true);
                        }
                    }
                    else if(dominoSelection.getAlign().compareToIgnoreCase("vertical")==0){
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setVisible(false);
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setOpaque(true);
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setBackground(Color.red); 
                        kingdomRadioButtons[game.getRow()][game.getColumn()].setVisible(true);
                        if(game.getRow()!=kingdomRadioButtons[game.getRow()].length-1){
                            kingdomRadioButtons[game.getRow()+1][game.getColumn()].setVisible(false);
                            kingdomRadioButtons[game.getRow()+1][game.getColumn()].setOpaque(true);
                            kingdomRadioButtons[game.getRow()+1][game.getColumn()].setBackground(Color.red);
                            kingdomRadioButtons[game.getRow()+1][game.getColumn()].setVisible(true);
                        }
                        
                    }
                }
            }
            else {
                int i = Character.getNumericValue(((JRadioButton)ie.getItem()).getName().charAt(0));
                int j = Character.getNumericValue(((JRadioButton)ie.getItem()).getName().charAt(2));
                
                 kingdomRadioButtons[i][j].setVisible(false);
                 kingdomRadioButtons[i][j].setOpaque(false);
                 kingdomRadioButtons[i][j].setVisible(true);
                 
                 if(j!=kingdomRadioButtons.length-1){
                    kingdomRadioButtons[i][j+1].setVisible(false);
                    kingdomRadioButtons[i][j+1].setOpaque(false);  
                    kingdomRadioButtons[i][j+1].setVisible(true);
                }
                 
                 if(i!=kingdomRadioButtons[i].length-1){
                    kingdomRadioButtons[i+1][j].setVisible(false);
                    kingdomRadioButtons[i+1][j].setOpaque(false);  
                    kingdomRadioButtons[i+1][j].setVisible(true);
                }
            }
        }
    }

    private class ClickListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String text = e.getActionCommand(); 
            
            if(text.equals("Enter")){
                
                if(game.getPlayerThread()==null)
                    game.setPlayerThread(new Player(game));
                game.getKingdominoClientFrame().setPlayer(game.getKingdominoClientFrame().getPlayer());
                if(game.getPlayerThread().connect()){
                    game.getPlayerThread().start();
                }

            }
            else if(text.equals("Pause")){
                JOptionPane.showMessageDialog(rootPane, text);
            }
            else if(text.equals("Exit")){
                game.getPlayer().sendExit();
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
                                            "for the course Advanced Topics of Programming Languages, guided by the supervisor Professor Nikolaos Platis.",
                                            "Information Message",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
