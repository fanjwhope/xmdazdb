<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String lsh=request.getParameter("lsh");
	String tbname=request.getParameter("tbname");
	String zdbdbName=request.getParameter("zdbdbName");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>��������</title>
<jsp:include page="../publicJsp/libInclude.jsp" />

<script type="text/javascript">
	$(function() {
		$('#cc').combobox({
			url : "<%=path%>/do/spnr/getType?zdbdbName=<%=zdbdbName%>",
			valueField : 'id',
			textField : 'spnr'
		});
	});
	
	function getDetail(){
		var archiveType=$('#cc').combobox('getValue');
        	
        	if(archiveType==''){
        		
        		alert("��ѡ��������ݣ�");
        		
        	}else{
		        	var  lsh='<%=lsh%>';
		        	if(lsh){
			        	var select = $('#cc').combobox('getValue');
			        	 $.ajax({
			        		 type:'post',
			        	     url:'<%=path%>/do/ArchiveDo/isNotStand?zdbdbName=<%=zdbdbName%>&archiveType='+archiveType+'&lsh='+lsh,
			        	     dataType:'json',
			        	     success:function(data){			        	    
			        	    		window.open('<%=path%>/pages/achiveDirectoryStandZDB.jsp?zdbdbName=<%=zdbdbName%>&archiveType=xmlrb&archiveTypeZDB='+archiveType+'&lsh='+lsh+'&type='+select);	        	    	 
			        	     }
			        	});
		        	}else{
		        		alert("������¼��Ŀ��Ϣ");
		        		return false;
		        	}
		        	
		        }
	}
</script>

</head>

<body>
	<table id="cellTable" name="cellTable" width="660" align="center"
		style="border-collapse:collapse;border-color:#000000" border=1
		cellspacing="0" cellpadding="0">
		<tr style="background-color:#40B0C0" align="center">
			<td width='10' nowrap></td>
			<td width='40' style='font-size:14px;color:#FFFFFF'>���</td>
			<td width='350' style='font-size:14px;color:#FFFFFF'>��������</td>
			<td width='30' style='font-size:14px;color:#FFFFFF'>ҳ��</td>
			<td width='60' style='font-size:14px;color:#FFFFFF'>ҳ��</td>
			<td width='180' style='font-size:14px;color:#FFFFFF'>��ע</td>
			<td width='0' style='font-size:14px;color:#FFFFFF'></td>
		</tr>
		<td colspan='6' align=center>��ѡ����������ģ�� <input id="cc"><input
			type="button" class="button" value=" ѡ �� " onclick="getDetail()"> <input
			type="button" class="button" value=" �� �� " onclick="window.close();">
		</td>
	</table>
</body>
</html>
