package net.xicp.zyl_me.xml2javacode;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class CodeGenerator {
	private List<ElementWrapper> xml2javacodeList(String xml, String xpathExpression, boolean isPrintAttribute) throws DocumentException, IOException, ElementNotFoundException {
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
		if (isPrintAttribute) {
			for (int i = 0; i < elementWrappers.size(); i++) {
				ElementWrapper wrapper = elementWrappers.get(i);
				if (wrapper.getNode().getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) wrapper.getNode();
					List<Attribute> attributes = element.attributes();
					for (Attribute attr : attributes) {
						elementWrappers.add(new ElementWrapper(wrapper.getDepth(), attr));
					}
				}
			}
		}
		return elementWrappers;
	}

	private List<ElementWrapper> xml2javacodeListRoot(String xml, boolean isPrintAttribute) throws DocumentException, IOException, ElementNotFoundException {
		return xml2javacodeList(xml, "/*", isPrintAttribute);
	}

	public String xml2javacodeSimple(String xml, String xpathExpression, boolean isPrintDepth, boolean isPrintAttribute) throws DocumentException, IOException, ElementNotFoundException {
		StringBuilder sb = new StringBuilder();
		List<ElementWrapper> list = xml2javacodeList(xml, xpathExpression, isPrintAttribute);
		for (ElementWrapper elementWrapper : list) {
			int depth = elementWrapper.getDepth();
			if (isPrintDepth)
				sb.append(elementWrapper.getDepth() + "\t");
			while (depth-- > 0) {
				sb.append("|--");
			}
			sb.append(elementWrapper.getNode().getName() + "\n");
		}
		return sb.toString();
	}

	private List<ElementWrapper> xml2javacodeList(Element element) throws IOException {
		List<ElementWrapper> elementWrappers = new ArrayList<ElementWrapper>();
		return xml2javacodeList(element, 1, elementWrappers);
	}

	public List<ElementWrapper> xml2javacodeListByDepth(File file, int depth, boolean isPrintAttribute) throws IOException, DocumentException, ElementNotFoundException {
		List<ElementWrapper> elementWrappers = xml2javacodeList(readFile(file), "/*", isPrintAttribute);
		List<ElementWrapper> newElementWrappers = new ArrayList<ElementWrapper>();
		for (ElementWrapper elementWrapper : elementWrappers) {
			if (elementWrapper.getDepth() == depth)
				newElementWrappers.add(elementWrapper);
		}
		return newElementWrappers;
	}

	public String xml2javacode(String xml, String xpathExpression, boolean isPrintAttribute) throws DocumentException, IOException, ElementNotFoundException {
		StringBuilder sb = new StringBuilder();
		List<ElementWrapper> list = xml2javacodeList(xml, xpathExpression, isPrintAttribute);
		for (ElementWrapper elementWrapper : list) {
			sb.append("private String " + elementWrapper.getNode().getName() + ";\n");
		}
		return sb.toString();
	}

	private List<ElementWrapper> xml2javacodeList(Element element, int depth, List<ElementWrapper> elementWrappers) throws IOException {
		List<Element> elements = element.elements();
		for (Element element_ : elements) {
			elementWrappers.add(new ElementWrapper(depth, element_));
			if (element_.elements().size() != 0) {
				xml2javacodeList(element_, depth + 1, elementWrappers);
			}
		}
		return elementWrappers;
	}

	public String xml2javacodeByDepth(File file, int depth, boolean isPrintAttribute) throws IOException, DocumentException, ElementNotFoundException {
		List<ElementWrapper> elementWrappers = xml2javacodeListByDepth(file, depth, isPrintAttribute);
		StringBuilder sb = new StringBuilder();
		for (ElementWrapper elementWrapper : elementWrappers) {
			sb.append("private String " + elementWrapper.getNode().getName() + ";\n");
		}
		return sb.toString();
	}

	public String xml2javacode(File file, boolean isPrintAttribute) throws DocumentException, IOException, ElementNotFoundException {
		return xml2javacode(new FileInputStream(file), isPrintAttribute);
	}

	public String readFile(File file) throws UnsupportedEncodingException, IOException, FileNotFoundException {
		return readInputStream(new FileInputStream(file));
	}

	public int getMaxDepth(File f) throws UnsupportedEncodingException, FileNotFoundException, DocumentException, IOException, ElementNotFoundException {
		List<ElementWrapper> elementWrappers = xml2javacodeListRoot(readFile(f), false);
		int maxDepth = 0;
		for (ElementWrapper wrapper : elementWrappers) {
			if (wrapper.getDepth() > maxDepth) {
				maxDepth = wrapper.getDepth();
			}
		}
		return maxDepth;
	}

	public String xml2javacode(InputStream in, boolean isPrintAttribute) throws DocumentException, IOException, ElementNotFoundException {
		return xml2javacode(readInputStream(in), "/*", isPrintAttribute);
	}

	public String readInputStream(InputStream in) throws UnsupportedEncodingException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		StringBuilder sb = new StringBuilder();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		br.close();
		return sb.toString();
	}

	public String xml2javacodeSimple(InputStream in, boolean isPrintDepth, boolean isPrintAttribute) throws DocumentException, IOException, ElementNotFoundException {
		return xml2javacodeSimple(readInputStream(in), "/*", isPrintDepth, isPrintAttribute);
	}

	public String xml2javacodeSimple(File file, boolean isPrintDepth, boolean isPrintAttribute) throws DocumentException, IOException, ElementNotFoundException {
		return xml2javacodeSimple(new FileInputStream(file), isPrintDepth, isPrintAttribute);
	}
}