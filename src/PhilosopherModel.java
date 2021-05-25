import java.awt.*;
import java.util.*;

class PhilosopherModel implements Runnable {
    private static final Color THINK_COLOR = Color.blue;
    private static final Color WAIT_COLOR = Color.red;
    private static final Color EAT_COLOR = Color.green;

    private static final double THINK_TIME = 2.0;
    private static final double WAIT_TIME = 2.0;
    private static final double EAT_TIME = 2.0;
    
    private Coordinator maincoordinator;
    private TableModel philosopherstable;
    
    private static final int XSIZE = 50;
    private static final int YSIZE = 50;
    
    private int current_x;
    private int current_y;
    
    private ForkModel left_fork;
    private ForkModel right_fork;
    
    private Random random;
    private Color color;

    

    public PhilosopherModel(TableModel T, int cx, int cy, ForkModel lf, ForkModel rf, Coordinator C) {
        philosopherstable = T;
        current_x = cx;
        current_y = cy;
        left_fork = lf;
        right_fork = rf;
        maincoordinator = C;
        random = new Random();
        color = THINK_COLOR;
    }

   

    public Random getRndom() {
        return random;
    }



    public void setRndom(Random rndom) {
        this.random = rndom;
    }



    public Color getOlor() {
        return color;
    }



    public void setOlor(Color olor) {
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



    public TableModel getPhilosopherstable() {
        return philosopherstable;
    }



    public void setPhilosopherstable(TableModel philosopherstable) {
        this.philosopherstable = philosopherstable;
    }



    public Coordinator getMainCoordinator() {
        return maincoordinator;
    }



    public void setMainCoordinator(Coordinator mainCoordinator) {
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
                 delay(EAT_TIME);

                think();
                if (getMainCoordinator().gate())
                
                delay(THINK_TIME);
                hunger();
                
                if (getMainCoordinator().gate())
                delay(WAIT_TIME);
                eat();

            } catch(ResetException e) { 
                color = THINK_COLOR;
                philosopherstable.repaint();
            }
        }
    }


    private static final double arbitary = 0.2;
    
    private void delay(double secs) throws ResetException {
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
        delay(THINK_TIME);
    }

    private synchronized void hunger() throws ResetException {
        synchronized(left_fork){   
            synchronized(right_fork){
                
        color = WAIT_COLOR;
        philosopherstable.repaint();
        delay(WAIT_TIME);

        left_fork.acquire(current_x, current_y);
        Thread.yield();    
        right_fork.acquire(current_x, current_y);
            }}
    }

    private synchronized void eat() throws ResetException {

        synchronized(left_fork){   
            synchronized(right_fork){
        color = EAT_COLOR;
        philosopherstable.repaint();
        delay(EAT_TIME);

        left_fork.release();
        Thread.yield();    
        right_fork.release();
            }}
    }



   
}
