package paint.controller;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import paint.model.Circle;
import paint.model.Ellipse;
import paint.model.Line;
import paint.model.Rectangle;
import paint.model.Shape;
import paint.model.Square;
import paint.model.Triangle;

public class JASONstrategy implements Strategy {
	static StringBuilder builder = new StringBuilder();
	private static BufferedWriter bw = null;
	private static BufferedReader br = null;

	@Override
	public void save(Shape[] shapes, String path) {
		try {
			bw = new BufferedWriter(new FileWriter(path));
			bw.write("{\n");
			bw.write("\"shapes\": [\n");
			for (int i = 0; i < shapes.length; i++) {
				writeProperties(shapes[i]);
				if (i != shapes.length - 1) {
					bw.write("},\n");
				} else {
					bw.write("}\n");
				}
			}
			bw.write("]\n");
			bw.write("}");
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public LinkedList<Shape> load(String path) {
		LinkedList<Shape> shapes = new LinkedList<>();
		int counter = 0;

		try {
			br = new BufferedReader(new FileReader(path));
			String currentLine = new String();

			for (int i = 0; i < 2; i++) {
				builder.append(br.readLine());
			}
			if (!builder.toString().equals("nullnull")) {
				while (!((currentLine = br.readLine()).equals("]"))) {
					currentLine = br.readLine();
					Shape shape = getShape(getValue(currentLine));
					if (shape != null) {
						currentLine = br.readLine();
						int x = Integer.parseInt(getValue(currentLine));
						Color c = new Color(x);
						try {
							shape.setColor(c);
						} catch (Exception e) {

						}
						Point position = new Point();
						currentLine = br.readLine();
						Double positionX = Double.parseDouble(getValue(currentLine));
						currentLine = br.readLine();
						Double positionY = Double.parseDouble(getValue(currentLine));
						currentLine = br.readLine();
						position.setLocation(positionX.intValue(), positionY.intValue());
						shape.setPosition(position);
						shape.setFillColor(new Color(Integer.parseInt(getValue(currentLine))));
						currentLine = br.readLine();
						//shape.setFilled(Boolean.parseBoolean(currentLine));
						currentLine = br.readLine();
						Integer x1 = Integer.parseInt(getValue(currentLine));
						shape.setX1(x1);
						currentLine = br.readLine();
						Integer x2 = Integer.parseInt(getValue(currentLine));
						shape.setX2(x2);
						currentLine = br.readLine();
						Integer y1 = Integer.parseInt(getValue(currentLine));
						shape.setY1(y1);
						currentLine = br.readLine();
						Integer y2 = Integer.parseInt(getValue(currentLine));
						shape.setY2(y2);
						String property = br.readLine();
						Map<String, Double> properties = new HashMap<String, Double>();
						while (true) {
							property = br.readLine();
							if ((property.equals("}"))) {
								break;
							}
							String key = getKey(property);
							String value = getValue(property);
							properties.put(key, Double.parseDouble(value));
						}
						shape.setProperties(properties);
						shapes.add(shape);
						currentLine = br.readLine();
					} else if (shape == null && counter == 0) {
						shapes.add(shape);
						counter = 1;
					}
				}
			}
			br.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return shapes;
	}

	private static void writeProperties(Shape shape) throws IOException {
		bw.write("{\n");
		writeKeyValue("Kind", shape.getClass().getSimpleName());
		bw.write(",\n");
		try {
			writeKeyValue("Color", String.valueOf(shape.getColor().getRGB()));
		} catch (Exception e) {
			shape.setColor(Color.black);
			writeKeyValue("Color", String.valueOf(shape.getColor().getRGB()));
		}
		bw.write(",\n");
		try {
			writeKeyValue("PositionX", String.valueOf(shape.getPosition().getX()));
		} catch (Exception e) {
			shape.setPosition(new Point(0, 0));
			writeKeyValue("PositionX", String.valueOf(shape.getPosition().getX()));
		}
		bw.write(",\n");
		writeKeyValue("PositionY", String.valueOf(shape.getPosition().getY()));
		bw.write(",\n");
		try {
			writeKeyValue("FillColor", String.valueOf(shape.getFillColor().getRGB()));
		} catch (Exception e) {
			shape.setFillColor(Color.WHITE);
			writeKeyValue("FillColor", String.valueOf(shape.getFillColor().getRGB()));
		}
		bw.write(",\n");
		//writeKeyValue("isFilled", String.valueOf(shape.isFilled()));
		bw.write(",\n");
		writeKeyValue("x1", String.valueOf(shape.getX1()));
		bw.write(",\n");
		writeKeyValue("x2", String.valueOf(shape.getX2()));
		bw.write(",\n");
		writeKeyValue("y1", String.valueOf(shape.getY1()));
		bw.write(",\n");
		writeKeyValue("y2", String.valueOf(shape.getY2()));
		Map<String, Double> properties = shape.getProperties();
		if (properties != null) {
			bw.write(",\n");
			bw.write("\"properties\": {\n");
			Set<String> keys = properties.keySet();
			int counter = 0;
			for (String key : keys) {
				counter++;
				writeKeyValue(key, "" + properties.get(key));
				if (counter != keys.size()) {
					bw.write(",\n");
				}
			}
			bw.write("\n");
			bw.write("}\n");
		}

	}

	private static void writeKeyValue(String key, String value) {
		try {
			bw.write("\"" + key + "\":" + "\"" + value + "\"");
		} catch (IOException e) {

			throw new RuntimeException("error");
		}
	}

	private static String getValue(String string) {
		String[] splited = string.split(":");
		String value = splited[1];
		if (value.charAt(value.length() - 1) == ',') {
			value = value.substring(1, value.length() - 2);
		} else {
			value = value.substring(1, value.length() - 1);
		}
		return value;
	}

	private static String getKey(String string) {
		String[] splited = string.split(":");
		String value = splited[0];
		value = value.substring(1, value.length() - 1);
		return value;
	}

	private Shape getShape(String nodeName) {
		if (nodeName.equals("Rectangle"))
			return new Rectangle();
		else if (nodeName.equals("Line"))
			return new Line();
		else if (nodeName.equals("Triangle"))
			return new Triangle();
		else if (nodeName.equals("Circle"))
			return new Circle();
		else if (nodeName.equals("Square"))
			return new Square();
		else
			return new Ellipse();

	}

}
