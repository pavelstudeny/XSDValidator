XSDXMLValidatorFactory
======================

A common javax.xml.validation.Validator validates XML by what the document says it conforms to.
This validator disregards any XSD/DTD specified in the DOCTYPE and validates the XML by whatever XSD you need.

Usage
-----

```
Validator validator = XSDXMLValidatorFactory.newValidator("classpath:xsd/myxml.xsd");
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

try {
	validator.validate(new StreamSource(myxml.getInputStream()));
}
catch (SAXParseException ex) {
	errors.add(ex.getMessage());
}
```
