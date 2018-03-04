import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class ReadXMLFileDOMExample {
 
    private static final String FILENAME = "xml\\Doctor.xsd";
 
    public static void main(String[] args) {
        try
        {
 
            // Строим объектную модель исходного XML файла
            final File xmlFile = new File(System.getProperty("user.dir")
                    + File.separator + FILENAME);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
 
            // Выполнять нормализацию не обязательно, но рекомендуется
            doc.getDocumentElement().normalize();
 
            System.out.println("Корневой элемент: "
                    + doc.getDocumentElement().getNodeName());
            System.out.println("============================");
 
            // Получаем все узлы с именем "table"
            NodeList nodeList = doc.getElementsByTagName("sequence");
            
            Node sequence = nodeList.item(0);
            int table_column_number = 0;
            NodeList elementList = sequence.getChildNodes();
            List<String> list = new ArrayList<>();
            for (int i = 0; i < elementList.getLength(); i++) 
            {
            	
	                if (elementList.item(i).getNodeName().equals("element"))
	                {
	                	Element e = (Element) elementList.item(i);
	                	list.add(e.getAttribute("name"));
	                	table_column_number++;
	                }
	                
	                         
            }
            list.stream().forEach( s -> System.out.println(s));
        } catch (ParserConfigurationException | SAXException
                | IOException ex) {
            Logger.getLogger(ReadXMLFileDOMExample.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}