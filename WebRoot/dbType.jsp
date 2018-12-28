<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<% String cxt = request.getContextPath();
String zdbdbName=request.getParameter("zdbdbName");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>担保方式管理</title>
<script src="js/jquery-1.8.0.min.js"></script>
<script>
 $(function(){
      load(0);
  });
  
  function load(check){
     $.ajax({
     url:"<%=cxt%>/do/DBTypeDo/findAll?zdbdbName=<%=zdbdbName%>",
     type:"post",
     dataType:"json",
     async:false,
     success:function(data){
       var item;
       $("tr[name='rad']").parent().remove();
	   $.each(data, function(i, result){
				item = "<tr name='rad'><td width='' align='left' height='' valign='middle'><font style='font-size:12px' color='#000099'>" +
					   "<input type='radio' id='" + result['id'] + "' name='radio' value='" + result['cname'] + "'>&nbsp;" + 
					   (i+1) + "、" + result['cname'] + "</input></font></td><tr>" ;
				$('#rate').append(item);
			});
			if(check == 0)
			     $("input[name='radio']:last").attr("checked", true);
			else
			     $("#"+check).attr("checked", true);
		}
	});
  }
  
  function change(operator){
      var addDiv = document.getElementById("addDiv");
      var cname = document.getElementById("cname");
      var insert = document.getElementById("insert");
      var update = document.getElementById("update");
      var tep = $("input[name='radio']:checked").attr('id');
      
      switch(operator){
         case 'add':
            addDiv.style.display = "";
            insert.style.display = "";
            update.style.display = "none";
            cname.value = "";
            break;
         case 'pri':
            addDiv.style.display = "none";
            $.ajax({
              url:"<%=cxt%>/do/DBTypeDo/up?zdbdbName=<%=zdbdbName%>&id=" + tep,
              type:"post",
              success:function(){
                load(tep);
              }
            });
            break;
         case 'next':
            addDiv.style.display = "none";
            $.ajax({
              url:"<%=cxt%>/do/DBTypeDo/down?zdbdbName=<%=zdbdbName%>&id=" + tep,
              type:"post",
              success:function(){
                load(tep);
              }
            });
            break;
         case 'edit':
         	if($("input[name='radio']:checked").val() == null)
		    	break;
            addDiv.style.display = "";
            update.style.display = "";
            insert.style.display = "none";
            cname.value = $("input[name='radio']:checked").val();
            break;
         case 'del':
            if(confirm("确认删除？")){
               $.ajax({
                  url:"<%=cxt%>/do/DBTypeDo/del?zdbdbName=<%=zdbdbName%>&id=" + tep,
                  type:"post",
                  success:function(){
                      load(0);
                      addDiv.style.display = "none";
                  }
               });
            }
            break;
      }
  };
  
  function insertData(){
      var cname = document.getElementById("cname").value;
      if(cname == "")
      	 alert("请输入完整！");
      else
     	 $.ajax({
        	 url:"<%=cxt%>/do/DBTypeDo/add?zdbdbName=<%=zdbdbName%>",
        	 data:{'cname':cname},
        	 type:"post",
        	 success:function(){
            	 load(0);
            	 document.getElementById("addDiv").style.display = "none";
        	 }
     	 }); 
  };
  
  function updateData(){
      var cname = document.getElementById("cname").value;
      var tep = $("input[name='radio']:checked").attr("id");
      if(cname == "")
      	 alert("请输入完整！");
      else
     	 $.ajax({
        	 url:"<%=cxt%>/do/DBTypeDo/update?zdbdbName=<%=zdbdbName%>",
        	 data:{'id':tep, 'cname':cname},
        	 type:"post",
        	 success:function(){
            	 load(tep);
            	 document.getElementById("addDiv").style.display = "none";
        	 }
     	 });
  };
  
</script>
</head>

<body>
	<center>
		<table border="3" cellpadding="0" cellspacing="0" width="350"
			bordercolorlight="#ffffff" bordercolordark="#000099"
			bgcolor="#F0F4FF">
			<tr>
				<td width="100%">
					<table border="0" cellPadding="0" cellSpacing="0" width="100%"
						bordercolorlight="#FFFFFF" bordercolordark="#000099"
						bgcolor="#ECF5FF" height="1" bordercolor="#000099">
						<tr>
							<td width="348" align="center" height="1" valign="middle"
								bgcolor="#0066CC">
								<p align="left">
									<font color="#FFFFFF" style="font-size:10pt">&nbsp;
										担保方式管理</font>
								</p>
							</td>
						</tr>
						<tr>
							<td width="348" align="center" height="1" valign="middle">
								<table id="rate" border="1" cellpadding="0" cellspacing="0" width="100%"
									bordercolordark="#000099" bordercolorlight="#F0F4FF"
									height="1">
								</table>
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
								    <tr>
									<td width="" align="center" valign='top'><input
										type='button' value="添 加" class="smallBtn"
										onclick="change('add')"></td>
									<td width="" align="center" valign='top'><input
										type='button' value="上 移" class="smallBtn"
										onclick="change('pri')"></td>
									<td width="" align="center" valign='top'><input
										type='button' value="下 移" class="smallBtn"
										onclick="change('next')"></td>
									<td width="" align="center"><input type='button'
										value="修 改" class="smallBtn" onclick="change('edit')">
									</td>
									<td width="" align="center"><input type='button'
										value="删除" class="smallBtn" onclick="change('del')">&nbsp;</td>
									</tr>
								</table>
								<div  id="addDiv" style="display:none">
									<table border="0" cellpadding="0" cellspacing="0"
										width:100%>
										<tr>
											<td align='middle'><br/><font style="font-size:12px"
												color="#000099">担保方式名称:</font>
											</td>
										</tr>
										<tr>
											<td align=left>
												<input type='text' name='cname' id='cname' value='' size='20'>&nbsp;
												<input id="insert" value=" 保 存 " type='button' onclick="insertData()" style="display:none">
												<input id="update" value=" 保 存 " type='button' onclick="updateData()" style="display:none">
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>
