import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class UIView extends JPanel {

    private final Coordinator mainCoordinator;
    private TableModel philosopherstable;

    private final JRootPane root;

    public UIView(RootPaneContainer pane, Coordinator C, TableModel T, boolean bool) { 
     
        mainCoordinator = C;
        philosopherstable = T;

        final JPanel javaPanel = new JPanel();   

        final JButton runButton = new JButton("Run");
        final JButton pauseButton = new JButton("Pause");
        final JButton resetButton = new JButton("Reset");
        final JButton quitButton = new JButton("Quit");

        Integer[] options = {1, 2, 3, 4, 5};
        JComboBox<Integer> javacombox = new JComboBox<>(options);
      

        javacombox.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
           philosopherstable.setPhilCount(javacombox.getSelectedIndex() + 1);
           philosopherstable.updatePhilsandForks();
           philosopherstable.repaint();
        }

        });

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainCoordinator.resume();
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                philosopherstable.pause();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                philosopherstable.reset();
            }
        });
       
        if (!bool) {
            quitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }

        javaPanel.setLayout(new FlowLayout());
        javaPanel.add(runButton);
        javaPanel.add(pauseButton);
        javaPanel.add(resetButton);
        javaPanel.add(javacombox);
        if (!bool) {
            javaPanel.add(quitButton);
        }

        setLayout(new BorderLayout());
        add(philosopherstable, BorderLayout.CENTER);
        add(javaPanel, BorderLayout.SOUTH);
        pane.getContentPane().add(this);
        root = getRootPane();
    }
}