# ChessGame
A basic chess game developed based on java

## Features:
* Basic game logic
  * Perform most of the pieces' movements such as "promotion" and "En passant" (although "castling" and other non-intuitive movements are not included).
  * Prompt possible moves and notify if the player is checked.
  * Terminate the game when there is stalemate or checkmate.
* Simple logging of players' actions
* Operations to manipulate the game such as undo and restart
* Custom game with two custom pieces
  * Apprentice: A class that inherits Pawn's logic, with additional ability to perform "En passant" on every turn. It can only promote to wizard class.
  * Wizard: An advanced class that only apprentice can promote to. A wizard can move in Rook's logic and Bishop's alternately with first move in Bishop's logic.
  
## Next Steps (TODOs)
* Basic game logic
  * Implement all the pieces' movements.
  * Implement all the other conditions that should terminate the game (in a tie).
* Game operations
  * Add "redo" as complement of "undo".
  * Add "export" / "import" functionalities to save / load the game to / from a file (probably in JSON).
* Typo corrections
