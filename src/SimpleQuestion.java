import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SimpleQuestion extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JRadioButton radioForward;
    private JRadioButton radioBackwards;
    private JRadioButton radioRight;
    private JRadioButton radioLeft;
    private JLabel labelLeft;
    private JLabel labelFrontLeft;
    private JLabel labelFront;
    private JLabel labelFrontRight;
    private JLabel labelRight;

    public SimpleQuestion() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        fillInVariables();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        ArrayList<Double> inputs = mainGUI.getInstance().getValues();
        String expectedWinner = "OUT";
        if(radioForward.isSelected()) {
            expectedWinner += "0";
        } else if (radioBackwards.isSelected()) {
            expectedWinner += "1";
        } else if (radioRight.isSelected()) {
            expectedWinner += "2";
        } else {
            expectedWinner += "3";
        }
        NetController.getInstance().learn(inputs, expectedWinner);
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void fillInVariables() {
        mainGUI gui = mainGUI.getInstance();
        ArrayList<Double> values = gui.getValues();
        labelLeft.setText("Left: " + values.get(0));
        labelFrontLeft.setText("Front Left: " + values.get(1));
        labelFront.setText("Front: " + values.get(2));
        labelFrontRight.setText("Front Right: " + values.get(3));
        labelRight.setText("Right: " + values.get(4));

    }

    public static void main(String[] args) {
        SimpleQuestion dialog = new SimpleQuestion();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
