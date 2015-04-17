/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.gui;

import engine.gui.overlays.*;
import engine.physics.Coordinate;
import engine.world.LevelManager;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author WHS-D4B1W8
 */
public class GUI extends Applet implements KeyListener, MouseListener, MouseMotionListener{
    //Code for the Text Box at the bottom
    /////////////////////////
    // The text field in which the user enters a string.
    private TextField inputField;
    //The GUI object
    private String lastInput = "";
    //////////////////////////
    
    private GUIOverlay overlay = new GUIOverlay();
    
    //////////////////////////
    
    
    private static GUI gui = new GUI(1200,900);
    
    //The controller
    private static Controller controller;
    
    //Pixels per radian
    private double PPR;
    
    //Applet stuff
    private Graphics graphics;
    private JFrame frame;
    
    //The camera
    private Camera camera = new Camera(new Coordinate(0,100,0),Math.toRadians(0),Math.toRadians(-90));
    
    ////////////////////////////////////////////////////
    
    private GUI(){
        this(800,600);
    }
    
    private GUI(int width, int height){
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        
        PPR = Math.max(width,height)/2.0;
        int[] keys = {
                  KeyEvent.VK_UP            //Camera Up
                , KeyEvent.VK_DOWN          //Camera Down
                , KeyEvent.VK_LEFT          //Camera Left
                , KeyEvent.VK_RIGHT         //Camera Right
                , KeyEvent.VK_SHIFT         //Allow Multi-Unit Selection
                , KeyEvent.VK_CONTROL       //Hold with A to select all units
                , KeyEvent.VK_A             //Hold to choose attack location when right-clicking
                , KeyEvent.VK_B             //Hold to choose boarding target when right-clicking
        };
        
        
        /*
        
        Key/Mouse Combinations
        ======================
        Ctrl+A ..... Select all friendly units
        Left Click ..... Select a unit
        Right Click ..... Move selected units to selected position or unit
        Right Click+A ..... Attack selected position or unit
        Shift+Ctrl+A ..... Select all units
        Shift+Left Click ..... Select units without deselecting units
        
        
        */
        
        controller = new Controller("Controller", keys);
        
        //Creates a JFrame with a title
        frame = new JFrame("Battlefield Commander");
        //Puts the Tester object into thhe JFrame
	frame.add(this);
        //Sets the size of the applet to be 800 pixels wide  by 600 pixels high
	frame.setSize(width, height);
        //Makes the applet visible
	frame.setVisible(true);
        //Sets the applet so that it can't be resized
        frame.setResizable(false);
        //This will make the program close when the red X in the top right is
        //clicked on
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        overlay = GUIOverlayBuilder.getGameOverlay(width,height);
        
        //Changes the Layout
        setLayout(new BorderLayout());
        //Creates a panel at the bottom
        Panel bottomPanel = new Panel();
        //Adds a label for the panel and then adds it.
        Label nameLabel = new Label ("Command: ");
        bottomPanel.add (nameLabel);
        
        
        //////////////////////////
        //This code creates a String input prompt at the bottom of the Applet.
        /////////////////////////
        
        //Creates a text box that is 30 units wide and then adds it
        inputField = new TextField (30);
        bottomPanel.add (inputField);
        
        //Creates a button with text
        Button nameEnteredButton = new Button ("Execute");
        
        nameEnteredButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        
        //Adds the button to the panel
        bottomPanel.add(nameEnteredButton);
        
        //Makes the button work by giving it an ActionListener of its own.
        nameEnteredButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                parseUserInput(inputField.getText(),0 + "");
                inputField.setText("");
            }
        });
        
        //Puts the panel on the bottom
        add(bottomPanel, BorderLayout.SOUTH);
        
        /////////////////////////
        /////////////////////////
        
    }
    
    public void redraw(){
        repaint();
    }

    public void update(Graphics g){
        Image image = null;
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            graphics = image.getGraphics();
        }
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,  0,  this.getWidth(),  this.getHeight());
        graphics.setColor(getForeground());
        paint(graphics);
        g.drawImage(image, 0, 0, this);
        
        cycleController();
        
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        WorldDrawer.drawWorld(g2, camera);
        g2.setColor(Color.BLACK);
        
        //Center of screen crosshair
        g2.drawLine(getCenterX(), getCenterY() - 15, getCenterX(), getCenterY() + 15);
        g2.drawLine(getCenterX() - 15,getCenterY(),getCenterX() + 15,getCenterY());
        
        //Mouse crosshair
        try{
            int x = controller.getMouseX(), y = controller.getMouseY();
            g2.drawLine(x, y - 15, x, y + 15);
            g2.drawLine(x - 15, y, x + 15, y);
        } catch(NullPointerException e){
            
        }
        
        for(int i = 0; i < LevelManager.getEvents().size(); i++)
            g2.drawString(LevelManager.getEvents().get(LevelManager.getEvents().size() - i - 1), 15, 15*(i+1));
        
        overlay.draw(g2);
        
    }
    
    public void setPixelsPerRadian(double ppr){ PPR = ppr; }
    
    public void setDegreesPerRadian(double dpr){
        setPixelsPerRadian(dpr*180.0/Math.PI);
    }
    
    public double getPixelsPerDegree(){
        return PPR * Math.PI/180.0;
    }
    
    public double getPixelsPerRadian() { return PPR; }
    public static GUI getGUI(){ return gui; }
    
    public static void initialize(){
        gui = new GUI(800,600);
    }
    
    public static void initialize(int width, int height){
        gui = new GUI(width, height);
    }
    
    public Camera getCamera(){ return camera; }
    
    
    //-----------------------
    //Controller
    //-----------------------
    
    public void setController(Controller c) { controller = c; }
    
    
    
    public Controller getController(){ return controller; }
    
    /**
     * This method runs a pre-built controller, and can be replaced later.
     */
    public void cycleController(){
        try {
            controller.execute();
        } catch(NullPointerException npe){
            
        }
        
    }
    
    //-----------------------
    //Keyboard and Mouse
    //-----------------------
    
    
    public void parseUserInput(String input, String factID){
        lastInput = input.toLowerCase();
        
        if(false)
            System.out.println("[" + factID + "] " + input);
        
        /*
        Possible Commands:
        >ASCEND
        >ATTACK
        >DEFEND
        >DESCEND
        >FOLLOW
        >HOLD POSITION
        >HOLD FIRE
        >MOVE TO
        >OPEN FIRE
        >ORBIT
        STAND DOWN
        STOP
        */
        
        if(lastInput.contains(" ascend")){
            String name = lastInput.substring(0,lastInput.indexOf(" ascend"));
            LevelManager.getLevel().getUnit(name).giveOrder("ascend",factID);
            
        } else if(lastInput.contains(" attack ")){
            String name = lastInput.substring(0,lastInput.indexOf(" attack "));
            LevelManager.getLevel().getUnit(name).giveOrder(lastInput.substring(lastInput.indexOf("attack ")),factID);
            
        } else if(lastInput.contains(" board ")){
            String name = lastInput.substring(0,lastInput.indexOf(" board "));
            LevelManager.getLevel().getUnit(name).giveOrder(lastInput.substring(lastInput.indexOf("board ")),factID);
            
        } else if(lastInput.contains(" defend ")){
            String name = lastInput.substring(0,lastInput.indexOf(" defend "));
            LevelManager.getLevel().getUnit(name).giveOrder(lastInput.substring(lastInput.indexOf("defend ")),factID);
            
        } else if(lastInput.contains(" descend")){
            String name = lastInput.substring(0,lastInput.indexOf(" descend"));
            LevelManager.getLevel().getUnit(name).giveOrder("descend",factID);
            
        } else if(lastInput.contains(" follow ")){
            String name = lastInput.substring(0,lastInput.indexOf(" follow "));
            LevelManager.getLevel().getUnit(name).giveOrder(lastInput.substring(lastInput.indexOf("follow ")),factID);
            
        } else if(lastInput.contains(" hold position")){
            String name = lastInput.substring(0,lastInput.indexOf(" hold position"));
            LevelManager.getLevel().getUnit(name).giveOrder("hold position",factID);
            
        } else if(lastInput.contains(" hold fire")){
            String name = lastInput.substring(0,lastInput.indexOf(" hold fire"));
            LevelManager.getLevel().getUnit(name).giveOrder("hold fire",factID);
            
        } else if(lastInput.contains(" move to ")){
            String name = lastInput.substring(0,lastInput.indexOf(" move to "));
            LevelManager.getLevel().getUnit(name).giveOrder(lastInput.substring(lastInput.indexOf("move to ")),factID);
            
        } else if(lastInput.contains(" open fire")){
            String name = lastInput.substring(0,lastInput.indexOf(" open fire"));
            LevelManager.getLevel().getUnit(name).giveOrder("open fire",factID);
            
        } else if(lastInput.contains(" orbit ")){
            String name = lastInput.substring(0,lastInput.indexOf(" orbit "));
            LevelManager.getLevel().getUnit(name).giveOrder(lastInput.substring(lastInput.indexOf("orbit ")),factID);
            
        } else if(lastInput.contains(" stop")){
            String name = lastInput.substring(0,lastInput.indexOf(" stop"));
            LevelManager.getLevel().getUnit(name).giveOrder("stop",factID);
            
        } else if(lastInput.contains(" unload ")){
            String name = lastInput.substring(0,lastInput.indexOf(" unload "));
            LevelManager.getLevel().getUnit(name).giveOrder(lastInput.substring(lastInput.indexOf("unload ")),factID);
            
        }
        
    }
    

    public int getCenterX() {
        return frame.getWidth()/2;
    }

    public int getCenterY() {
        return frame.getHeight()/2;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        controller.setKeyboardState(ke.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        controller.setKeyboardState(ke.getKeyCode(), false);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        gui.requestFocus();
        if(!overlay.getButtonOverlay().click(me.getX(), me.getY())){
            if(SwingUtilities.isLeftMouseButton(me))
                controller.executeLeftClick(me);
            else if(SwingUtilities.isRightMouseButton(me))
                controller.executeRightClick(me);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        gui.requestFocus();
        controller.setMouseState(true);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        controller.setMouseState(false);
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        
    }

    
    
}
