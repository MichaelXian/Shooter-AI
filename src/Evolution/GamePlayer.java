package Evolution;

import Game.Game;

public class GamePlayer {
    public static void playGame(Game game) {
        while (!game.isGameEnd()) {
            game.update();
        }
        game.update();
    }
}
