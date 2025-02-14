package org.example;

import org.example.drawing.Const;
import org.example.drawing.Edge;
import org.example.drawing.EdgeEnd;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapReader {
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document document;

    public MapReader(String filePath) {

        factory = DocumentBuilderFactory.newInstance();

        try {

            builder = factory.newDocumentBuilder();
            document = builder.parse(new File(filePath));
            document.getDocumentElement().normalize();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void readGraph(List<org.example.drawing.Node> nodes, List<List<EdgeEnd>> adjLists) {
        try {
            int nodeCount = readNodes(nodes);

            adjLists.clear();
            for(int i = 0; i < nodeCount; i++)
                adjLists.add(new ArrayList<>());
            readArcs(adjLists);

            //optimizeGraph(nodes, adjLists);

        } catch(XPathExpressionException e) {
            System.out.println(e.getMessage());
        }
    }

    private int readNodes(List<org.example.drawing.Node> nodes) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expression = xpath.compile("//nodes//node");
        NodeList nodeListXML = (NodeList) expression.evaluate(document, XPathConstants.NODESET);

        nodes.clear();

        int minWidth = Integer.MAX_VALUE, minHeight = Integer.MAX_VALUE;
        int maxWidth = Integer.MIN_VALUE, maxHeight = Integer.MIN_VALUE;

        for (int i = 0; i < nodeListXML.getLength(); i++) {
            Element nodeElement = (Element) nodeListXML.item(i);

            org.example.drawing.Node node = new org.example.drawing.Node(
                    Integer.parseInt(nodeElement.getAttribute("latitude")),
                    Integer.parseInt(nodeElement.getAttribute("longitude")),
                    Integer.parseInt(nodeElement.getAttribute("id")));

            minWidth = Math.min(minWidth, node.x);
            minHeight = Math.min(minHeight, node.y);

            maxWidth = Math.max(maxWidth, node.x);
            maxHeight = Math.max(maxHeight, node.y);

            nodes.add(node);
        }

        int deltaMax = Math.max(maxWidth - minWidth, maxHeight - minHeight);
        int maxSize = Math.max(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        for(org.example.drawing.Node node : nodes) {
            node.x = (node.x - minWidth) * maxSize / deltaMax;
            node.y = (node.y - minHeight) * maxSize / deltaMax;
        }

        return nodeListXML.getLength();
    }

    private void readArcs(List<List<EdgeEnd>> adjLists) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expression = xpath.compile("//arcs//arc");
        NodeList edgeListXML = (NodeList) expression.evaluate(document, XPathConstants.NODESET);

        for (int i = 0; i < edgeListXML.getLength(); i++) {
            Element edgeElement = (Element) edgeListXML.item(i);

            Edge edge = new Edge(
                    Integer.parseInt(edgeElement.getAttribute("from")),
                    Integer.parseInt(edgeElement.getAttribute("to")),
                    Integer.parseInt(edgeElement.getAttribute("length")));

            adjLists.get(edge.start).add(new EdgeEnd(edge.end, edge.length));
        }
    }

    private void optimizeGraph(List<org.example.drawing.Node> nodes, List<List<EdgeEnd>> adjLists) {
        for(int i = 0; i < nodes.size(); i++) {
            if(adjLists.get(i).isEmpty()) {
                boolean hasLink = false;

                for(List<EdgeEnd> list : adjLists) {
                    for (EdgeEnd edgeEnd : list)

                        if (edgeEnd.end == i && list != adjLists.get(i)) {
                            hasLink = true;
                            break;
                        }

                    if (hasLink)
                        break;
                }

                if(!hasLink) {
                    nodes.remove(i);
                    adjLists.remove(i);
                    i--;
                }
            }
        }
    }
}
