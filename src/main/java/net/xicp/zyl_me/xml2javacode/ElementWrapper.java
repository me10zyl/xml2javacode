package net.xicp.zyl_me.xml2javacode;

import org.dom4j.Node;

public class ElementWrapper {
	/**
	 * 节点的深度,从0开始
	 */
	private int depth;
	/**
	 * 此节点
	 */
	private Node node;

	public ElementWrapper(int depth, Node node) {
		super();
		this.depth = depth;
		this.node = node;
	}
	public int getDepth() {
		return depth;
	}

	public Node getNode() {
		return node;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	@Override
	public String toString() {
		return "ElementWrapper [depth=" + depth + ", node=" + node + "]";
	}
}
