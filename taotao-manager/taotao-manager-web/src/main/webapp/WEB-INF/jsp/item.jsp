<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'item.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <table border="1" align="center" cellpadding="">
<tbody>
<tr><th colspan="2">主体</th></tr>
<tr>
<td>品牌</td><td>苹果（Apple）</td>
</tr>
<tr>
<td>型号</td><td>iPhone 6 A1586</td>
</tr>
<tr>
<td>颜色</td><td>金色</td>
</tr>
<tr>
<td>上市年份</td><td>2014</td>
</tr>
<tr><th colspan="2">网络</th></tr>
<tr>
<td>4G网络制式</td><td>移动4G(TD-LTE)/联通4G(FDD-LTE)/电信4G(FDD-LTE)</td>
</tr>
<tr>
<td>3G网络制式</td><td>移动3G(TD-SCDMA)/联通3G(WCDMA)/电信3G（CDMA2000）</td>
</tr>
<tr>
<td>2G网络制式</td><td>移动2G/联通2G(GSM)/电信2G(CDMA)</td>
</tr>
<tr><th colspan="2">存储</th></tr>
<tr>
<td>机身内存</td><td>16GB ROM</td>
</tr>
<tr>
<td>储存卡类型</td><td>不支持</td>
</tr>
</tbody>
</table>
  ${itemParam }
  </body>
</html>
