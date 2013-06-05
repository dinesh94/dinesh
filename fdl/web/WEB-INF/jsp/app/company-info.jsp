<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Rage Frameworks</title>
<meta http-equiv="x-ua-compatible" content="IE=7">

<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ddsmoothmenu.css">
<link href="${pageContext.request.contextPath}/js/Multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
 <link href="${pageContext.request.contextPath}/js/Multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />

<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="css/all-ie-only.css" />
<![endif]-->

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/Multiselect/jquery.multiselect.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/Multiselect/jquery.multiselect.filter.js"></script>
	<script src="${pageContext.request.contextPath}/js/date.js" type="text/javascript"></script>

	<script type="text/javascript">
			$(function(){
				$("#company_name").multiselect().multiselectfilter();
				$("#company_name").multiselect({
					selectedList: 2
				});
				
				$("#Geography").multiselect().multiselectfilter();
				$("#Geography").multiselect({
					selectedList: 2
				});
				
				$("#Time_Period").multiselect().multiselectfilter();
				$("#Time_Period").multiselect({
					selectedList: 2
				});
				
				$("#Regulatory_Bodies").multiselect().multiselectfilter();
				$("#Regulatory_Bodies").multiselect({
					selectedList: 2
				});
				
				$("#Relationship_Type").multiselect().multiselectfilter();
				$("#Relationship_Type").multiselect({
					selectedList: 2
				});
				
				$("#analyst_name").multiselect().multiselectfilter();
				$("#analyst_name").multiselect({
					selectedList: 2
				});
				
				$("#name_person").multiselect().multiselectfilter();
				$("#name_person").multiselect({
					selectedList: 2
				});
				
				$("#Document").multiselect().multiselectfilter();
				$("#Document").multiselect({
					selectedList: 2
				});
			});
			
			
		</script>
</head>
<body>
<div class="headerwpr"> 
	<div class="logowpr">
		<a href="http://www.rageframeworks.com/" target="_blank"> <img style="MARGIN-TOP: 6px; MARGIN-LEFT: 10px" alt="RAGE Frameworks" src="images/logo_rage.gif" border="0"> </a>
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
      <li style="z-index: 100;"> <a class="" style="padding-right: 23px;" href="${pageContext.request.contextPath}/home"> Network Graph </a></li>
      <li style="z-index: 99;"> <a style="padding-right: 23px;" href="javascript:void(0)" class="selected"> Company Information </a></li>
      <li> <a href="javascript:void(0)"> Ontology Management </a></li>
    </ul>
  </div>
</div>
<div style="padding-left: 5px; display: block;">
  <div class="main_wpr" style="padding-bottom:20px">
    <div class="header_wpr">
	<div>
		<table width="99%" cellpadding="2" cellspacing="0" border="0" class="tablestyle">
			<tr>
				<th> Group by Company Name </th>
				<th> Sr No </th>
				<th> Company Name </th>
				<th> Analyst Name </th>
				<th> Period </th>
				<th> Domicile </th>
				<th> Investment Advisor Code </th>
				<th> Rating </th>
				<th> Outlook </th>
			</tr>
			
			<tr>
				<td rowspan="6" valign="top"> +/- </td>
				<td rowspan="4" valign="top"> 1 </td>
				<td> Home Depot, Inc </td>
				<td> Nitin Valecha </td>
				<td> Q113 </td>
				<td> US </td>
				<td> &nbsp; </td>
				<td> A- </td>
				<td> Positive </td>
			</tr>
			
			<tr>
				<td> Home Depot, Inc</td>
				<td> Nitin Valecha</td>
				<td> Q412</td>
				<td> US</td>
				<td> &nbsp;</td>
				<td> B+ </td>
				<td> Neutral </td>
			</tr>
			
			<tr>
				<td> Home Depot, Inc</td>
				<td> Nitin Valecha</td>
				<td> Q312</td>
				<td> US</td>
				<td> &nbsp;</td>
				<td> B+ </td>
				<td> Neutral </td>
			</tr>
			
			<tr>
				<td> Home Depot, Inc</td>
				<td> Nitin Valecha</td>
				<td> Q212</td>
				<td> US</td>
				<td> &nbsp;</td>
				<td> B+ </td>
				<td> Neutral </td>
			</tr>
			
			<tr>
				<td> 2 </td>
				<td> Verizon</td>
				<td> Vaibhav Tandon</td>
				<td> Q113</td>
				<td> US</td>
				<td> &nbsp;</td>
				<td> B </td>
				<td> Negative </td>
			</tr>
			
			<tr>
				<td> 3 </td>
				<td> AT&T, Inc</td>
				<td> Vishaal Jatav </td>
				<td> Q113</td>
				<td> US</td>
				<td> &nbsp;</td>
				<td> B+ </td>
				<td> Neutral </td>
			</tr>
		
		</table>
	</div>
	</div>
  </div>
</div>
</body>
</html>