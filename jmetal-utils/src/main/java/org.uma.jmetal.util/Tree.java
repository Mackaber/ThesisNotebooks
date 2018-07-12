package org.uma.jmetal.util;

import java.util.List;

public class Tree {
    private Node root;



    private class Node {
        private String value;
        private Node parent;
        private List<Node> children;

        public List<Node> getChildren() {
            return children;
        }

        public void addChild(Node node) {
            this.children.add(node);
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

