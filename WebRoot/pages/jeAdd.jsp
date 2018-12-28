<%@page import="com.hr.util.BaseDataOP"%>
<%@page import="com.hr.bean.Biz"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
   String tableName= ArchiveUtil.getFullTableName(request, Biz.class);;
   String sql= "select biz from " + tableName;
   String[][] bz =new BaseDataOP(null).queryRowAndCol(sql);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <jsp:include page="/publicJsp/libInclude.jsp" />
    <title>µµ°¸ÖøÂ¼</title>
    <style type="text/css">
      	.button{
	        valign="top";height="24px";font-size:14px;padding-top:1px;padding-left:1px;padding-right:1px;
                background-color: buttonface; color: #000000;
                border-left: 1 solid #FFffff; border-right: 1 solid #000000;
                border-top: 1 solid #FFffff; border-bottom: 1 solid #000000
	       }
	       .input{valign="top";height="18px";font-size:14px;padding-top:1px;padding-left:5px;padding-right:5px;
               background-color: #0094BD; color: #ffffff;
               border-left: 0 solid #FFffff; border-right: 0 solid #000099;
               border-top: 0 solid #FFffff; border-bottom: 0 solid #000099;
               cursor:hand
               }
 TD{
    FONT-SIZE: 14PX;
 }
    </style>
   <script type="text/javascript">
     function addJe(){
    	 var bz=document.getElementById("dwList").value;
    	 var str='<tr ><td width="15px"><img  src="image/del.png" onclick="dell(this)" ></td>'
	                +'<td align="right">'+bz+'£º</td>'
	                +'<td><input type="text" id="nums" name="nums" style="width:85px"/> Íò</td>'
	                +'</tr>';
    	 $('#jeTab').append(str);
     }
     
     function dell(obj)
     {
       var tr=obj.parentNode.parentNode;   
       var tbody=tr.parentNode;  
       tbody.removeChild(tr);
   }
     
     function col(){
    	 window.close();
     }
     
    </script> 
  </head>
  
  <body  >
  
  </body>
</html>
