package src.entities;

public class TranspositionWrapper {
    public String bestmove;
    public int evaluation;

    public TranspositionWrapper(String bestmove, int evaluation) {
        this.bestmove = bestmove;
        this.evaluation = evaluation;
    }
}
