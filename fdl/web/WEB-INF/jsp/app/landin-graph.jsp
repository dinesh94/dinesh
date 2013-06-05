<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
   
 <link rel="stylesheet" href="${pageContext.request.contextPath}/d3js/app.css">
<script src="${pageContext.request.contextPath}/d3js/d3.v3.min.js"></script>

<link href="${pageContext.request.contextPath}/css/freeow.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/demo.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.2.js"></script>

<script src="${pageContext.request.contextPath}/js/app/main.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/d3js/jquery1_6_3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/d3js/jquery.tipsy.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/d3js/tipsy.css" type="text/css" title="no title" charset="utf-8">

<script src="${pageContext.request.contextPath}/js/jquery.freeow.js" type="text/javascript"></script>
    
    <style type="text/css">

.node circle {
  cursor: pointer;
  stroke: #3182bd;
  stroke-width: 1.5px;
}

.node text {
  font: 10px Tahoma;
  pointer-events: none;
}

line.link {
  fill: none;
  stroke: #9ecae1;
  stroke-width: 1.5px;
}

path.link {
	  fill: none;
	  stroke: #666;
	  stroke-width: 1.5px;
	}
	
	marker#licensing {
	  fill: green;
	}
	
	path.link.licensing {
	  stroke: green;
	}
	
	path.link.resolved {
	  stroke-dasharray: 0,2 1;
	}
	
	circle {
	  fill: #ccc;
	  stroke: #333;
	  stroke-width: 1.5px;
	}
	
	text {
	  font: 10px Tahoma;
	  pointer-events: none;
	}
	
	text.shadow {
	  stroke: #fff;
	  stroke-width: 3px;
	  stroke-opacity: .8;
	}
	
    svg.a {
 		cursor: pointer;
	}

    </style>
  </head>
  <body>
  
  	<div id="freeow-tr" class="freeow freeow-top-right"></div>
	<div id="freeow-br" class="freeow freeow-bottom-right"></div>
  
    <div id="chart"></div>
    
    <script type="text/javascript">

    var nodeClicked = {};
    
    var nodeIdParam = 0;
    var rootNodeList = [];
    rootNodeList.push(0);
    
var w = 1300,
    h = 600,
    root;
    
var defs;

var force = d3.layout.force()
    .linkDistance(80)
    .charge(-120)
    .gravity(.05)
    .size([w, h]);

var vis = d3.select("#chart").append("svg:svg")
    .attr("width", w)
    .attr("height", h)
    .attr("pointer-events", "all")
    .call(d3.behavior.zoom().on("zoom", redraw))
  	.append('svg:g');

colorScale = d3.scale.category20();

var reqParameters = {};
reqParameters.companyIds = ('${command.companyIds}').replace('[','').replace(']','');
reqParameters.geoIds = ('${command.geoIds}').replace('[','').replace(']','');
reqParameters.regulatoryBodiesIds = ('${command.regulatoryBodiesIds}').replace('[','').replace(']','');
reqParameters.topicIds = ('${command.topicIds}').replace('[','').replace(']','');
reqParameters.defaultGraphIdList = ('${command.defaultGraphIdList}').replace('[','').replace(']','');

var param = decodeURIComponent($.param(reqParameters));

var url = "/fdl/json-concept-map?"+param;

$('#graphIFrame').attr('src', url);
$('#graphNewWindowLink').attr('href', url);
 
d3.json(url, function(json) {

	//alert(" json = "+ JSON.stringify(json.rootNodes));
	
	var rootNodes = json.rootNodes;
  
	//Build the arrow
	defs = vis.insert("svg:defs").selectAll("marker")
	    .data(["end"]);

	defs.enter().append("svg:marker")
	    .data(["suit", "licensing", "resolved"])
         .enter().append("svg:marker")
         .attr("id", String)
         .attr("viewBox", "0 -5 10 10")
         .attr("refX", 15)
         .attr("refY", -1.5)
         .attr("markerWidth", 6)
         .attr("markerHeight", 6)
         .attr("orient", "auto")
         .append("svg:path")
         .attr("d", "M0,-5L10,0L0,5");
	
	var graphId = 0;
	var cnt = 0;
	
	//root = json.nodes[0];
	
	//root = json.nodes;
	
	root = JSON.parse('${nodes}');
	
	/* var rootId;
	for(var i=0; i < rootNodes.length; i++){
		rootId = rootNodes[i];
		if(rootId == -1)
			root = json.nodes[0];
	} */
	
	/* for(var nodeId in json.nodes){
		//if(cnt == graphId){
			root.push(json.nodes[nodeId]);
  			cnt++;
		//}
	} */
	
	update();

});

function update() {
	
  var linkText;
  var nodes = flatten(root),
      links = d3.layout.tree().links(nodes); //treeCreateLinks(nodes,parentChild);

  // Restart the force layout.
  force
      .nodes(nodes)
      .links(links)
      .start();

  // Update the links…
  var link = vis.selectAll("line.link")
      .data(links, function(d) { return d.target.id; });

  // Enter any new links.
  link.enter().insert("svg:line", ".node")
      .attr("class", "link")
      //.style("stroke", function(d) {  console.log(" stroke of "+ JSON.stringify(d.id)); if(nodeIdParam == d.source.node.id) { return colorScale(15); } else return "#ccc"; })
      .style("stroke-width", function(d) { if( d.id == -1 ) return 10; else return 1;  } ) // Give the node strokes some
      .attr("class", function(d) { if(d.target.radius == DEFAULT_WEIGHT_DOT) return "link"; else return "link " + 'licensing'; })
      .attr("marker-end", function(d) { if(d.target.radius != DEFAULT_WEIGHT_DOT) { return "url(#" + 'licensing' + ")"; } })
      .attr("x1", function(d) { return d.source.x; })
      .attr("y1", function(d) { return d.source.y; })
      .attr("x2", function(d) { return d.target.x; })
      .attr("y2", function(d) { return d.target.y; })
      .on("mouseover", function(d){ displayEdgeAttr(d.source,d.target,'link'); })
  	  .on("mouseout", function(d){ $("#freeow-tr").html(""); });
  
   //Append text to Link edges
  	linkText = linktext = vis.selectAll("g.linklabelholder").data(links);
    linktext.enter().append("g").attr("class", "linklabelholder")
	    .append("text")
	    .attr("class", "linklabel")
	    .attr("text-anchor", "start")
	    .attr("fill", "Maroon")
	    .style("font", "normal 10px Tahoma")
	    .attr("x1", function(d) { return d.source.x; }) 
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; })
	    .text(function(d) {
	    	
	    	if(nodeClicked){
	    		//console.log(JSON.stringify(nodeClicked));
	    	}
	    	
	    	if(nodeClicked != null && nodeClicked.d != null && nodeClicked.d.id == d.target.id){
	    		//console.log(" nodeClicked.d.cuePhrase = "+nodeClicked.d.cuePhrase);
	    	}
	    	if(d.source.id == 1 && d.target.id == 2) {
	    		//console.log(" d.target.cuePhrase = "+d.target.cuePhrase+" d.source.cuePhrase "+d.source.cuePhrase); 
	    	}
	    	
	    	return d.target.cuePhrase; 
	   	});
	    
  // Exit any old links ancher.
  //linkText.exit().remove();
    
  // Exit any old links.
  link.exit().remove();

  // Update the nodes…
  var node = vis.selectAll("g.node")
      .data(nodes, function(d) { return d.id; });

  //node.select("circle").style("fill", color);
  
  node.on("dblclick", dblclick);

  // Enter any new nodes.
  var nodeEnter = node.enter().append("svg:g")
      .attr("class", "node")
      .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; })
      .on("click", click)
      .on("mouseover", nodeMouseover)
      .on("mouseout", nodeMouseout)
      .call(force.drag);

  //Create a circle
  nodeEnter.append("svg:circle")
      //.attr("r", function(d) { return Math.sqrt(d.size) / 10 || 4.5; })
      .attr("x", function(d) { return d.x; })
	  .attr("y", function(d) { return d.y; })
	  .attr("r", function(d) { return d.radius; } ) // Node radius
	  .style("fill", function(d) { var entityType = ( d.entityType != null && d.entityType.length > 0) ? d.entityType.replace(/\s/g, '') : "defaultEntity"; var color = entityTypeColor[entityType] != null ? entityTypeColor[entityType] : "#3182bd"; return color;  } ); // Make the nodes hollow looking
	  //.style("stroke-width", function(d) { if( nodeIdParam == d.id ) return 1; else return 0;  } ) // Give the node strokes some
	  //.style("stroke", function(d, i) { if( nodeIdParam == d.id ) { return colorScale(15); } else return ""; } ) // Node stroke colors
	  //.on("click", click)

  	// text on circle	  
 	nodeEnter.append("svg:text")
      .attr("text-anchor", "middle")
      //.attr("x", function(d) { return 0; })
	  //.attr("y", function(d) { return 0; })
      //.attr("x", 12)
      .attr("dy", ".35em")
      .attr("fill", "#3A2F0B")
      .attr("font-family", "Tahoma")
      .style("font", "normal 11px Tahoma")
      .style("font-weight", "550")
      .text(function(d) { return d.nodeName; });

  
  node.append("svg:image")
	  .attr("class", "circle")
	  //.attr("xlink:href", "http://www.mricons.com/store/png/110950_27866_16_happy_positive_smiley_icon.png")
	  .attr("x", "-8px")
	  .attr("y", "-8px")
	  .attr("width", "16px")
	  .attr("height", "16px")
	  .append("title")
	  .text(function(d) { return "Document ID =  "+ d.newsID; });

	 $(".circle").tipsy({gravity:'w'});
	
  // Exit any old nodes.
  node.exit().remove();

  // Re-select for update.
  link = vis.selectAll("line.link");
  node = vis.selectAll("g.node");
  
  force.on("tick", function() {
	  
    link.attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });

    node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
    
    linkText.attr("transform", function(d) { return "translate(" + (d.source.x + d.target.x) / 2 + "," + (d.source.y + d.target.y) / 2 + ")"; });
    
  });
}

function dblclick(d){
	//alert("dblclick = "+JSON.stringify(d));
	var url = d.hlink;
	window.open(url , '_blank');
}

// Color leaf nodes orange, and packages white or blue.
function color(d) {
  return d._children ? "#3182bd" : d.children ? "#c6dbef" : "#fd8d3c";
  //return d.color; 
}

// Toggle children on click.
function click(d) {

  nodeClicked = d;
	
  if (d.children) {
    d._children = d.children;
    d.children = null;
  } else {
    d.children = d._children;
    d._children = null;
  }
  update();
}

// Returns a list of all nodes under the root.
function flatten(root) {
  var nodes = [], i = 0;

  function recurse(node) {
    if (node != null && node.children) node.children.forEach(recurse);
    //if (!node.id) node.id = ++i;
    nodes.push(node);
  }

  nodes.push(root);
  recurse(root);
  return nodes;
}

    </script>
  </body>
</html>