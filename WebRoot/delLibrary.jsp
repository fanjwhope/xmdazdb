<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String zdbdbName=request.getParameter("zdbdbName");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>删除过时库</title>
<script src="js/jquery-1.8.0.min.js"></script>
<script>
  $(function(){
  	 load();
  });
  
  function load(){
  	 $.ajax({
  	    url:"<%=path%>/do/UserDo/getDep?zdbdbName=<%=zdbdbName%>",
  	    type:"post",
  	    dataType:"json",
     	async:false,
  	    success:function(data){
  	    	var item;
  	    	$("option[name='dwm']").remove();
	   		$.each(data, function(i, result){
				item = "<option name='dwm' value='" + result['dwh'] + "'>" + result['dwmc'] + "</option>" ;
				$('#dwmc').append(item);
			});
			$("option[name='dwm']:first").attr("selected", true);
  	    }
  	 });
  }
  
  function del(){
  	 var xmnd = $("#nd").val();
  	 var department = $("option[name='dwm']:selected").val();
  	 if(xmnd == "")
  	 	alert("不允许空！");
  	 else
  	 	if(confirm("确认删除？"))
  	 		$.ajax({
     			url:"<%=path%>/do/ArchiveDo/delByXmnd?zdbdbName=<%=zdbdbName%>",
     			type:"post",
     			data:{'xmnd':xmnd, 'department':department},
     			success:function(data){
     				var json = $.parseJSON(data);
     				alert(json.msg);
     				load();
     				$('#nd').attr('value', '');
     			}
     		});
  }   
</script>
</head>
<body>
	<center>
		<table border="3" cellpadding="0" cellspacing="0" width="380"
			bordercolorlight="#F0F4FF" bordercolordark="#000099"
			bgcolor="#F0F4FF" height="60">
			<tr>
				<td width="100%" height="58" valign="top"><input type="hidden"
					value="" id="okdelete" name="okdelete">
					<table border="0" cellpadding="0" cellspacing="0" width="100%"
						height="34">
						<tr>
							<td width="100%" bgcolor="#0066CC" height="1">
								<p align="left">
									<font color="#FFFFFF" style="font-size:10pt">&nbsp;
										删除过时库项</font>
							</td>
						</tr>
						<tr>
							<td width="100%" height="8" align="center">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td width="16%">
											<p align="center">&nbsp;&nbsp;
										</td>
										<td width="12%">
											<p align="center">
										</td>
										<td width="21%">
											<p align="center">
										</td>
										<td width="20%"></td>
										<td width="32%" colspan="2"></td>
									</tr>
									<tr>
										<td width="16%" align="center"><font color="#000099"
											style="font-size:12pt" >年度:</font></td>
										<td width="12%" align="center"><input type="text"
											value="" id="nd" size='4' maxlength='4' 
											onkeyup="this.value=this.value.replace(/\D/g,'')"  
											onafterpaste="this.value=this.value.replace(/\D/g,'')"
											style="ime-mode:disabled"></td>
										<td width="21%" align="center"><font color="#000099"
											style="font-size:12pt">单位</font></td>
										<td width="20%" align="center">
										   <select id="dwmc">
										   </select>
										</td>
										<td width="18%">
											<p align="left">
												&nbsp;&nbsp;&nbsp;<input type="button" name="del"
													width="56" height="21" value="确 定" onclick="del()" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>
