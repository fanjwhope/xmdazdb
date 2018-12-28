<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<% String cxt = request.getContextPath();
String zdbdbName=request.getParameter("zdbdbName");
%>
<html>
<head>
<title>�������͹���</title>
<script src="js/jquery-1.8.0.min.js"></script>
<script>
 $(function(){
      load(0);
  });
  
  function load(check){
     $.ajax({
     url:"<%=cxt%>/do/ProjectContentDo/find?zdbdbName=<%=zdbdbName%>",
     type:"post",
     dataType:"json",
     async:false,
     success:function(data){
       var item;
       $("tr[name='rad']").parent().remove();
	   $.each(data, function(i, result){
				item = "<tr name='rad'><td width='' align='left' height='' valign='middle'><font style='font-size:12px' color='#000099'>" +
					   "<input type='radio' id='" + result['ename'] + "' name='radio' value='" + result['cname'] + "'>&nbsp;" + 
					   (i+1) + "��" + result['cname'] + "(" + result['ename'] + ")" + "</input></font></td><tr>" ;
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
      var ename = document.getElementById("ename");
      var insert = document.getElementById("insert");
      var update = document.getElementById("update");
      var tep = $("input[name='radio']:checked").attr('id');
      switch(operator){
         case 'add':
            addDiv.style.display = "";
            insert.style.display = "";
            update.style.display = "none";
            cname.value = "";
            ename.value = "";
            $("input[name='ename']").removeAttr("readonly");
            break;
         case 'pri':
            addDiv.style.display = "none";
            $.ajax({
              url:"<%=cxt%>/do/ProjectContentDo/up?zdbdbName=<%=zdbdbName%>",
              data:{'ename':tep},
              type:"post",
              success:function(){
                load(tep);
              }
            });
            break;
         case 'next':
            addDiv.style.display = "none";
            $.ajax({
              url:"<%=cxt%>/do/ProjectContentDo/down?zdbdbName=<%=zdbdbName%>",
              data:{'ename':tep},
              type:"post",
              success:function(){
                load(tep);
              }
            });
            break;
         case 'edit':
            addDiv.style.display = "";
            update.style.display = "";
            insert.style.display = "none";
            cname.value = $("input[name='radio']:checked").val();
            ename.value = tep;
            $("input[name='ename']").attr("readonly", "");
            break;
         case 'del':
            if(confirm("ȷ��ɾ����")){
               $.ajax({
                  url:"<%=cxt%>/do/ProjectContentDo/del?zdbdbName=<%=zdbdbName%>",
                  type:"post",
                  data:{'ename':tep},
                  success:function(){
                      load(0);
                      addDiv.style.display = "none";
                  }
               });
            }
            break;
         case 'back':
        	 parent.$('#listPage').show();
        	 parent.$('#modifyPage').hide(); 
         	break;
      }
  };
  
  function insertData(){
      var cname = document.getElementById("cname").value;
      var ename = document.getElementById("ename").value;
      if(cname == "" | ename == "")
      	  alert("������������");
      else
      	 $.ajax({
        	 url:"<%=cxt%>/do/ProjectContentDo/add?zdbdbName=<%=zdbdbName%>",
        	 data:{'cname':cname, 'ename':ename},
        	 type:"post",
        	 success:function(){
            	 load(0);
            	 document.getElementById("addDiv").style.display = "none";
        	 }
     	 }); 
  };
  
  function updateData(){
      var cname = document.getElementById("cname").value;
      var ename = document.getElementById("ename").value;
      if(cname == "")
      	 alert("������������");
      else
     	 $.ajax({
        	 url:"<%=cxt%>/do/ProjectContentDo/update?zdbdbName=<%=zdbdbName%>",
        	 data:{'cname':cname, 'ename':ename},
        	 type:"post",
        	 success:function(){
            	 load(ename);
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
			bgcolor="#F0F4FF" >
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
										�������͹���</font>
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
										type='button' value="�� ��" class="smallBtn"
										onclick="change('add')"></td>
									<td width="" align="center" valign='top'><input
										type='button' value="�� ��" class="smallBtn"
										onclick="change('pri')"></td>
									<td width="" align="center" valign='top'><input
										type='button' value="�� ��" class="smallBtn"
										onclick="change('next')"></td>
									<td width="" align="center"><input type='button'
										value="�� ��" class="smallBtn" onclick="change('edit')">
									</td>
									<td width="" align="center"><input type='button'
										value="ɾ��" class="smallBtn" onclick="change('del')"></td>
								    <td >
								    <td width="" align="center"><input type='button'
										value="����" class="smallBtn" onclick="change('back')">&nbsp;</td>
									</tr>
								</table>
								<div id="addDiv" style="display:none">
									<table id = "hid" border="0" cellpadding="0" cellspacing="0"
										width:100%>
										<tr>
											<td>
											<font style="font-size:12px" color="#000099">&nbsp;������������:</font>
												<input type='text' id='cname' name='cname' value='' size='20'><br>
												&nbsp;<font style="font-size:12px;letter-spacing:6px"
												color="#000099">ƴ����д:</font> <input type='text' id='ename'
												name='ename' value='' size='10' style="ime-mode:Disabled"> 
												<input id="insert" value=" �� �� " type='button' onclick="insertData()" style="display:none">
												<input id="update" value=" �� �� " type='button' onclick="updateData()" style="display:none">
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
