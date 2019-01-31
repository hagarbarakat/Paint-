package paint.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import paint.model.Circle;
import paint.model.Ellipse;
import paint.model.Line;
import paint.model.Rectangle;

import paint.model.Shape;
import paint.model.ShapeImplementation;
import paint.model.Square;
import paint.model.Triangle;

public class DrawingEngineImplementation implements DrawingEngine {
	private LinkedList<Shape> shapes = new LinkedList<>();
	private Stack<LinkedList<Shape>> undostack = new Stack<>();
	private Stack<LinkedList<Shape>> redostack = new Stack<>();

	private static DrawingEngineImplementation drawingEngineImplementation;

	public DrawingEngineImplementation() {

	}

	public static DrawingEngineImplementation getinstance() {
		if (drawingEngineImplementation == null) {
			drawingEngineImplementation = new DrawingEngineImplementation();
		}
		return drawingEngineImplementation;
	}
        @Override
        	 public void removeall (){
     undostack.add(copyshapeslist(shapes));
	shapes.removeAll(shapes);
 }
                 @Override
        public void rearrange(Shape shape){
        
        undostack.add(copyshapeslist(shapes));
		shapes.remove(shape);
                undostack.add(copyshapeslist(shapes));
                shapes.add(shape);
        }

	@Override
	public void refresh(Object canvas) {
		for (int i = 0; i < shapes.size(); i++) {
			shapes.get(i).draw(canvas);
		}
	}

	@Override
	public void addShape(Shape shape) {
		undostack.add(copyshapeslist(shapes));
		System.out.println("Undo" + undostack.toString());
		shapes.add(shape);
		System.out.println("List" + shapes.toString());
	}

	@Override
	public void removeShape(Shape shape) {
		undostack.add(copyshapeslist(shapes));
		shapes.remove(shape);

	}
       

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		undostack.add(copyshapeslist(shapes));
		shapes.remove(oldShape);
		shapes.add(newShape);

	}

	@Override
	public Shape[] getShapes() {

		return shapes.toArray(new Shape[shapes.size()]);
	}

	@Override
	public void undo() {
		if (!undostack.isEmpty()) {
			redostack.add(copyshapeslist(shapes));
			System.out.println("Redo" + redostack.toString());
			shapes = undostack.pop();
			System.out.println("List" + shapes.toString());

		}

	}
       
           public ShapeImplementation createCopy(ShapeImplementation Original) throws CloneNotSupportedException {
        ShapeImplementation Copy;
        if (Original instanceof Circle) {
            Copy = (Circle) Original.clone();
            Copy.setX2(Copy.calculateWidth());
            Copy.setX1(0);
            Copy.setY2(Copy.calculateWidth());
            Copy.setY1(0);
            return Copy;
        } else if (Original instanceof Ellipse) {
            Copy = (Ellipse) Original.clone();
            Copy.setX2(Copy.calculateWidth());
            Copy.setX1(0);
            Copy.setY2(Copy.calculateHeight());
            Copy.setY1(0);
            return Copy;
        } else if (Original instanceof Line) {
            Copy = (Line) Original.clone();
            Copy.setX1(Copy.getX1() + 20);
            Copy.setX2(Copy.getX2() + 20);
            Copy.setY1(Copy.getY1() + 20);
            Copy.setY2(Copy.getY2() + 20);
            return Copy;
        } else if (Original instanceof Rectangle) {
            Copy = (Rectangle) Original.clone();
            Copy.setX2(Copy.calculateWidth());
            Copy.setX1(0);
            Copy.setY2(Copy.calculateHeight());
            Copy.setY1(0);
            return Copy;
        } else if (Original instanceof Square) {
            Copy = (Square) Original.clone();
            Copy.setX2(Copy.calculateWidth());
            Copy.setX1(0);
            Copy.setY2(Copy.calculateWidth());
            Copy.setY1(0);
            return Copy;
        } else {
            Copy = (Triangle) Original.clone();
            Copy.setX1(Copy.calculateHeight());
            if (Copy.getY1() > Copy.getY2()) {
                Copy.setY1(Copy.calculateHeight());
                Copy.setY2(0);
            } else {
                Copy.setY2(Copy.calculateHeight());
                Copy.setY1(0);
            }
            return Copy;
        }
    }


	@Override
	public void redo() {
		if (!redostack.isEmpty()) {
			undostack.add(copyshapeslist(shapes));
			System.out.println("Undo" + undostack.toString());
			shapes = redostack.pop();
			System.out.println("List" + shapes.toString());
			System.out.println("Redo" + redostack.toString());

		}
	}

	@Override
	public void save(String path) {
		Strategy s;
		if (path.endsWith(".XML")) {
			s = new XMLstrategy();
		} else {
			s = new JASONstrategy();
		}
		s.save(getShapes(), path);

	}

	@Override
	public void load(String path) {
		Strategy s;
		if (path.endsWith(".XML")) {
			s = new XMLstrategy();
		} else {
			s = new JASONstrategy();
		}
		shapes = copyshapeslist(s.load(path));
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void installPluginShape(String jarPath) {
		// TODO Auto-generated method stub

	}

	private LinkedList<Shape> copyshapeslist(LinkedList<Shape> original) {
		LinkedList<Shape> copy = new LinkedList<>();
		for (int i = 0; i < original.size(); i++) {
			try {
				copy.add((Shape) original.get(i).clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return copy;

	}

	@Override
	public void undo(LinkedList<Shape> shapes) {
		// TODO Auto-generated method stub

	}

}
