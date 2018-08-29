package Controllers.NeuralNetwork.Visualization;

import org.neuroph.core.Layer;

public class LayerVisual {
    private static final Double DISTANCE_BETWEEN_LAYERS = NeuralNetworkVisual.DISTANCE_BETWEEN_LAYERS;
    private Double x;
    private Double y;
    private boolean isTop;
    LayerVisual(Layer layer, int layerNumber, boolean isTop) {
        this.isTop = isTop;
        this.x = NeuralNetworkVisual.X + layerNumber*NeuralNetworkVisual.DISTANCE_BETWEEN_LAYERS;
        this.y = NeuralNetworkVisual.BORDER_DISTANCE;
        if (!isTop) {
            y += NeuralNetworkVisual.DISTANCE_BETWEEN_NETWORKS;
        }
    }


}
