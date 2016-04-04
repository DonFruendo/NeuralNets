package neuralNet;

import java.util.ArrayList;

/**
 * Neuron
 *
 * A Node in the NeuralNet
 *
 * Created by Don on 31.03.2016.
 */
public class Neuron {
    private String ID;
    private double threshold = 0;
    private double learnrate = 0.1;

    private ArrayList<Synapse> incoming, outgoing;
    private double input;

    // --- Constructors ---
    public Neuron() {
        ID = "UNASSIGNED";
    }

    public Neuron(double input) {
        this();
        this.input = input;
    }

    public Neuron(String ID) {
        this();
        this.ID = ID;
    }


    // --- Getter/Setter ---

    public void setInput(double input) {
        this.input = input;
    }

    public String getID() {
        return ID;
    }
    // --- Other Methods ---

    public double getSignal() {
        if(incoming != null) {
            double result = 0;
            for (Synapse synapse : incoming) {
                result += synapse.getSignal();
            }
            if(result >= threshold) {
                return result;
            } else {
                return 0;
            }
        } else {
            return input;
        }
    }

    public void attachIncomingSynapse(Synapse incomingSynapse) {
        if(incoming == null) {
            incoming = new ArrayList<>();
        }
        incoming.add(incomingSynapse);
    }

    public void attachOutgoingSynapse(Synapse outgoingSynapse) {
        if(outgoing == null) {
            outgoing = new ArrayList<>();
        }
        outgoing.add(outgoingSynapse);
    }

    public void giveTreats() {
        for(Synapse synapse : incoming) {
            if(synapse.getSignal() > threshold) {
                synapse.setWeight(synapse.getWeight() + learnrate);
            }
        }
    }

    public void giveSours() {
        for(Synapse synapse : incoming) {
            if(synapse.getSignal() > threshold) {
                synapse.setWeight(synapse.getWeight() - learnrate);
            }
        }
    }
 }
