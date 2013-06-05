<!DOCTYPE html>
<html>
  <head>
    <title>My Force Directed Radial Graph</title>
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
	  font: 10px sans-serif;
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
  		
      	var rootNodeList = JSON.parse('${rootNodeList}');
      	var nodeIdParam = "${param.nodeId}";
      	
  		var idNode =  JSON.parse('${idNode}');
  		var parentChild =  JSON.parse('${parentChild}');
  		var nodeSet = new Array();
  		var linkSet = new Array();
  		var expandNodeCnt = 0;

  		for(i=0;i<rootNodeList.length;i++) {
  			var rootNodeId = rootNodeList[i]; 
	  		idNode[rootNodeId].name = idNode[rootNodeId].nodeName;
	  		idNode[rootNodeId].id = idNode[rootNodeId].nodeId;
	  		idNode[rootNodeId].reflexive = false;
	  		idNode[rootNodeId].type = idNode[rootNodeId].level;
	  		idNode[rootNodeId].direction = "OUT";
	  		idNode[rootNodeId].radius = idNode[rootNodeId].weight;
	  		//idNode[0].index = idNode[0].nodeId;
	  		
	  		nodeSet.push(idNode[rootNodeId]);
  		}
  		
  		for(var nod in idNode){
			if(idNode[nod].expand == true){
				expandNodeCnt++;
				fillNodesAndLinks(idNode[nod].nodeId,expandNodeCnt);
			}
        }
  		
		console.log("nodeSet = "+JSON.stringify(nodeSet));
		console.log("linkSet = "+JSON.stringify(linkSet));
 			
  	</script>
  	
    <script type="text/javascript">

      var width = 1300,
          height = 600,
          colorScale = d3.scale.category20();

      /* var svgCanvas = d3.select('body')
      	  .append("svg:svg")
      	  .attr({
	        "width": "100%",
	        "height": "100%"
	      })
      	.attr("viewBox", "0 0 " + width + " " + height )
      	.attr("preserveAspectRatio", "xMidYMid meet")
      	.attr("pointer-events", "all")
      	.call(d3.behavior.zoom().on("zoom", redraw));

		var vis = svgCanvas.append('svg:g');
		
		function redraw() {
		  vis.attr("transform", "translate(" + d3.event.translate + ")" + " scale(" + d3.event.scale + ")");
		} */
		
		var svgCanvas = d3.select('body')
    	  .append("svg:svg")
		  .attr("width", width)
          .attr("height", height);

		var w = width, h = height;
		
		 // Create a force layout and bind Nodes and Links
	      var force = d3.layout.force()
	          .charge(-200)
	          .gravity(0.05)
	          //.size([width, height])
	          .size([w *= 2 / 3, h *= 2 / 3])
	          //.linkDistance( function(d) { if (width < height) { return width*1/3; } else { return height*1/3; } } ); // Controls edge length
	          .linkDistance( 80 )
	          .nodes(nodeSet)
			  .links(linkSet);

			// Per-type markers, as they don't inherit styles.
		      svgCanvas.append("svg:defs").selectAll("marker")
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
	      	
		     /*  svgCanvas.append("svg:rect")
		      .attr("width", w)
		      .attr("height", h)
		      .style("stroke", "#000"); */
		      
		   // Use a timeout to allow the rest of the page to load first.
				//setTimeout(function() {
					
					 force.start();
					/*  var n = nodeSet.length;
					 for (var i = n * n; i > 0; --i){ 
						 force.tick();
					  	 force.stop();
					 } */
					 
					 // Draw lines for Links between Nodes
				     var gLink = svgCanvas.selectAll(".gLink")
				          .data(force.links())
				          .enter().append("g")
					      .attr("class", "gLink")
				          .append("line")
				          .attr("class", "link")
				          .style("stroke", "#ccc")
				          .attr("x1", function(d) { return d.source.x; })
				          .attr("y1", function(d) { return d.source.y; })
				          .attr("x2", function(d) { return d.target.x; })
				          .attr("y2", function(d) { return d.target.y; });          
				          
				     var link = svgCanvas.append("svg:g").selectAll("path")
				          .data(force.links())
				          .enter().append("svg:path")
				          //.style("stroke", "#ccc")
				          .style("stroke", function(d) {  console.log(" stroke of "+ JSON.stringify(d)); if(nodeIdParam == d.source.node.nodeId) { return colorScale(15); } else return "#ccc"; })
				          .attr("class", function(d) { return "link " + 'licensing'; })
				          .attr("marker-end", function(d) { return "url(#" + 'licensing' + ")"; });
				     
				   
				      // Create Nodes
				      var node = svgCanvas.selectAll(".node")
				          .data(force.nodes())
				          .enter().append("g")
				          .attr("class", "node")
				          .on("click", click)
				          .on("mouseover", nodeMouseover)
				          .on("mouseout", nodeMouseout)
				          .call(force.drag);
						
				      
				      // Append circles to Nodes
				      node.append("circle")
				          .attr("x", function(d) { return d.x; })
				          .attr("y", function(d) { return d.y; })
				          .attr("r", function(d) { return d.radius; } ) // Node radius
				          .style("fill", function(d) { return d.color; } ) // Make the nodes hollow looking
				          .style("stroke-width", function(d) { if( nodeIdParam == d.id ) return 1; else return 0;  } ) // Give the node strokes some
				          .style("stroke", function(d, i) { if( nodeIdParam == d.id ) { return colorScale(15); } else return ""; } ) // Node stroke colors
				          //.style("stroke", function(d, i) { colorVal = colorScale(i); return colorVal; } ) // Node stroke colors
				          .call(force.drag);
		
				      // Append text to Nodes
				      node.append("a")
				          .attr("xlink:href", function(d) { console.log(d.hlink);  return "http://www.if4it.com/glossary.html"; })
				          .attr('target','_blank')
					      .append("text")
				          .attr("x", function(d) { if ( rootNodeList.indexOf(d.id) != -1 ) { return 0; } else { return 5; } } )
				          .attr("y", function(d) { if (  rootNodeList.indexOf(d.id) != -1 ) { return 0; } else { return -(d.radius); } } )
					      .attr("text-anchor", function(d) { if (rootNodeList.indexOf(d.id) != -1) {return "middle";} else {return "start";} })
					      .attr("font-family", "Arial, Helvetica, sans-serif")
				          .style("font", "normal 11px Arial")
				          .attr("fill", "black")
				          .attr("dy", ".35em")
				          .attr("title", function(d) { return d.name; })
				          .text(function(d) { return d.name; });
		
				     node.append("svg:image")
					      .attr("class", "circle")
					      //.attr("xlink:href", "http://www.mricons.com/store/png/110950_27866_16_happy_positive_smiley_icon.png")
					      .attr("x", "-8px")
					      .attr("y", "-8px")
					      .attr("width", "16px")
					      .attr("height", "16px")
					      .append("title")
					      .text(function(d) { return d.title; });
		
				  	  $(".circle").tipsy({gravity:'w'});
				  	  
				      // Append text to Link edges
				      var linkText = svgCanvas.selectAll(".gLink")
				          .data(force.links())
				          .append("text")
					      .attr("font-family", "Arial, Helvetica, sans-serif")
					      .attr("x", function(d) {
					      if (d.target.x > d.source.x) { return (d.source.x + (d.target.x - d.source.x)/2); }
					      	else { return (d.target.x + (d.source.x - d.target.x)/2); }
					  	  })
				          .attr("y", function(d) {
					      if (d.target.y > d.source.y) { return (d.source.y + (d.target.y - d.source.y)/2); }
					      else { return (d.target.y + (d.source.y - d.target.y)/2); }
					  	  })
					  	 .attr("fill", "Maroon")
				         .style("font", "normal 10px Arial")
				         .attr("dy", ".35em")
				         .text(function(d) { return d.linkName; });
		
				      force.on("tick", function() {
		
				    	  /* link
					          .attr("x1", function(d) { return d.source.x; })
					          .attr("y1", function(d) { return d.source.y; })
					          .attr("x2", function(d) { return d.target.x; })
					          .attr("y2", function(d) { return d.target.y; }); */
					        
					        link.attr("d", function(d) {
					        	var dx = d.target.x - d.source.x,
					            dy = d.target.y - d.source.y;
					            //dr = Math.sqrt(dx * dx + dy * dy);
					        	//return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
					        	return 'M' + d.source.x + ',' + d.source.y + 'L' + d.target.x + ',' + d.target.y;
					        });
					      
					        node
					          .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
					
					       /*  r = 5;
					        node.attr("cx", function(d) { return d.x = Math.max(r, Math.min(w - r, d.x)); })
					        .attr("cy", function(d) { return d.y = Math.max(r, Math.min(h - r, d.y)); }); */
					        
					        linkText
						       .attr("x", function(d) {
						       if (d.target.x > d.source.x) { return (d.source.x + (d.target.x - d.source.x)/2); }
						       else { return (d.target.x + (d.source.x - d.target.x)/2); }
						  	})
						     .attr("y", function(d) {
						      if (d.target.y > d.source.y) { return (d.source.y + (d.target.y - d.source.y)/2); }
						      else { return (d.target.y + (d.source.y - d.target.y)/2); }
						    });
				      });
				      
				//}, 10);
		   

		      function click(d){
		    	  console.log("d = "+JSON.stringify(d));
		    	  
		    	  for(var x in idNode){
		  			var node = idNode[x];
		  			if(node.nodeId == d.id){
		  				currentElement = node;
		  				key = ""+d.id;
		  				childs = parentChild[key];
		  			}
		  		}
		  		
		  		if(childs == null || childs.length == 0){
		  			alert('No further relation available');
		  		}else{
		    	  window.location = "${pageContext.request.contextPath}/expand-node?nodeId="+d.id;
		  		}
		  		
			  }

    </script>

	<%-- <script src="${pageContext.request.contextPath}/js/app/d3js_tooltip.js" type="text/javascript"></script> --%>

  </body>

<script type="text/javascript">

      /* var nodeSet = [   
        {id: 0, reflexive: false, name : 'UBS', type:0, radius:20, color:'#fdae6b',hlink:"2879486.jsp", direction:"OUT" },
		{id: 1, reflexive: false, name : 'result 1Q12', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 2, reflexive: false, name : 'wealth management solid', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 3, reflexive: false, name : 'investment banking', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 4, reflexive: false, name : 'retail and corporate banking', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 5, reflexive: false, name : 'RWA ( reduction )', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 6, reflexive: false, name : 'Balance sheet', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 7, reflexive: false, name : 'funding market global, at low cost', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 8, reflexive: false, name : 'capital', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 9, reflexive: false, name : 'upward of CHF 2.1bn of collateral or termination payment on certain derivate', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 10, reflexive: false, name : 'rating downgrade', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 11, reflexive: false, name : 'rating-B', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 12, reflexive: false, name : 'outlook negative', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 13, reflexive: false, name : 'Operating income CHF 7.7bn up 26% sequentially', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 14, reflexive: false, name : 'Pretax profit CHF 2.2bn', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" },
		{id: 15, reflexive: false, name : 'One off them', type:1, radius:17, color:'#fdae6b',hlink:"2879486.jsp", direction:"IN" }
      ]; */

     /*  var linkSet = [
        {source: nodeSet[0], target: nodeSet[1], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[2], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[3], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[4], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[5], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[6], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[7], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[8], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[9], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[10], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[11], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[12], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[13], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[14], linkName: "1"},
		{source: nodeSet[0], target: nodeSet[15], linkName: "1"}
      ]; */

</script>


</html>
