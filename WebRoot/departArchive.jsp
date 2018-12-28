<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cxt=request.getContextPath();
String archiveType=request.getParameter("archiveType");
String departCode=request.getParameter("departCode");
String archiveText = request.getParameter("archiveText");
String zdbdbName=request.getParameter("zdbdbName");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
     <jsp:include page="/publicJsp/libInclude.jsp" />
     <style type="text/css">
        
     
     
     </style>
  <script type="text/javascript">
    $(function(){
    	//获取该单位卷类型的文档目录
    	refreshForm();
    	$("#dalx").combobox({
    		valueField:'text',   
     	    textField:'text',
     	    editable : false,
     	   panelHeight:100,
     	    data:[{
     	    	"text":"主要文档",
     	    	"selected":true
     	    },{
     	    	"text":"一般文档"  
     	    }]
    	});
    	$('#StandardDialog').hide();
    });
    
    
    function refreshForm(){
    	$.ajax({
   		 type:"post",
   		 url:'<%=cxt%>/do/DepartStandardDo/getList?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&departCode=<%=departCode%>',
   		 dataType:'json',
   		 success:function(data){
   			 var leng=data.length;
   			 if(leng>0){
   				 for(var i=0;i<leng;i++){
   					 var str='<tr><td width=64 align=center height=19 valign=middle><font style="font-size:12px" color="#000099" >'+data[i].dalx+'</font></td>';
   					str+='<td width=32 align=center height=19 valign=middle ><font style="font-size:12px" color="#000099"><input type=text" class="indexClass" size="3" maxlength="3" name="sort" value='+(i+1)+'>  </td>';
					str+=' <td width=492 align=center height=19 valign=middl><font style="font-size:12px"color="#000099">'+data[i].damc+'</font> </td>';
					str+='<td width=32 align=center height=19 valign=middle><font style="font-size:12px" color="#000099"><input type="radio" name="myid" id="myid" value='+data[i].id+'></font> </td> </tr>';
					$('#tForm').append(str);
   				 }
   				/*  $('input.indexClass').numberbox({
   					min:0,   
   				    precision:1 
   				 }); */
   			 }
   		 }
   	});
    }
    
    function submitCheck(){
    	var sort = "";
    	var id = "";
    	$("input[name='sort']").each(function(index){
    		sort += $(this).val() + "/";
		});
		$("input[name='myid']").each(function(index){
    		id += $(this).val() + "/";
		});
    	$.ajax({
    		type:'post',
    		url:'<%=cxt%>/do/DepartStandardDo/sort?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&departCode=<%=departCode%>',
    		dataType:'json',
    		data:{'sort':sort, 'id':id},
    		success:function(data){
    			window.location.href="departArchive.jsp?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&departCode=<%=departCode%>&archiveText=<%=archiveText%>";
    		}
    	}); 
    } 
    
    function insertnewcheck(){
    	$('#main_form').form('clear');
    	$('#StandardDialog').css('display','');
    	 $('#StandardDialog').dialog({
 		    title:'添加内容',
 		    modal: true,
 		    closable:true
 		   });
    }
    
    function modifycheck(){
    	if($('input[name=myid]:checked').length==0){
    		alert("请选择一条记录！");
    		return  false;
    	}
    	var id=$('input[name=myid]:checked').val();
    	$.ajax({
    		type:"post",
    		url:'<%=cxt%>/do/DepartStandardDo/getOne?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&departCode=<%=departCode%>&id='+id,
    		dataType:'json',
    		success:function(data){
    			if(data!=null){
    			 $('#id').val(data.id);
    			 $('#dalx').val(data.dalx);
    			 $('#damc').val(data.damc);
    			 $('#StandardDialog').css('display','');
    	         $('#StandardDialog').dialog({
    	 		    title:'修改内容',
    	 		    modal: true,
    	 		    closable:true
    	 		   }); 
    		      }
    	     }
    	});
    }
    
    function deletecheck(){
    	if($('input[name=myid]:checked').length==0){
    		alert("请选择一条记录");
    		return  false;
    	}
    	var id=$('input[name=myid]:checked').val();
    	$.messager.confirm('确认','删除将不可恢复，是否确认删除？', function(b){
			if(b){
			    	$.ajax({
			      		 type:"post",
			      		 url:'<%=cxt%>/do/DepartStandardDo/del?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&departCode=<%=departCode%>&id='+id,
			      		 dataType:'json',
			      		 success:function(data){
			      			window.location.href="departArchive.jsp?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&departCode=<%=departCode%>";
			      			 parent.$.messager.show({
								title : '提示',
								msg : data.msg
							});
			      		 }
			      		 });
			}
    	});	
    }
    
    function insertd(){
    	var damc=$('#damc').val();
    	if(damc==null||damc==""){
    		alert("请填写档案名称");
    		return false;
    	}
    	var id=$('#id').val();
    	var url;
    	if(id==null||id==""){
    		url='<%=cxt%>/do/DepartStandardDo/add?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&departCode=<%=departCode%>';
    	}else{
    		url='<%=cxt%>/do/DepartStandardDo/update?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&departCode=<%=departCode%>&id='+id;
    	}
    	$('#main_form').form('submit', {
    		url:url,
    		success:function(data){
    			window.location.href="departArchive.jsp?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&departCode=<%=departCode%>";
    			var json = $.parseJSON(data);
    			 $('#StandardDialog').dialog('close');
    			parent.$.messager.show({
					title : '提示',
					msg : json.msg
				});
    		}
    	});
    }
    
    function modifydk(){
    	$('#damc').val("");
    }
    
    function  clo(){
    	$('#StandardDialog').dialog('close');
    }
    
    function goBack(){
    	 parent.$('#listPage').show();
    	 parent.$('#modifyPage').hide(); 
    }
    
    function len(){
    	alert($('body').height()+","+$('#modifyPage').height());
    }
  </script>

  </head>
  
  <body class="easyui-layout" data-options="fit:true" >
        <div data-options="region:'center',border:false" style="overflow:auto" >
        <div id="modifyPage" align="center" style="hidden;margin-top: 20px">
          <table border="3" cellpadding="0" cellspacing="0" width="350" bordercolorlight="#F0F4FF" bordercolordark="#000099" bgcolor="#F0F4FF">
            <tr><td colspan='5' align="center" height="12" valign="middle" bgcolor="#0066CC">
                  <p align="left"><font color="#FFFFFF" style="font-size:12px">&nbsp; <%=archiveText %>维护(如果想将3调整到1和2之间，只须将3改为1.2再存盘即可！)</font></p>
                </td> </tr>
            <tr><td width="348" align="center"  valign="middle">
            <table id="tForm" border="1" cellpadding="0" cellspacing="0" width="588" bordercolordark="#000099" bordercolorlight="#F0F4FF" >
                <tr style="height: 14px">
                <td width="64" align="center" height="12" valign="middle"><font style="font-size:12px" color="#000099">档案类型</font></td>
                <td width="32" align="center" height="12" valign="middle"><font style="font-size:12px" color="#000099">序号</font></td>
                <td width="492" align="center" height="12" valign="middle"><font style="font-size:12px" color="#000099">档案名称</font></td>
                <td width="32" align="center" height="12" valign="middle">&nbsp;</td>
              </tr>
               
            </table>
            </td></tr>
            <tr><td><table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                      <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                      <td  align="center"><IMG  style='cursor:hand' border="0" src="pages/image/an-cp.gif" width="56" height="21" onclick="submitCheck()"></a>&nbsp;</td>
                      <td  align="center"><IMG  style='cursor:hand' border="0" src="pages/image/an-tj.gif" width="56" height="21" onclick="insertnewcheck()"></a>&nbsp;</td>
                      <td  align="center"><IMG  style='cursor:hand' border="0" src="pages/image/an-xg.gif" width="56" height="21" onclick="modifycheck()"></a>&nbsp;</td>
                      <td  align="center"><IMG  style='cursor:hand' border="0" src="pages/image/an-sc.gif" width="56" height="21" onclick="deletecheck()">&nbsp;</td>
                      <td  align="center"><IMG  style='cursor:hand' border="0" src="pages/image/an-fh.gif" width="56" height="21" onclick="goBack()">&nbsp;</td>
                    </tr>
                  </table>
            </td></tr>
          </table>
        </div> 
       
      <div id="StandardDialog"  style="width: 300px; height:160px;" >
          <form id="main_form" name="main_form" method="post" >
         <input type="hidden" id="id"  name="id"/>
             <table style="margin-top:12px;margin-bottom:5px;margin-left:5px;font-size:12px">
               <tr>
               <td>档案类型:</td>
               <td><input type="text" id="dalx" name="dalx" style="width:210px" /></td>
               </tr>
               <tr>
               <td>档案名称:</td>
               <td><textarea rows="2" style="width:210px" id="damc" name="damc"></textarea></td>
               </tr>
               <tr>
                 <td colspan="2" align="center">
                      <IMG  style='cursor:hand' border="0" src="pages/image/an-tj.gif" width="56" height="18" onclick="insertd()"></a>&nbsp;
                      <IMG  style='cursor:hand' border="0" src="pages/image/an-qx.gif" width="56" height="18" onclick="modifydk()"></a>&nbsp;
                      <IMG  style='cursor:hand' border="0" src="pages/image/an-fh.gif" width="56" height="18" onclick="clo()">
                 </td>
               </tr>
             </table>
		 </form> 
       </div>
       </div>
  </body>
</html>
