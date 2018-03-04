import java.io.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.xml.sax.SAXException;
 
public class XMLValidatorTest {
 
    public static void main(String[] args) throws SAXException, IOException {
 
        // 1. Поиск и создание экземпляра фабрики для языка XML Schema
        SchemaFactory factory = 
            SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
         
        // 2. Компиляция схемы
        // Схема загружается в объект типа java.io.File, но вы также можете использовать 
        // классы java.net.URL и javax.xml.transform.Source
        File schemaLocation = new File("xml\\Recipe.xsd");
        Schema schema = factory.newSchema(schemaLocation);
     
        // 3. Создание валидатора для схемы
        Validator validator = schema.newValidator();
         
        // 4. Разбор проверяемого документа
        File f = new File("xml\\RecipeTest.xml");
        Source source = new StreamSource(f);
         
        // 5. Валидация документа
        try {
            validator.validate(source);
            System.out.println(f + " is valid.");
        }
        catch (SAXException ex) {
            System.out.println(f + " is not valid because ");
            System.out.println(ex.getMessage());
        }  
         
    }
 
}