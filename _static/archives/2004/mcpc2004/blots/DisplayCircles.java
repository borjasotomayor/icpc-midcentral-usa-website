/*  Graphical display of judge's data:
 Shows all datasets, either at a scale to see all circles,
 or shows additional blown up details if necessary.
 The user can resize the display windows to see more detail.
 Minimize windows you are done looking at (shows last dataset on top).
 Closing any display window closes the whole program.
*/
import java.awt.*;
import java.awt.event.*;

/************************************************************
  Display blots problem's circles.
  Show extra blown up views when scales vary enormously.
 ***********************************************************/
public class DisplayCircles extends Frame {
  final int WIDTH = 600;
  final int HEIGHT = 600;
  double[] x;
  double[] y;
  double[] r;
  int n;
  double mag;
  double[][] minMax; // [min-max][x-y]

  // first call for each dataset
  public DisplayCircles (blots.Disk[] disk, int nn, int dataSet) {
    this(disk, nn, dataSet, null, "");
  }

  // called with special labels and regions for detail views
  public DisplayCircles (blots.Disk[] disk, int nn, int dataSet,
                       double[][] mMax, String label) {
    n = nn;
    x = new double[n]; y = new double[n]; r = new double[n];
    for (int i = 0; i < n; i++) {
      x[i] = disk[i].x; y[i] = disk[i].y; r[i] = disk[i].r;
    }
    setSize(WIDTH, HEIGHT); // resizable
    if (mMax == null) {
      minMax = calcMinMax(0, n); // whole dataset
      if (x[0] == 300000) { // dataset with nested similar shapes
        double[][] mM = new double[][] {{455190, 455190}, {455380, 455380}};
        (new DisplayCircles(disk, nn, dataSet, mM, "Nest 2")).setVisible(true);
        mM = new double[][] {{450000, 450000}, {468000, 468000}};
        (new DisplayCircles(disk, nn, dataSet, mM, "Nest 1")).setVisible(true);
      }
      else if (x[0] == 11000) { // dataset with tiny protrusions on big circle
        double[][] mM = new double[][] {{248400, 356700}, {248800, 357100}};
        (new DisplayCircles(disk,nn,dataSet,mM, "Lower Blob 2")).setVisible(true);
        mM = calcMinMax(4, 6);
        (new DisplayCircles(disk,nn,dataSet,mM, "Lower Blob 1")).setVisible(true);
        mM = new double[][] {{10840, 989825}, {11160, 990045}};
        (new DisplayCircles(disk,nn,dataSet, mM, "Left Blob 2")).setVisible(true);
        mM = calcMinMax(11, 13);
        (new DisplayCircles(disk,nn,dataSet, mM, "Left Blob 1")).setVisible(true);
      }
      else if (x[0] == 500000) { // circles splitting holes three ways
        double[][] mM = new double[][] {{710000, 440000}, {790000, 520000}};
        (new DisplayCircles(disk,nn,dataSet,mM, "Below Middle")).setVisible(true);
      }
    }
    else
      minMax = mMax;  // special case view

    setTitle("Dataset "+dataSet + " " + label +
             " x: " + (int)minMax[0][0] + " to " + (int)minMax[1][0] +
             " y: " + (int)minMax[0][1] + " to " + (int)minMax[1][1] );
    addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {System.exit(0);}
        public void windowDeiconified(WindowEvent e) {repaint();}
      });
    addComponentListener(new ComponentAdapter() {
         public void componentResized(ComponentEvent e){repaint();}
      });
  }

  double[][] calcMinMax(int startI, int pastI) {
    double maxX = 0, maxY = 0, minX = 2000000, minY = minX;
    for(int i = startI ; i < pastI; i++) {
      double d = x[i] + r[i];
      if (d > maxX) maxX = d;
      d = y[i] + r[i];
      if (d > maxY) maxY = d;
      d = x[i] - r[i];
      if (d < minX) minX = d;
      d = y[i] - r[i];
      if (d < minY) minY = d;
    }
    return new double[][]{{minX, minY}, {maxX, maxY}};
  }

  public void paint(Graphics g) {
    g.setColor(Color.black);
    setMag();
    int h = getHeight() - 10;
    for (int i = n - 1; i >= 0; i--) {
      int xs = scaleX(x[i]), ys = scaleY(y[i]), rs = scale(r[i]);
      g.drawOval(10+ xs-rs, h - ys - rs, 2*rs, 2*rs);
    }
  }

  int toInt(double x) {
    return (int)(x+.5);
  }

  int scale(double v) {
    return toInt(v * mag);
  }

  int scaleX(double v) {
    return toInt((v-minMax[0][0]) * mag);
  }

  int scaleY(double v) {
    return toInt((v-minMax[0][1]) * mag);
  }

  void setMag() {
    mag = Math.min((getWidth() - 20)/ (minMax[1][0] - minMax[0][0]),
                   (getHeight() - 50) / (minMax[1][1] - minMax[0][1]));
  }
}

