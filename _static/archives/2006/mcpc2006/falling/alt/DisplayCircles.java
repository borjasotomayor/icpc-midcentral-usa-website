/*  Graphical display of judge's data:
 Shows all datasets, either at a scale to see all circles,
 or shows additional blown up details if necessary.
 The user can resize the display windows to see more detail.
 Minimize windows you are done looking at (shows last dataset on top).
 Closing any display window closes the whole program.
*/
import java.util.List;
import java.awt.*;
import java.awt.event.*;

/************************************************************
  Display falling problem's circles.
  Show extra blown up views when scales vary enormously.
 ***********************************************************/
public class DisplayCircles extends Frame {
    int dataSet;
    int thisDataSet;
    double mag = 10.0;
    int width;
    double height;
    int h;
    int x0 = 10;
    falling.Ice[] c;
    int n;

  // first call for each dataset
    public DisplayCircles (int width_, double height_, int dataSet_, falling.Ice[] all_, int n_ ) {
    dataSet = dataSet_;
    width = width_;
    height = height_;
    c = all_;
    n = n_;
    setSize(Math.max(300, 2*x0 + scale(width)), 2*x0 + scale(height) + 60); // resizable
    setTitle("Dataset "+ dataSet);
    addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {System.exit(0);}
        public void windowDeiconified(WindowEvent e) {repaint();}
      });
    addComponentListener(new ComponentAdapter() {
         public void componentResized(ComponentEvent e){repaint();}
      });
  }

  public void paint(Graphics g) {
    h = getHeight() - 10;
    g.setColor(Color.black);
    scaleLine(g, 0, 0, 0, height);
    scaleLine(g, width, 0, width, height);
    scaleLine(g, 0, 0, width, 0);
    g.drawString(String.format("Data Set: %d  Height: %.2f (raw: %f)",
                               dataSet, height, height), 15, 60);
    for ( int i = 0; i < n; i++ ) {
      int xs = scaleX(c[i].x), ys = scaleY(c[i].y), rs = scale(c[i].r);
      g.drawOval(xs - rs, ys - rs, 2*rs, 2*rs);
      g.drawString("" + i, xs-3, ys+5);
    }
  }

  void scaleLine(Graphics g, double x1, double y1, double x2, double y2) {
      g.drawLine(scaleX(x1), scaleY(y1), scaleX(x2), scaleY(y2));
  }
  
  int toInt(double x) {
    return (int)(x+.5);
  }

  int scale(double v) {
    return toInt(v * mag);
  }

  int scaleX(double v) {
    return toInt(x0 + v * mag);
  }

  int scaleY(double v) {
    return toInt(h - v * mag);
  }

}

