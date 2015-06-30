package net.xicp.zyl_me.xml2javacode;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class CodeGenerator {
	public String xml2javacode(String xml,String xpathExpression) throws DocumentException, IOException {
		StringBuilder sb = new StringBuilder();
		Document document = DocumentHelper.parseText(xml);
		List<Node> nodes = document.selectNodes(xpathExpression);
		Iterator<Node> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) node;
				xml2javacode(element);
			}
		}
		return sb.toString();
	}
	
	private List<String> xml2javacode(Element element) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		List<String> list = xml2javacode(element,baos);
		baos.close();
		return list;
	}
	
	private List<String> xml2javacode(Element element,ByteArrayOutputStream baos) throws IOException
	{
		List<ElementWrapper> list = new ArrayList<ElementWrapper>();
		List<Element> elements = element.elements();
		for(Element element_ : elements)
		{
			System.out.println(element_.getName());
			if(element_.elements().size() != 0)
			{
				xml2javacode(element_);
			}
		}
		return null;
	}

	public String xml2javacode(InputStream in) throws DocumentException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		StringBuilder sb = new StringBuilder();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		br.close();
		return xml2javacode(sb.toString(),"/*");
	}

	public String xml2javacode(File file) throws DocumentException, IOException {
		return xml2javacode(new FileInputStream(file));
	}
}