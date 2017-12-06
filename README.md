# Tordenskiold
Java UCI chess engine 

Currently there is 2 ways to play with the engine. Using UCI and using a custom debug mode. 
Use the UCI protocol if you want to play with a chess GUI. This engine has been tested with the Arena chess GUI. 
The debug mode is recommended if you want to use a physical chessboard, or any other GUI that you will like. 
Here you can easier set the pace of the game. Further instructions follows.

Known issues:
  1. When using UCI, the engine can't currently play as white, but only black. 
  2. Sometimes the engine suggests an illigal move. Usually but not always, this happens in check positions. 
    The issue has been introduced after iterative deepening has been implemented. 
    This shouldn't have anything to do with the move generation.
    A workaround for this could be to use UCI to setup the position again, 
      and run the latest move again to make the engine recalculate the move. 
      This is done by using the 'position fen' followed by a fen string representing the board.
      
Using the debug mode:
  using the debug mode is simple. Use the command 'startgame' to start the game. 
    use the command 'pickside' followed by either b or w, representing the color you want to play. 
    The engine will play the opposing color of which you picked.
    From there use the command 'move e2e4' if you want to move your piece from e2 to e4. so the command 'move' 
    followed by the move you want to make. The engine will automaticly answer your movements with the chosen move of the engine. 
    it could look like: 'bestmove d7d6', and will automaticly update the chessboard reprented in the enigne. 
    Update your chosen physical board or the GUI you are using manually afterwards.
    Be aware that this debug mode has no referee. You chose if you want to make legal or illegal moves. 
    
Using the UCI protocol:
  Using the UCI protocol is recommended if you choose to use a chess GUI. 
  The engine is tested with the arena chess engine: http://www.playwitharena.com/
  Simply import the JAR to your chess GUI. Configure your GUI to no time limit (since time cap. isn't implemented yet). 
  Keep in mind that the engine can only play as black, when using the UCI protocol. 
