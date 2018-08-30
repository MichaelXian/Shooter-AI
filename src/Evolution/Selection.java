package Evolution;

import org.neuroph.core.NeuralNetwork;
import Game.Game;

import java.util.*;

public class Selection {
    private List<Double> grades;
    private List<NeuralNetwork> networks;

    Selection(List<NeuralNetwork> networks) {
        grades = new ArrayList<>();
        this.networks = networks;
        for (NeuralNetwork neuralnetwork: networks) {
            grades.add(0.1);
        }
    }

    /**
     * Grades the performance of the two neural networks by the outcome of the game
     * @param matchup the two neural networks
     * @param game
     */
    public void grade(List<NeuralNetwork> matchup, Game game) {
        NeuralNetwork net1 = matchup.get(0);
        NeuralNetwork net2 = matchup.get(1);
        int net1Index = networks.indexOf(net1);
        int net2Index = networks.indexOf(net2);
        Double scoreChange1 = 0d;
        Double scoreChange2 = 0d;
        if (game.getWinner() == Game.FIRST_WIN) {
            scoreChange1 = game.isKilled() ? 1 : 0.1;
        } else if (game.getWinner() == Game.SECOND_WIN) {
            scoreChange1 = game.isKilled() ? 1 : 0.1;
        }
        grades.set(net1Index, grades.get(net1Index) + scoreChange1);
        grades.set(net2Index, grades.get(net2Index) + scoreChange2);
    }


    /**
     * Selects given number of nets and returns them
     * @param number
     * @return
     */
    public List<NeuralNetwork> select(int number) {
        List<NeuralNetwork> ret = new ArrayList<>();
        for (int i = 0; i < number; i ++) {
            NeuralNetwork networkToAdd = selectOne();
            grades.remove(networks.indexOf(networkToAdd));
            networks.remove(networkToAdd);
            ret.add(networkToAdd);
        }
        return ret;
    }

    private NeuralNetwork selectOne() {
        normalize();
        Double random = Math.random();
        Double sum = 0d;
        for (int i = 0; i < grades.size(); i++) {
            sum += grades.get(i);
            if (random < sum) {
                return networks.get(i);
            }
        }
        throw new NullPointerException("Somehow nothing was selected");
    }


    /**
     * Normalizes the grades
     */
    private void normalize() {
        Double sum = 0d;
        for (Double d: grades) {
            sum += d;
        }
        for (int i = 0; i < grades.size(); i++) {
            grades.set(i, grades.get(i)/sum);
        }
    }



}
