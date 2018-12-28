<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cxt=request.getContextPath();
String zdbdbName=request.getParameter("zdbdbName");

%>

<html>
  <head>
    <jsp:include page="/publicJsp/libInclude.jsp" />
    <script type="text/javascript">
    $(function(){
    	$('#archiveType').combobox({   
    		url:'<%=cxt%>/do/ProjectContentDo/find?zdbdbName=<%=zdbdbName%>',
    		valueField:'ename',   
     	    textField:'cname',
     	    editable:false,
     	    onLoadSuccess: function () { //加载完成后,设置选中第一项
               var val = $(this).combobox("getData");
               for (var item in val[0]) {
                   if (item == "ename") {
                       $(this).combobox("select", val[0][item]);
                   }
               }
     	    }
    	});  
    	
    	$('#dw').combobox({   
    		url:'<%=cxt%>/do/UserDo/getAllDep?zdbdbName=<%=zdbdbName%>',
    		valueField:'dwh',   
     	    textField:'dwmc',
     	    editable:false,
     	   onLoadSuccess: function () { //加载完成后,设置选中第一项
               var val = $(this).combobox("getData");
               for (var item in val[0]) {
                   if (item == "dwh") {
                       $(this).combobox("select", val[0][item]);
                   }
               }
     	    }
    	});
    	$('#modifyPage').height($('#listPage').height());
    	
    });
    
    function maintain(){
    	var archiveType=$('#archiveType').combobox('getValue');
    	var departCode=$('#dw').combobox('getValue');
    	var archiveText = $('#archiveType').combobox('getText');
    	if(!archiveType||!departCode){
    		$.messager.alert('提示', '请选择内容或单位！');
    		return false;
    		};
    	$('#load').attr('src',"departArchive.jsp?zdbdbName=<%=zdbdbName%>&archiveType="+archiveType+"&departCode="+departCode+"&archiveText="+archiveText);
    	$('#listPage').hide();
		$('#modifyPage').show(); 
    }	
    function addNR(){
    	$('#load').attr('src',"contentManage.jsp?zdbdbName=<%=zdbdbName%>");
    	$('#listPage').hide();
		$('#modifyPage').show(); 
    }
    </script>

  </head>
  
  <body class="easyui-layout" data-options="fit:true">
   <div  data-options="region:'center',border:false" style="overflow:hidden">
  <div id="listPage" style="width:100%;height:100%;padding:0px;margin:30px;">
   <center>
  <table border="3" cellpadding="0" cellspacing="0" width="380"  bordercolorlight="#F0F4FF" bordercolordark="#000099" bgcolor="#F0F4FF">
    <tr>
      <td width="100%" height="58" valign="top">
        <form method='post'   id="lrwh" >
     <input type="hidden" value="" id="okdelete" name="okdelete">
          <table border="0" cellpadding="0" cellspacing="0" width="100%" height="34">
            <tr>
              <td width="100%" bgcolor="#0066CC" height="1">
                <p align="left"><font color="#FFFFFF" style="font-size:10pt">&nbsp;请选择单位</font></td>
            </tr>
            <tr>
              <td width="100%" height="8" align="center">
                <table border="0" cellpadding="0" cellspacing="0" width="550">
                  <br>
				   <tr>
                    <td  align="center"><font color="#000099"></font></td>
                    <td  align="center"><input type="hidden" value="2014" name="nd" size='4' maxlength='4'></td>
                    <td  align="center"><font color="#000099" style="font-size:12pt">内容类型</font></td>
                    <td  align="center"><input type="text" id="archiveType" name="archiveType" style="width: 150px"/>
                   <img src="pages/image/add.gif" alt="添加内容类型" onclick="addNR()"></td>
						
                    <td  align="center"><font color="#000099" style="font-size:12pt">单位</font></td>
                    <td  align="center"><input type="text" id="dw" name="dw" style="width: 150px"/></td>
          </center>
                    <td width="18%">
                      <p align="left">&nbsp;&nbsp;<img src="pages/image/anqd.gif" width="56" height="21" name="I19" onclick="maintain()" ></td>
  <center>
                   
                </table>
              </td>
            </tr>
          </table>
        </form>
      </td>
    </tr>
  </table>
  </div>
   <div id="modifyPage" style="width:100%;padding:0px;margin:0px;display:none;overflow:hidden" >
	  	      <iframe id="load" style="width:100%;height:100%;" src="" scrolling="no" FRAMEBORDER="0"></iframe>
	  	</div>
   </div>
  </body>
</html>
