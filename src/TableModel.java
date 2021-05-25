
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;


// Graphics panel in which philosophers and forks appear.
//
class TableModel extends JPanel { // View

    private static int NUM_PHILS = 100; // default value (needs to be changed)

    // following fields are set by constructor:
    private final Coordinator mainCoordinator;
    private final int CanvasSize;

    private ArrayList<ForkModel> forks; // array of forks
    private ArrayList<PhilosopherModel> philosophers; // array of philosophers
    private ArrayList<Thread> runningThreads;


    public void setPhilCount(int philCount){
    TableModel.NUM_PHILS = philCount;
    }

    public synchronized void pause() { // pauses the entire UI
            mainCoordinator.pause();
            for (int i = 0; i < NUM_PHILS; i++) {
                runningThreads.get(i).interrupt();
        }
    }

    // Called by the UI when it wants to start over.
    //
    public synchronized void reset() {
        mainCoordinator.reset();
        // force philosophers to notice change in coordinator state:
        for (int i = 0; i < NUM_PHILS; i++) {
            runningThreads.get(i).interrupt();
        }
        for (int i = 0; i < NUM_PHILS; i++) {
            forks.get(i).reset();
        }
    }

    // The following method is called automatically by the graphics
    // system when it thinks the Table canvas needs to be re-displayed.
    // This can happen because code elsewhere in this program called
    // repaint(), or because of hiding/revealing or open/close
    // operations in the surrounding window system.
    //
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < NUM_PHILS; i++) {
            forks.get(i).draw(g);
            philosophers.get(i).draw(g);
        }
        g.setColor(Color.black);
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
    }

    // Constructor
    //
    // Note that angles are measured in radians, not degrees.
    // The origin is the upper left corner of the frame.
    //



public synchronized void Propagate(){
  
    // Propagate the Fork Circles
   for (int i = 0; i < NUM_PHILS; i++) {
    double angle = Math.PI/2 + 2*Math.PI/NUM_PHILS*(i-0.5);
    forks.add( new ForkModel(this,
        (int) (CanvasSize/2.0 + CanvasSize/6.0 * Math.cos(angle)),
        (int) (CanvasSize/2.0 - CanvasSize/6.0 * Math.sin(angle))));
}

// Propagate the Philosopher circles
for (int i = 0; i < NUM_PHILS; i++) {
        double angle = Math.PI/2 + 2*Math.PI/NUM_PHILS*i;
        philosophers.add(  new PhilosopherModel(this,
        (int) (CanvasSize/2.0 + CanvasSize/3.0 * Math.cos(angle)),
        (int) (CanvasSize/2.0 - CanvasSize/3.0 * Math.sin(angle)),
        forks.get(i),
        forks.get((i+1) % NUM_PHILS),
        mainCoordinator));
       synchronized(this){ Thread t = new Thread(philosophers.get(i), "Philosopher " + (i + 1));
        runningThreads.add(t);
        t.start();

    }
    }
    }

public void updatePhilsandForks(){
forks.clear();
philosophers.clear();
runningThreads.clear();
Propagate();

 
}
    public TableModel(Coordinator C, int CANVAS_SIZE) {    // constructor
        mainCoordinator = C;
        CanvasSize = CANVAS_SIZE;
        forks =new ArrayList<>();
        philosophers = new ArrayList<>();
        runningThreads = new ArrayList<>();

        setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));
        
        Propagate();
    }
} 



// class closing brace

