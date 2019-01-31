package paint.model;

import java.awt.Point;

public interface Shape {
	public void setPosition(java.awt.Point position);

	public java.awt.Point getPosition();

	/* update shape specific properties (e.g., radius) */
	public void setProperties(java.util.Map<String, Double> properties);

	public java.util.Map<String, Double> getProperties();

	public void setColor(java.awt.Color color);

	public java.awt.Color getColor();

	public void setFillColor(java.awt.Color color);

	public java.awt.Color getFillColor();

	public int getX1();

	public void setX1(int x1);

	public int getX2();

	public void setX2(int x2);

	public int getY1();

	public void setY1(int y1);

	public int getY2();

	public void setY2(int y2);

	/*
	 * redraw the shape on the canvas, for swing, you will cast canvas to
	 * java.awt.Graphics
	 */
	public void draw(Object canvas);

	/* create a deep clone of the shape */
	public Object clone() throws CloneNotSupportedException;

	public boolean containsPoint(Point mouse);
}