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
	private List<ElementWrapper> xml2javacodeList(String xml, String xpathExpression) throws DocumentException, IOException, ElementNotFoundException {
		Document document = DocumentHelper.parseText(xml);
		List<Node> nodes = document.selectNodes(xpathExpression);
		List<ElementWrapper> elementWrappers = new ArrayList<ElementWrapper>();
		if (nodes.size() == 0) {
			throw new ElementNotFoundException("没有找到此元素");
		}
		Node node = nodes.get(0);
		elementWrappers.add(new ElementWrapper(0, node));
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			elementWrappers.addAll(xml2javacodeList((Element) node));
		}
		return elementWrappers;
	}

	public String xml2javacodeSimple(String xml, String xpathExpression) throws DocumentException, IOException, ElementNotFoundException {
		StringBuilder sb = new StringBuilder();
		List<ElementWrapper> list = xml2javacodeList(xml, xpathExpression);
		for (ElementWrapper elementWrapper : list) {
			int depth = elementWrapper.getDepth();
			while(depth-- > 0)
			{
				sb.append("|--");
			}
			sb.append(elementWrapper.getNode().getName() + "\n");
		}
		return sb.toString();
	}
	
	public String xml2javacode(String xml, String xpathExpression) throws DocumentException, IOException, ElementNotFoundException {
		StringBuilder sb = new StringBuilder();
		List<ElementWrapper> list = xml2javacodeList(xml, xpathExpression);
		for (ElementWrapper elementWrapper : list) {
		}
		return sb.toString();
	}

	private List<ElementWrapper> xml2javacodeList(Element element) throws IOException {
		List<ElementWrapper> elementWrappers = new ArrayList<ElementWrapper>();
		return xml2javacodeList(element, 1, elementWrappers);
	}

	private List<ElementWrapper> xml2javacodeList(Element element, int depth, List<ElementWrapper> elementWrappers) throws IOException {
		List<Element> elements = element.elements();
		for (Element element_ : elements) {
			elementWrappers.add(new ElementWrapper(depth, element_));
			if (element_.elements().size() != 0) {
				xml2javacodeList(element_, depth + 1,elementWrappers);
			}
		}
		return elementWrappers;
	}

	public String xml2javacode(InputStream in) throws DocumentException, IOException, ElementNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		StringBuilder sb = new StringBuilder();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		br.close();
		return xml2javacode(sb.toString(), "/*");
	}

	public String xml2javacode(File file) throws DocumentException, IOException, ElementNotFoundException {
		return xml2javacode(new FileInputStream(file));
	}
	
	public String xml2javacodeSimple(InputStream in) throws DocumentException, IOException, ElementNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		StringBuilder sb = new StringBuilder();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		br.close();
		return xml2javacodeSimple(sb.toString(), "/*");
	}

	public String xml2javacodeSimple(File file) throws DocumentException, IOException, ElementNotFoundException {
		return xml2javacodeSimple(new FileInputStream(file));
	}
}