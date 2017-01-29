package com.crayons_2_0.model.graph;

import java.util.HashSet;
import java.util.Set;

public class Node {
        
        private String unitNodeTitle ;
        // private UnitType unitType;
        // private boolean availability;
        
        private Set<Node> Nodes = new HashSet<Node>();
        public Node () {}
        public Node (String unitTitle) {
            this.unitNodeTitle =unitTitle;
            
        }
        
        public Set<Node> getNodes() {
            return Nodes;
        }
        public String getUnitNodeTitle(){
            return unitNodeTitle;
        }
        public void addNode(Node node) {
            this.Nodes.add(node);
        }

      
    }


