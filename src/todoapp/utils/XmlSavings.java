package todoapp.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import todoapp.task.Task;

public class XmlSavings {
	
	 public static void saveTasks(List<Task> tasks) {
	        try {
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document doc = builder.newDocument();

	            Element rootElement = doc.createElement("tasks");
	            doc.appendChild(rootElement);

	            for (Task task : tasks) {
	                Element taskElement = doc.createElement("task");
	                rootElement.appendChild(taskElement);

	                Element idElement = doc.createElement("id");
	                idElement.appendChild(doc.createTextNode(String.valueOf(task.getId())));
	                taskElement.appendChild(idElement);

	                Element taskNameElement = doc.createElement("taskName");
	                taskNameElement.appendChild(doc.createTextNode(task.getTaskName()));
	                taskElement.appendChild(taskNameElement);

	                Element descriptionElement = doc.createElement("description");
	                descriptionElement.appendChild(doc.createTextNode(task.getDescription()));
	                taskElement.appendChild(descriptionElement);

	                Element checkedElement = doc.createElement("checked");
	                checkedElement.appendChild(doc.createTextNode(String.valueOf(task.isChecked())));
	                taskElement.appendChild(checkedElement);
	            }

	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            DOMSource source = new DOMSource(doc);
	            StreamResult result = new StreamResult(new File("tasks.xml"));
	            transformer.transform(source, result);

	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	 public static List<Task> loadTasks(File file) {
	        List<Task> tasks = new ArrayList<>();

	        try {
	            File xmlFile = new File("tasks.xml");
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document doc = builder.parse(xmlFile);

	            doc.getDocumentElement().normalize();
	            NodeList nodeList = doc.getElementsByTagName("task");

	            for (int i = 0; i < nodeList.getLength(); i++) {
	                Node node = nodeList.item(i);
	                if (node.getNodeType() == Node.ELEMENT_NODE) {
	                    Element element = (Element) node;
	                    int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
	                    String taskName = element.getElementsByTagName("taskName").item(0).getTextContent();
	                    String description = element.getElementsByTagName("description").item(0).getTextContent();
	                    boolean checked = Boolean.parseBoolean(element.getElementsByTagName("checked").item(0).getTextContent());

	                    Task task = new Task();
	                    task.setId(id);
	                    task.setTaskName(taskName);
	                    task.setDescription(description);
	                    task.setChecked(checked);

	                    tasks.add(task);
	                }
	            }

	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return tasks;
	    }
	
	}