package paint.model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Square extends ShapeImplementation {

    public Square() {
        super();
        getProperties().put("s", 0.0);
    }

    @Override
    public void draw(Object canvas) {
        setPosition(getUpperLeftPoint());
        setside((double) calculateWidth());
        if (isFilled()) {
            ((Graphics2D) canvas).setColor(getFillColor());
            ((Graphics2D) canvas).fillRect(getPosition().x, getPosition().y, (int) getside(), (int) getside());
        } else {
            ((Graphics2D) canvas).setColor(getColor());
            ((Graphics2D) canvas).drawRect(getPosition().x, getPosition().y, (int) getside(), (int) getside());
        }
    }

    public void setside(double side) {
        getProperties().put("s", side);
    }

    public double getside() {
        return getProperties().get("s");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ShapeImplementation x = new Square();
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
        return getH().inisideSquare(getPosition(), mouse, getside());
    }
}
