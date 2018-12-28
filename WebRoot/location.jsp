<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String zdbdbName=request.getParameter("zdbdbName");
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>扫描件设置</title>
    <jsp:include page="publicJsp/libInclude.jsp" />
   <!--  <script src="js/jquery-1.8.0.min.js"></script> -->
    <script>
    	$(function(){
    		load();
    	});
    	
    	function load(){
    		$.ajax({
       			type:"POST",
       			url:"<%=path%>/do/LocationDo/find?zdbdbName=<%=zdbdbName%>",
       			dataType:"json",
       			success:function(data){
          			var item;
          			$("tr[name='sel']").parent().remove();
	      			$.each(data, function(i, result) {
	      				var flag = result['flag'];
	      				if(flag == 'y')
	      					item = "<tr name='sel'><td width='60%' style='padding-left:2cm'><input type='radio' color='#000099' " +
							   	   "style='font-size:12pt' name='radio' id='" + result['path'] + "' value='" + result['flag'] + 
							   	   "'>&nbsp;" + result['path'] + "</input></td><td width='40%'>" +
							   	   "<font color='red' style='font-size:12pt'>(存储目录)</font></td></tr>";
	      				else
							item = "<tr name='sel'><td width='60%' style='padding-left:2cm'><input type='radio' color='#000099' " +
							   	   "style='font-size:12pt' name='radio' id='" + result['path'] + "' value='" + result['flag'] + 
							   	   "'>&nbsp;" + result['path'] + "</input></td></tr>";
						$("#gpk").append(item);
		   			});
		   			//$("input[name='radio']:last").attr("checked", true);
       			}
       		});
    	};
    	
    	function add(){
    		$("#extra").css("display", "block");
    		$("#path").attr("value", "");
    		$("#insert").show();
    		$("#update").hide();
    		$("#no").attr("selected", "selected");
    	};
    	
    	function edit(){
    		cancel();
    		var path = $("input[type='radio']:checked").attr("id");
    		if(path == null)
    			alert("请选择路径！");
    		else{
    			$("#extra").css("display", "block");
    			document.getElementById("path").value = path;
    			var flag = $("input[type='radio']:checked").val();
    			if(flag == 'y')
    				$("#yes").attr("selected", "selected");
    			else
    				$("#no").attr("selected", "selected");
    			$("#insert").hide();
    			$("#update").show();
    		}
    	};
    	
    	function del(){
    		cancel();
    		var path = $("input[type='radio']:checked").attr("id");
    		if(path == null)
    			alert("请选择路径！");
    		else{
    			if(confirm("确定删除？"))
    				$.ajax({
    					url:"<%=path%>/do/LocationDo/del?zdbdbName=<%=zdbdbName%>",
    					type:"post",
    					data:{'path':path},
    					success:function(){
    						load();
    						cancel();
    					}
    				});
    		}
    	};
    	
    	function insert(){
    		var path = $("#path").val();
    		var flag = $("option:selected").val();
    		if(path == "")
    			alert("不允许空！");
    		else
    			$.ajax({
    				url:"<%=path%>/do/LocationDo/add?zdbdbName=<%=zdbdbName%>",
    				type:"post",
    				data:{'path':path, 'flag':flag},
    				success:function(){
    					load();
    					cancel();
    				}
    			}); 
    	};
    	
    	function update(){
    		var path1 = $("input[type='radio']:checked").attr("id");
    		var flag1 = $("input[type='radio']:checked").val();
    		var path2 = $("#path").val();
    		var flag2 = $("option:selected").val();
    		if(path2 == "")
    			alert("不允许空！");
    		else
    			$.ajax({
    				url:"<%=path%>/do/LocationDo/update?zdbdbName=<%=zdbdbName%>",
    				type:"post",
    				data:{'path1':path1, 'path2':path2, 'flag1':flag1, 'flag2':flag2},
    				success:function(){
    					load();
    					cancel();
    				}
    			});
    	};
    	
    	function cancel(){
    		$("#extra").css("display", "none");
    	};
    </script>
  </head>
  <body class="easyui-layout" data-options="fit:true">
	<div align="center"  data-options="region:'center',border:false" style="overflow:hidden;padding: 15px">
		<table border="3" cellpadding="0" cellspacing="0" width="38%"
			bordercolorlight="#f0f4ff" bordercolordark="#000099"
			bgcolor="#f0f4ff">
			<tr>
				<td width="100%">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td width="100%" colspan="3" bgcolor="#0066cc">
								<p align="left">
									<font color="#ffffff" style="font-size:10pt">扫描件路径项</font>
							</td>
						</tr>
						<tr>
							<td width="100%" colspan="2">&nbsp;&nbsp;</td>
						</tr>
						<tr>
							<td width="100%" colspan="2">
								<table id="gpk" border="0" cellpadding="0" cellspacing="0" width="100%">
								</table>
							</td>
						</tr>
						<tr>
							<td width="33%" align="right"></td>
							<td width="67%">&nbsp;</td>
						</tr>
						<tr>
							<td width="100%" colspan="2">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td width="25%" align="center">
											<input type="button" value="删 除" onclick="del()" />
										</td>
										<td width="25%" align="center">
											<input type="button" value="修 改" onclick="edit()" />
										</td>
										<td width="25%" align="center">
											<input type="button" value="添 加" onclick="add()" />
										</td>
									</tr>
								</table>
								<div id="extra" style="display:none">
									<table style="margin-top:20px;">
										<tr>
											<td width="55%" align="center"><font color="#000099">扫描件路径:</font></td>
											<td width="45%"><input id="path" type="text" /></td>
										</tr>
										<tr>
											<td align="center"><font color="#000099">存储目录:</font></td>
											<td><select>
												<option id="yes" value="y" >是</option>
												<option id="no" value="n" selected="selected">否</option>
											</select></td>
										</tr>
										<tr id="insert">
											<td align="right"><input type="button" value="确 定" onclick="insert()" /></td>
											<td align="right"><input type="button" value="取 消" onclick="cancel()" /></td>
										</tr>
										<tr id="update">
											<td align="right"><input type="button" value="确 定" onclick="update()" /></td> 
											<td align="right"><input type="button" value="取 消" onclick="cancel()" /></td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
