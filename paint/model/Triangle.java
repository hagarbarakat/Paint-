package paint.model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Triangle extends ShapeImplementation {

    public Triangle() {
        super();
        getProperties().put("x2", 0.0);
        getProperties().put("y2", 0.0);
        getProperties().put("x3", 0.0);
        getProperties().put("y3", 0.0);
    }

    @Override
    public void draw(Object canvas) {
        setPosition(new Point(getX1() + calculateHeight(), getY1()));
        setx2(getX1() - calculateHeight());
        sety2(getY1());
        setx3(getX1());
        sety3(getY2());

        if (isFilled()) {
            ((Graphics2D) canvas).setColor(getFillColor());
            ((Graphics2D) canvas).fillPolygon(new int[]{getPosition().x, (int) getx2(), (int) getx3()},
                    new int[]{getPosition().y, (int) gety2(), (int) gety3()}, 3);
        } else {
            ((Graphics2D) canvas).setColor(getColor());
            ((Graphics2D) canvas).drawPolygon(new int[]{getPosition().x, (int) getx2(), (int) getx3()},
                    new int[]{getPosition().y, (int) gety2(), (int) gety3()}, 3);
        }
    }

    public void setx2(double x2) {
        getProperties().put("x2", x2);
    }

    public double getx2() {
        return getProperties().get("x2");
    }

    public void sety2(double y2) {
        getProperties().put("y2", y2);
    }

    public double gety2() {
        return getProperties().get("y2");
    }

    public void setx3(double x3) {
        getProperties().put("x3", x3);
    }

    public double getx3() {
        return getProperties().get("x3");
    }

    public void sety3(double y3) {
        getProperties().put("y3", y3);
    }

    public double gety3() {
        return getProperties().get("y3");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ShapeImplementation x = new Triangle();
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
        return getH().insideTriangle(getPosition(), new Point((int) getx2(), (int) gety2()), new Point((int) getx3(), (int) gety3()), mouse);
    }

}
