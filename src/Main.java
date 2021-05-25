import javax.swing.*;
public class Main{ 
    private static final int ContainerPanelSize = 360;
    private void start(final RootPaneContainer pane, final boolean bool) {

        final Coordinator mainCoordinator = new Coordinator();
        final TableModel philosopherstable = new TableModel(mainCoordinator, ContainerPanelSize);
       
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    new UIView(pane, mainCoordinator, philosopherstable, bool);
                }
            });
            
        } catch (Exception e) {
            System.err.println("unable to create GUI");
        }
    }

    public void init() {
        start((RootPaneContainer) this, true);
    }
    public static void main(String[] args) {
        JFrame javaFrame = new JFrame("Main Dining");
       javaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main main = new Main();
        main.start(javaFrame, false);
       javaFrame.pack();            
       javaFrame.setVisible(true);
    }
}
