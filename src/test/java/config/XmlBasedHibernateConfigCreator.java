package config;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class XmlBasedHibernateConfigCreator extends DefaultHandler {

    private final static String HEADER = """
            <!DOCTYPE hibernate-configuration PUBLIC
                    "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
                    "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">\n""";

    private PrintWriter writer;
    private String url;
    private String username;
    private String password;
    private String attrValue;

    public XmlBasedHibernateConfigCreator(String url, String username, String password, String hibernateProperties) throws FileNotFoundException {
        this.url = url;
        this.username = username;
        this.password = password;
        writer = new PrintWriter(hibernateProperties);
        writer.write(HEADER);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        writer.write("<" + qName);
        String attr = attributes.getLocalName(0);
        if(attr != null) {
            attrValue = attributes.getValue(0);
            writer.write(String.format(" %s =\"%s\">", attr, attrValue));
        } else {
            writer.write(">\n");
            attrValue = null;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        writer.write(switch (attrValue) {
            case "connection.url" -> url;
            case "connection.username" -> username;
            case "connection.password" -> password;
            default -> new String(ch, start, length);
        });
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        writer.write("</" + qName + ">\n");
    }

    public void close() {
        writer.flush();
        writer.close();
    }
}
