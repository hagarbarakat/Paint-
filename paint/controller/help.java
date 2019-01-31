/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.controller;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.Polygon;

public class help {

    public boolean insideTriangle(Point One, Point Two, Point Three, Point mouse) {
        Polygon T = new Polygon(new int[]{One.x, Two.x, Three.x}, new int[]{One.y, Two.y, Three.y}, 3);
        return T.contains(mouse);
    }

    public boolean inisideRectangle(Point UpperLeftPoint, Point mouse, double width, double height) {
        Rectangle R = new Rectangle(UpperLeftPoint.x, UpperLeftPoint.y, (int) width, (int) height);
        return R.contains(mouse);
    }

    public boolean inisideSquare(Point UpperLeftPoint, Point mouse, double sidelength) {
        Rectangle S = new Rectangle(UpperLeftPoint.x, UpperLeftPoint.y, (int) sidelength, (int) sidelength);
        return S.contains(mouse);
    }

    public boolean inisideEllipse(Point UpperLeftPoint, Point mouse, double d1, double d2) {
        Ellipse2D E = new Ellipse2D.Double(UpperLeftPoint.x, UpperLeftPoint.y, (int) d1, (int) d2);
        return E.contains(mouse);
    }

    public boolean inisideCircle(Point UpperLeftPoint, Point mouse, double radius) {
        Ellipse2D C = new Ellipse2D.Double(UpperLeftPoint.x, UpperLeftPoint.y, (int) (2 * radius), (int) (2 * radius));
        return C.contains(mouse);
    }

    public boolean withinLine(Point One, Point Two, Point mouse) {
        if ((mouse.x >= One.x && mouse.x <= Two.x) && (mouse.y >= One.y && mouse.y <= Two.y)) {
            return true;
        } else if ((mouse.x <= One.x && mouse.x >= Two.x) && (mouse.y <= One.y && mouse.y >= Two.y)) {
            return true;
        } else if ((mouse.x >= One.x && mouse.x <= Two.x) && (mouse.y <= One.y && mouse.y >= Two.y)) {
            return true;
        } else if ((mouse.x <= One.x && mouse.x >= Two.x) && (mouse.y >= One.y && mouse.y <= Two.y)) {
            return true;
        } else if ((One.x == Two.x) && (mouse.y >= One.y && mouse.y <= Two.y)) {
            return true;
        } else if ((mouse.x <= One.x && mouse.x >= Two.x) && (One.y == Two.y)) {
            return true;
        } else {
            return false;
        }

    }

}
