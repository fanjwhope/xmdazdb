<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String zdbdbName=request.getParameter("zdbdbName");

%>

<html>
  <head>
    <title>�û�����</title>
    <script src="js/jquery-1.8.0.min.js"></script>
    <script src="js/easyui/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
	<script>
	$(function(){
		$('#rolesNew').combobox({
	        url: '<%=path%>/do/UserDo/getRoles?zdbdbName=<%=zdbdbName%>',
			valueField: 'id',   
	        textField: 'text',   
	        panelHeight:150,
    	    editable:false,
    	    onLoadSuccess: function () { //������ɺ�,����ѡ�е�һ��
                var val = $(this).combobox("getData");
                for (var item in val[0]) {
                    if (item == "id") {
                        $(this).combobox("select", val[0][item]);
                    }
                }
      	    }
		});
		load();
	});
	
	function load(){
		$('#content').hide();
		$('#tt').tree({   
	    	url:'<%=path%>/do/UserDo/getAllUser?zdbdbName=<%=zdbdbName%>',
	    	onClick:function(node){
	    		if(node.id == 1)
	    			$('#content').hide();
	    		else{
		    		$('#content').show();
		    		$('#preview').show();
		    		$('#edit').hide();
		    		view(node.id)
		    		$("#nodeDWH").val(node.id);
	    		}
	    	},
	    	onContextMenu: function(e, node){//��������
    	    	 e.preventDefault();
    	    	 $("#nodeDWH").val(node.id);
    	    	 $('#tt').tree('select', node.target);
    	    	 if(node.id == 1)
	    	    	 $('#r1').menu('show', {
	    	 			left: e.pageX,
	    	 			top: e.pageY
	    	 		 });
	    	 	 else if(node.id.substring(6,10) == 0 )//|| node.id.substring(1,2) == 6
	    	 	 	 $('#r2').menu('show', {
	    	 			left: e.pageX,
	    	 			top: e.pageY
	    	 		 });
	    	 	 else
	    	 	 	$('#r3').menu('show', {
	    	 			left: e.pageX,
	    	 			top: e.pageY
	    	 		 });
    	     }
		});
	};
	
	function view(dwh){
		$.ajax({
	 		url:'<%=path%>/do/UserDo/getOne?zdbdbName=<%=zdbdbName%>',
	    	type:'post',
	    	data:{'dwh':dwh},
	   		success:function(data){
	    		var json = $.parseJSON(data);
	    		$("#dwmc").html(json.dwmc);
	    		$("#userid").html(json.userid);
	    		$("#userpwdShow").html("******"); //json.userpwd
	    		$("#userpwd").val(json.userpwd);
	    		$("#roles").html(json.roles);
	   		}
	   	});
	   	$('#btn1').show();
	    $('#btn2').hide();
	}
	
	
	function del(){
		if(confirm('ȷ��ɾ��?')){
			//var node = $('#tt').tree('getSelected');
			var dwh=$("#nodeDWH").val();
			$.ajax({
				url:"<%=path%>/do/UserDo/del?zdbdbName=<%=zdbdbName%>",
				type:"post",
				data:{'dwh':dwh},
				success:function(){
					load();
				}
			});
		}
	};
	
	function edit(){
		$("#cap").html('�޸��û���Ϣ');
		$('#content').show();
	    $('#preview').hide();
	    $('#edit').show();
	    $('#btn2').show();
	    $('#btn1').hide();
	    $('#addE').show();
	    $('#addF').hide();
	    $('#addC').hide();
	    $("#dwmcNew").attr("value", $("#dwmc").html());
	    $("#useridNew").attr("value", $("#userid").html());
		$("#userpwdNew").attr("value",  $("#userpwd").val());
		$("#rolesNew").combobox("setValue",$("#roles").html());
	}
	
	function cancel(){
		 $('#preview').show();
	    $('#edit').hide();
		var dwh=$("#nodeDWH").val();
		view(dwh); 
	}
	
	function appendChildren(){
		$("#cap").html('������û�');
		$('#content').show();
	    $('#preview').hide();
	    $('#edit').show();
	    $('#btn2').show();
	    $('#btn1').hide();
	    $('#form').form('clear');
	    $('#addE').hide();
	    $('#addF').hide();
	    $('#addC').show();
	}
	
	function appendFather(){
		$("#cap").html('��Ӹ��û�');
		$('#content').show();
	    $('#preview').hide();
	    $('#edit').show();
	    $('#btn2').show();
	    $('#btn1').hide();
	    $('#form').form('clear');
	    $('#addE').hide();
	    $('#addF').show();
	    $('#addC').hide();
	}
	
	function saveToShow(){
		$("#edit").hide();//�����޸�table
		$("#btn2").hide();//���ذ�ť
		$("#preview").show();//������ʾtable
		$("#btn1").show();//��ʾ��ť
		$("#dwmc").html($("#dwmcNew").val());
		$("#userid").html($("#useridNew").val());
		$("#userpwdShow").html($("#userpwdNew").val());
		$("#roles").html($("#rolesNew").combobox('getText'));
	}
	
	function saveAddE(){
		var id1 = $("#userid").html();
		var dwmcOld = $("#dwmc").html();
		var dwmc = $("#dwmcNew").val();
		var id2 = $("#useridNew").val();
		var pwd = $("#userpwdNew").val();
		var roles=$("#rolesNew").combobox('getValue');
		$('#btn2').show();
		$('#btn1').hide();
		if(dwmc == '' | id2 == '' | pwd == ''){
			alert('������գ�');
		}else{
			$.ajax({
				url:"<%=path%>/do/UserDo/update?zdbdbName=<%=zdbdbName%>",
				type:"post",
				data:{'id1':id1, 'dwmcOld':dwmcOld, 'dwmc':dwmc, 'id2':id2, 'pwd':pwd,'roles':roles},
				success:function(data){
					var json = $.parseJSON(data);
					if(json.success == false){
						alert(json.msg);
					}
					else{
						$('#tt').tree('reload');
					}
					    saveToShow();
				}
			});
		}
	}
	
	function saveAddF(){
		var dwmc = $("#dwmcNew").val();
		var id = $("#useridNew").val();
		var pwd = $("#userpwdNew").val();
		var roles=$("#rolesNew").combobox('getValue');
		if(dwmc == '' | id == '' | pwd == ''){
			alert('������գ�');
		}else{
			$.ajax({
				url:"<%=path%>/do/UserDo/insertF?zdbdbName=<%=zdbdbName%>",
				type:"post",
				data:{'dwmc':dwmc, 'id':id, 'pwd':pwd,'roles':roles},
				success:function(data){
					var json = $.parseJSON(data);
					if(json.success == false){
						alert(json.msg);
					} else {
					    $('#tt').tree('reload');
					    saveToShow();
					}
				}
			});
		}
	}
	
	function saveAddC(){
		var dwh = $("#nodeDWH").val();
		var dwmc = $("#dwmcNew").val();
		var id = $("#useridNew").val();
		var pwd = $("#userpwdNew").val();
		var roles=$("#rolesNew").combobox('getValue');
		if(dwmc == '' | id == '' | pwd == ''){
			alert('������գ�');
		}else{
			$.ajax({
				url:"<%=path%>/do/UserDo/insertC?zdbdbName=<%=zdbdbName%>",
				type:"post",
				async:false,
				data:{'dwmc':dwmc, 'id':id, 'pwd':pwd, 'dwh':dwh,'roles':roles},
				success:function(data){
					var json = $.parseJSON(data);
					if(json.success == false){
						alert(json.msg);
					} else {
					    $('#tt').tree('reload');
					    saveToShow();
					}
				}
			});
		}
	}
	
	
	<%-- function save(temp){
		switch(temp){
			case 'addE':
				/* var node = $('#tt').tree('getSelected');
				view(node.id); */
				//var dwh=$("#nodeDWH").val();
				//view(dwh);
				var id1 = $("#userid").html();
				var dwmcOld = $("#dwmc").html();
				var dwmc = $("#dwmcNew").val();
				var id2 = $("#useridNew").val();
				var pwd = $("#userpwdNew").val();
				var roles=$("#rolesNew").combobox('getValue');
				$('#btn2').show();
	    		$('#btn1').hide();
				if(dwmc == '' | id2 == '' | pwd == '')
					alert('������գ�');
				else
					$.ajax({
						url:"<%=path%>/do/UserDo/update",
						type:"post",
						data:{'id1':id1, 'dwmcOld':dwmcOld, 'dwmc':dwmc, 'id2':id2, 'pwd':pwd,'roles':roles},
						success:function(data){
							var json = $.parseJSON(data);
							if(json.success == false)
								alert(json.msg);
							else
								$('#tt').tree('reload');
							saveToShow();
						}
					});
				break;
			case 'addF':
				var dwmc = $("#dwmcNew").val();
				var id = $("#useridNew").val();
				var pwd = $("#userpwdNew").val();
				var roles=$("#rolesNew").combobox('getValue');
    			if(dwmc == '' | id == '' | pwd == '')
    				alert('������գ�');
    			else
    				$.ajax({
    					url:"<%=path%>/do/UserDo/insertF",
    					type:"post",
    					data:{'dwmc':dwmc, 'id':id, 'pwd':pwd,'roles':roles},
    					success:function(data){
    						var json = $.parseJSON(data);
    						if(json.success == false){
	    						alert(json.msg);
	    						//$('#content').hide();
	    					} else {
							    $('#tt').tree('reload');
							    saveToShow();
							}
    					}
    				});
				break;
			case 'addC':
				//var node = $('#tt').tree('getSelected');
				//var dwh = node.id;
				var dwh = $("#nodeDWH").val();
				var dwmc = $("#dwmcNew").val();
				var id = $("#useridNew").val();
				var pwd = $("#userpwdNew").val();
				var roles=$("#rolesNew").combobox('getValue');
				if(dwmc == '' | id == '' | pwd == '')
					alert('������գ�');
				else
					$.ajax({
						url:"<%=path%>/do/UserDo/insertC",
						type:"post",
						async:false,
						data:{'dwmc':dwmc, 'id':id, 'pwd':pwd, 'dwh':dwh,'roles':roles},
						success:function(data){
							var json = $.parseJSON(data);
							if(json.success == false){
								alert(json.msg);
								//$('#content').hide();
							} else {
							    $('#tt').tree('reload');
							    saveToShow();
							}
						}
					});
				break;
		}
	}
	 --%>
	</script>
	<style>
	.tab{text-align:center;border-width:1px;border-collapse:collapse;margin-top:40px;BORDER: #0066CC 1px solid;}
	.tab td{BORDER: #0066CC 1px solid;width:150px}
	.tab2{text-align:center;}
	.font1{font-size:14px;cursor:hand;color: #000099;}
	</style>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'west',title:'��֯�ṹ',split:true" style="width:380px;">
  		<ul id="tt" style="margin-left:10px"></ul>  
  	</div>
  	<div data-options="region:'center',title:'�û���Ϣ'">
	  	<div id="content" style="width:100%;" align="center">
	  	    <input type="hidden" id="nodeDWH" name="nodeDWH"/>
	  	     <input type="hidden" id="userpwd" name="userpwd"/>
	  		<table id="preview" class="tab" border="1">
	  			<caption><font class="font1">�û���Ϣ</font></caption>
	  			<tr>
	  				<td><font class="font1">��λ����</font></td>
	  				<td><font class="font1">�û��˺�</font></td>
	  				<td><font class="font1">�û�����</font></td>
	  				<td><font class="font1">�û�����</font></td>
	  			</tr>
	  			<tr>
	  				<td><font class="font1"><p id="dwmc"></p></font></td>
	  				<td><font class="font1"><p id="userid"></p></font></td>
	  				<td><font class="font1"><p id="userpwdShow"></p></font></td>
	  				<td><font class="font1"><p id="roles"></p></font></td>
	  			</tr>
	  			<tr>
	  				<table id="btn1" class="tab2">
		  				<tr>
			  				<td width="300px">
			  					<input type="button" value="�޸�" style="margin-right:20%;margin-top:10px" onclick="edit()">
			  					<input type="button" value="ɾ��" onclick="del()">
			  				</td>
		  				</tr>
	  				</table>
	  			</tr>
	  		</table>
	  		<form id="form">
		  		<table id="edit" class="tab" border="1">
		  			<caption ><font class="font1" id="cap"></font></caption>
		  			<tr>
		  				<td><font class="font1">��λ����</font></td>
		  				<td><font class="font1">�û��˺�</font></td>
		  				<td><font class="font1">�û�����</font></td>
		  				<td><font class="font1">�û�����</font></td>
		  			</tr>
		  			<tr>
		  				<td><font class="font1"><input id="dwmcNew" type="text" size="10" value=""></font></td>
		  				<td><font class="font1"><input id="useridNew" type="text" size="10" value="" maxlength="10" style="ime-mode:disabled"></font></td>
		  				<td><font class="font1"><input id="userpwdNew" type="password" size="10" value="" maxlength="8" style="ime-mode:disabled"></font></td>
		  					<td><font class="font1"><input id="rolesNew" type="text" size="10" value="" maxlength="8" ></font></td>
		  			</tr>
		  			<tr>
		  				<table id="btn2" class="tab2">
			  				<tr>
				  				<td width="300px">
				  					<input id="addE" type="button" value="ȷ��" onclick="saveAddE()">
				  					<input id="addF" type="button" value="ȷ��" onclick="saveAddF()">
				  					<input id="addC" type="button" value="ȷ��" onclick="saveAddC()">
				  					<input type="button" value="ȡ��" style="margin-left:20%;margin-top:10px" onclick="cancel()">
			  					</td>
			  				</tr>
		  				</table>
		  			</tr>
		  		</table>
	  		</form>
	  	</div>
  	</div>
  	
  	<div id="r1" class="easyui-menu" style="width:100px;">
		<div onclick="appendFather()" data-options="iconCls:'icon-add'">��Ӹ��û�</div>
	</div>
  	<div id="r2" class="easyui-menu" style="width:100px;">
		<div onclick="appendChildren()" data-options="iconCls:'icon-add'">������û�</div>
		<div onclick="edit()" data-options="iconCls:'icon-remove'">�޸�</div>
		<div onclick="del()" data-options="iconCls:'icon-cancel'">ɾ��</div>
	</div>
  	<div id="r3" class="easyui-menu" style="width:100px;">
  		<div onclick="edit()" data-options="iconCls:'icon-remove'">�޸�</div>
		<div onclick="del()" data-options="iconCls:'icon-cancel'">ɾ��</div>
  	</div>
  </body>
</html>
