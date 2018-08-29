package Evolution;

import Controllers.NeuralNetwork.NeuralNetworkFactory;
import org.neuroph.core.NeuralNetwork;

public class NeuralNetPlayground {
    NeuralNetwork neuralNetwork;


    public static void main(String[] args) {
        for (int i = 0; i < 10; i ++) {
            NeuralNetwork neuralNetwork = NeuralNetworkFactory.randomNeuralNet();
            neuralNetwork.save("NeuralNets/net" + i + ".nnet");
        }
    }


}
