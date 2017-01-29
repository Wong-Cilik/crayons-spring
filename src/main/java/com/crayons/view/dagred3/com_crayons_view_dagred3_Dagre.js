/**
 * 
 */
window.com_crayons_view_dagred3_Dagre = function() {
	var g = new dagreD3.graphlib.Graph()
	  .setGraph({})
	  .setDefaultEdgeLabel(function() { return {}; });
/*
	// Here we"re setting nodeclass, which is used by our custom drawNodes function
	// below.
	g.setNode(0,  { label: "Start",     class: "t" });
	g.setNode(1,  { label: "Lineare Algebra",         class: "t" });
	g.setNode(2,  { label: "Höhere Mathematik",        class: "t" });
	g.setNode(3,  { label: "Programmieren",        class: "t" });
	g.setNode(4,  { label: "Praxis in der Softwareentwicklung",      class: "t" });
	g.setNode(5,  { label: "Betriebssystheme",        class: "t" });
	g.setNode(6,  { label: "Rechnerorganisation",       class: "t" });
	g.setNode(7,  { label: "Algorithmen",        class: "t" });
	g.setNode(8,  { label: "Sicherheit",        class: "t" });
	g.setNode(9,  { label: "Grundbegriffe der Informatik",        class: "t" });
	g.setNode(10, { label: "Numerik",        class: "t" });
	g.setNode(11, { label: "Datenbanken",        class: "t" });
	g.setNode(12, { label: "Digitaltechnik",   class: "t" });
	g.setNode(13, { label: "Programmierparadigmen",         class: "t" });
	g.setNode(14, { label: "Softwaretechnik",  class: "t" });
	g.setNode(15, { label: "Ende",  class: "t" });


	g.nodes().forEach(function(v) {
	  var node = g.node(v);
	  // Round the corners of the nodes
	  node.rx = node.ry = 5;
	});

	// Set up edges, no special attributes.
	g.setEdge(3, 4);
	g.setEdge(2, 3);
	g.setEdge(1, 2);
	g.setEdge(6, 7);
	g.setEdge(5, 6);
	g.setEdge(9, 10);
	g.setEdge(8, 9);
	g.setEdge(11,12);
	g.setEdge(8, 11);
	g.setEdge(5, 8);
	g.setEdge(1, 5);
	g.setEdge(13,14);
	g.setEdge(1, 13);
	g.setEdge(0, 1);
	g.setEdge(4, 15);
	g.setEdge(7, 15);
	g.setEdge(10, 15);
	g.setEdge(12, 15);
	g.setEdge(14, 15);
*/	
	var Nodes = this.getState().unitNodes;
	//create graphNodes
	for(count=0;count < Nodes.length;count++){
		var tmp = Nodes[count];
		g.setNode(tmp,{label:tmp, class: "t"})
	}
	g.nodes().forEach(function(v) {
		  var node = g.node(v);
		  // Round the corners of the nodes
		  node.rx = node.ry = 5;
		});
	/*
	for(count=0;count < myArr.length;count++){
		g.setEdge(myArr[count].getUnitNodeTitle(),myArr[count].getUnitNodeTitle())
	}
	*/
	
	/*
	 this.onStateChange = function() {
	        var coords = this.getState().coords;
	        d3.selectAll("circle").transition().attr("cx", parseInt(coords[0]));
	 }*/
	// Create the renderer
	var render = new dagreD3.render();

	// Set up an SVG group so that we can translate the final graph.
	var svg = d3.select(this.getElement()).append("svg:svg").attr("width", 1000).attr("height", 500),
	    svgGroup = svg.append("g");

	// Run the renderer. This is what draws the final graph.
	render(d3.select("svg g"), g);

	// Center the graph
	var xCenterOffset = (svg.attr("width") - g.graph().width) / 2;
	svgGroup.attr("transform", "translate(" + xCenterOffset + ", 20)");
	svg.attr("height", g.graph().height + 40);
	
	

}