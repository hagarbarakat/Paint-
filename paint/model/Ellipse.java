package paint.model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Ellipse extends ShapeImplementation {

    public Ellipse() {
        super();
        getProperties().put("d1", 0.0);
        getProperties().put("d2", 0.0);
    }

    @Override
    public void draw(Object canvas) {
        setPosition(getUpperLeftPoint());
        setd1((double) calculateWidth());
        setd2((double) calculateHeight());
        if (isFilled()) {
            ((Graphics2D) canvas).setColor(getFillColor());
            ((Graphics2D) canvas).fillOval(getPosition().x, getPosition().y, (int) getd1(), (int) getd2());
        } else {
            ((Graphics2D) canvas).setColor(getColor());
            ((Graphics2D) canvas).drawOval(getPosition().x, getPosition().y, (int) getd1(), (int) getd2());
        }
    }

    public void setd1(double d1) {
        getProperties().put("d1", d1);
    }

    public double getd1() {
        return getProperties().get("d1");
    }

    public void setd2(double d2) {
        getProperties().put("d2", d2);
    }

    public double getd2() {
        return getProperties().get("d2");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ShapeImplementation x = new Ellipse();
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
        return getH().inisideEllipse(getPosition(), mouse, getd1(), getd2());
    }
}
