import java.awt.*;
import java.util.*; 

public class PhilospherView {

    public void draw(Graphics g, Color color, int current_x, int XSIZE, int current_y, int YSIZE) {
        g.setColor(color);
        g.fillOval(current_x-XSIZE/2, current_y-YSIZE/2, XSIZE, YSIZE);
    }
  
}
