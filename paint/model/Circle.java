package paint.model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Circle extends ShapeImplementation {

	public Circle() {
		super();
		getProperties().put("r", 0.0);
	}

	@Override
	public void draw(Object canvas) {
		setPosition(getUpperLeftPoint());
		setradius((double) (0.5 * calculateWidth()));
		if (isFilled()) {
			((Graphics2D) canvas).setColor(getFillColor());
			((Graphics2D) canvas).fillOval(getPosition().x, getPosition().y, (int) (2 * getradius()),
					(int) (2 * getradius()));
		} else {
			((Graphics2D) canvas).setColor(getColor());
			((Graphics2D) canvas).drawOval(getPosition().x, getPosition().y, (int) (2 * getradius()),
					(int) (2 * getradius()));
		}
	}

	public void setradius(double radius) {
		getProperties().put("r", radius);
	}

	public double getradius() {
		return getProperties().get("r");
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		ShapeImplementation x = new Circle();
		x.setX1(getX1());
		x.setY1(getY1());
		x.setX2(getX2());
		x.setY2(getY2());
		x.setColor(getColor());
		x.setFillColor(getFillColor());
		x.setPosition(getPosition());
		x.setFilled(isFilled());
		Map newprop = new HashMap<>();
		for (Map.Entry s : getProperties().entrySet()) {
			newprop.put(s.getKey(), s.getValue());
		}
		x.setProperties(newprop);
		return x;
	}

	@Override
	public boolean containsPoint(Point mouse) {
		return getH().inisideCircle(getPosition(), mouse, getradius());
	}

}
