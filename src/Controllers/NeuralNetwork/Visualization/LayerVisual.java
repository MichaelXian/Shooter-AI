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

    LayerVisual(Layer layer, Double x, Double y, boolean isTop) {
        this.layer = layer;
        this.isTop = isTop;
        this.x = x;
        this.y = y;
        initializeNeurons();
    }


    private void initializeNeurons() {
        List<Neuron> neurons = layer.getNeurons();
        int count = layer.getNeuronsCount();
        for (int i = 0; i < count; i ++) {
            Neuron neuron = layer.getNeuronAt(i);
            Double neuronY = (i + 1) * NeuralNetworkVisual.HEIGHT/count;
            neuronVisuals.add( new NeuronVisual(neuron, x, neuronY, isTop));
        }
    }
    
    private List<Graphic> getNeuronGraphics() {
        List<Graphic> ret = new ArrayList<>();
        for (NeuronVisual neuron: neuronVisuals) {
            ret.add(neuron.getGraphic());
        }
        return ret;
    }






}
