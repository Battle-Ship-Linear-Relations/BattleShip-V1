import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

   /**
   * new class that implements the JFrame and creates and adds all components of the game
   */
public class gameFrame extends JFrame{ //extends JFrame so this class can just be treated as the JFrame
    ImageIcon logo = new ImageIcon(new ImageIcon("logo.png").getImage().getScaledInstance(350, 100, Image.SCALE_SMOOTH)); // scale the image to be the right size
    ImageIcon background = new ImageIcon("Background.jpg");
    ImageIcon gridImage = new ImageIcon(new ImageIcon("grid.png").getImage().getScaledInstance(550, 550, Image.SCALE_SMOOTH)); // scale the image to be the right size
    JButton play[][] = new JButton[11][11]; // size of the game
    JButton play2[][] = new JButton[play.length][play[0].length];
    JLabel logoLabel = new JLabel(logo);
    JLabel errorLabel = new JLabel("Error, please try again");
    String[] ships = {"Battleship", "Destroyer", "Submarine", "Battleship", "Destroyer", "Submarine"}; //names of all the ships that can be placed
    JLabel shipType = new JLabel("Ship: " + ships[0]); // displays the ship type
    JLabel grid = new JLabel(gridImage);
    JLabel turnJLabel = new JLabel("Your Turn");
    JLabel instructionLabel = new JLabel("Press 'r' to rotate");
    JLabel coordinatLabel = new JLabel("Coordinate:");
    JTextField coordinateField = new JTextField();
    JButton resetButton = new JButton("Play Again");
    int rotate = 0;
    int shipCount;
    int playerTurn = 0;
    Color player = Color.GREEN;
    java.util.Timer timer = new java.util.Timer();
    java.util.Timer timer2 = new java.util.Timer();
    boolean shipCheck1 = true, shipCheck2 = true, shipCheck3 = true;
    boolean p2ShipCheck1 = true, p2ShipCheck2 = true, p2ShipCheck3 = true;
    int counter1 = 0, counter2 = 0, counter3 = 0; // these count the number of ships on the board after every turn
    int p2Counter1 = 0, p2Counter2 = 0, p2Counter3 = 0;
    /**
     * parameter initializes everything in the game needed
     */
    gameFrame() { // initialize everything that is going to be on the GUI
        setVisible(true);
        setSize(1000, 750);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Battleship");
        logoLabel.setBounds(325, 0, 350, 100);
        setContentPane(new JLabel(background)); //set the background of the game
        add(logoLabel);
        addKeyListener(new KeyAdapter() { // add a key listener
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_R){ // if r is pressed, rotate the sip by incrementing the rotate variable
                    rotate++; // increase rotate by 1
                    for (int i = 0; i < play.length; i ++) {
                        for (int j = 0; j < play[0].length; j++) {
                            if(play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray); // reset the colour of the board to the ship before it was rotate is no longer visible
                        }
                    }
                }
            }
        });

        coordinateField.setBounds(650, 270, 200, 75); // Text field for the coordinate to be inputted
        coordinateField.setFont(new Font("Verdana", Font.BOLD, 40));
        coordinateField.setFocusable(false);
        coordinateField.setBackground(Color.lightGray);
        coordinateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){ // add key listener, so if enter is pressed, go to the confirm equation method
                    confirmEquation();
                }
            }
        });
        add(coordinateField);


        resetButton.setFont(new Font("Verdana", Font.BOLD, 25)); // button to play again after someone has won the game
        resetButton.setBounds(655, 570, 200, 75);
        resetButton.setForeground(Color.BLACK);
        resetButton.setVisible(false);
        resetButton.addActionListener(e -> playAgain()); // if reset button is pressed
        add(resetButton);

        grid.setBounds(30, 100, 550, 550); // the grid image in from of the buttons
        grid.setVisible(true);
        add(grid);

        errorLabel.setFont(new Font("Verdana", Font.BOLD, 30)); // the label if there is an error with something the user inputted (out of range or not peoper coordinate notation)
        errorLabel.setBounds(575, 130, 400, 150);
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        add(errorLabel);

        instructionLabel.setFont(new Font("Verdana", Font.BOLD, 30)); // instructions displaying you can rotate the ships with 'r'
        instructionLabel.setBounds(680, 25, 400, 40);
        instructionLabel.setForeground(Color.BLACK);
        add(instructionLabel);

        coordinatLabel.setFont(new Font("Verdana", Font.BOLD, 30)); // label that said Coordinate: above the input field
        coordinatLabel.setBounds(650, 230, 400, 40);
        coordinatLabel.setForeground(Color.BLACK);
        add(coordinatLabel);

        turnJLabel.setFont(new Font("Verdana", Font.BOLD, 40)); // label telling you whos turn it is
        turnJLabel.setBounds(640, 450, 300, 150);
        turnJLabel.setForeground(Color.WHITE);
        add(turnJLabel);

        shipType.setBounds(60, 650, 300, 50); // tells you what kind of ship you are placing depending on size
        shipType.setFont(new Font("Verdana", Font.BOLD, 30));
        shipType.setForeground(Color.WHITE);
        add(shipType);

        
        for (int i = 0; i < play.length; i ++) { //initializes both set of buttons (boards for the game)
            for (int j = 0; j < play[0].length; j++) {
                play[i][j] = new JButton(); // play if the array of buttons the user will be playing on
                play[i][j].setBounds(i * 46 + 50, j * 46 + 125, 46, 46); // set the location and size of the buttons
                play[i][j].setFocusable(false);
                play[i][j].setVisible(true);
                play[i][j].setFont(new Font("Verdana", Font.BOLD, 0));      
                play[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 0)); //remove the outlines of the buttons        
                buttonPressed(i, j); // initialize what happens when the button is pressed
                play[i][j].setBackground(Color.lightGray);
                play[i][j].setText("");
                buttonSet(i, j);
                add(play[i][j]);  //add the user's buttons to the game 


                play2[i][j] = new JButton(); //play2 is the array of buttons the comupter will be playing on
                play2[i][j].setBounds(i * 46 + 50, j * 46 + 125, 46, 46); // set the location and size of the buttons
                play2[i][j].setFocusable(false);
                play2[i][j].setVisible(true);
                play2[i][j].setFont(new Font("Verdana", Font.BOLD, 0));               
                play2[i][j].setBackground(Color.lightGray);
                play2[i][j].setText("");
                play2[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 0)); //remove the outlines of the buttons  
                play2[i][j].setVisible(false); // the compiuter's game should not be visible to the user
                add(play2[i][j]);   //add the computer's buttons to the game 
            }
        }
        
        
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                repaint(); // refresh the JFrame every millisecond, this removes lag from the user input
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1);
    }

    /**
     * if the play again button is pressed, create a new game
     * @return void
     */
    public void playAgain() { // if play again is pressed, detroy the current JFrame and create a new one.
        dispose(); // destroy the current JFrame
        new gameFrame(); // create a new JFrame
    }

    /**
     * set what happens to the buttons when the mouse hovers over them, and exits
     * @param int i, the x vakue of the button
     * @param int j, the y value of the button
     * @return void
     */
    public void buttonSet(int i, int j) { // set the action for when the mouse hovers and exits over the JButtons
        
        play[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
            /**
             * if the mouse hovers over a button
             * @param MouseEvent evt
             * @return void
             */
            public void mouseEntered(java.awt.event.MouseEvent evt) { 
                if (shipCount != 3) { // while the ship count is not equal to 3 (it is the user's turn), if not do nothing on hover afterwards
                    if (shipCount == 0) ShipLength.shipLength4(play, rotate, player, i, j); // when on hover, depending on the length of the ship being placed, change the colour of the JButtons to represent a ship
                    if (shipCount == 1) ShipLength.shipLength3(play, rotate, player, i, j);
                    if (shipCount == 2) ShipLength.shipLength2(play, rotate, player, i, j);
                }
            }
        
            /**
             * if the mouse is no longer hovering over the JButton, reset its colour, this will depend on the length of the ship, what is the orientation of the ship, and the location of the ship
             * @param MouseEvent evt
             * @return void
             */
            public void mouseExited(java.awt.event.MouseEvent evt) { 
                try {
                    if (shipCount == 0) { //if the shipCount is 0, the length of the ship is 4
                        if (rotate % 2 == 0) { // if the ship is placed vertically
                            if (j < play.length - 3) { //if the ship is within the range of the game
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i][j + 1].getText().equals("")) play[i][j + 1].setBackground(Color.lightGray);
                                if (play[i][j + 2].getText().equals("")) play[i][j + 2].setBackground(Color.lightGray);
                                if (play[i][j + 3].getText().equals("")) play[i][j + 3].setBackground(Color.lightGray);
                            }
                            if (j == play.length - 3) { // if the user is trying to place a ship outside of the game, adjust it so it moves inside (applies to if statements below)
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i][j + 1].getText().equals("")) play[i][j + 1].setBackground(Color.lightGray);
                                if (play[i][j - 1].getText().equals("")) play[i][j - 1].setBackground(Color.lightGray);
                                if (play[i][j + 2].getText().equals("")) play[i][j + 2].setBackground(Color.lightGray);
                            }
                            if (j == play.length - 2) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i][j + 1].getText().equals("")) play[i][j + 1].setBackground(Color.lightGray);
                                if (play[i][j - 1].getText().equals("")) play[i][j - 1].setBackground(Color.lightGray);
                                if (play[i][j - 2].getText().equals("")) play[i][j - 2].setBackground(Color.lightGray);
                            }
                            if (j == play.length - 1) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i][j - 1].getText().equals("")) play[i][j-  1].setBackground(Color.lightGray);
                                if (play[i][j - 2].getText().equals("")) play[i][j - 2].setBackground(Color.lightGray);
                                if (play[i][j - 3].getText().equals("")) play[i][j - 3].setBackground(Color.lightGray);
                            }
                        }
                        if (rotate % 2 == 1) { // if the ship is places horizontally
                            if (i < play.length - 3) { // previous commets for this method apply below depending on the size of the ship being placed
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i + 1][j].getText().equals("")) play[i + 1][j].setBackground(Color.lightGray);
                                if (play[i + 2][j].getText().equals("")) play[i + 2][j].setBackground(Color.lightGray);
                                if (play[i + 3][j].getText().equals("")) play[i + 3][j].setBackground(Color.lightGray);
                            }
                            if (i == play.length - 3) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i + 1][j].getText().equals("")) play[i + 1][j].setBackground(Color.lightGray);
                                if (play[i - 1][j].getText().equals("")) play[i - 1][j].setBackground(Color.lightGray);
                                if (play[i + 2][j].getText().equals("")) play[i + 2][j].setBackground(Color.lightGray);
                            }
                            if (i == play.length - 2) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i + 1][j].getText().equals("")) play[i + 1][j].setBackground(Color.lightGray);
                                if (play[i - 1][j].getText().equals("")) play[i - 1][j].setBackground(Color.lightGray);
                                if (play[i - 2][j].getText().equals("")) play[i - 2][j].setBackground(Color.lightGray);
                            }
                            if (i == play.length - 1) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i - 1][j].getText().equals("")) play[i - 1][j].setBackground(Color.lightGray);
                                if (play[i - 2][j].getText().equals("")) play[i - 2][j].setBackground(Color.lightGray);
                                if (play[i - 3][j].getText().equals("")) play[i - 3][j].setBackground(Color.lightGray);
                            }
                        }
                    }


                    if (shipCount == 1) { // if the shipCount is 1, the length of the ship if 3
                        if (rotate % 2 == 0) {
                            if (j < play.length - 2) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i][j + 1].getText().equals("")) play[i][j + 1].setBackground(Color.lightGray);
                                if (play[i][j + 2].getText().equals("")) play[i][j + 2].setBackground(Color.lightGray);
                            }
                            if (j == play.length - 2) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i][j + 1].getText().equals("")) play[i][j + 1].setBackground(Color.lightGray);
                                if (play[i][j - 1].getText().equals("")) play[i][j - 1].setBackground(Color.lightGray);
                            }
                            if (j == play.length - 1) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i][j - 1].getText().equals("")) play[i][j-  1].setBackground(Color.lightGray);
                                if (play[i][j - 2].getText().equals("")) play[i][j - 2].setBackground(Color.lightGray);
                            }
                        }
                        if (rotate % 2 == 1) { // if the ship is places horizontally
                            if (i < play.length - 2) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i + 1][j].getText().equals("")) play[i + 1][j].setBackground(Color.lightGray);
                                if (play[i + 2][j].getText().equals("")) play[i + 2][j].setBackground(Color.lightGray);
                            }
                            if (i == play.length - 2) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i + 1][j].getText().equals("")) play[i + 1][j].setBackground(Color.lightGray);
                                if (play[i - 1][j].getText().equals("")) play[i - 1][j].setBackground(Color.lightGray);
                            }
                            if (i == play.length - 1) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i - 1][j].getText().equals("")) play[i - 1][j].setBackground(Color.lightGray);
                                if (play[i - 2][j].getText().equals("")) play[i - 2][j].setBackground(Color.lightGray);
                            }
                        }
                    }


                    if (shipCount == 2) { //  if the shipCount is 2, the length of the ship is 2
                        if (rotate % 2 == 0) { // if the ship is places vertically
                            if (j < play.length - 1) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i][j + 1].getText().equals("")) play[i][j + 1].setBackground(Color.lightGray);
                            }
                            if (j == play.length - 1) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i][j - 1].getText().equals("")) play[i][j - 1].setBackground(Color.lightGray);
                            }
                        }
                        if (rotate % 2 == 1) { // if the ship is places horizontally
                            if (i < play.length - 1) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i + 1][j].getText().equals("")) play[i + 1][j].setBackground(Color.lightGray);
                            }
                            if (i == play.length - 1) {
                                if (play[i][j].getText().equals("")) play[i][j].setBackground(Color.lightGray);
                                if (play[i - 1][j].getText().equals("")) play[i - 1][j].setBackground(Color.lightGray);
                            }
                        }
                    }



                    if (shipCount != 3) { // when the user has placed 3 ships, do nothing over hover, and whatever the button has been assigned set their fixed colour that can no longer change
                        for (int i = 0; i < play.length; i ++) {
                            for (int j = 0; j < play[0].length; j++) {
                                if(play[i][j].getText().equals("0") || play[i][j].getText().equals("1") || play[i][j].getText().equals("2")) {
                                    play[i][j].setBackground(Color.GREEN);
                                } 
                            }
                        }
                    }
                } 
                catch (ArrayIndexOutOfBoundsException e){} // if it tries to place a ship outside the game board area
                catch (NullPointerException e){}
            }
        });
    }

    /**
     * when the computer selects a location for thier ship
     * @param int i, the x value of the button
     * @param int j, the y value of the button
     * @return void
     */
    public void buttonPressed2(int i, int j) { // if the robot is confirming its ships
                    
        if (shipCount == 3) {
            if (ShipLength.shipLength4(play2, rotate, player, i, j)) {
                ShipLength.confirmShipLength4(play2, rotate, player, i, j, shipCount); // place a ship of the computer's board and if successful, increase the shipcount, so the next length of ship will be placed
                shipCount++; // increase shipCount if the ship is placed without any errors
            }
        }
        else if (shipCount == 4) {
            if (ShipLength.shipLength3(play2, rotate, player, i, j)) {
                ShipLength.confirmShipLength3(play2, rotate, player, i, j, shipCount);
                shipCount++;
            }
        }
        else if (shipCount == 5) {
            if (ShipLength.shipLength2(play2, rotate, player, i, j)) {
                ShipLength.confirmShipLength2(play2, rotate, player, i, j, shipCount);
                shipCount++;
            }
        }
        
        for (int a = 0; a < play2.length; a++) { // after every placement make sure there are only 3 ships and no errors with the placement
            for (int b = 0; b < play2.length; b++) {
                if (play2[a][b].getText().equals("")) {
                    play2[a][b].setBackground(Color.lightGray); // this avoids there being multiple of the same ship if there is an error with its placement
                }
                else {
                    play2[a][b].setBackground(Color.BLUE);
                }
            }
        }

        try {
            shipType.setText("Ship: " + ships[shipCount]); // display the type of ship being placed based on length
        } catch (ArrayIndexOutOfBoundsException e) {
            coordinateField.setBackground(Color.WHITE);
            coordinateField.setFocusable(true);
            shipType.setVisible(false);
            for (int q = 0; q < play2.length; q++) {
                for (int w = 0; w < play2[0].length; w++) {
                    play2[q][w].setBackground(Color.lightGray); // when all of the computer's shipd have been placed, reset their colour so the user can't see them and change boards with the user
                    play[q][w].setVisible(false);
                    play2[q][w].setVisible(true);
                }
            }
            repaint();
            turnJLabel.setText("Your Turn"); // indicate its the user's turn
        }
        
    }


    /**
     * if the user is selecting the location of thier ships (button is pressed)
     * @param int i, the x value of the button
     * @param int j, the y value of the button
     * @return void
     */
    public void buttonPressed(int i, int j) { // if the user if confirming a ship
        play[i][j].addActionListener(
        new ActionListener() {
            /**
             * action listener if a button is pressed
             * @return void
             */
            public void actionPerformed(ActionEvent e) {

                if (shipCount == 0 && ShipLength.shipLength4(play, rotate, player, i, j)) {
                    ShipLength.confirmShipLength4(play, rotate, player, i, j, shipCount); // place a ship of the user's board and if successful, increase the shipcount, so the next length of ship will be placed
                    shipCount++;
                }
                else if (shipCount == 1 && ShipLength.shipLength3(play, rotate, player, i, j)) {
                    ShipLength.confirmShipLength3(play, rotate, player, i, j, shipCount);
                    shipCount++;
                }

                else if (shipCount == 2 && ShipLength.shipLength2(play, rotate, player, i, j)) {
                    ShipLength.confirmShipLength2(play, rotate, player, i, j, shipCount);
                    shipCount++;
                }

                shipType.setText("Ship: " + ships[shipCount]); // display the type of ship being placed based on length

                if (shipCount == 3) {
                    player = Color.BLUE; // when shipCount is equal to 3 (the user has placed all thier ships), set player to Colour Blue
                }
                if (shipCount == 3) {
                    repaint();
                    playerTurn++;
                    turnJLabel.setText("<html>Computer's<br/>Turn</html>"); // once the user has placed all of their ships, indicate its the coputer's turn and allow the bot to play its ship
                    instructionLabel.setVisible(false);
                    botTurn(); // tell the computer to place its ships
                }
            }
        });
    }

    /**
     * when it is the robot's turn to place ships, it will select random coordinates
     * @return void
     */
    public void botTurn() { 
 
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                rotate += (int)Math.floor(Math.random()*(10));
                int xCoord = (int)Math.floor(Math.random()*(play2.length));
                int yCoord = (int)Math.floor(Math.random()*(play2.length)); // select a random location for the bot's ships
                buttonPressed2(xCoord, yCoord);
                if (shipCount == 6) timer2.cancel();
            }
        };
        timer2.scheduleAtFixedRate(task2, 3000, 3000); // set a timer, so the placement seems realitic as if a person is placing the sips
        for (int i = 0; i < play2.length; i++) {
            for (int j = 0; j < play2[0].length; j++) {
                play2[i][j].setVisible(false); // set the computers board to no visible
                add(play2[i][j]);
            }
        }
        playerTurn++; // increase player turn 
    }

    /**
     * when the user is inputting a coordinate, obtain the value the user inputted and apply it to the game 
     * @return void
     */
    public void confirmEquation() { // when enter is pressed on the coordinate field
        try {
            errorLabel.setVisible(false);
            errorLabel.setText("Error, please try again");
            String parts[] = String.valueOf(coordinateField.getText()).split(","); // split the contents of the coordinate and 
            int xCoord = Integer.valueOf(parts[0].trim().substring(1).trim());
            int yCoord = Integer.valueOf(parts[1].trim().substring(0, parts[1].trim().length() - 1).trim()); // set the variables to the x and y values inputted
            if (xCoord > 5 || xCoord < -5 || yCoord > 5 || yCoord < -5) { // if the coordinates are out of the range
                coordinateField.setText("");
                errorLabel.setVisible(true);
                errorLabel.setFont(new Font("Verdana", Font.BOLD, 30));
                errorLabel.setText("Error, please try again"); //show an error and do not apply to the game
            }
            else {
                coordinateField.setText("");
                if (guessSpot(xCoord, yCoord) == 1) { // if the coordinate is valid 
                    coordinateField.setBackground(Color.lightGray);
                    coordinateField.setFocusable(false);
                    playerTurn++;
                    robotTurn();
                }
                else if(guessSpot(xCoord, yCoord) == 2){ // if the coordinate is valid and you win the game
                    coordinateField.setBackground(Color.lightGray);
                    coordinateField.setFocusable(false);
                }
                else { // if you have laready guessed that previously
                    errorLabel.setVisible(true);
                    errorLabel.setFont(new Font("Verdana", Font.BOLD, 25));
                    errorLabel.setText("You already guessed that");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) { // if there is any of the following errors (not proper notation, not numerical values, out of bounds of the game)
            coordinateField.setText("");
            errorLabel.setVisible(true);
            errorLabel.setFont(new Font("Verdana", Font.BOLD, 30));
            errorLabel.setText("Error, please try again");
        }
        catch (StringIndexOutOfBoundsException e) {
            coordinateField.setText("");
            errorLabel.setVisible(true);
            errorLabel.setFont(new Font("Verdana", Font.BOLD, 30));
            errorLabel.setText("Error, please try again");
        }
        catch (NumberFormatException e) {
            coordinateField.setText("");
            errorLabel.setVisible(true);
            errorLabel.setFont(new Font("Verdana", Font.BOLD, 30));
            errorLabel.setText("Error, please try again");
        }
    }

    /**
     * when eiither player is trying to guess a spot on the game
     * @param int x, the x coordinate passed
     * @param int y, the y coordinate passed
     * @return void
     */
    public int guessSpot(int x, int y) {
        java.util.Timer timer4 = new java.util.Timer();
        int middle = play.length/2; // middle of the game (0, 0)
        y = -y; // y value will be inverted because of how the JButtons are arranged
        if (playerTurn % 2 == 0) { // if it is the user's turn
            if (!play2[middle + x][middle + y].getText().equals("X")) { // if the guessed coordinate isnt already guessed
                if (!play2[middle + x][middle + y].getText().equals(""))  { // if there is a ship, mark a red X
                    play2[middle + x][middle + y].setForeground(Color.RED);
                    play2[middle + x][middle + y].setText("X");
                    play2[middle + x][middle + y].setFont(new Font("Verdana", Font.BOLD, 50));
                }
                else { // if there is no ship, mark a black X
                    play2[middle + x][middle + y].setForeground(Color.BLACK);
                    play2[middle + x][middle + y].setText("X");
                    play2[middle + x][middle + y].setFont(new Font("Verdana", Font.BOLD, 50));
                }
                //Counting the number of ships after every turn to determine if there is a sink and if you win
                if (shipCheck1) counter3 = 0; // only reset the value if the ship has not already sunk
                if (shipCheck2) counter2 = 0;
                if (shipCheck3) counter1 = 0;
                for (int i = 0 ; i < play2.length; i++) {
                    for (int j = 0; j < play2.length; j++) {
                        if (shipCheck1 && play2[i][j].getText().equals("3")) counter3++; // count the number of each boat for the user
                        if (shipCheck2 && play2[i][j].getText().equals("4")) counter2++;
                        if (shipCheck3 && play2[i][j].getText().equals("5")) counter1++;
                    }
                }
                if (counter1 == 0 || counter2 == 0 || counter3 == 0) {
                    shipType.setVisible(true);
                    shipType.setText("SINK!");
                    if (counter1 == 0) { // if any boats sink, set them to 1 and don't reset their value to avoid saying sink after every turn
                        shipCheck3 = false;
                        counter1 = 1;
                    }
                    if (counter2 == 0) {
                        shipCheck2 = false;
                        counter2 = 1;
                    }
                    if (counter3 == 0) {
                        shipCheck1 = false;
                        counter3 = 1;
                    }
                    TimerTask task4 = new TimerTask() { // timer to show sink text and remove after 2 seconds
                        @Override
                        public void run() {
                            shipType.setVisible(false);
                            timer4.cancel();
                        }
                    };
                    timer4.scheduleAtFixedRate(task4, 2000, 1000); // create a timer with a delay of 2 seconds that only runs once
                }
                if (!shipCheck1 && !shipCheck2 && !shipCheck3) { // if all ships are sunk, return 2 indicating a win
                    turnJLabel.setText("You Win!");
                    turnJLabel.setVisible(true);
                    resetButton.setVisible(true);
                    return 2;
                }
                return 1; // if everything is valid and is not a win, return 1
            }
            else {
                return 0; // if there is an error, if it has already been guessed, return 0
            }
        }
        else { // if it is the computers turn 
            if (!play[middle + x][middle + y].getText().equals("X")) { // if the guessed coordinate isnt already guessed
                if (!play[middle + x][middle + y].getText().equals(""))  { // if there is a ship, mark a red X
                    play[middle + x][middle + y].setForeground(Color.RED);
                    play[middle + x][middle + y].setText("X");
                    play[middle + x][middle + y].setFont(new Font("Verdana", Font.BOLD, 50));
                }
                else { // if there is no ship, mark a black X
                    play[middle + x][middle + y].setForeground(Color.BLACK);
                    play[middle + x][middle + y].setText("X");
                    play[middle + x][middle + y].setFont(new Font("Verdana", Font.BOLD, 50));
                }
                //Counting the number of ships after every turn to determine if there is a sink and if you win
                if (p2ShipCheck1) p2Counter3 = 0; // only reset the value if the ship has not already sunk
                if (p2ShipCheck2) p2Counter2 = 0;
                if (p2ShipCheck3) p2Counter1 = 0;
                for (int i = 0 ; i < play.length; i++) {
                    for (int j = 0; j < play.length; j++) {
                        if (p2ShipCheck1 && play[i][j].getText().equals("0")) p2Counter3++;  // count the number of each boat for the computer
                        if (p2ShipCheck2 && play[i][j].getText().equals("2")) p2Counter2++;
                        if (p2ShipCheck3 && play[i][j].getText().equals("1")) p2Counter1++;
                    }
                }
                if (p2Counter1 == 0 || p2Counter2 == 0 || p2Counter3 == 0) {
                    shipType.setVisible(true);
                    shipType.setText("SINK!");
                    if (p2Counter1 == 0) { // if any  of the computer's boats sink, set them to 1 and don't reset their value to avoid saying sink after every turn
                        p2ShipCheck3 = false;
                        p2Counter1 = 1;
                    }
                    if (p2Counter2 == 0) {
                        p2ShipCheck2 = false;
                        p2Counter2 = 1;
                    }
                    if (p2Counter3 == 0) {
                        p2ShipCheck1 = false;
                        p2Counter3 = 1;
                    }
                    TimerTask task4 = new TimerTask() { // timer to show sink text and remove after 2 seconds
                        @Override
                        public void run() {
                            shipType.setVisible(false);
                            timer4.cancel();
                        }
                    };
                    timer4.scheduleAtFixedRate(task4, 2000, 1000); // create a timer with a delay of 2 seconds that only runs once
                }
                if (!p2ShipCheck1 && !p2ShipCheck2 && !p2ShipCheck3) { // if all ships are sunk, return 2 indicating a win for the computer
                    turnJLabel.setText("Computer Wins!");
                    turnJLabel.setVisible(true);
                    resetButton.setVisible(true);
                    return 2;
                }
                return 1; // if everything is valid and is not a win, return 1
            }
            else {
                return 0; // if there is an error, if it has already been guessed, return 0
            }
        }
    }

    /**
     * the computer selects random spots to guess on the user's ships
     * @return void
     */
    public void robotTurn() { // the computer's turn
        
    java.util.Timer timer3 = new java.util.Timer();
        TimerTask task3 = new TimerTask() { // starts a timer to immatate the time a person would take 
            int count = 0;
            @Override
            public void run() {
                if (count == 0) { // first iteration of the timer
                    for (int i = 0; i < play.length; i++) {
                        for (int j = 0; j < play[0].length; j++) {
                            play[i][j].setVisible(true);
                            play2[i][j].setVisible(false); // set the board to the player's board for the computer to guess
                        }
                    }
                    turnJLabel.setText("<html>Computer's<br/>Turn</html>"); // indicate its the computer's turn
                    count++; // increment counter
                }
                else if (count == 1) { // second interation
                    int xCoord = (int)Math.floor(Math.random()*((play.length/2)-(-play.length/2)+1)+(-play.length/2)); // create random numbers within the range of the game
                    int yCoord = (int)Math.floor(Math.random()*((play.length/2)-(-play.length/2)+1)+(-play.length/2));
                    if (guessSpot(xCoord, yCoord) == 1) count++; // if the coordiate is valid increment the counter
                    else if(guessSpot(xCoord, yCoord) == 2){ // if the computer wins the game
                        coordinateField.setBackground(Color.lightGray);// dont allow any more inputs
                        coordinateField.setFocusable(false);
                        timer3.cancel(); // stop the timer
                    }
                }
                else if (count == 2) { // last iteration
                    for (int i = 0; i < play.length; i++) {
                        for (int j = 0; j < play[0].length; j++) {
                            play[i][j].setVisible(false);
                            play2[i][j].setVisible(true); // set the board to the computer's board for the user to guess on
                        }
                    }
                    playerTurn++; // increment playerTurn, so its the user's turn
                    turnJLabel.setText("Your Turn");
                    repaint();
                    coordinateField.setBackground(Color.WHITE);
                    coordinateField.setFocusable(true); // set the text field to focusable
                    timer3.cancel();
                }
            }
        };
        timer3.scheduleAtFixedRate(task3, 4000, 3000); // create a timer with a delay of 4 seconds, and will run every 3 seconds
    }
}
