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
    private ArrayList<Synapse> incoming, outgoing;
    private double input;

    // --- Constructors ---
    public Neuron() {
    }

    public Neuron(double input) {
        this();
        this.input = input;
    }


    // --- Getter/Setter ---

    public void setInput(double input) {
        this.input = input;
    }


    // --- Other Methods ---

    public double getSignal() {
        if(incoming != null) {
            double result = 0;
            for (Synapse synapse : incoming) {
                result += synapse.getSignal();
            }
            return result;
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
 }
