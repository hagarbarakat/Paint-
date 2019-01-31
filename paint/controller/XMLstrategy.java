package paint.controller;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import paint.model.Circle;
import paint.model.Ellipse;
import paint.model.Line;
import paint.model.Rectangle;
import paint.model.Shape;
import paint.model.ShapeImplementation;
import paint.model.Square;
import paint.model.Triangle;

public class XMLstrategy implements Strategy {

	@Override
	public void save(Shape[] shapes, String path) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document d = db.newDocument();
			Element root = d.createElement("Shapes");
			for (int i = 0; i < shapes.length; i++) {
				Element e = d.createElement(shapes[i].getClass().getSimpleName());
				e.setAttribute("colour", String.valueOf(shapes[i].getColor().getRGB()));

				e.setAttribute("fillcolour", String.valueOf(shapes[i].getFillColor().getRGB()));
				e.setAttribute("positionX", String.valueOf(shapes[i].getPosition().x));
				e.setAttribute("positionY", String.valueOf(shapes[i].getPosition().y));
				e.setAttribute("x1", String.valueOf(shapes[i].getX1()));
				e.setAttribute("y1", String.valueOf(shapes[i].getY1()));
				e.setAttribute("x2", String.valueOf(shapes[i].getX2()));
				e.setAttribute("y2", String.valueOf(shapes[i].getY2()));
				Map<String, Double> map = shapes[i].getProperties();
				if (map != null) {
					for (Map.Entry<String, Double> entry : map.entrySet()) {
						Element eentry = d.createElement(entry.getKey());
						eentry.appendChild(d.createTextNode(entry.getValue().toString()));
						e.appendChild(eentry);

					}
				}
				root.appendChild(e);

			}
			d.appendChild(root);
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			t.transform(new DOMSource(d), new StreamResult(new FileOutputStream(path)));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public LinkedList<Shape> load(String path) {
		LinkedList<Shape> shapes = new LinkedList<>();
		Document dom;
		File file = new File(path);
		InputStream inputStream;
		Reader reader = null;
		try {
			inputStream = new FileInputStream(file);
			try {
				reader = new InputStreamReader(inputStream, "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InputSource is = new InputSource(reader);
		is.setEncoding("ISO-8859-1");

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			dom = builder.parse(is);

			Element root = dom.getDocumentElement();
			NodeList shapesNodes = root.getChildNodes();
			for (int i = 0; i < shapesNodes.getLength(); i++) {
				Node shapeNode = shapesNodes.item(i);
				ShapeImplementation shape = getShape(shapeNode.getNodeName());
				if (shape != null) {
					NodeList propNodes = shapeNode.getChildNodes();
					Map<String, Double> prop = shape.getProperties();
					for (int j = 0; j < propNodes.getLength(); j++) {
						Node probNode = propNodes.item(j);
						Element probElement = (Element) probNode;
						prop.put(probElement.getNodeName(), Double.valueOf(probElement.getTextContent()));
					}
					shape.setProperties(prop);
					Element shapeElement = (Element) shapeNode;
					// load position
					int x = Double.valueOf(shapeElement.getAttribute("positionX")).intValue();
					int y = Double.valueOf(shapeElement.getAttribute("positionY")).intValue();
					shape.setPosition(new Point(x, y));
					// load Color
					shape.setColor(new Color(Integer.parseInt(shapeElement.getAttribute("colour"))));
					// load FillColor
					shape.setFillColor(new Color(Integer.parseInt(shapeElement.getAttribute("fillcolour"))));
					shape.setX1(Integer.parseInt(shapeElement.getAttribute("x1")));
					shape.setX2(Integer.parseInt(shapeElement.getAttribute("x2")));

					shape.setY1(Integer.parseInt(shapeElement.getAttribute("y1")));
					shape.setY2(Integer.parseInt(shapeElement.getAttribute("y2")));
				}
				shapes.add(shape);

			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(shapes);
		return shapes;
	}

	private ShapeImplementation getShape(String nodeName) {
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
