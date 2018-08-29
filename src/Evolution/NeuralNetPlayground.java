package Evolution;

import Controllers.NeuralNetwork.NeuralNetworkFactory;
import org.neuroph.core.NeuralNetwork;

public class NeuralNetPlayground {
    NeuralNetwork neuralNetwork;


    public static void main(String[] args) {
        //double[] input = {1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d};
        NeuralNetwork neuralNetwork = NeuralNetworkFactory.randomNeuralNet();
        //neuralNetwork.setInput(input);
        neuralNetwork.save("NeuralNets/net1.nnet");
    }


}
