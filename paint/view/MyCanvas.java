/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.view;

import paint.model.Line;
import paint.model.Ellipse;
import paint.model.ShapeImplementation;
import paint.model.Rectangle;
import paint.model.Circle;
import paint.model.Square;
import paint.model.Triangle;
import java.awt.Color;
;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import paint.model.*;
import java.awt.Graphics;
import java.awt.Point;

import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collection;
import static javafx.scene.input.KeyCode.H;
import static javafx.scene.input.KeyCode.W;
import paint.controller.DrawingEngineImplementation;

/**
 * f
 *
 * @author lap
 */


public class MyCanvas extends JPanel implements MouseListener, MouseMotionListener {

   
    public ArrayList<ShapeImplementation> shapeArray;//ArrayList of shapes
    private LinkedList<ShapeImplementation> myShapes; //dynamic stack of shapes
    private LinkedList<ShapeImplementation> clearedShapes; //dynamic stack of cleared shapes from undo    
    //current Shape variables
    private int currentShapeType;
    private boolean isSelected;
    
    private ShapeImplementation newShapeObject;
    private ShapeImplementation currentShapeObject;//stores the current shape object
    private int currentMode;//1 for drawing, 2 for selection
    DrawingEngineImplementation DE = new DrawingEngineImplementation();
     private Color currentShapeColor; //current shape color
    private boolean currentShapeFilled; //determine whether shape is filled or not
 private Point p1, p2, p3,start1,start2;
      private int index;

    public void print() {
        System.out.println(shapeArray.size());
    }

    public int getCurrentMode() {
        return currentMode;
    }

    public void setCurrentShapeObjectNull() {
        currentShapeObject = null;
    }

    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
    }
  
  

    public MyCanvas() {
        //Initialize current Shape variables
        currentShapeType = 1;
        currentMode = 1;
        currentShapeObject = null;
    newShapeObject=null;
        isSelected = false;
        currentShapeColor = Color.BLACK;
        currentShapeFilled = false;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
         p1 = new Point();
        p2 = new Point();
        p3 = new Point();
        shapeArray = new ArrayList<ShapeImplementation>();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        /*g.clearRect(0, 0, this.getWidth(), this.getHeight());
        //for (int counter = shapeArray.size() - 1; counter >= 0; counter--) 
        for(int counter=0; counter<shapeArray.size(); counter++) {
           shapeArray.get(counter).draw(g);*/
        DrawingEngineImplementation.getinstance().refresh(g);
        if (currentShapeObject != null) {
            if (currentMode == 2) {
                currentShapeObject.setColor(currentShapeColor);
                currentShapeObject.setFillColor(currentShapeColor);
            }
            currentShapeObject.draw(g);
        }

        if (currentShapeObject != null) {
            currentShapeObject.draw(g);

        }
        repaint();
        //System.out.println("here");
    }

    public int getCurrentShapeType() {
        return currentShapeType;
    }

    public void setCurrentShapeType(int currentShapeType) {
        this.currentShapeType = currentShapeType;
    }

    public ShapeImplementation getCurrentShapeObject() {
        return currentShapeObject;
    }

    //Factory DP
    public void setCurrentShapeObject() {
        switch (currentShapeType) {

            case 1:
                currentShapeObject = new Circle();
                break;
            case 2:
                currentShapeObject = new Line();
                break;
            case 3:
                currentShapeObject = new Rectangle();
                break;
            case 4:
                currentShapeObject = new Triangle();
                break;
            case 5:
                currentShapeObject = new Ellipse();
                break;
            case 6:
                currentShapeObject = new Square();
                break;

            default:
                currentShapeObject = null;
        }
    }



    public Color getCurrentShapeColor() {
        return currentShapeColor;
    }

    public void setCurrentShapeColor(Color currentShapeColor) {
        this.currentShapeColor = currentShapeColor;
    }

    public boolean isCurrentShapeFilled() {
        return currentShapeFilled;
    }

    public void setCurrentShapeFilled(boolean currentShapeFilled) {
        this.currentShapeFilled = currentShapeFilled;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (currentMode == 1) {
            if (currentShapeObject == null) {
                setCurrentShapeObject();
                currentShapeObject.setX1(e.getX());
                currentShapeObject.setY1(e.getY());
                currentShapeObject.setFilled(isCurrentShapeFilled());
                currentShapeObject.setColor(currentShapeColor);
                currentShapeObject.setFillColor(currentShapeColor);
            } else {
                if (currentShapeType != 0) {
                    currentShapeObject.setX2(e.getX());
                    currentShapeObject.setY2(e.getY());
                }
            }
        } else if (currentMode == 2) {

 ArrayList<Shape> shapeArray = new ArrayList<>(
                    Arrays.asList(DrawingEngineImplementation.getinstance().getShapes()));
            for (int counter = shapeArray.size() - 1; counter >= 0; counter--) {
                if (shapeArray.get(counter).containsPoint(new Point(e.getX(), e.getY()))) {
                    setCurrentShapeColor(shapeArray.get(counter).getColor());
                              currentShapeObject = (ShapeImplementation) shapeArray.get(counter);
                    setIndex(counter);
                    DrawingEngineImplementation.getinstance().rearrange(currentShapeObject);
                                        
                }
                         

            }}
         else if (currentMode == 3) {
             
            for (int counter = shapeArray.size() - 1; counter >= 0; counter--) {
                if (shapeArray.get(counter).containsPoint(new Point(e.getX(), e.getY()))) {
                    setCurrentShapeColor(shapeArray.get(counter).getColor());
                    currentShapeObject= shapeArray.get(counter);
                             DrawingEngineImplementation.getinstance().rearrange(currentShapeObject);
                   
                }
            }

     /*   } else if (currentMode == 4) {
            if (currentShapeObject != null && (isSelected)) {
                DrawingEngineImplementation.getinstance().removeShape(currentShapeObject);
                DrawingEngineImplementation.getinstance().redo();
                currentShapeObject = null;
                isSelected = false;

            }*/
        } else if (currentMode == 5) {
            if (currentShapeObject != null) {

                try {
                    currentShapeObject = (ShapeImplementation) currentShapeObject.clone();
                    DrawingEngineImplementation.getinstance().addShape(currentShapeObject);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(MyCanvas.class.getName()).log(Level.SEVERE, null, ex);
                }
                repaint();
            }

        } else if (currentMode == 6) {
           p1.setLocation(e.getPoint());
            start1=new Point(currentShapeObject.getX1(),currentShapeObject.getY1());
            start2=new Point(currentShapeObject.getX2(),currentShapeObject.getY2());
            DrawingEngineImplementation.getinstance().rearrange(currentShapeObject);
        }

        repaint();
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentMode == 1) {
            if (currentShapeObject != null) {
                currentShapeObject.setX2(e.getX());
                currentShapeObject.setY2(e.getY());
                currentShapeObject.setFilled(isCurrentShapeFilled());
                //  shapeArray.add(currentShapeObject);
                DrawingEngineImplementation.getinstance().addShape(currentShapeObject);
                currentShapeObject = null;
            }
        }
        
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentMode == 6) {
            p2.setLocation(e.getPoint());
            p3.x = p2.x - p1.x;
            p3.y = p2.y - p1.y;
            if (currentShapeObject != null) {
                currentShapeObject.setX1(start1.x + p3.x);
                currentShapeObject.setY1(start1.y + p3.y);
                currentShapeObject.setX2(start2.x + p3.x);
                currentShapeObject.setY2(start2.y + p3.y);
            }

        } else {
            if (currentShapeObject != null) {
                currentShapeObject.setX2(e.getX());
                currentShapeObject.setY2(e.getY());
                         //DrawingEngineImplementation.getinstance().rearrange(currentShapeObject);
            }
        }
        repaint();
    }
     public void creatingClone() throws CloneNotSupportedException {
        DrawingEngineImplementation.getinstance().addShape(DrawingEngineImplementation.getinstance().createCopy(currentShapeObject));
        ArrayList<Shape> shapeArray = new ArrayList<>(
                Arrays.asList(DrawingEngineImplementation.getinstance().getShapes()));
        setCurrentShapeColor(shapeArray.get(shapeArray.size() - 1).getColor());
        currentShapeObject = (ShapeImplementation) shapeArray.get(shapeArray.size() - 1);
        repaint();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public void repain() {

        System.out.println("Test");
        for (int counter = DrawingEngineImplementation.getinstance().getShapes().length - 1; counter >= 0; counter--) {
            DrawingEngineImplementation.getinstance().getShapes()[counter].draw(this.getGraphics());
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void getCurrentShapeColor(JColorChooser colorChooser) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
