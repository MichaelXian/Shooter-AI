package Controllers.NeuralNetwork.Visualization;

import Game.Game;
import UI.ShooterAI;
import Utility.Vector;
import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkVisual {
    public static final Double BORDER_DISTANCE = 50d;
    public static final Double X = Game.WIDTH + BORDER_DISTANCE;
    public static final Double WIDTH = (ShooterAI.WIDTH - Game.WIDTH - 2 * BORDER_DISTANCE);
    public static final Double DISTANCE_BETWEEN_NETWORKS = new Double(ShooterAI.HEIGHT)/2;
    public static final Double HEIGHT = ((ShooterAI.HEIGHT/2) - 2*BORDER_DISTANCE);
    public static final Double DISTANCE_BETWEEN_LAYERS = WIDTH/5;

    private Double x;
    private Double y;
    private boolean isTop;
    private NeuralNetwork neuralNetwork;
    private List<LayerVisual> layerVisuals;
//    private List<NeuronVisual> neuronVisuals;
    private int layerCount;

    public NeuralNetworkVisual(NeuralNetwork neuralNetwork, boolean isTop) {
        this.isTop = isTop;
        this.neuralNetwork = neuralNetwork;
        layerVisuals = new ArrayList<>();
//        neuronVisuals = new ArrayList<>();
        layerCount = neuralNetwork.getLayersCount();
        initializePosition();
        if (neuralNetwork != null) {
            initializeNetwork();
        }
    }

    /**
     * Gets all graphics of the neural net (graphics of all of the layers)
     * @return
     */
    public List<Graphic> getGraphics() {
        List<Graphic> ret = new ArrayList<>();
        for (LayerVisual layer: layerVisuals) {
            ret.addAll(layer.getGraphics());
        }
        return ret;
    }

    /**
     * Gets positions of input neuronVisuals
     * @return
     */
    public List<Vector> getInputPositions() {
        ArrayList<Vector> ret = new ArrayList<>();
        LayerVisual inputLayer = layerVisuals.get(0);
        for (NeuronVisual neuron: inputLayer.getNeuronVisuals()) {
            ret.add(neuron.getPosition());
        }
        return ret;
    }

    /**
     * Gets positions of output neuronVisuals
     * @return
     */
    public List<Vector> getOutputPositions() {
        ArrayList<Vector> ret = new ArrayList<>();
        LayerVisual inputLayer = layerVisuals.get(layerCount - 1);
        for (NeuronVisual neuron: inputLayer.getNeuronVisuals()) {
            ret.add(neuron.getPosition());
        }
        return ret;
    }

    /**
     * Initializes position, depending on whether this is the top or bottom network
     */
    private void initializePosition() {
        this.x = X;
        x += BORDER_DISTANCE;
        this.y = BORDER_DISTANCE;
        if (!isTop) {
            y += DISTANCE_BETWEEN_NETWORKS;
        }
    }

    /**
     * Creates all the layerVisuals and connectionVisuals for this networkVisual
     */
    private void initializeNetwork() {
        initializeLayers();
        initializeConnections();
    }

    /**
     * Creates all the layerVisuals
     */
    private void initializeLayers() {
        for (int i = 0; i < layerCount; i++) {
            Layer layer = neuralNetwork.getLayerAt(i);
            Double layerX = x + i * DISTANCE_BETWEEN_LAYERS;
            layerVisuals.add(new LayerVisual(layer, layerX, y, isTop));
        }
    }

    /**
     * Creates all outgoing connections. Iterates through all neurons, calling connectNeuron
     */
    private void initializeConnections() {
        for (int i = 0; i < layerCount - 1; i ++) { // last layer can't have outgoing connections
            Layer originLayer = neuralNetwork.getLayerAt(i);
            for (Neuron neuron: originLayer.getNeurons()) {
                List<Connection> outputs = neuron.getOutConnections();
                if (outputs.size() > 0) {
                    connectNeuron(neuron, outputs);
                }
            }
        }
    }

    /**
     * Uses the given neuron to connect the corresponding neuronVisual to all other neuronVisuals
     * it should be connected to
     * @param neuron
     */
    private void connectNeuron(Neuron neuron, List<Connection> outputs) {
        NeuronVisual fromNeuron = getNeuronVisual(neuron);
        if (fromNeuron != null) {
            for (Connection connection : outputs) {
                NeuronVisual toNeuron = getNeuronVisual(connection.getToNeuron());
                if (toNeuron != null) {
                    fromNeuron.addConnection(connection, toNeuron);
                }
            }
        }
    }


    /**
     * Gets the NeuronVisual corresponding to the given neuron. Returns null if not found
     * @param neuron
     * @return the corresponding NeuronVisual
     */
    private NeuronVisual getNeuronVisual(Neuron neuron) {
        List<NeuronVisual> ret = new ArrayList<>();
        for (LayerVisual layer: layerVisuals) {
            ret.add(layer.getNeuronVisual(neuron));
        }
        for (NeuronVisual neuronVisual: ret) {
            if (neuronVisual != null) {
                return neuronVisual;
            }
        }
        return null;
        //throw new NullPointerException("No neuron found");
    }

}
