package Controllers.NeuralNetwork.Visualization;

import Game.Game;
import Game.Ship;
import UI.ShooterAI;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;

import java.util.List;

public class NeuralNetworkVisual {
    public static final Double BORDER_DISTANCE = 50d;
    public static final Double X = Game.WIDTH + BORDER_DISTANCE;
    public static final Double WIDTH = (ShooterAI.WIDTH - Game.WIDTH - 2 * BORDER_DISTANCE);
    public static final Double DISTANCE_BETWEEN_NETWORKS = new Double(ShooterAI.HEIGHT/2);
    public static final Double HEIGHT = ((ShooterAI.HEIGHT/2) - 2*BORDER_DISTANCE);
    public static final Double DISTANCE_BETWEEN_LAYERS = WIDTH/5;

    private Double x;
    private Double y;
    private boolean isTop;
    private NeuralNetwork neuralNetwork;
    private List<LayerVisual> layerVisuals;
    private List<NeuronVisual> neuronVisuals;

    public NeuralNetworkVisual(Ship ship) {
        isTop = ship.isFirst();
        this.neuralNetwork = ship.getController().getNeuralNetwork();
        initializePosition();
        if (neuralNetwork != null) {
            initializeNetwork();
        }
    }

    /**
     * Initializes position, depending on whether this is the top or bottom network
     */
    private void initializePosition() {
        this.x = X;
        this.y = DISTANCE_BETWEEN_LAYERS;
        if (!isTop) {
            y += DISTANCE_BETWEEN_NETWORKS;
        }
    }

    /**
     * Creates all the layers for this network
     */
    private void initializeNetwork() {
        int count = neuralNetwork.getLayersCount();
        for (int i = 0; i < count; i++) {
            Layer layer = neuralNetwork.getLayerAt(i);
            Double layerX = x + i * DISTANCE_BETWEEN_LAYERS;
            layerVisuals.add(new LayerVisual(layer, layerX, y, isTop));
        }
    }


}
