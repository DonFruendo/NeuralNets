package neuralNet;

import javax.swing.*;
import java.util.Vector;

/**
 * Created by Don on 06.04.2016.
 */
public class main extends JFrame{
    private JTextArea console;
    private JPanel panel1;

    public static void main(String[] args) {
        main instance = new main("Neural Net");
        instance.setContentPane(instance.panel1);
        instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instance.pack();
        instance.setVisible(true);

        NeuralNet nn = new NeuralNet();


        Vector<Double> vec = new Vector<>();
        vec.add(0.3);
        vec.add(-0.8);
        vec.add(-0.2);

        vec.add(0.6);
        vec.add(0.1);
        vec.add(-0.1);

        vec.add(0.4);
        vec.add(0.5);

        instance.addToConsole(vec.toString());

        nn.putWeights(vec);

        Vector<Double> input = new Vector<>();
        input.add(1.);
        input.add(1.);
        input.add(1.);

        Vector<Double> output = nn.update(input);
        instance.addToConsole(output.toString());
    }

    public main(String title) {
        super(title);
    }

    public void addToConsole(String line) {
        console.setText(line + "\n" + console.getText());
    }
}
