<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cxt=request.getContextPath();
String zdbdbName=request.getParameter("zdbdbName");
%>
<html>
  <head>
    <base href="<%=basePath%>">
     <jsp:include page="/publicJsp/libInclude.jsp" />
     <style type="text/css">
	  .font14{font-size:14px;color:#000080}
	</style>
    <script type="text/javascript">
        $(function(){
        	$('#projectType').combobox({ 
        		url:'<%=cxt%>/do/ProjectTypeDo/getAllProjectType?zdbdbName=<%=zdbdbName%>',
        		valueField:'id',   
         	    textField:'name',
         	    panelHeight:100,
         	    editable:false
        	});
        	$('#square').combobox({
        		valueField:'id',   
         	    textField:'name',
         	    panelHeight:70,
         	    editable:false,
         	    data:[{"id":"2","name":"全部","selected":true},{"id":"1","name":"是"},{"id":"0","name":"否"}]
        	});
        	
        });
        function _search(){
        	var  url="dkqx="+$('#dkqx').val()+"&xmbh="+$('#xmbh').val()+"&xmmc="+$('#xmmc').val()+"&qymc="+$('#qymc').val()+"&ywhc="+$('#ywhc').val()+"&by1="+$('#by1').val()+"&projectType="+$('#projectType').combobox('getValue')+"&xmfzr="+$('#xmfzr').val()+"&by2="+$('#by2').val()+"&dkje="+$('#dkje').val()+"&tbDate="+$('#tbDate').val()+"&tbDateE="+$('#tbDateE').val()+"&square="+$('#square').combobox('getValue');
        	window.location.href='queryHuiBian.jsp?zdbdbName=<%=zdbdbName%>&url='+url;
        }
        
        
        function resetD(){
        	$('#searchForm').form('clear');
        	$('#square').combobox('setValue','2');
        }
    </script>

  </head>
  
  <body >
   <div id="searchDiv">
        <br>
         <table align="center" border="3" cellpadding="0" cellspacing="0" width="490" bordercolorlight="#FFFFE8" bordercolordark="#999999" bgcolor="#FFFFE8" rules="none"> 
          <thead border="0" cellpadding="0" cellspacing="0" width="100%" > 
                <tr>
		          <td width="100%" bgcolor="#999999" height="16" colspan="2"><font style='font-size:10px' color="#FFFFFF">[案卷查询]请输入查询条件：</font></td>
		        </tr>
          </thead>
          <br>
           <tbody border="0"  >
             <form action='' id="searchForm" method="post">
							  <tr style="margin: 0px;padding-top:15px ">
							    <td  class="font14">贷款期限 <input name="dkqx" id="dkqx" type="text" style="width: 135px" /></td>
							  	<td  class="font14">借款合同号 <input name="xmbh" id="xmbh" type="text" style="width: 122px" /></td>
							  </tr>
							   <tr>
							    <td  class="font14">项目名称 <input name="xmmc" id="xmmc" type="text" style="width: 135px" /></td>
							    <td  class="font14">借款人 <input type="text" name="qymc" id="qymc" style="width: 150px"/></td>
							  </tr>
							   <tr>
							     <td  class="font14">实施单位 <input name="ywhc" id="ywhc" type="text" style="width: 135px" /></td>
							     <td  class="font14">保证人 <input name="by1" id="by1" type="text" style="width: 150px"/></td>
							  </tr>
							 <tr>
							    <td  class="font14">项目品种 <input name="projectType" id="projectType" type="text"  style="width: 135px" /></td>
							    <td  class="font14">信贷员 <input type="text" name="xmfzr" id="xmfzr" style="width: 150px"/></td>
							  </tr>
							   <tr>
							    <td  class="font14">档案编号 <input name="by2" id="by2" type="text" style="width: 135px" /></td>
							    <td  class="font14">金额 <input name="dkje" id="dkje" type="text" style="width: 165px" /></td>
							  </tr>
							   <tr>
							    <td  class="font14" colspan="2">审批时间 <input name="tbDate" class="easyui-datebox" id="tbDate" type="text" style="width:90px;" /> 至 <input name="tbDateE" id="tbDateE" class="easyui-datebox" type="text" style="width:90px;" />
							   &nbsp;  &nbsp; &nbsp;是否结清 <input name="square" id="square" type="text" style="width:62px;" /></td>
							   </tr> 
							   <tr>
							    <td width="100%" colspan="2" height="37" bordercolor="#FFFFE8">
							              &nbsp;&nbsp;<img src="images/t_query.gif" onclick="_search();">
							             &nbsp;&nbsp;<img src="images/t_reset.gif" onclick="resetD();">
							      </td>
							  </tr>
				</form>  
           </tbody>
         </table>
     </div> 
  </body>
</html>
