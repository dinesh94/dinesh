<%@include file="common/taglib.jsp" %>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Rage Frameworks</title>
<meta http-equiv="x-ua-compatible" content="IE=7">

<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ddsmoothmenu.css">
<link href="${pageContext.request.contextPath}/js/Multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/js/Multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />

<link href="${pageContext.request.contextPath}/css/freeow.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/demo.css" rel="stylesheet" type="text/css" />

<link href="${pageContext.request.contextPath}/js/qtip/jquery.qtip.min.css" rel="stylesheet" type="text/css" />

<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="css/all-ie-only.css" />
<![endif]-->

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/Multiselect/jquery.multiselect.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/Multiselect/jquery.multiselect.filter.js"></script>
	<script src="${pageContext.request.contextPath}/js/date.js" type="text/javascript"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/qtip/jquery.qtip.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/jquery.freeow.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/app/main.js" type="text/javascript"></script>
	
<script type="text/javascript">
		$(function(){
			$("#company_name").multiselect().multiselectfilter();
			$("#company_name").multiselect({	
				noneSelectedText: "Select Company",
				selectedList: 0
			});
			
			$("#Geography").multiselect().multiselectfilter();
			$("#Geography").multiselect({
				header: "Select Geography",
				selectedList: 2
			});
			
			$("#Time_Period").multiselect().multiselectfilter();
			$("#Time_Period").multiselect({
				header: "Select Period",
				selectedList: 2
			});
			
			$("#Regulatory_Bodies").multiselect().multiselectfilter();
			$("#Regulatory_Bodies").multiselect({
				header: "Select Regulatory Bodies",
				selectedList: 2
			});
			
			$("#topic").multiselect().multiselectfilter();
			$("#topic").multiselect({
				header: "Select Topics",
				selectedList: 2
			});
			
			$("#analyst_name").multiselect().multiselectfilter();
			$("#analyst_name").multiselect({
				header: "Select Name",
				selectedList: 2
			});
			
			$("#name_person").multiselect().multiselectfilter();
			$("#name_person").multiselect({
				header: "Select Name",
				selectedList: 2
			});
			
			$("#Document").multiselect().multiselectfilter();
			$("#Document").multiselect({
				multiple : false,
				selectedList: 1
			});
			
			var url = "${pageContext.request.contextPath}/landin-graph?defaultGraphIdList="+defaultGraphIdList;
			
			$('#graphIFrame').attr('src', url);
			$('#graphNewWindowLink').attr('href', url);
			
			$("#company_name").multiselect("uncheckAll");
			$("#Geography").multiselect("uncheckAll");
			$("#Time_Period").multiselect("uncheckAll");
			$("#Regulatory_Bodies").multiselect("uncheckAll");
			$("#topic").multiselect("uncheckAll");
			$("#analyst_name").multiselect("uncheckAll");
			$("#name_person").multiselect("uncheckAll");
			$("#Document").multiselect("uncheckAll");
		});
		
		
    </script>
</head>
<body>

<div id="freeow-tr" class="freeow freeow-top-right"></div>
<div id="freeow-br" class="freeow freeow-bottom-right"></div>

<div class="headerwpr"> 
	<div class="logowpr">
		<a href="http://www.rageframeworks.com/" target="_blank"> <img style="MARGIN-TOP: 6px; MARGIN-LEFT: 10px" alt="RAGE Frameworks" src="${pageContext.request.contextPath}/images/logo_rage.gif" border="0"> </a>
	</div>
	<div class="searchwpr">
		<input maxlength="30" size="30" type="text">
		<input class="btn" value="Search" title="Search" type="button">
		<input class="btn" value="Reset" title="Reset" type="reset">
	</div>
	<div class="user_info_wpr">
		<font id="Orange"> Welcome Guest &nbsp;&nbsp; </font> |&nbsp;&nbsp; <a href="${pageContext.request.contextPath}/logout"><font id="Orange"><u>Logout</u> </font></a>
	</div>
	<div class="clr"></div>
</div>
<div id="menuDiv" style="padding-top:0px;padding-bottom:10px;"> 
  
  <div id="smoothmenu1" class="ddsmoothmenu">
    <ul style="margin-right: 5px;">
      <li style="z-index: 100;"> <a class="selected" style="padding-right: 23px;" href="#"> Network Graph </a></li>
      <li style="z-index: 99;"> <a style="padding-right: 23px;" href="${pageContext.request.contextPath}/company-info"> Company Information </a></li>
      <li> <a href="javascript:void(0)" >Ontology Management</a></li>
    </ul>
  </div>
</div>
<div style="padding-left: 5px; display: block;">
  <div class="main_wpr" style="padding-bottom:20px">
    <div class="header_wpr">
	<h4 class="sub_heading sub_heading_closed"> <a class="fl arrow_up arrow_down"> Show advanced search filters</a> </h4>
	<div class="filters" style="display:none;">
		<table cellpadding="0" cellspacing="3" border="0" width="100%">
			<tr>
				<td style="width:8%"> Company Name </td>
				<td style="width:25%"> 
					<select style="width:250px;" id="company_name">
						 <c:forEach items="${companyList}" var="company">
						 	<option value="${company.conceptId}">${company.normalizedConcept}</option>
						 </c:forEach>
					</select>
				 </td>
				<td style="width:8%"> Geography </td>
				<td style="width:25%">
					<select style="width:250px;" id="Geography">
						<c:forEach items="${geographyList}" var="geography">
						 	<option value="${geography.conceptId}">${geography.normalizedConcept}</option>
						</c:forEach>
					</select>
				</td>
				<td style="width:8%"> Time Period </td>
				<td style="width:25%">
					<select style="width:250px;" id="Time_Period">
						<option>1 Month </option>
						<option>3 Months </option>
						<option>6 Months</option>
						<option>1 Year </option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:8%"> Regulatory Bodies </td>
				<td style="width:25%">
					<select style="width:250px;" id="Regulatory_Bodies">
						<c:forEach items="${regulatoryBodyList}" var="regulatoryBody">
						 	<option value="${regulatoryBody.conceptId}">${regulatoryBody.normalizedConcept}</option>
						</c:forEach>
					</select>
				</td>
				<td style="width:8%"> Topic </td>
				<td style="width:25%">
					<select style="width:250px;" id="topic">
						<c:forEach items="${topicList}" var="topic">
						 	<option value="${topic.conceptId}">${topic.normalizedConcept}</option>
						</c:forEach>
					</select>
				</td>
				<td style="width:8%"> Name of the person </td>
				<td style="width:25%"> 
					<select style="width:250px;" id="name_person">
					</select>
				</td>
			</tr>
			<tr>
				<td style="width:8%"> Document </td>
				<td style="width:25%"> 
					<div>
						<select style="width:250px;" id="Document" onchange="showGraphType()">
							<option value="156">All Documents</option>
							<c:forEach items="${documentList}" var="document">
							 	<option value="${document.newsId}">${document.title}</option>
							</c:forEach>
						</select>
					</div>
					<div id="graphTypeDiv" style="display: none">
						<input type="radio" name="graphType" value="NORMALIZED" checked="checked"/> Normalized Graph
						<input type="radio" name="graphType" value="RAW"/> Raw Graph
					</div>
				</td>
				<td style="width:8%"> Fidelity Analyst Name </td>
				<td style="width:25%"> 
					<select style="width:250px;" id="analyst_name">
						<c:forEach items="${analystNameList}" var="analystName">
						 	<option value="${analystName.conceptId}">${analystName.normalizedConcept}</option>
						</c:forEach>
					</select>
				</td>
				<td style="width:8%">  </td>
				<td style="width:25%">
					<input type="button" value="Search" class="btn" onclick="javascript:refreshGraph();" />
				</td>
			</tr>
			
		</table>
	</div>
	
	
	
	<div style="margin-top:5px;">
		<div class="fl" style="margin-left:10px;">
			<span>Search: </span> <input name="companySearch" maxlength="30" size="30" type="text">
		</div>
		<div class="fr" style="margin-right:20px; margin-top:7px;">
			<a href="javascript:void(0);" class="btn color_legends"> legends </a>
		</div>
		<div class="fr" style="margin-right:20px;  margin-top:7px;">
			Updated as of <script type="text/javascript">document.write(dateFormat);</script> 
		</div>
	</div>
	
	  <div class="left_panel rounded-corner" style="width:99%; margin:10px auto;">
	  	<div style="float:right; margin-right:10px">
	  		<a target="_blank" id="graphNewWindowLink" title="Open in New Window"><img src="${pageContext.request.contextPath}/images/openinnew.png" width="22" height="22" alt="Open in New Window" /></a></div>
	  	<div style="clear: both"></div>
	  	<iframe id="graphIFrame" width="100%" height="100%" style="border: 0px;" ></iframe>
	  </div>
   </div>
  </div>
</div>


<div id="legend_tooltip" style="display: none; border-top:2px solid #eee;">	
	<table width="100%" cellpadding="2" cellspacing="5" border="0" style="margin-top:10px;">
		<tr>
			<td><div style="width:30px; height:20px; background:#ACACEC;"></div> </td>
			<td> Company </td>
		</tr>
		<tr>
			<td><div style="width:30px; height:20px; background:#FFFF00;"></div> </td>
			<td> Geography</td>
		</tr>
		<tr>
			<td><div style="width:30px; height:20px; background:#7CFC00;"></div> </td>
			<td> Regulatory Body </td>
		</tr>
		<tr>
			<td><div style="width:30px; height:20px; background:#E86850;"></div> </td>
			<td> Topic </td>
		</tr>
		<tr>
			<td><div style="width:30px; height:20px; background:#F56BFC;"></div> </td>
			<td> Name of a person </td>
		</tr>
		<tr>
			<td><div style="width:30px; height:20px; background:#3182bd;"></div> </td>
			<td> Default </td>
		</tr>
	</table>
</div>

	
</body>

<script type="text/javascript">
$('.color_legends').qtip({
	content : {
		text : $("#legend_tooltip"),
		title : {
			text : 'Legends',
			button : true
		}
	},
	show : {
		solo : true,
		event : 'hover'
	},
	position : {
		viewport: $(window),
		adjust: { method: 'shift' },
		corner: {
	      target: 'top center',
	      tooltip: 'bottom center'
	   }
	},
	hide : {
		event : 'unfocus'
	},
	style : {
		classes : 'qtip-shadow qtip-deloitte'
	}
});
</script>

</html>