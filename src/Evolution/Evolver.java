package Evolution;

import Game.Game;
import UI.GameDrawer;
import UI.ShooterAI;
import org.neuroph.core.NeuralNetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Evolver {
    private final int POPULATION_SIZE = 100;
    private final String FILEPATH = "NeuralNets/net";
    private final String BACKUP_FILEPATH = "NeuralNets/backup/net";
    private final String EXTENSION = ".nnet";
    private GameDrawer gameDrawer;
    private List<NeuralNetwork> networks;
    private List<NeuralNetwork> children;
    private Iterator<List<NeuralNetwork>> iterator;
    private List<List<NeuralNetwork>> watchedHighlights;
    private MatchMaker matchMaker;
    private Selection selection;
    private ShooterAI shooterAI;
    private boolean isEvolved;

    public Evolver(ShooterAI shooterAI) {
        this.shooterAI = shooterAI;
        networks = new ArrayList<>();
        loadNets();
        watchedHighlights = new ArrayList<>();
        isEvolved = false;
        matchMaker = new MatchMaker(networks);
        iterator = matchMaker.iterator();
        selection = new Selection(networks);
        children = new ArrayList<>();
    }


    /**
     * Does evolution and increments the generation if there are no more matches for this generation, then returns the
     * next matchup
     */
    public List<NeuralNetwork> next(Game game, List<NeuralNetwork> currentMatchup) {
        /*for (int i = 0; i < 100; i++) { // for testing
            evolution();
            saveNets();
            resetEvolver();
        }*/
        if(currentMatchup != null) {
            selection.grade(currentMatchup, game);
        }
        if (!iterator.hasNext()) {
            evolution();
            saveNets();
            resetEvolver();
            isEvolved = true;
        }
        return iterator.next(); // after resetting evolver, it will have a next
    }

    /**
     * Retrieves the next highlight;  returns null if no highlight
     * @return
     */
    private List<NeuralNetwork> nextHighlight() {
        NeuralNetwork bestNetwork = selection.getBestNetwork();
        List<NeuralNetwork> matchup = matchMaker.getMatchupWith(bestNetwork, watchedHighlights);
        watchedHighlights.add(matchup);
        return matchup;
    }



    public boolean isEvolved() {
        if (isEvolved) {
            isEvolved = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Loads the neural networks from the NeuralNets folder
     */
    private void saveNets() {
        for (int i = 0; i < networks.size(); i ++) {
            networks.get(i).save(FILEPATH + i + EXTENSION);
        }
        for (int i = 0; i < networks.size(); i ++) {
            networks.get(i).save(BACKUP_FILEPATH + i + EXTENSION);
        }
    }

    /**
     * Loads the neural networks from the NeuralNets folder
     */
    private void loadNets() {
        for (int i = 0; i < POPULATION_SIZE; i ++) {
            networks.add(NeuralNetwork.createFromFile(FILEPATH + i + EXTENSION));
        }
    }


    /**
     * Creates a new selection, matchmaker, and iterator (for the new neural networks after evolution())
     */
    private void resetEvolver() {
        watchedHighlights = new ArrayList<>();
        selection = new Selection(networks);
        matchMaker = new MatchMaker(networks);
        iterator = matchMaker.iterator();
        children = new ArrayList<>();
    }

    /**
     * Selects, then crossover/mutates
     */
    private void evolution() {
        networks = selection.select((int) POPULATION_SIZE/2);
        repopulate();
        mutate();
    }

    /**
     * Crossovers to create children until POPULATION_SIZE has been reached
     */
    private void repopulate() {
        while (networks.size() + children.size() < POPULATION_SIZE) {
            children.add(Crossover.breed(networks));
        }
        networks.addAll(children);
    }


    /**
     * Mutates all networks
     */
    private void mutate() {
        for (NeuralNetwork network: networks) {
            Mutator.mutate(network);
        }
    }


    /**
     * Gets the number of matchups made by the matchmaker
     * @return
     */
    public int getNumMatchups() {
        return matchMaker.getNumMatchups();
    }

}
