package org.example.task2;

import org.example.task1.Employee;
import static org.example.task1.Main.listToJson;
import static org.example.task1.Main.writeString;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            String path = "./src/main/resources/";
            List<Employee> list = parseXML(path + "data.xml");
            String json = listToJson(list);
            writeString(json, path + "data.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Employee> parseXML(String pathToFile) throws IOException,
                                                                     ParserConfigurationException,
                                                                     SAXException
    {
        Document doc = DocumentBuilderFactory.newInstance()
                                             .newDocumentBuilder()
                                             .parse(new File(pathToFile));
        Node staff = doc.getDocumentElement();
        NodeList childrenStaff = staff.getChildNodes();
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < childrenStaff.getLength(); i++) {
            Node node = childrenStaff.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                NodeList attributes = node.getChildNodes();
                long id = -1;
                String firstName = "";
                String lastName = "";
                String country = "";
                int age = -1;
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node innerNode = attributes.item(j);
                    if (innerNode.getNodeType() == Node.ELEMENT_NODE) {
                        if (innerNode.getNodeName().equals("id")) {
                            id = Long.parseLong(innerNode.getChildNodes().item(0).getTextContent());
                        } else if (innerNode.getNodeName().equals("firstName")) {
                            firstName = innerNode.getChildNodes().item(0).getTextContent();
                        } else if (innerNode.getNodeName().equals("lastName")) {
                            lastName = innerNode.getChildNodes().item(0).getTextContent();
                        } else if (innerNode.getNodeName().equals("country")) {
                            country = innerNode.getChildNodes().item(0).getTextContent();
                        } else if (innerNode.getNodeName().equals("age")) {
                            age = Integer.parseInt(innerNode.getChildNodes().item(0).getTextContent());
                        }
                    }
                }
                employees.add(new Employee(id, firstName, lastName, country, age));
            }
        }
        return employees;
    }
}
