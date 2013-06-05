<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Force-Directed Graph</title>

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
		circle.node {
		    cursor: pointer;
		    stroke: #3182bd;
		    stroke-width: 1.5px;
		}
		path.link {
		    fill: none;
		    stroke: #666;
		    stroke-width: 1.5px;
		}
		.simpleDiv {
		    position: absolute;
		    overflow: visible;
		}
		.simpleDiv .mainDiv {
		    overflow: visible;
		    width: 100%;
		}
		.simpleDiv .mainDiv .userImage {
		    width: 36px;
		    height: 36px;
		    border: 2px solid;
		    border-radius:30px;
		    overflow: hidden;
		}
		.simpleDiv .mainDiv .docIcon {
		    width: 36px;
		}
		.simpleDiv .mainDiv .content {
		    margin-left: 45px;
		    margin-top: -30px;
		}
		#hiddenText {
		    visibility: hidden;
		    padding: 0;
		}
	</style>
	
</head>

	<div id="chart"></div>
	
	
	<script type="text/javascript">
	var w = 1300,
    h = 600,
    node,
    path,
    root, nodes, links, linkText;

	var nodeMap = {};
	
	var force, svgCanvas;
	var LoadData = true;
	
	var expandNodesIds = [];
	expandNodesIds.push(0);
	
	var nodeIdParam = 0;
	var link;
	
	var rootNodeList = [];
	rootNodeList.push(0);
	
	var gLink;
	
    var parentChild =  JSON.parse('${parentChild}');
    
function update(d) {
	
    if (force) force.stop();
    
    nodes = flatten(root);
    links = createLinks(nodes,parentChild);
    
    //links = d3.layout.tree().links(nodes);
    //console.log(JSON.stringify(links));

    force.nodes(nodes)
        .links(links)
        .linkDistance(120)
        .charge(-500)
        .start();

    path = svgCanvas.selectAll("path.link");
    path = path.data(force.links());
    path.exit().remove();
    path.enter().append("svg:path")
        .attr("class", "link")
        .attr("marker-end", "url(#end)");
    svgCanvas.selectAll(".node .simpleDiv").remove();

    node = svgCanvas.selectAll(".node");
    node = node.data(force.nodes());
    node.exit().remove();

    node = svgCanvas.selectAll(".circle");
    node = node.data(force.nodes());
    node.exit().remove();
    
    node = svgCanvas.selectAll(".href");
    node = node.data(force.nodes());
    node.exit().remove();
    /* node = svgCanvas.selectAll("g.node");
    node = node.data(force.links());
    node.exit().remove(); */
    
    /* var div = d3.select("#chart").selectAll(".node");
    div.exit().remove(); */
    
    //svgCanvas.selectAll(".node").remove();
    //svgCanvas.selectAll(".node .simpleDiv").remove();
    
    /* link = svgCanvas.append("svg:g").selectAll("path")
	    .data(force.links())
	    .enter().append("svg:path")
	    //.style("stroke", "#ccc")
	    .style("stroke", function(d) {  if(nodeIdParam == d.source.node.nodeId) { return colorScale(15); } else return "#ccc"; })
	    .attr("class", function(d) { return "link " + 'licensing'; })
	    .attr("marker-end", function(d) { return "url(#" + 'licensing' + ")"; }); */
   

    /* node = svgCanvas.selectAll(".node");
    node = node.data(force.nodes());
    node.exit().remove();
    node.enter().append("g")
        .attr("class", "node")
        .on("click", click)
        .call(force.drag);

    node.append("foreignObject")
    .attr("class", "simpleDiv")
    .attr("width", function (d) {
        var f = document.createElement("span");
        f.id = "hiddenText";
        f.style.display = 'hidden';
        f.style.padding = '0px';
        f.innerHTML = d.nodeName;
        document.body.appendChild(f);
        textWidth = f.offsetWidth;
        var f1 = document.getElementById('hiddenText');
        f1.parentNode.removeChild(f1);
        return textWidth + 50;
    })
    .attr("overflow", "visible")
    .attr("height", 50)
    .append("xhtml:div").attr("class", "mainDiv").style("cursor", hoverStyle)
    .html(function (d) {
    	var htmlString = "";
        var userImage = "http://t2.gstatic.com/images?q=tbn:ANd9GcT6fN48PEP2-z-JbutdhqfypsYdciYTAZEziHpBJZLAfM6rxqYX";
       	 htmlString += "<div class='userImage' style='border-color:" + color(d) + "'><image src='" + userImage + "' width='36' height='36'></image></div>";
         htmlString += "<div class='content' style='color:" + color(d) + ";'>" + d.nodeName + "</div>";
         htmlString += "<div style='clear:both;'></div>";
        
        return htmlString;
	}); */
    
	
 // Create Nodes
    node = svgCanvas.selectAll(".node");
    node = node.data(force.nodes());
    node.data(force.nodes())
        .enter().append("g")
        .attr("class", "simpleDiv")
        .attr("class", "node")
        .on("click", click)
        .on("mouseover", nodeMouseover)
        .on("mouseout", nodeMouseout)
        .call(force.drag);
        
    // Append circles to Nodes
    svgCanvas.selectAll(".node").append("circle")
        .attr("x", function(d) { return d.x; })
        .attr("y", function(d) { return d.y; })
        .attr("r", function(d) { return d.radius; } ) // Node radius
        .style("fill", function(d) { return d.color; } ) // Make the nodes hollow looking
        .style("stroke-width", function(d) { if( nodeIdParam == d.id ) return 1; else return 0;  } ) // Give the node strokes some
        .style("stroke", function(d, i) { if( nodeIdParam == d.id ) { return colorScale(15); } else return ""; } )
        .call(force.drag);
		
    
    // Append text to Nodes
    node = svgCanvas.selectAll(".node").append("a").attr('class','href')
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

    node = svgCanvas.selectAll(".node").append("svg:image")
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
      linkText = svgCanvas.selectAll(".gLink")
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
	  
    /* node.append("foreignObject")
        .attr("class", "simpleDiv")
        .attr("width", function (d) {
	        var f = document.createElement("span");
	        f.id = "hiddenText";
	        f.style.display = 'hidden';
	        f.style.padding = '0px';
	        f.innerHTML = d.nodeName;
	        document.body.appendChild(f);
	        textWidth = f.offsetWidth;
	        var f1 = document.getElementById('hiddenText');
	        f1.parentNode.removeChild(f1);
	        return textWidth + 50;
	    })
        .attr("overflow", "visible")
        .attr("height", 50)
        .append("xhtml:div").attr("class", "mainDiv").style("cursor", hoverStyle)
        .html(function (d) {
        	var htmlString = "";

	        var userImage = "http://t2.gstatic.com/images?q=tbn:ANd9GcT6fN48PEP2-z-JbutdhqfypsYdciYTAZEziHpBJZLAfM6rxqYX";
	        if (d.type == 'user') {
	            htmlString += "<div class='userImage' style='border-color:" + color(d) + "'><image src='" + userImage + "' width='36' height='36'></image></div>";
	            htmlString += "<div class='content' style='color:" + color(d) + ";'>" + d.nodeName + "</div>";
	            htmlString += "<div style='clear:both;'></div>";
	        } else if (d.type == 'chat') {
	            htmlString += "<div class='docIcon'><i class='icon-comment icon-3x'></i></div>";
	            htmlString += "<div class='content' style='color:" + color(d) + ";'>" + d.nodeName + "</div>";
	            htmlString += "<div style='clear:both;'></div>";
	        } else if (d.type == 'message') {
	            htmlString += "<div class='docIcon'><i class='icon-file-alt icon-3x'></i></div>";
	            htmlString += "<div class='content' style='color:" + color(d) + ";'>" + d.nodeName + "</div>";
	            htmlString += "<div style='clear:both;'></div>";
	        } else {
	            //htmlString += "<div class='docIcon'><i class='icon-exclamation icon-3x'></i></div>";
	            //htmlString += "<div class='content' style='color:" + color(d) + ";'>" + d.nodeName + "</div>";
	            //htmlString += "<div style='clear:both;'></div>"; 
	            
	        	 htmlString += "<div class='userImage' style='border-color:" + color(d) + "'><image src='" + userImage + "' width='36' height='36'></image></div>";
		         htmlString += "<div class='content' style='color:" + color(d) + ";'>" + d.nodeName + "</div>";
		         htmlString += "<div style='clear:both;'></div>";
	        }
	        return htmlString;
    }); */ 
}

function tick() {
    
	path.attr("d", function (d) {
		var dx = d.target.x - d.source.x,
        dy = d.target.y - d.source.y;
        //dr = Math.sqrt(dx * dx + dy * dy);
    	//return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
    	return 'M' + d.source.x + ',' + d.source.y + 'L' + d.target.x + ',' + d.target.y;
    });

    /* link.attr("d", function(d) {
    	var dx = d.target.x - d.source.x,
        dy = d.target.y - d.source.y;
        //dr = Math.sqrt(dx * dx + dy * dy);
    	//return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
    	return 'M' + d.source.x + ',' + d.source.y + 'L' + d.target.x + ',' + d.target.y;
    }); */
    
    svgCanvas.selectAll(".node").attr("transform", function (d) {
        return "translate(" + (d.x) + "," + (d.y) + ")";
    });
    
    linkText
	    .attr("x", function(d) {
	    if (d.target.x > d.source.x) { return (d.source.x + (d.target.x - d.source.x)/2); }
	    else { return (d.target.x + (d.source.x - d.target.x)/2); }
		})
	  .attr("y", function(d) {
	   if (d.target.y > d.source.y) { return (d.source.y + (d.target.y - d.source.y)/2); }
	   else { return (d.target.y + (d.source.y - d.target.y)/2); }
	 });

}

function color(d) {
    return d._children ? "#3182bd" : d.children ? "#c6dbef" : "#fd8d3c";
}

function hoverStyle(d) {
    return d._children ? "pointer" : d.children ? "pointer" : "default";
}

// Toggle children on click.
function click(d) {
    if (d.children) {
        d._children = d.children;
        d.children = null;
    } else {
        d.children = d._children;
        d._children = null;
    }
    
    markNodeExpand(d.id);
    
    update(d);
}

var findNode = function (node) {
    for (var i in force.nodes()) {
        if (force.nodes()[i] === node) return true
    };
    return false;
}


 function loadImage() {
     if (LoadData) {
         data = JSON.parse('${idNode}');
         root = data["0"];

         force = d3.layout.force()
             .on("tick", tick)
             .size([w, h]);

         colorScale = d3.scale.category20();
         
         svgCanvas = d3.select("#chart").append("svg:svg")
             .attr("width", w)
             .attr("height", h);
         
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
         
         gLink = svgCanvas.selectAll(".gLink")
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
         
         update();
         
         LoadData = false;
     }

 }
 
 loadImage();
    
</script>
	
  </body>
</html>