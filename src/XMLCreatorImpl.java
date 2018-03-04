
import java.io.*;
import java.util.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

//<tns:recipe_medicament>lonozepam</tns:recipe_medicament>
//<tns:recipe_morning_dosage>2x100g</tns:recipe_morning_dosage>
//<tns:recipe_day_dosage></tns:recipe_day_dosage>
//<tns:recipe_evening_dosage></tns:recipe_evening_dosage>
//<tns:Patients_idPatient>2</tns:Patients_idPatient>
//<tns:Doctors_idDoctors>8</tns:Doctors_idDoctors>
/**
 *
 * @author Danon
 */
public class XMLCreatorImpl {
	public Validator v;
    public static void main(String[] args) {
        new XMLCreatorImpl().create(new File("xml\\testCreator.xml"));
    }
    
    
    public Document create(File xmlFile) {
        Schema schema = null;
        Document doc = null;
        try{
            SchemaFactory sfactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schema = sfactory.newSchema(XMLCreatorImpl.class.getResource("Recipe.xsd"));
        } catch (SAXException ex) {}
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        if(schema!=null)
            factory.setSchema(schema);
        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            DOMImplementation domImplementation = db.getDOMImplementation();
            doc = domImplementation.createDocument("http://www.alexjedamenko.org/Recipe", "tns:Recipe", null);
            
            buildTree(doc, doc.getDocumentElement());
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            Properties transfProps = new Properties();
            transfProps.put("method", "xml");
            transfProps.put("indent", "yes");
            transformer.setOutputProperties(transfProps);
            
            DOMSource source = new DOMSource(doc);
            OutputStream out = new FileOutputStream(xmlFile);
            StreamResult result =  new StreamResult(out);
            //StreamResult result =  new StreamResult(System.out);
            transformer.transform(source, result);
            try {
                out.close();
            } catch (Exception ex) {}
            if(schema!=null){
                v = schema.newValidator();
                v.validate(source);
            }
        } catch (Exception ex) {
            System.err.println(XMLCreatorImpl.class.getName()+": XML error\n"+ex.toString());
        }
        return doc;
    }
    
    private void buildTree(Document doc, Element root) {
        // setting up document
        doc.setXmlStandalone(true);
        doc.setStrictErrorChecking(true);
        doc.setXmlVersion("1.0");
        /**
         * <tns:root fixed-attr="fixed value of the attribute" xmlns:p="ns:task01-simpleTypes" 
         * xmlns:tns="ns:task01" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
         * xsi:schemaLocation="ns:task01 task01.xsd ">
         */
      
       
        root.appendChild(createElement(doc, "http://www.alexjedamenko.org/Recipe", "recipe_medicament", "tns", "Clonozepams"));
        root.appendChild(createElement(doc, "http://www.alexjedamenko.org/Recipe", "recipe_morning_dosage", "tns", "2x100g"));
        root.appendChild(createElement(doc, "http://www.alexjedamenko.org/Recipe", "recipe_day_dosage", "tns", ""));
        root.appendChild(createElement(doc, "http://www.alexjedamenko.org/Recipe", "recipe_evening_dosage", "tns", ""));
        root.appendChild(createElement(doc, "http://www.alexjedamenko.org/Recipe", "Patients_idPatient", "tns", "6"));
        root.appendChild(createElement(doc, "http://www.alexjedamenko.org/Recipe", "Doctors_idDoctors", "tns", "2"));
        
         
       
    }
    
    private Element createElement(Document doc, String nameSpaceURI, String name, String prefix, String textContent) {
        Element elem = (nameSpaceURI!=null) ? doc.createElementNS(nameSpaceURI, name) : doc.createElement(name);
        if(prefix!=null) elem.setPrefix(prefix);
        if(textContent!=null)
            elem.setTextContent(textContent);
        return elem;
    }
    
  
    
   
    
    
}
