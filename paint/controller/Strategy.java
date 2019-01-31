package paint.controller;

import java.util.LinkedList;

import paint.model.Shape;

public interface Strategy {
	void save(Shape[] shapes, String path);

	LinkedList<Shape> load(String path);

}
