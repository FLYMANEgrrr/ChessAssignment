# Chess Game
Consider a chess board, to which a king and a knight chess piece is placed. Initially, the king is on b3, and the knight is on c3, respectively. The goal of the game is to move the king or the knight piece to the square g1. The pieces move according to the rules of the chess, with the following extra constraint: a piece can be moved if and only if it is on a square that is under attack by the other piece.

When a new game is started the program must ask for the name of the player.

The program must store the result of the games as follows. For each game, the following information must be stored: the date and time when the game was started/finished, the name of the player, the number of moves made by the player during the game, and the outcome (the puzzle is solved/given up). The program must show a high score table in which the top 10 results are displayed. You can score the players based on the steps/time required to solve the puzzle.

The program must store data in a database, in a JSON file, or in an XML document. Optionally, you can implement load/save game functionality.
