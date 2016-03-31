import javax.swing.*;
import java.util.ArrayList;

/**
 * NeuralNets
 * Created by Don on 31.03.2016.
 */
public class mainGUI extends JFrame {
    private JPanel consolePanel;
    private JPanel inputPanel;
    private JPanel outputPanel;
    private JPanel mainPanel;
    private JSlider sliderLeft;
    private JSlider sliderFrontLeft;
    private JSlider sliderFront;
    private JSlider sliderFrontRight;
    private JSlider sliderRight;
    private JTextArea console;
    private JLabel outputLeft;
    private JLabel outputFrontLeft;
    private JLabel outputFront;
    private JLabel outputFrontRight;
    private JLabel outputRight;

    public static void main(String[] args) {
        mainGUI frame = new mainGUI("mainGUI");
        frame.setContentPane(frame.mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        NetController.getInstance().createNet();
    }

    public mainGUI(String caption) {
        super(caption);
    }

    public void setValues(ArrayList<Integer> values) {
        outputLeft.setText(values.get(0) + "");
        outputFrontLeft.setText(values.get(1) + "");
        outputFront.setText(values.get(2) + "");
        outputFrontRight.setText(values.get(3) + "");
        outputRight.setText(values.get(4) + "");
    }

    public void addToConsole(String line) {
        console.setText(line + "\n" + console.getText());
    }
}
