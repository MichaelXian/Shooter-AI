package Evolution;

import Game.Game;

public class MatchPlayer {
    public static void playMatch(Game game) {
        while (!game.isGameEnd()) {
            game.update();
        }
    }
}
