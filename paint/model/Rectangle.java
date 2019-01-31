package paint.model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Rectangle extends ShapeImplementation {

    public Rectangle() {
        super();
        getProperties().put("w", 0.0);
        getProperties().put("h", 0.0);
    }

    @Override
    public void draw(Object canvas) {
        setPosition(getUpperLeftPoint());
        setwidth((double) calculateWidth());
        setheight((double) calculateHeight());
        if (isFilled()) {
            ((Graphics2D) canvas).setColor(getFillColor());
            ((Graphics2D) canvas).fillRect(getPosition().x, getPosition().y, (int) getwidth(), (int) getheight());
        } else {
            ((Graphics2D) canvas).setColor(getColor());
            ((Graphics2D) canvas).drawRect(getPosition().x, getPosition().y, (int) getwidth(), (int) getheight());
        }

    }

    public void setwidth(double width) {
        getProperties().put("w", width);
    }

    public double getwidth() {
        return getProperties().get("w");
    }

    public void setheight(double height) {
        getProperties().put("h", height);
    }

    public double getheight() {
        return getProperties().get("h");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ShapeImplementation x = new Rectangle();
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
        return getH().inisideRectangle(getPosition(), mouse, getwidth(), getheight());
    }

}
