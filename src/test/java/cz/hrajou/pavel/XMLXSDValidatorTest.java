package  cz.hrajou.pavel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;

import java.util.List;
import java.util.Vector;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = XSDXMLValidatorFactory.class)
public class XMLXSDValidatorTest {

	@Test
	public void contextLoads() {
	}

	@Test
	public void validateValidMXML() throws org.xml.sax.SAXException, java.io.IOException {
		Validator validator = XSDXMLValidatorFactory.newValidator("classpath:xsd/musicxml.xsd");
		Vector<String> errors = new Vector<String>();
		validator.setErrorHandler(new ErrorHandler() {
			public void error(SAXParseException exception) {
				errors.add(exception.getLineNumber() + ":" + exception.getColumnNumber() + " " + exception.getMessage());
			}
			public void fatalError(SAXParseException exception) {
				errors.add(exception.getLineNumber() + ":" + exception.getColumnNumber() + " " + exception.getMessage());
			}
			public void warning(SAXParseException exception) {}
		});

		MultipartFile xml = new MockMultipartFile("musicxml.xml", mxml.getBytes());

		validator.validate(new StreamSource(xml.getInputStream()));

		assert errors.size() == 0;
	}

	@Test
	public void validateXSDXML() throws org.xml.sax.SAXException, java.io.IOException {
		Validator validator = XSDXMLValidatorFactory.newValidator("classpath:xsd/musicxml.xsd");
		Vector<String> errors = new Vector<String>();
		validator.setErrorHandler(new ErrorHandler() {
			public void error(SAXParseException exception) {
				errors.add(exception.getLineNumber() + ":" + exception.getColumnNumber() + " " + exception.getMessage());
			}
			public void fatalError(SAXParseException exception) {
				errors.add(exception.getLineNumber() + ":" + exception.getColumnNumber() + " " + exception.getMessage());
			}
			public void warning(SAXParseException exception) {}
		});

		MultipartFile xml = new MockMultipartFile("musicxml.xml", mxml_xsd.getBytes());

		validator.validate(new StreamSource(xml.getInputStream()));

		assert errors.size() == 0;
	}

	@Test
	public void validateInvalidMXML() throws org.xml.sax.SAXException, java.io.IOException {
		Validator validator = XSDXMLValidatorFactory.newValidator("classpath:xsd/musicxml.xsd");
		Vector<String> errors = new Vector<String>();
		validator.setErrorHandler(new ErrorHandler() {
			public void error(SAXParseException exception) {
				errors.add(exception.getLineNumber() + ":" + exception.getColumnNumber() + " " + exception.getMessage());
			}
			public void fatalError(SAXParseException exception) {
				errors.add(exception.getLineNumber() + ":" + exception.getColumnNumber() + " " + exception.getMessage());
			}
			public void warning(SAXParseException exception) {}
		});

		MultipartFile xml = new MockMultipartFile("musicxml.xml", mxml_invalid_tag.getBytes());

		validator.validate(new StreamSource(xml.getInputStream()));

		assert errors.size() > 0;
	}

	@Test
	public void validateMalformedMXML() throws org.xml.sax.SAXException, java.io.IOException {
		Validator validator = XSDXMLValidatorFactory.newValidator("classpath:xsd/musicxml.xsd");
		Vector<String> errors = new Vector<String>();
		validator.setErrorHandler(new ErrorHandler() {
			public void error(SAXParseException exception) {
				errors.add(exception.getLineNumber() + ":" + exception.getColumnNumber() + " " + exception.getMessage());
			}
			public void fatalError(SAXParseException exception) {
				errors.add(exception.getLineNumber() + ":" + exception.getColumnNumber() + " " + exception.getMessage());
			}
			public void warning(SAXParseException exception) {}
		});

		MultipartFile xml = new MockMultipartFile("musicxml.xml", mxml_malformed.getBytes());

		try {
			validator.validate(new StreamSource(xml.getInputStream()));
		}
		catch (SAXParseException ex) {
			errors.add(ex.getMessage());
		}

		assert errors.size() > 0;
	}

	@Test
	public void validateBinary() throws org.xml.sax.SAXException, java.io.IOException {
		Validator validator = XSDXMLValidatorFactory.newValidator("classpath:xsd/musicxml.xsd");
		Vector<String> errors = new Vector<String>();
		validator.setErrorHandler(new ErrorHandler() {
			public void error(SAXParseException exception) {
				errors.add(exception.getLineNumber() + ":" + exception.getColumnNumber() + " " + exception.getMessage());
			}
			public void fatalError(SAXParseException exception) {
				errors.add(exception.getLineNumber() + ":" + exception.getColumnNumber() + " " + exception.getMessage());
			}
			public void warning(SAXParseException exception) {}
		});

		MultipartFile xml = new MockMultipartFile("musicxml.xml", new byte []{1, 2, 3});

		try {
			validator.validate(new StreamSource(xml.getInputStream()));
		}
		catch (SAXParseException ex) {
			errors.add(ex.getMessage());
		}

		assert errors.size() > 0;
	}

	private static String mxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
			"<!DOCTYPE score-partwise PUBLIC\n" +
			"    \"-//Recordare//DTD MusicXML 3.0 Partwise//EN\"\n" +
			"    \"http://www.musicxml.org/dtds/partwise.dtd\">\n" +
			"<score-partwise version=\"3.0\">\n" +
			"  <part-list>\n" +
			"    <score-part id=\"P1\">\n" +
			"      <part-name>Music</part-name>\n" +
			"    </score-part>\n" +
			"  </part-list>\n" +
			"  <part id=\"P1\">\n" +
			"    <measure number=\"1\">\n" +
			"      <attributes>\n" +
			"        <divisions>1</divisions>\n" +
			"        <key>\n" +
			"          <fifths>0</fifths>\n" +
			"        </key>\n" +
			"        <time>\n" +
			"          <beats>4</beats>\n" +
			"          <beat-type>4</beat-type>\n" +
			"        </time>\n" +
			"        <clef>\n" +
			"          <sign>G</sign>\n" +
			"          <line>2</line>\n" +
			"        </clef>\n" +
			"      </attributes>\n" +
			"      <note>\n" +
			"        <pitch>\n" +
			"          <step>C</step>\n" +
			"          <octave>4</octave>\n" +
			"        </pitch>\n" +
			"        <duration>4</duration>\n" +
			"        <type>whole</type>\n" +
			"      </note>\n" +
			"    </measure>\n" +
			"  </part>\n" +
			"</score-partwise>";

	private static String mxml_xsd = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
			"<!DOCTYPE score-partwise PUBLIC\n" +
			"    \"-//Recordare//DTD MusicXML 3.0 Partwise//EN\"\n" +
			"    \"http://www.musicxml.org/dtds/musicxml.xsd\">\n" +
			"<score-partwise version=\"3.0\">\n" +
			"  <part-list>\n" +
			"    <score-part id=\"P1\">\n" +
			"      <part-name>Music</part-name>\n" +
			"    </score-part>\n" +
			"  </part-list>\n" +
			"  <part id=\"P1\">\n" +
			"    <measure number=\"1\">\n" +
			"      <attributes>\n" +
			"        <divisions>1</divisions>\n" +
			"        <key>\n" +
			"          <fifths>0</fifths>\n" +
			"        </key>\n" +
			"        <time>\n" +
			"          <beats>4</beats>\n" +
			"          <beat-type>4</beat-type>\n" +
			"        </time>\n" +
			"        <clef>\n" +
			"          <sign>G</sign>\n" +
			"          <line>2</line>\n" +
			"        </clef>\n" +
			"      </attributes>\n" +
			"      <note>\n" +
			"        <pitch>\n" +
			"          <step>C</step>\n" +
			"          <octave>4</octave>\n" +
			"        </pitch>\n" +
			"        <duration>4</duration>\n" +
			"        <type>whole</type>\n" +
			"      </note>\n" +
			"    </measure>\n" +
			"  </part>\n" +
			"</score-partwise>";

	private static String mxml_invalid_tag = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
			"<!DOCTYPE score-partwise PUBLIC\n" +
			"    \"-//Recordare//DTD MusicXML 3.0 Partwise//EN\"\n" +
			"    \"http://www.musicxml.org/dtds/partwise.dtd\">\n" +
			"<score-partwise version=\"3.0\">\n" +
			"  <part-list>\n" +
			"    <score-part id=\"P1\">\n" +
			"      <h1>Music</h1>\n" +
			"    </score-part>\n" +
			"  </part-list>\n" +
			"  <part id=\"P1\">\n" +
			"    <measure number=\"1\">\n" +
			"      <attributes>\n" +
			"        <divisions>1</divisions>\n" +
			"        <key>\n" +
			"          <fifths>0</fifths>\n" +
			"        </key>\n" +
			"        <time>\n" +
			"          <beats>4</beats>\n" +
			"          <beat-type>4</beat-type>\n" +
			"        </time>\n" +
			"        <clef>\n" +
			"          <sign>G</sign>\n" +
			"          <line>2</line>\n" +
			"        </clef>\n" +
			"      </attributes>\n" +
			"      <note>\n" +
			"        <pitch>\n" +
			"          <step>C</step>\n" +
			"          <octave>4</octave>\n" +
			"        </pitch>\n" +
			"        <duration>4</duration>\n" +
			"        <type>whole</type>\n" +
			"      </note>\n" +
			"    </measure>\n" +
			"  </part>\n" +
			"</score-partwise>";

	private static String mxml_malformed = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
			"<!DOCTYPE score-partwise PUBLIC\n" +
			"    \"-//Recordare//DTD MusicXML 3.0 Partwise//EN\"\n" +
			"    \"http://www.musicxml.org/dtds/partwise.dtd\">\n" +
			"<score-partwise version=\"3.0\">\n" +
			"  <part-list>\n" +
			"    <score-part id=\"P1\">\n" +
			"      <h1>Music</h1>\n" +
			"    </score-part>\n" +
			"  </part-list>\n" +
			"  <part id=\"P1\">\n" +
			"    <measure number=\"1\">\n" +
			"      <attributes>\n" +
			"        <divisions>1</divisions>\n" +
			"        <key>\n" +
			"          <fifths>0</fifths>\n" +
			"        </key>\n" +
			"        <time>\n" +
			"          <beats>4</beats>\n" +
			"          <beat-type>4</beat-type>\n" +
			"        </time>\n" +
			"        <clef>\n" +
			"          <sign>G</sign>\n" +
			"          <line>2</line>\n" +
			"        </clef>\n" +
			"      </attributes>\n" +
			"      <note>\n" +
			"        <pitch>\n" +
			"          <step>C</step>\n" +
			"          <octave>4</octave>\n" +
			"        </pitch>\n" +
			"        <duration>4</duration>\n" +
			"        <type>whole</type>\n" +
			"      </note>\n" +
			"    </measure>\n" +
			"  </part>";
}
