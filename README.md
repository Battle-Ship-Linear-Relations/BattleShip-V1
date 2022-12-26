# BattleShip-V1
This is the first version of the battleship game


Design the game board:

- Decide on the size of the game board. The board should be a grid of squares, with each square representing a location on the board.
- Decide on the types of ships that will be used in the game. Each ship should have a length and a set of coordinates that define its position on the board.
- Create a data structure, JButtons, to represent the game board and the ships. This could be a two-dimensional array or a class that stores the information about the board and ships.

Implement the game logic:

- Write code to initialize the game board and place the ships on it. You may want to allow the player to choose where to place their ships, or you can randomly place them.
- Write code to handle the player's turn. This should include prompting the player for a target coordinate, checking if the target is a valid location on the board, and determining if the target is a hit or a miss.
- The Jbuttons will be placed in a way so that (0, 0) will be in the top left corner. To avoid this, the board needs to be odd, so we can find the middle of the board, x and y, and set that as the (0, 0) value.
- Both players will play different coloured ships can can't see eachother's ships.
- When a ship is placed, it will change the colour of the JButtons and will assign them a numerical value of what ship number it is in order to identify what ship is placed to know if it sunk later on.
- Write code to handle the computer's turn. This should include selecting a target coordinate and determining if it is a hit or a miss.
- Write code to check if a ship has been sunk. When all of the squares occupied by a ship have been hit, the ship is sunk.
- Write code to determine if the game is over. The game is over when all of the player's ships have been sunk or all of the computer's ships have been sunk.

Add graphics:

- Use a graphics library, such as JavaFX or Swing, to create a graphical interface for the game.
- Create visual representations of the game board and the ships, and use these to display the current state of the game to the player.
- Create buttons or other controls that the player can use to input their target coordinates and perform other actions in the game.
- Use animation and other visual effects to make the game more engaging and enjoyable to play.
- Make a board out of JButtons and add a hover effect on the JButtons to simulate a ship being placed.

Test and debug the game:

- Thoroughly test the game to ensure that all of the logic and graphics are working correctly.
- Debug any issues that you encounter, using print statements and other debugging techniques as needed.

_______________________________________________________________________________________________________________________________________________________________________

Possible Ideas for Future Versions:

- make the size of the game adjustable

- make the imput method with slopes

- make the input method with inequalities

- add a give up button

- create a better solution for the play again method


Known Bugs:


- the ship dissapears when you rotate the ship and becomes visible when you move the mouse to a different location

- the computer might be able to cross over its own ships (might be fixed, we dont know for sure)

- it does not display what coordinate the computer plays

- The Jframe is destroyed and a new JFrame is created when you play again

- The grid may not be properly alligned with the board
