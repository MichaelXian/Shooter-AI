package Evolution;

import org.neuroph.core.NeuralNetwork;

import java.util.*;

/**
 * Creates 5 match-ups for each network.
 */
public class MatchMaker implements Iterable<List<NeuralNetwork>>{

    private final Integer MAX_MATCHUPS = 5;
    List<NeuralNetwork> networks;
    List<List<NeuralNetwork>> matchups;
    Map<NeuralNetwork, Integer> matchupCount;
    Random random;
    MatchMaker(List<NeuralNetwork> networks) {
        random = new Random();
        this.networks = new ArrayList<>();
        this.networks.addAll(networks);
        matchups = new ArrayList<>();
        matchupCount = new HashMap<>();
        for (NeuralNetwork network: networks) {
            matchupCount.put(network, 0);
        }
        while (this.networks.size() > 1) {
            createMatchup();
        }
    }

    /**
     * Creates one random matchup, increments the matchupCount for the chosen networks, removes them from networks if
     * they have reached MAX_MATCHUPS, and adds them to matchups
     */
    private void createMatchup() {
        ArrayList<NeuralNetwork> matchup = new ArrayList<>();
        NeuralNetwork network1 = networks.get(random.nextInt(networks.size()));
        networks.remove(network1); // Make sure we dont choose the same network twice
        NeuralNetwork network2 = networks.get(random.nextInt(networks.size()));
        networks.add(network1);
        matchup.add(network1);
        matchup.add(network2);
        matchups.add(matchup);
        manageMatchupCount(matchup);
    }

    /**
     * Removes the networks from the list of networks if they already have enough matchups
     * @param matchup
     */
    private void manageMatchupCount(ArrayList<NeuralNetwork> matchup) {
        for (NeuralNetwork network: matchup) {
            Integer currentMatchups = matchupCount.get(network) + 1;
            matchupCount.put(network, currentMatchups);
            if (currentMatchups >= MAX_MATCHUPS) {
                networks.remove(network);
            }
        }
    }

    @Override
    public Iterator<List<NeuralNetwork>> iterator() {
        return matchups.iterator();
    }

    /**
     * Returns a matchup with the given network, ignoring any matchups in watched
     * @param network
     * @param watched
     * @return
     */
    public List<NeuralNetwork> getMatchupWith(NeuralNetwork network, List<List<NeuralNetwork>> watched) {
        removeIrrelevant(network);
        for (List<NeuralNetwork> highlight: matchups) {
            if (!watched.contains(highlight)) {
                return highlight;
            }
        }
        return null;
    }

    /**
     * Removes all matchups that dont contain given network
     * @param network
     */
    private void removeIrrelevant(NeuralNetwork network) {
        for (List<NeuralNetwork> matchup: matchups) {
            if (!matchup.contains(network)) {
                matchups.remove(matchup);
            }
        }
    }



    /*
    private class MatchIterator implements Iterator<List<NeuralNetwork>> {
        Iterator<NeuralNetwork> iterator1;
        Iterator<NeuralNetwork> iterator2;
        NeuralNetwork currentFirst;
        NeuralNetwork currentSecond;
        MatchIterator() {
            iterator1 = networks.iterator();
            iterator2 = networks2.iterator();

        }

        @Override
        public boolean hasNext() {
            return iterator1.hasNext() || iterator2.hasNext();
        }

        @Override
        public List<NeuralNetwork> next() {
            ArrayList ret = new ArrayList();
            if (currentFirst == null) { // If first call, then initialize currents
                currentFirst = iterator1.next();
                currentSecond = iterator2.next();
            } else {
                if (iterator2.hasNext()) { // If second iterator still has next, then no need to increment first
                    currentSecond = iterator2.next();
                    if (currentFirst == currentSecond) { // If they are the same neural net, next again
                        next();
                    }
                } else { // Due to hasNext() iterator1 must have a next
                    currentFirst = iterator1.next();
                    iterator2 = networks2.iterator();
                    currentSecond = iterator2.next();
                }
            }
            ret.add(currentFirst);
            ret.add(currentSecond);
            return ret;
        }
    }
    */
}
