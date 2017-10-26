import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLPeopleReader {
	
	Document doc;
	XPath xpath;

	/**
	 * The health profile reader gets information from the command line about
	 * weight and height and calculates the BMI of the person based on this 
	 * parameters
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		XMLPeopleReader test = new XMLPeopleReader();
		test.loadXML();
		
		if(args.length>0) {
			String task = args[0];
			if(task.equals("stampAllPeople"))
				test.printAllPeople();
			else if(task.equals("printPreference"))
				test.printActivityPreference(5);
			else if(task.equals("printPeopleWithDate")) {
				String filterType = ">";
				String date = "2017-10-13";
				if(args.length == 3) {
					filterType = args[1];
					date = args[2];
				}
				System.out.println("> searching for date "+filterType+" "+date);
				test.filterActivity(filterType, date);
			}
		}
		else {
			System.out.println("please select a task using args");
		}
	}
	
	public String getActivityDescription(int personID) throws XPathExpressionException {
		XPathExpression expr = xpath.compile("/people/person[@id='" + personID + "']/activitypreference/description");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		String text = node.getTextContent();		
		return text;
	}
	
	public String getActivityPlace(int personID) throws XPathExpressionException {
		XPathExpression expr = xpath.compile("/people/person[@id='" + personID + "']/activitypreference/place");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		String text = node.getTextContent();
		return text;
	}
	
	public void printActivityPreference(int personID) throws XPathExpressionException {
		XPathExpression expr = xpath.compile("/people/person[@id='" + personID + "']/activitypreference");
		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		printListPerson(nodes);
	}
	
	public void filterActivity(String operator, String date) throws XPathExpressionException {
		date = date + "T00:00:00.0";
		XPathExpression expr = xpath.compile("/people/person[translate(activitypreference/startdate, \"-:T\", \"\") "+operator+" translate(\""+date+"\", \"-:T\", \"\")]");
		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		printListPerson(nodes);
	}
	
	private void printListPerson(NodeList nodes) {
		for (int i = 0; i < nodes.getLength(); i++) {
			printPerson(nodes.item(i));
		}
	}
	
	private void printPerson(Node node) {
		System.out.println("------ Person info -------");
		printNode(node);
		System.out.println("--------------------------");
	}
	
	private void printNodeList(NodeList nodes) {
		for (int i = 0; i < nodes.getLength(); i++) {
			printNode(nodes.item(i));
		}
	}
	
	private void printNode(Node node) {
		NodeList children = node.getChildNodes();
		if(children.getLength() == 1) {
			Node child = children.item(0);
			if (child.getNodeType() == Node.TEXT_NODE)
                System.out.printf("%-20s%s%n", node.getNodeName(), child.getNodeValue());
		}
		else if(children.getLength()>1) {
			System.out.println("-> "+node.getNodeName());
			printNodeList(children);
		}
	}
	
	public void printAllPeople() throws XPathExpressionException {
		XPathExpression expr = xpath.compile("/people/person");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		printListPerson(nodes);
	}
	
	public void loadXML() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		doc = builder.parse("src/people.xml");

		// creating xpath object
		getXPathObj();
	}
	
	public XPath getXPathObj() {
		XPathFactory factory = XPathFactory.newInstance();
		xpath = factory.newXPath();
		return xpath;
	}
}