<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String cxt = request.getContextPath();
		String zdbdbName=request.getParameter("zdbdbName");
	
%>

<html>
<head>
<title>项目品种管理</title>
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
										<font color="#FFFFFF" style="font-size:10pt">&nbsp;项目品种管理</font>
									</p>
								</td>
							</tr>
							<tr>
								<td width="348" align="center" height="1" valign="middle">
									<table id="bi" border="1" cellpadding="0" cellspacing="0" width="100%"
										bordercolordark="#000099" bordercolorlight="#F0F4FF"
										height="1">
										<tr>
											<td width="100" align="center" height="1" valign="middle"
												colspan=''><font color="#000099" style="font-size:10pt">当前项目品种:</font>
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
											<td><input type="text" id="biz" name="biz" value="" style="width:150px"/>&nbsp;
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
       url:"<%=cxt%>/do/ProjectTypeDo/getAllProjectType?zdbdbName=<%=zdbdbName%>",
       dataType:"json",
       success:function(data){
          var item;  
          $("tr[name='rad']").parent().remove();
	      $.each(data, function(i, result) {
					item = "<tr name='rad'><td width='' align='left' height='' valign='middle'><font style='font-size:12px' color='#000099'>" +
					       "&nbsp;<input type='radio' id='" + result['id'] + "' name='radio' value='" + result['name'] + "'>" + result['name'] + 
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
			p.innerHTML = "请输入您要添加的项目品种名称:";
			biz.value = "";
			update.style.display = "none";
			insert.style.display = "";
			break;
		case 'edit':
		    if(tep == null)
		    	break;
		    addDiv.style.display = "";
			p.innerHTML = "请输入新的项目品种名称:";
			biz.value = tep;
			update.style.display = "";
			insert.style.display = "none";
			break;
		case 'del':
			var id = $("input[name='radio']:checked").attr("id");
		   	if(confirm("确认删除？")){
		   	   $.ajax({
		   	      url:"<%=cxt%>/do/ProjectTypeDo/del?zdbdbName=<%=zdbdbName%>",
		   	      type:"post",
		   	      data:{'id':id},
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
	  var bizVal=document.getElementById("biz").value;
	  if(bizVal==''){
		  alert('请输入项目品种！');
	  }
	  $.ajax({
		   url:'<%=cxt%>/do/ProjectTypeDo/isExit?zdbdbName=<%=zdbdbName%>&name='+bizVal,
	   	   type:'post',
	   	   dataType:'json',
	   	   success:function(data){
	   		   if(data.msg=='1'){
	   			  alert('该品种已经存在！');
	   			return ;
	   		   }else{
	   			 $.ajax({
	   	        	 url:"<%=cxt%>/do/ProjectTypeDo/add?zdbdbName=<%=zdbdbName%>",
	   	        	 data:{'name':bizVal},
	   	        	 type:"post",
	   	        	 success:function(){
	   	            	 load();
	   	            	 document.getElementById("addDiv").style.display = "none";
	   	        	 }
	   	     	 }); 
	   		   }
	   	   }
	   });
  };
  
  function updateData(){
	  var bizVal=document.getElementById("biz").value;
      var id = $("input[name='radio']:checked").attr("id");
      if(bizVal==''){
		  alert('请输入项目品种！');
	  }
      $.ajax({
		   url:'<%=cxt%>/do/ProjectTypeDo/isExit?zdbdbName=<%=zdbdbName%>&name='+bizVal,
	   	   type:'post',
	   	   dataType:'json',
	   	   success:function(data){
	   		   if(data.msg=='1'){
	   			   alert('该品种已经存在！');
	   			return ;
	   		   }else{
		     	 $.ajax({
		        	 url:"<%=cxt%>/do/ProjectTypeDo/update?zdbdbName=<%=zdbdbName%>",
		        	 data:{'id':id, 'name':bizVal},
		        	 type:"post",
		        	 success:function(){
		            	 load();
		            	 document.getElementById("addDiv").style.display = "none";
		        	 }
		     	 });
	         }
	   	 }
      });
  };
</script>