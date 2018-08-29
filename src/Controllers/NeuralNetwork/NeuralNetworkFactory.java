package Controllers.NeuralNetwork;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.nnet.MultiLayerPerceptron;

public class NeuralNetworkFactory {

    public static NeuralNetwork randomNeuralNet() {
        NeuralNetwork neuralNetwork = new MultiLayerPerceptron(12,5);
        pruneAllConnections(neuralNetwork);
        return neuralNetwork;
    }

    private static void pruneAllConnections(NeuralNetwork neuralNetwork) {
        for (Object layer: neuralNetwork.getLayers()) {
            for (Neuron neuron: ((Layer) layer).getNeurons()) {
                neuron.removeAllConnections();
            }
        }
    }


}
