package Controllers.NeuralNetwork.Visualization;

import org.neuroph.core.Layer;
import org.neuroph.core.Neuron;

import java.util.ArrayList;
import java.util.List;

public class LayerVisual {
    private Double x;
    private Double y;
    private boolean isTop;
    private Layer layer;
    private List<NeuronVisual> neuronVisuals;

    /**
     * Creates a layerVisualization at the specified location with the given layer
     * @param layer
     * @param x
     * @param y
     * @param isTop
     */
    LayerVisual(Layer layer, Double x, Double y, boolean isTop) {
        this.layer = layer;
        this.isTop = isTop;
        this.x = x;
        this.y = y;
        neuronVisuals = new ArrayList<>();
        initializeNeurons();
    }


    /**
     * Creates and adds neuronVisuals to list of neuronVisuals
     */
    private void initializeNeurons() {
        List<Neuron> neurons = layer.getNeurons();
        int count = layer.getNeuronsCount();
        for (int i = 0; i < count; i ++) {
            Neuron neuron = layer.getNeuronAt(i);
            Double neuronY = (i + 1) * NeuralNetworkVisual.HEIGHT/count;
            neuronVisuals.add( new NeuronVisual(neuron, x, neuronY, isTop));
        }
    }

    /**
     * Returns graphics of all neurons and outgoing connections in this layer
     * @return graphics of all neurons and outgoing connections in this layer
     */
    public List<Graphic> getGraphics() {
        List<Graphic> ret = new ArrayList<>();
        for (NeuronVisual neuron: neuronVisuals) {
            ret.add(neuron.getGraphic());
            ret.addAll(neuron.getConnectionGraphics());
        }
        return ret;
    }

    /**
     * Tries to find the corresponding neuronVisual to the given neuron, if not found, returns null
     * @param neuron
     * @return
     */
    public NeuronVisual getNeuronVisual(Neuron neuron) {
        for (NeuronVisual neuronVisual: neuronVisuals) {
            if (neuronVisual.getNeuron() == neuron) {
                return neuronVisual;
            }
        }
        return null;
    }
}
