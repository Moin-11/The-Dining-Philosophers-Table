import java.awt.*;

class ForkModel {

    private TableCanvas maintable;
    private static final int radius = 10;
    private Color color = Color.black; 
    
    /* Stores the value of orignal current_x and y */
    private int orig_x;
    private int orig_y; 

    /*Stores the current coordinates of forks */
    private int current_x;
    private int current_y;

    public ForkModel(TableCanvas T, int cx, int cy) {
        maintable = T;

        orig_x = cx;
        orig_y = cy;
        
        current_x = cx;
        current_y = cy;
    }

    public Color getC() {
        return color;
    }

    public void setC(Color c) {
        this.color = c;
    }

    public void reset() {
        clear();
        current_x = orig_x;
        current_y = orig_y;
        this.color = Color.black;
        maintable.repaint();
    }

    
    public boolean BelongToPhil(int phil_x, int phil_y) {
        clear();
        current_x = (orig_x + phil_x)/2;
        current_y = (orig_y + phil_y)/2;
        this.color = Color.green;
        maintable.repaint();

        return true;
    }

    public void release() {
        reset();
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(current_x-radius/2, current_y-radius/2, radius, radius);
    }
    
    private void clear() {
        Graphics g = maintable.getGraphics();
        g.setColor(maintable.getBackground());
        g.fillOval(current_x-radius/2, current_y-radius/2, radius, radius);
    }
}

