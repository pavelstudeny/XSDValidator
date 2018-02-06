package cz.hrajou.pavel;

import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.util.DOMEntityResolverWrapper;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

class Input implements LSInput {

    private String publicId;

    private String systemId;

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getBaseURI() {
        return null;
    }

    public InputStream getByteStream() {
        return null;
    }

    public boolean getCertifiedText() {
        return false;
    }

    public Reader getCharacterStream() {
        return null;
    }

    public String getEncoding() {
        return null;
    }

    public String getStringData() {
        synchronized (inputStream) {
            try {
                byte[] input = new byte[inputStream.available()];
                inputStream.read(input);
                String contents = new String(input);
                return contents;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Exception " + e);
                return null;
            }
        }
    }

    public void setBaseURI(String baseURI) {
    }

    public void setByteStream(InputStream byteStream) {
    }

    public void setCertifiedText(boolean certifiedText) {
    }

    public void setCharacterStream(Reader characterStream) {
    }

    public void setEncoding(String encoding) {
    }

    public void setStringData(String stringData) {
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public BufferedInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(BufferedInputStream inputStream) {
        this.inputStream = inputStream;
    }

    private BufferedInputStream inputStream;

    public Input(String publicId, String sysId, InputStream input) {
        this.publicId = publicId;
        this.systemId = sysId;
        this.inputStream = new BufferedInputStream(input);
    }
}

class ResourceResolver  implements LSResourceResolver {

    public LSInput resolveResource(String type, String namespaceURI,
                                   String publicId, String systemId, String baseURI) {

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource r = resolver.getResource(systemId);

        try {
            return new Input(publicId, systemId, new FileInputStream(r.getFile()));
        }
        catch (java.io.IOException ex) {
            return null;
        }
    }

}


public class XSDXMLValidatorFactory {
    static Validator newValidator(String xsdPath) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // associate the schema factory with the resource resolver, which is responsible for resolving the imported XSD's
        factory.setResourceResolver(new ResourceResolver());

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource r = resolver.getResource(xsdPath);
        Source schemaFile = new StreamSource(new FileInputStream(r.getFile()));
        Schema schema = factory.newSchema(schemaFile);

        Validator validator = schema.newValidator();

        validator.setProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.ENTITY_RESOLVER_PROPERTY, new DOMEntityResolverWrapper() {
            public XMLInputSource resolveEntity(XMLResourceIdentifier resourceIdentifier)
                    throws XNIException, IOException {
                return new XMLInputSource(null, null, null) {
                    InputStream is = null;
                    public InputStream getByteStream() { return null; }
                    public void setCharacterStream(Reader charStream) { }
                    public Reader getCharacterStream() {
                        return new StringReader("<!-- empty -->");
                    }

                };
            }
        });

        return validator;
    }
}
