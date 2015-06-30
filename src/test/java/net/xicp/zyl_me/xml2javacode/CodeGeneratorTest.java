package net.xicp.zyl_me.xml2javacode;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.junit.Test;
public class CodeGeneratorTest {
	@Test
	public void testXml2javacodeSimple() throws DocumentException, IOException, ElementNotFoundException {
		CodeGenerator generator = new CodeGenerator();
		String xml2javacode = generator.xml2javacodeSimple(this.getClass().getResourceAsStream("xml/CustomerSchema.xml"),false,false);
		System.out.println(xml2javacode);
	}
}
