------INITALIZE----

1.  
Initialize a game
- open scanner
- display welcome message
- ask for player name
- display menu options
2. Initialize a board
- Ask for board size
- Create 2D array based on size
- set initial state of each cell in the board

------START A GAME-----
3. Player selects new game from the menu
4. Print the board on screen
5. Display a message to the player to start the game

------- VISUAL EXAMPLE-----

======== welcome to Javafield =======

Please enter your player name:

Menu
1. Play new game
2. Display game result table
3. New player

7.  Player picks 1 (Switch)

8. The following is displayed  on the screen
nameX are you ready to face the challenge. XYZ,..........

-enter board size (extension)

    a b c d e
    --------
1 |x|x|x|x|x|
2 |x|x|x|x|x|
3 |x|x|x|x|x|
4 |x|x|x|x|x|
5 |x|x|x|x|x|
    --------

Enter your first co-ordindate:  a2

To note
* Row: char to cast to int (outer)
* Column: int (inner)

----- Game Loop--------
9. Ask for player's coordinates
10. Validate the coordinates (check if they are within the board boundaries and the cell hasn't been interacted with before)
- players coordinates are checked to see if
Rows are  the indexes of the elements within the container array
Columns are indexes of elements within the inner array
- check that the cell hasn't been picked before

userCoords = user input
ColumnCoords = Interger userCoords.get(0).lowercase()
RowCoords = Integer userCoords.get (1)
(Can you impliedly cast from String to Interfere)

preform checks
Is row or colum not zero and smaller than board size

Let boardCoordsfromUserChoice =
    Get the right inner array
    Board array.get(rowCoors)
    Get the right element in that inner array
    Board array.get(rowCoors).get(columnCoors)

    let bombsAroundCoordiate - NOT SURE HOW TO SEE if the 8 surrounding cells have bombs
    // need to also ensure that you can't go out of the bounds of the board

11. Update the board based on the player's move
boardCoordsfromUserChoice.isB
If true isGameOver = true

boardCoordsfromUserChoice.isB is false
    movesrequiredtowin--
    check to see how many nearby bombs (not sure how to do this)
    gameMessaging = "There are x number of bombs in the vincinity"

    Check if movesrequiredtowin is zero
    If bigger than
        Ask player for next cordindate
    If movesrequiredtowin is zero
        isGameOver is true
        gameMessaging = "congratulations you have outsmarted is and won the game"
        Games won++
        Write to file with updated player stats
        Print submenu
        - 1. Play new game
        - 2. Return to main menu

12. Check game state
13. If the game is not over, repeat the game loop
14.  If the game is over, display the game result

boardCoordsfromUserChoice.isB is true isGameOver is true,
gameMessaging = "sorry game over"
Games lost++
Write to file with updated player stats
Print submenu
- 1. Play new game
- 2. Return to main menu







b









 
