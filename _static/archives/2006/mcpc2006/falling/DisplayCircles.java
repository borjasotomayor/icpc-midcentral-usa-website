/*  Graphical display of judge's data:
 Minimize windows you are done looking at (shows last dataset on top).
 Closing any display window closes the whole program.
*/
import java.util.List;
import java.awt.*;
import java.awt.event.*;

/************************************************************
  Display falling problem's circles.
 ***********************************************************/
public class DisplayCircles extends Frame {
  int dataSet;
  int thisDataSet;
  double mag = 10.0;
  int width;
  double height;
  int h;
  int x0 = 10;
  List<falling.Circle> all; 

  // first call for each dataset
  public DisplayCircles (int width_, double height_, int dataSet_,
                         List<falling.Circle> all_) {
    dataSet = dataSet_;
    width = width_;
    height = height_;
    all = all_;
    if (width > 100) mag = mag/2;
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
    int i = 1;   
    for (falling.Circle c : all) {
      int xs = scaleX(c.x), ys = scaleY(c.y), rs = scale(c.r);
      g.drawOval(xs - rs, ys - rs, 2*rs, 2*rs);
      g.drawString("" + i, xs-3, ys+5);
      i++;     
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

  /*
  void setMag() {
    mag = Math.min((getWidth() - 20)/ (minMax[1][0] - minMax[0][0]),
                   (getHeight() - 50) / (minMax[1][1] - minMax[0][1]));
  }
  */
}

