package v1.neuralNet;

/**
 * Synapse
 *
 * A connection between two Neurons
 *
 * Created by Don on 31.03.2016.
 */
public class Synapse {
    private Neuron from, to;
    private double weight;

    public Synapse() {
        weight = Math.random();
    }

    public Synapse(Neuron from, Neuron to) {
        this();
        attachTo(from, to);
    }

    public Synapse(Neuron from, Neuron to, double weight) {
        this(from, to);
        this.weight = weight;
    }

    public double getSignal() {
        return from.getSignal() * weight;
    }

    public Neuron getFrom() {
        return from;
    }

    public Neuron getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void attachTo(Neuron from, Neuron to) {
        this.from = from;
        this.to = to;
        from.attachOutgoingSynapse(this);
        to.attachIncomingSynapse(this);
    }

    public String toString() {
        return "Synapse (" + from + "->" + to +")";
    }
}
