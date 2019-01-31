package paint.model;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import paint.controller.help;

public abstract class ShapeImplementation implements Shape {

	private Point position;// coordinates of upper left point (starting point) of the shape
	private Map<String, Double> properties;
	private Color color;
	private Color fillColor;
	private boolean filled;// boolean variable that determines wether the shape is filled ot not
	private int x1, x2, y1, y2;// coordinates from mouse
	private help h;

	public ShapeImplementation() {
		this.filled = false;
		this.x1 = 0;
		this.x2 = 0;
		this.y1 = 0;
		this.y2 = 0;
		this.color = Color.BLACK;
		this.fillColor = Color.BLACK;
		this.position = new Point(0, 0);
		properties = new HashMap<>();
		h = new help();
	}

	public help getH() {
		return h;
	}

	public void setH(help h) {
		this.h = h;
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public Point getUpperLeftPoint() {
		Point temp = new Point(100, 100);
		temp.x = Math.min(getX1(), getX2());
		temp.y = Math.min(getY1(), getY2());
		return temp;
	}

	public int calculateWidth() {
		return Math.abs(getX1() - getX2());
	}

	public int calculateHeight() {
		return Math.abs(getY1() - getY2());
	}

	@Override
	public void setPosition(Point position) {
		this.position = position;

	}

	@Override
	public Point getPosition() {

		return position;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		this.properties = properties;

	}

	@Override
	public Map<String, Double> getProperties() {

		return properties;
	}

	@Override
	public abstract Object clone() throws CloneNotSupportedException;

	@Override
	public void setColor(Color color) {
		this.color = color;

	}

	@Override
	public Color getColor() {

		return color;
	}

	@Override
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;

	}

	@Override
	public Color getFillColor() {
		return fillColor;
	}

	@Override
	public abstract void draw(Object canvas);

	@Override
	public abstract boolean containsPoint(Point mouse);

}
