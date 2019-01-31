package paint.model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Line extends ShapeImplementation {

    public Line() {
        super();
        getProperties().put("y2", 0.0);
    }

    @Override
    public void draw(Object canvas) {
        setPosition(new Point(getX1(),getY1()));
        setx2((double)getX2());
        sety2((double)getY2());
        ((Graphics2D) canvas).setColor(getColor());
        ((Graphics2D) canvas).drawLine(getPosition().x, getPosition().y, (int) getx2(), (int) gety2());
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        ShapeImplementation x = new Line();
        x.setX1(getX1());
        x.setY1(getY1());
        x.setX2(getX2());
        x.setY2(getY2());
        x.setColor(getColor());
        x.setPosition(getPosition());
        Map newprop = new HashMap<>();
        for (Map.Entry s : getProperties().entrySet()) {
            newprop.put(s.getKey(), s.getValue());
        }
        x.setProperties(newprop);
        return x;
    }
    @Override
    public boolean containsPoint(Point mouse)
    {
        return getH().withinLine(getPosition(),new Point((int)getx2(),(int)gety2()), mouse);
    }

}
