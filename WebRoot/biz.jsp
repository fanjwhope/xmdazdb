<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String cxt = request.getContextPath();
	String zdbdbName=request.getParameter("zdbdbName");
	
%>

<html>
<head>
<title>币种管理</title>
<script src="js/jquery-1.8.0.min.js"></script>
</head>
<body>
	<center>
		<table border="3" cellpadding="0" cellspacing="0" width="250"
			bordercolorlight="#F0F4FF" bordercolordark="#000099"
			bgcolor="#F0F4FF">
			<tr>
				<td width="100%">
						<table border="0" cellPadding="0" cellSpacing="0" width="248"
							bordercolorlight="#FFFFFF" bordercolordark="#000099"
							bgcolor="#ECF5FF" height="1" bordercolor="#000099">
							<tr>
								<td width="348" align="center" height="1" valign="middle"
									bgcolor="#0066CC">
									<p align="left">
										<font color="#FFFFFF" style="font-size:10pt">&nbsp;币种管理</font>
									</p>
								</td>
							</tr>
							<tr>
								<td width="348" align="center" height="1" valign="middle">
									<table id="bi" border="1" cellpadding="0" cellspacing="0" width="100%"
										bordercolordark="#000099" bordercolorlight="#F0F4FF"
										height="1">
										<tr>
											<td width="64" align="center" height="1" valign="middle"
												colspan=''><font color="#000099" style="font-size:10pt">当前币种:</font>
											</td>
										</tr>
									</table> <br>
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td width="" align="center" valign='top'><button
													style='cursor:hand' border="0" onclick="change('add')">添加</button>
											</td>
											<td width="" align="center"><button style='cursor:hand'
													border="0" onclick="change('edit')">修改</button></td>
											<td width="" align="center"><button style='cursor:hand'
													border="0" onclick="change('del')">删除</button></td>
										</tr>
									</table>
									<div id="addDiv" style="display:none">
									<table style="margin-top:20px;">
										<tr>
											<td><font style="font-size:10pt" color="#000099"><p id="p"></p></font></td>
										</tr>
										<tr>
											<td><input type="text" id="biz" name="biz" value="" style="width:80px"/>&nbsp;
											<input id="insert" value="确  定" type='button' onclick="insertData()" style="display:none">
											<input id="update" value="确 定" type='button' onclick="updateData()" style="display:none">
											</td>
										</tr>
									</table>
									</div></td>
							</tr>
						</table>
			      </td>
			</tr>
		</table>
	</center>
</body>
</html>
<script>
  $(function(){
      load();
  });
  
  function load(){
      $.ajax({
       	type:"POST",
       	url:"<%=cxt%>/do/BizDo/findAll?zdbdbName=<%=zdbdbName%>",
       	dataType:"json",
       	success:function(data){
          	var item;  
          	$("tr[name='rad']").parent().remove();
	      	$.each(data, function(i, result) {
					item = "<tr name='rad'><td width='' align='left' height='' valign='middle'><font style='font-size:12px' color='#000099'>" +
					       "&nbsp;<input type='radio' name='radio' value='" +result['biz']+ "'>" + result['biz'] + 
					        "</input></font></td><tr>" ;
					$("#bi").append(item);
		   	});
		   	$("input[name='radio']:last").attr("checked", true);
       	}
       });
  };
  
  function change(z) {
	 var addDiv = document.getElementById("addDiv");
	 var p = document.getElementById("p");
	 var biz = document.getElementById("biz");
	 var insert = document.getElementById("insert");
     var update = document.getElementById("update");
     var tep = $("input[name='radio']:checked").val();
        
	 switch (z) {
		case 'add':
	        addDiv.style.display = "";
			p.innerHTML = "请输入您要添加的币种名称:";
			biz.value = "";
			update.style.display = "none";
			insert.style.display = "";
			break;
		case 'edit':
		    if(tep == null)
		    	break;
		    addDiv.style.display = "";
			p.innerHTML = "请输入新的币种名称:";
			biz.value = tep;
			update.style.display = "";
			insert.style.display = "none";
			break;
		case 'del':
		   	if(confirm("确认删除？")){
		   	   $.ajax({
		   	      url:"<%=cxt%>/do/BizDo/del?zdbdbName=<%=zdbdbName%>",
		   	      type:"post",
		   	      data:{'biz':tep},
		   	      success:function(){
		   	          load();
		   	          addDiv.style.display = "none";
		   	      }
		   	   });
		   	}
			break;
	  }
   };
	
  function insertData(){
      var biz = document.getElementById("biz").value;
      if(biz == "")
      	 alert("请输入完整！");
      else
     	 $.ajax({
        	 url:"<%=cxt%>/do/BizDo/add?zdbdbName=<%=zdbdbName%>",
        	 data:{'biz':biz},
        	 type:"post",
        	 success:function(){
            	 load();
            	 document.getElementById("addDiv").style.display = "none";
        	 }
     	 }); 
  };
  
  function updateData(){
      var biz1 = document.getElementById("biz").value;
      var biz2 = $("input[name='radio']:checked").val();
      if(biz1 == "")
      	 alert("请输入完整！");
      else
     	 $.ajax({
        	 url:"<%=cxt%>/do/BizDo/update?zdbdbName=<%=zdbdbName%>",
        	 data:{'biz1':biz1, 'biz2':biz2},
        	 type:"post",
        	 success:function(){
            	 load();
            	 document.getElementById("addDiv").style.display = "none";
        	 }
     	 });
  };
</script>