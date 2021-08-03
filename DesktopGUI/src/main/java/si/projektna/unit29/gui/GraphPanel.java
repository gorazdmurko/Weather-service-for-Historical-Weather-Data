package si.projektna.unit29.gui;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;


public class GraphPanel extends JPanel {
    
    private Float[] tempsArray;
    private boolean cakeGraph;

    // constructor
    public GraphPanel() {
        
        tempsArray = null;

    }

    // our method
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        this.setBackground(Color.BLACK);

        int xPanel = this.getWidth();       // width / height of panel
        int yPanel = this.getHeight();
        int xStart = 30;
        int yStart = yPanel - yPanel/4;     // starting X & Y points of coordinate system
        int xEnd = xPanel - 20;
        int yEnd = 20;                      // ending X & Y points of coordinate system
        int xsize = xEnd - xStart;          // X size of co.sys.
        int ysize = yStart - yEnd;          // y size of co.sys.

        int dx = (xsize) / 24;  // dx
        int dy = (ysize) / 8;  // dy

        // draw grid
        g2d.setColor(Color.GREEN.darker());   // color of co.sys. x,y
        g2d.drawLine(xStart, yStart, xStart, yEnd);  // y-line of co.sys.
        g2d.drawLine(xStart, yStart, xEnd, yStart);  // x-line of co.sys

        // x - line
        for (int i = 0; i < 24; i++) {
            int x = xStart + dx*i + dx/2;
            g2d.drawLine(x, yStart + 5, x, yStart - 5);
            String hoursStr = Integer.toString(i + 1);
            g2d.drawString(hoursStr, x - dx / 10, yStart + 20);
        }
        // y - line
        for (int i = 1; i <= 8; i++) {
            int y = yStart - dy*i;
            g2d.drawLine(xStart - 5, y, xStart + 5, y);
            String tempStr = Integer.toString(i * 5);
            g2d.drawString(tempStr, xStart - 25, y + dy / 10);
        }

    }

    private void drawGraph(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        int xPanel = this.getWidth();
        int yPanel = this.getHeight();
        int xStart = 30;
        int yStart = yPanel - yPanel/4;
        int xEnd = xPanel - 20;
        int yEnd = 20;
        int xSize = xEnd - xStart;
        int ySize = yStart - yEnd;
        int dx = (xSize) / 24;  // dx
        int dy = (ySize) / 8;  // dy

        if (tempsArray != null) {
            for (int i = 0; i < tempsArray.length && i < 24; i++) {
                float temp = (float) tempsArray[i];
                int x = xStart + dx*i +1;
                if (temp > 0) {
                    int height = (int) (dy*temp)/5 - 1;
                    // g2D.setColor(Color.magenta);
                    g2D.setColor(new ColorUIResource(25, 109, 187));
                    g2D.fillRect(x, yStart - height, dx - 5, height);
                } else if (temp < 0) {
                    int height = (int) (dy*temp)/5 + 1;     // height < 0 -> negative
                    // g2D.setColor(Color.blue);
                    g2D.setColor(new ColorUIResource(181, 247, 225));
                    g2D.fillRect(x, yStart + 1, dx - 5, -height);

                    g2D.setColor(Color.yellow);
                    String hours = Integer.toString(i + 1);
                    g2D.drawString(hours, x + dx/3, yStart - 10);
                }
            }
        }
    }

    private void drawPoly(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        if (tempsArray != null) {
            int xPanel = getWidth();
            int yPanel = getHeight();
            int xStart = 30;
            int yStart = yPanel - yPanel / 4;
            int xEnd = xPanel - 20;
            int yEnd = 20;
            int dx = (xEnd - xStart) / 24;
            int dy = (yStart - yEnd) / 8;

            int elements = tempsArray.length;
            // float[] xPoints = new float[elements];
            float[] yPoints = new float[elements];
            int[] xPts = new int[elements];     // ure
            int[] yPts = new int[elements];     // temperature

            // iz 'ArrayList' dam temperature v 'array' !!
            for (int i = 0; i < elements; i++) {
                yPoints[i] = tempsArray[i];
            }

            // temperature pretvorim iz float v int !!
            for (int i = 0; i < elements; i++) {
                yPts[i] = (int) yPoints[i];
                // xPts[i] = (int) xPoints[i];
            }

            for (int i = 0; i < elements; i++) {
                int x = xStart + dx * i + dx / 2;
                xPts[i] = x;

                int temp = yPts[i];
                int height = temp * dy / 5;

                yPts[i] = yStart - height;
            }

            g2D.setColor(Color.magenta);
            g2D.setStroke(new BasicStroke(3));
            g2D.drawPolyline(xPts, yPts, elements);
        }
    }

 // ----------------------------------------------------------------
    @Override
    protected void paintComponent(Graphics g) {     // this method already exists in JPanel class   ->   always called by itself
        super.paintComponent(g);                    // it needs to call super w/Graphics object as parameter
        doDrawing(g);
        if(cakeGraph) {
            drawGraph(g);
        } else {
            drawPoly(g);
        }
    }
// ----------------------------------------------------------------
    public void setTemperatures(Float[] tempsArray) {
        this.tempsArray = tempsArray;
        repaint();      // revalidate(); invalidate(); validate(); updateUI();
    }

    public Float[] getTemperatures() {
        return tempsArray;
    }

    public void setCakeGraph() {
        this.cakeGraph = !this.cakeGraph;
        repaint();
    }

    public void setDefaultCakeGraph() {
        this.cakeGraph = true;
    }

}

