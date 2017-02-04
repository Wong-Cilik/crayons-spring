/**
 * 
 */
window.com_crayons_view_dagred3_Dagre = function() {
	var g = new dagreD3.graphlib.Graph({}).setGraph({});
	
	g.graph().transition = function transition(selection) { // transition with
		// duration 1000ms
		return selection.transition().duration(1000);
	};
	
	var Nodes = this.getState().unitNodes;
	
	// create graphNodes
	for (count = 0; count < Nodes.length; count++) {
		var tmp = Nodes[count];
		g.setNode(tmp, {label : tmp,class : "t"})
	}
	g.nodes().forEach(function(v) {
		var node = g.node(v);
		// Round the corners of the nodes
		node.rx = node.ry = 5;
	});
	
	var Edges = this.getState().edges;
	for (var count = 0; count < Edges.length; count += 2) {
		var tmpEdgeBegin = Edges[count];
		var tmpEdgeEnd = Edges[count + 1];

		g.setEdge(tmpEdgeBegin, tmpEdgeEnd, {})

	}

	
	
	// Create the renderer
	var render = new dagreD3.render();

	// Set up an SVG group so that we can translate the final graph.
	var svg = d3.select(this.getElement()).append("svg:svg")
			.attr("width", 1000).attr("height", 500), svgGroup = svg
			.append("g");

	// Run the renderer. This is what draws the final graph.
	render(d3.select("svg g"), g);

	// Center the graph
	var xCenterOffset = (svg.attr("width") - g.graph().width) / 2;
	svgGroup.attr("transform", "translate(" + xCenterOffset + ", 20)");
	svg.attr("height", g.graph().height + 40);
	
	 this.onStateChange = function() {
		 var g = new dagreD3.graphlib.Graph({}).setGraph({});
			
			g.graph().transition = function transition(selection) { // transition with
				// duration 1000ms
				return selection.transition().duration(1000);
			};
			
			var Nodes = this.getState().unitNodes;
			
			// create graphNodes
			for (count = 0; count < Nodes.length; count++) {
				var tmp = Nodes[count];
				g.setNode(tmp, {label : tmp,class : "t"})
			}
			g.nodes().forEach(function(v) {
				var node = g.node(v);
				// Round the corners of the nodes
				node.rx = node.ry = 5;
			});
			
			var Edges = this.getState().edges;
			for (var count = 0; count < Edges.length; count += 2) {
				var tmpEdgeBegin = Edges[count];
				var tmpEdgeEnd = Edges[count + 1];

				g.setEdge(tmpEdgeBegin, tmpEdgeEnd, {})

			}

			
			
			// Create the renderer
			var render = new dagreD3.render();

			// Set up an SVG group so that we can translate the final graph.
			var svg = d3.select(this.getElement()).append("svg:svg")
					.attr("width", 1000).attr("height", 500), svgGroup = svg
					.append("g");

			// Run the renderer. This is what draws the final graph.
			render(d3.select("svg g"), g);

			// Center the graph
			var xCenterOffset = (svg.attr("width") - g.graph().width) / 2;
			svgGroup.attr("transform", "translate(" + xCenterOffset + ", 20)");
			svg.attr("height", g.graph().height + 40);
	 }
}