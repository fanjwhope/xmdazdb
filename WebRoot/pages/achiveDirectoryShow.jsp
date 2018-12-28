<%@page import="com.hr.dao.BaseDao"%>
<%@page import="com.hr.global.util.Validation"%>
<%@page import="com.hr.bean.ArchiveDepart"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cxt=request.getContextPath();
String zdbdbName=request.getParameter("zdbdbName");
String archiveType=request.getParameter("archiveType");//地址最好带上此条件
String lsh=request.getParameter("lsh");
String tbname=request.getParameter("tbname");
BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
String dwh=IncString.formatNull(ArchiveUtil.getDepartmentCode(session));
String dbName;
if(tbname!=null){
dbName=tbname.split("_")[0];
}else{
dbName=IncString.formatNull(ArchiveUtil.getDataBaseName(session));
}
String table=dbName+"_"+"xmwj_"+dwh;
if(tbname==null||"".equals(tbname))
tbname=table;
String tab=ArchiveUtil.getFullTableName(request, ArchiveDepart.class);
if("2002".equals(archiveType)){
	archiveType = "xmlrb";
}
if(!Validation.isEmpty(tbname)){
	table=tbname;
	tab=dbName+"_"+archiveType+"_"+tbname.split("_")[2];
}
String sql = "select xmbh from " + table + " where lsh = '" + lsh + "'";
String xmbh = op.queryRow(sql)[0];
/* String isStandSql="select isnotstd from " + tab+" where lsh ='"+lsh+"'";
String isStand=op.queryRow(isStandSql)[0];
 */
 
%>
<html>
  <head>
    <base href="<%=basePath%>">
  <jsp:include page="/publicJsp/libInclude.jsp" />
     <style type="text/css">
	 .main_tab{
		    background-color: #D4D0C8;
		    color: #000000;
		    border-left:1px solid #FFFFFF;
		    border-right: 1px solid gray;
		    border-bottom: 1px solid gray;
         }
       .whiteCenter{
         background-color:white;
       }
        .whiteCenter1{
         background-color:white;
       }
       .inputNoborder{
          background-color:#FFEFE8;
       }
       .readTb{
	     background-color:#99ccff;
	     font-size:14px;
	     bordercolor:#C0C0C0;
	     bordercolordark:#FFFFFF;
	     bordercolorlight:#FFFFFF;
	  }
       
       TD{
       FONT-SIZE: 14PX;
     }
     .input{valign:top;height:18px;font-size:14px;padding-top:1px;padding-left:5px;padding-right:5px;
               background-color: #0094BD; color: #ffffff;
               border-left: 0 solid #FFffff; border-right: 0 solid #000099;
               border-top: 0 solid #FFffff; border-bottom: 0 solid #000099;
               cursor:hand
               }
     
     .button{
	        valign:top;height:24px;font-size:14px;padding-top:1px;padding-left:1px;padding-right:1px;
                background-color: buttonface; color: #000000;
                border-left: 1 solid #FFffff; border-right: 1 solid #000000;
                border-top: 1 solid #FFffff; border-bottom: 1 solid #000000
	       }
     </style>
     <script type="text/javascript">
        $(function(){
        	 $.ajax({
       		  type:'post',
       		  url:'<%=cxt%>/do/ArchiveDepartDo/getListShow?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&lsh=<%=lsh%>&tbname=<%=tab%>',
       		  dataType:'json',
       		  success:function(data){
       			  if(data!=null){
       				  var a=1;
       				  var b=0;
       				  var str;
       				  for(var i=0;i<data.length;i++){
       					 <%--  if('<%=isStand%>'=='1'){ --%>
       						  str='<tr ><td class="readTb" width="64" align=center>&nbsp;</td>';
       						  str+='<td class="readTb" width="30" nowrap align=center>'+(i+1)+'</td>';
       						  str+='<td class="readTb" width="290">'+data[i].damc+'</td>';
       						  str+='<td class="readTb" width="64">'+data[i].days+'</td>';
       						  str+='<td class="readTb" width="177">'+data[i].dabz+'</td>';
       						if(/^\d+$/.test(data[i].days)){
       						   b=b+parseInt(data[i].days);
       						   str+='<td class="readTb" width="70" align=center nowrap>'+(a+'-'+b)+'</td></tr>';
       						   a=b+1;
       						}else{
       						  str+='<td class="readTb" width="70" align=center nowrap></td></tr>';
       						}
       						$('#StandardTable').append(str);
       					  /* }else{
	       					  str='<tr ><td  style="background-color:white;"><input type="radio" name="flag" value="'+(i+1)+'"'+' /></td>';		 	  
	       					  str+='<td><input   type="text" name="sort_num"     style="width:40;text-align:center;" value="'+(i+1)+'"'+' class="whiteCenter" readonly></td>';
	       					  str+='<td><input   type="text" name="dalx"         style="width:100;text-align:center;" value="'+data[i].dalx+'"'+' class="inputNoborder" ></td>';
	       					  str+='<td><input   type="text" name="damc"         style="width:390;text-align:left;" value="'+data[i].damc+'"'+'  class="inputNoborder" ></td>';
	       					  str+='<td><input   type="text" name="days"         style="width:30;text-align:center;" value="'+data[i].days+'"'+' class="inputNoborder" ></td>';
	       					  str+='<td><input   type="text" name="archivetime"  style="width:75;text-align:left;"   value="'+data[i].archivetime+'"'+' class="inputNoborder" ></td>';
	       					  if(!data[i].days){
	       					   str+='<td><input   type="text" name="daym"         style="width:50;text-align:center;"  class="whiteCenter1" readonly></td>';  
	       					  }else{
	       					   b=b+parseInt(data[i].days);
	       					   str+='<td><input   type="text" name="daym"         style="width:50;text-align:center;" value="'+(a+'-'+b)+'"'+' class="whiteCenter1" readonly></td>';
	       				       a=b+1;
	       					  }
	       					  str+='<td><input   type="text" name="dabz"         style="width:200;text-align:left;"  value="'+data[i].dabz+'"'+' class="inputNoborder" ></td></tr>';
	       				     $('#notStandDivAdd').append(str);
       					  } */
       				  }
       			  }
       		  }
       	  });
        });
        
        function  PrnJnmlcheck(){//卷内文件
        	   window.open("<%=cxt%>/pages/content.jsp?zdbdbName=<%=zdbdbName%>&lsh=<%=lsh%>&type=<%=archiveType%>&tbname=<%=tbname%>");
        }
        
        function PrnWjbkcheck(){//备考表
        	   window.open("<%=cxt%>/pages/bak.jsp?zdbdbName=<%=zdbdbName%>&lsh=<%=lsh%>&tbname=<%=tbname%>");
        }
        
        function scan2(){//
        	   $.ajax({
         		  type:'post',                               
         	      url:'<%=cxt%>/do/ArchiveDepartDo/isExitPdf?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&lsh=<%=lsh%>&xmbh=<%=xmbh%>&tbname=<%=tbname%>&dbName=<%=dbName%>',
         	      dataType:'json',
         	      success:function(data){
         	    	  if(data.msg=='true'){
           	            window.open("<%=cxt%>/pages/showImg.jsp?zdbdbName=<%=zdbdbName%>&lsh=<%=lsh%>&archiveType=<%=archiveType%>&dwh=<%=dwh%>&dbName=<%=dbName%>&xmbh=<%=xmbh%>&tbname=<%=tbname%>&rnum="+ Math.random());
         	    	  }else{
         	    		  alert("文档不存在！");
         	    	  }
         	      }
         	  });
        }
        
        function clo(){//关闭 返回
       	 parent.$('#listPage').show();
         parent.$('#modifyPage').hide(); 
       }
     </script>
  </head>
     
  <body>
   <div id="standDiv" >
    <center>
       <form>
          <table  border="3" bordercolorlight="#FFFFFF" bordercolordark="#333399" cellspacing="0" cellpadding="0" width="770">
                 <tr>
			      <td bordercolor="#C0C0C0" bordercolordark="#FFFFFF" bgcolor="#FFFF99" bordercolorlight="#FFFFFF" style="background-color: #333399" width="15"><font color="#FFFFFF" face="宋体" style='font-size:10pt'>&nbsp;</td>
			      <td bordercolor="#C0C0C0" bordercolordark="#FFFFFF" bgcolor="#FFFF99" bordercolorlight="#FFFFFF" style="background-color: #333399" width="30" nowrap><font color="#FFFFFF" face="宋体" style='font-size:10pt'>序号</td>
			      <td bordercolor="#C0C0C0" bordercolordark="#FFFFFF" bgcolor="#FFFF99" bordercolorlight="#FFFFFF" style="background-color: #333399" width="230"><font color="#FFFFFF" face="宋体" style='font-size:10pt'>档案内容</td>
			      <td bordercolor="#C0C0C0" bordercolordark="#FFFFFF" bgcolor="#FFFF99" bordercolorlight="#FFFFFF" style="background-color: #333399" width="64"><font color="#FFFFFF" face="宋体" style='font-size:10pt'>页数</td>
			      <td bordercolor="#C0C0C0" bordercolordark="#FFFFFF" bgcolor="#FFFF99" bordercolorlight="#FFFFFF" style="background-color: #333399" width="177"><font color="#FFFFFF" face="宋体" style='font-size:10pt'>备注</td>
			      <td bordercolor="#C0C0C0" bordercolordark="#FFFFFF" bgcolor="#FFFF99" bordercolorlight="#FFFFFF" style="background-color: #333399" width="70" nowrap align=center><font color="#FFFFFF" face="宋体" style='font-size:10pt'>页码</td>
			      </tr>
			 <tbody id="StandardTable" >
   
            </tbody>
	          <tr>
	               <td colspan="6" style="text-align: center">
	                   <input type="button" class="button" value="卷内文件" name="PrnDhgl"  id="PrnDhgl" onclick="PrnJnmlcheck()" >
					   <input type="button" class="button" value="备考表" name="PrnWjbk"  id="PrnWjbk" onclick="PrnWjbkcheck()" >
					   <input type="button" class="button" value="关 闭"  onclick="clo()">
					   <input type="button" class="button" value="扫描件" onclick="scan2()">  
				   </td>
	           </tr>
       </form>
    </center>
  </div>
  
   <!-- <div align="center" style="overflow:hidden;display: none" id="notStandardDiv">
        <form  id="directoryForm"  name="directoryForm">
          <input type="hidden" id="archiveType" name="archiveType"/>
          <input type="hidden" id="lsh" name="lsh"/>
          <table >
            <tr>
              <table   width="860"  align="center" style="border-collapse:collapse;border-color:#000000"  border=1 cellspacing="0" cellpadding="0">
               <tr style="background-color:#40B0C0" height="18px"  align="center">
	               <td width='10' nowrap></td>
	               <td width='40' style='font-size:10pt;color:#FFFFFF'>序号</td>
	               <td width='100' style='font-size:10pt;color:#FFFFFF'>子号</td>
	               <td width='390' style='font-size:10pt;color:#FFFFFF'>档案内容</td>
	               <td width='30' style='font-size:10pt;color:#FFFFFF'>页数</td>
	               <td width='75' style='font-size:10pt;color:#FFFFFF'>日期</td>
	               <td width='50' style='font-size:10pt;color:#FFFFFF'>页码</td>
	               <td width='200' style='font-size:10pt;color:#FFFFFF'>备注</td>
               </tr>
              </table>
           </tr>
            <div id="notStandDivAdd">
	          </div>
           <tr>
             <br>
           <fieldset align=center style="width:660">
              <div id="notStandard">
               <table width="660" align="center" >
               <tr>
                <td><div align="center">
                    <font style="font-size:13px;color:#0070B0" valign=middle>添加的行数:</font>
                    <input style="width:30px;height:20px;" type="text" id="rowNumber" name="rowNumber" value="1">   <input class="button" type="button" name="saveBtn" value="存 盘" onclick="toZlAction()" disabled="disabled"><input class="button" type="button" id="addBtn" name="addBtn" value="添 加" onclick="AppendData()" disabled="disabled"><input class="button" type="button" id="insertBtn" name="insertBtn" value="插 入" onclick="InsertData()" disabled="disabled"><input class="button" type="button" id="delBtn" name="delBtn" value="删 除" onclick="DelData()" disabled="disabled"><input class="button" type="button" name="priBtn" value="上 移" onclick="priMove()" disabled="disabled"><input class="button" type="button" name="nextBtn" value="下 移" onclick="nextMove()" disabled="disabled"><input class="button" type="button" id="abanBtn" name="abanBtn" value="放 弃" onclick="cancelZl();" disabled="disabled"><input class="button" type="button" name="abanBtn" value="全部删除" onclick="DelAllZl()" disabled="disabled"> <br>
                    <input class="button" type="button" id="upload" value="上传扫描件" name="uploadF" onclick="uploaded()" disabled="disabled" /><input class="button" type="button" name="abanBtn" value="扫描件" onclick="scan2();"> 打印范围<input type=text size=3 name="beginNo" id="beginNo" value=1>-<input type=text size=3 name="endNo" id="endNo" value="0"><input type="button" class="button" value="卷内文件" name="PrnDhgl"  id="PrnDhgl" onclick="PrnJnmlcheck()" ><input type="button" class="button" value="备考表" name="PrnWjbk"  id="PrnWjbk" onclick="PrnWjbkcheck()" ><input class="button" type="button" name="abanBtn" value="关 闭" onclick="clo(1)">
                  <div id=dwdiv style="width:250;display:none"><font style='font-size:10pt' color='blue'><b>合同号</font></b><input name="fzxyh" id="fzxyh" size="13"</div>
              </tr>
             </table>
            </div>
          </fieldset>
        </tr>
          </table>
        </form>
      </div> -->
  </body>
</html>
