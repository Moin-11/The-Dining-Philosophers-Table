import java.awt.*;
import java.util.*;

class PhilosopherModel implements Runnable {
    private static final Color THINK_COLOR = Color.blue;
    private static final Color WAIT_COLOR = Color.red;
    private static final Color EAT_COLOR = Color.green;

    private static final double THINK_TIME = 2.0;
    private static final double WAIT_TIME = 2.0;
    private static final double EAT_TIME = 2.0;
    
    private StateManagement maincoordinator;
    private TableCanvas philosopherstable;
    
    private static final int XSIZE = 50;
    private static final int YSIZE = 50;
    
    private int current_x;
    private int current_y;
    
    private ForkModel left_fork;
    private ForkModel right_fork;
    
    private Random random;
    private Color color;

    

    public PhilosopherModel(TableCanvas T, int cx, int cy, ForkModel lf, ForkModel rf, StateManagement C) {
        philosopherstable = T;
        current_x = cx;
        current_y = cy;
        left_fork = lf;
        right_fork = rf;
        maincoordinator = C;
        random = new Random();
        color = THINK_COLOR;
    }

   

    public Random getRandom() {
        return random;
    }



    public void setRandom(Random rndom) {
        this.random = rndom;
    }



    public Color getColor() {
        return color;
    }



    public void setColor(Color olor) {
        this.color = olor;
    }



    public ForkModel getRight_fork() {
        return right_fork;
    }



    public void setRight_fork(ForkModel right_fork) {
        this.right_fork = right_fork;
    }



    public ForkModel getLeft_fork() {
        return left_fork;
    }



    public void setLeft_fork(ForkModel left_fork) {
        this.left_fork = left_fork;
    }



    public static int getYsize() {
        return YSIZE;
    }



    public static int getXsize() {
        return XSIZE;
    }



    public TableCanvas getPhilosophersTable() {
        return philosopherstable;
    }



    public void setPhilosophersTable(TableCanvas philosopherstable) {
        this.philosopherstable = philosopherstable;
    }



    public StateManagement getMainCoordinator() {
        return maincoordinator;
    }



    public void setMainCoordinator(StateManagement mainCoordinator) {
        this.maincoordinator = mainCoordinator;
    }



    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(current_x-XSIZE/2, current_y-YSIZE/2, XSIZE, YSIZE);
    }




    public synchronized void run() {
        while(true) {
            try {
                if (getMainCoordinator().gate())
                 Sleep(EAT_TIME);

                think();
                if (getMainCoordinator().gate())
                
                Sleep(THINK_TIME);
                hunger();
                
                if (getMainCoordinator().gate())
                Sleep(WAIT_TIME);
                eat();

            } catch(ResetException e) { 
                color = THINK_COLOR;
                philosopherstable.repaint();
            }
        }
    }


    
    private void Sleep(double secs) throws ResetException {
        
        double arbitary = 0.2;

        double miliseconds = 1000 * secs;
        int window = (int) (2.0 * miliseconds * arbitary);

        int rand = random.nextInt() % window;

        int original_duration = (int) ((1.0 - arbitary) * miliseconds + rand);
        int duration = original_duration;
       
        while(true) {
            try {
                Thread.sleep(duration);
                return;
                
            } catch(InterruptedException e) {
                if (maincoordinator.isReset()) {
                    throw new ResetException();
                } else {       
                    maincoordinator.gate();   
                    duration = original_duration / 2;
                }
            }
        }
    }

    private synchronized void think() throws ResetException {
        color = THINK_COLOR;
        philosopherstable.repaint();
        Sleep(THINK_TIME);
    }

    private synchronized void hunger() throws ResetException {
        synchronized(left_fork){   
            synchronized(right_fork){
                
        color = WAIT_COLOR;
        philosopherstable.repaint();
        Sleep(WAIT_TIME);

        left_fork.BelongToPhil(current_x, current_y);
        Thread.yield();    
        right_fork.BelongToPhil(current_x, current_y);
            }}
    }

    private synchronized void eat() throws ResetException {

        synchronized(left_fork){   
            synchronized(right_fork){
        color = EAT_COLOR;
        philosopherstable.repaint();
        Sleep(EAT_TIME);

        left_fork.release();
        Thread.yield();    
        right_fork.release();
            }}
    }



   
}
