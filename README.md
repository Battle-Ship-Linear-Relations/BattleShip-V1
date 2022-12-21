# BattleShip-V1
This is the first version of the battleship game

The idea for the game is to make a board out of JButtons and add a hover effect on the JButtons to simulate a ship being placed.
The Jbuttons will be placed in a way so that (0, 0) will be in the top left corner. To avoid this, the board needs to be odd, so I can find the middle of the board, x and y, and set that as the (0, 0) valu.
This can be made a 2 player game that switches between people, or can be a game against a computer.
Both players will play different coloured ships can can't see eachother's ships.
The game will detect if a ship is sunk by counting the number of ships after each turn, if the number of any ship equals 0, there has been a sunk.



Possible Ideas:

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
