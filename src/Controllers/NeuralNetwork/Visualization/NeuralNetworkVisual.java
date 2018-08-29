package Controllers.NeuralNetwork.Visualization;

import UI.ShooterAI;
import Game.Game;

public class NeuralNetworkVisual {
    public static final Double BORDER_DISTANCE = 50d;
    public static final Double X = Game.WIDTH + BORDER_DISTANCE;
    public static final Double WIDTH = (ShooterAI.WIDTH - Game.WIDTH - 2 * BORDER_DISTANCE);
    public static final Double DISTANCE_BETWEEN_NETWORKS = new Double(ShooterAI.HEIGHT/2);
    public static final Double HEIGHT = ((ShooterAI.HEIGHT/2) - 2*BORDER_DISTANCE);
    public static final Double DISTANCE_BETWEEN_LAYERS = WIDTH/5;
}
