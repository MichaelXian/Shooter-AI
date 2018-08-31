package Evolution;

import Controllers.NeuralNetwork.NeuralNetworkFactory;
import org.neuroph.core.NeuralNetwork;

public class NeuralNetPlayground {
    static NeuralNetwork neuralNetwork;


    public static void main(String[] args) {
        for (int i = 0; i < 100; i ++) {
            neuralNetwork = NeuralNetworkFactory.randomNeuralNet();
            neuralNetwork.save("NeuralNets/net" + i + ".nnet");
        }
        neuralNetwork = NeuralNetworkFactory.emptyNeuralNet();
        //neuralNetwork.save("NeuralNets/net2.nnet");
    }


}
