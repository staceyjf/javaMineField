main menu - ENUM
1 : new game
2 : read player game results

sub menu -ENUM
1 : new game
2 : main menu

------------GAME CLASS HANDLES THE GAME FLOW----
Game class
    Player player;
    Board board; 
    Scanner scanner;

    public Game() {
    print out welcome message
    print our main menu message
    }

// addPlayer
system stuff
scanner
create new player object

//createBoard
system scanner
boardSize
initite board

//hanlemainmeu
switch

//handle submenu

//end game
close scanner


IsGameover = false
gameMessaging = " nameX are you ready to face the challenge. XYZ,..........

methods
1. one to set up and deal with the main menu and the sub menu
* could these be switch expressions
2. to start the scanner / to end the scanner
3. to set the player's name from the provided input
4. to handle the end of a game
5. check 
4. to string overwride
with message as a param - returns the print of the welcome message and ask for player name


------------BOARD CLASS TO HANDLE BOARD LOGIC AND CONTROL THE SQUARES---

Board class

/// an array of `Squares`
Square[][] boardElements;
boardSize = 10
amountOfBombs = board size

public Board(int boardSize) {
    this.boardSize = boardSize;
    this.amountOfBombs = amountOfBombs;
    this.boardElements = new Square[boardSize][boardSize]; //could be arraylist

    // need to work out a way to populate the baordElement will - array seems the easist?
}

methods
1. methods to set the board size 
2. how do i populare the 2D array with square? Nestes loops?
3. how do i randomly position the bombs (number random generator and then use the set method on the 2D array - nested loops?)
4. validateCoordinates
5. Print out player statics

------------SQUARE DEFINES WHAT THE BOARD ELEMENTS DO---
Square class
boolean hasBeenPlayed
boolean hasBomb

construcor ()
this.hasbomb = false
this.hasbeenplayed = false

1. (get) returns hasBeenPlayed
2. (get) returns hasBomb
3. (set) setHasBeenPlayed

--------PLAYER CLASS--------------
Player class ()
Name
Games lost = 0
Games won = 0

Constructor(string name)
This.name = name
)

---my getters----
GetplatyerName() {
Return name}

Getamountifgameswon {
Return gameswon}

Getamountofgameslost {
Return gameslost}

----setters
incrGameswon
incrGamesLose

Override
ToString {
Return player.name + has played: + (gameswon + gameslost) + "\n" + "won:" + Gameswon + "/n" + "lost" + gameslost


Things to work out how to do
1. Create board array from board size variable
2. Randomly set X number of bombs based on variable on list (bomb is 1)
3. ToString overwrite to print board ()
4. ToString overwrite to print board post choice (selection)
5. Check to ensure player choose a coord on the board
        Eg not null or zero
        Smaller than row or column
6. Display