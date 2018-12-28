<%@page import="com.hr.dao.BaseDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cxt=request.getContextPath();
String archiveTypeZDB=request.getParameter("archiveTypeZDB");//地址最好带上此条件
String archiveType=request.getParameter("archiveType");//地址最好带上此条件
String lsh=request.getParameter("lsh");
String zdbdbName=request.getParameter("zdbdbName");
BaseDataOP op=BaseDao.getBaseDataOP(zdbdbName);
String dwh=IncString.formatNull(ArchiveUtil.getDepartmentCode(session));
String dbName=IncString.formatNull(ArchiveUtil.getDataBaseName(session));
String tab=dbName+"_"+"xmwj_"+dwh;
String sql = "select xmbh from " + tab + " where lsh = '" + lsh + "'";
String xmbh = op.queryRow(sql)[0];
%>

<html>
<head>
<base href="<%=basePath%>">
<jsp:include page="/publicJsp/libInclude.jsp" />
<style type="text/css">
.whiteCenter {
	background-color: white;
}

.whiteCenter1 {
	background-color: white;
}

.inputNoborder {
	background-color: #FFEFE8;
}

TD {
	FONT-SIZE: 14PX;
}

.input {
	valign: top;
	height: 18px;
	font-size: 14px;
	padding-top: 1px;
	padding-left: 5px;
	padding-right: 5px;
	background-color: #0094BD;
	color: #ffffff;
	border-left: 0 solid #FFffff;
	border-right: 0 solid #000099;
	border-top: 0 solid #FFffff;
	border-bottom: 0 solid #000099;
	cursor: hand
}

.button {
	valign: top;
	height: 20px;
	font-size: 12px;
	padding-top: 1px;
	padding-left: 1px;
	padding-right: 1px;
	background-color: buttonface;
	color: #000000;
	border-left: 1 solid #FFffff;
	border-right: 1 solid #000000;
	border-top: 1 solid #FFffff;
	border-bottom: 1 solid #000000
}

.main_tab {
	background-color: #D4D0C8;
	color: #000000;
	border-left: 1px solid #FFFFFF;
	border-right: 1px solid gray;
	border-bottom: 1px solid gray;
}

.readTb {
	background-color: #99ccff;
	font-size: 14px;
	bordercolor: #C0C0C0;
	bordercolordark: #FFFFFF;
	bordercolorlight: #FFFFFF;
}
</style>
<script type="text/javascript">
      $(function(){
    	  getFormData();
    	  $('#archiveType').val('<%=archiveType%>');
    	  $('#lsh').val('<%=lsh%>');
      });
      
      //载入时获取表单
      function  getFormData(){
    	  $.ajax({
    		  type:'post',
    		  url:'<%=cxt%>/do/ArchiveDepartDo/getList?zdbdbName=<%=zdbdbName%>&archiveTypeZDB=<%=archiveTypeZDB%>&archiveType=<%=archiveType%>&lsh=<%=lsh%>',
    		  dataType:'json',
    		  success:function(data){
    			  if(data!=null){
    				   /* if(data.length>0){
	    				  $('#chec').hide();
	    				  $('#okfz').hide();
	    				  $('#oktj').attr("disabled",true);
    				  }  */
    				  $('#StandardTable').empty();
    				  var a=1;
    				  var b=0;
    				  var firstInput="hidden";
    				  var str;var dalx;var damc;var days;var dabz;var sortNumber;
    				  for(var i=0;i<data.length;i++){
    					     //if(data[i].dalx==null||data[i].dalx==''){ 
    					    	 firstInput="radio";
    					    	 $('#chec').hide();
    					    	 $('#oktj').attr("disabled",false); 
    					    	 $('#okcr').attr("disabled",false);
    			    		     $('#oksc').attr("disabled",false);
    			    		str='<tr id="del"><td class="readTb" width="64" align=center><input type='+firstInput+' name=flag id=flag value='+data[i].sortNum+'>&nbsp;</td>';
    			    		if(data[i].sortNum){ sortNumber=i+1;}else{ sortNumber=i+1;}
           					str+='<td class="readTb" width="30" nowrap align=center><input type=hidden name=sort_num id=sort_num size=8 value='+sortNumber+'>'+(i+1)+'</td>';
           					if(data[i].dalx){dalx=data[i].dalx;}else{ dalx='';}
           					str+='<input type=hidden name=dalx id=dalx size=8 value='+data[i].dalx+'>';
           					if(data[i].damc){damc=data[i].damc;}else{ damc='';}
           					str+='<td class=readTb width="330"><input type=text  name=damc id=damc'+(i+1)+' size=39 value="'+damc+'"></td>';
    			    		    /*  }else{
    			    		     str='<tr id="del"><td class="readTb" width="64" align=center><input type='+firstInput+' name=flag id=flag value='+data[i].sortNum+'>&nbsp;</td>';
           					str+='<td class="readTb" width="30" nowrap align=center><input type=hidden name=sort_num id=sort_num size=8 value='+data[i].sortNum+'>'+(i+1)+'</td>';
           					if(data[i].dalx){dalx=data[i].dalx;}else{ dalx='';}
           					str+='<input type=hidden name=dalx id=dalx size=8 value='+data[i].dalx+'>';
           					if(data[i].damc){damc=data[i].damc;}else{ damc='';}
           					str+='<td class=readTb width="330"><input type=hidden name=damc id=damc'+(i+1)+' size=39 value='+damc+'>'+damc+'</td>';
    			    		     } */
           					
           					if(data[i].days){days=data[i].days;}else{days='';}
           					str+='<td class=readTb width="64"><input type=text size=8 name=days id=days value='+days+'></td>';
           					if(data[i].dabz){dabz=data[i].dabz;}else{dabz='';}
           					str+='<td class=readTb width="207"><input type=text size=33 name=dabz id= dabz value="'+dabz+'"></td>';
           					if(/^\d+$/.test(data[i].days)){
           						b=b+parseInt(data[i].days);
           						str+='<td class=readTb width="75" align=center nowrap>'+(a+'-'+b)+'</td></tr>';
           					    a=b+1;
           					}else{
           						str+='<td class=readTb width="75" align=center nowrap></td></tr>';
           					}
           				$('#StandardTable').append(str);
           				//new tabTableInput("shutaGrid","text");
    				  }
    			  }
    		  }
    	  });
      }
      
      function toZlAction(){//存盘
     	 $('#okcp').attr("disabled",true);
    	  var damcs=$("input[name='damc']").length;
    	  if(damcs!=0){
    	  		
    	  		for(var i=0;i<damcs;i++){
        	   if($('#damc'+(i+1)).val()==null||$('#damc'+(i+1)).val()==''){
        		alert("序号["+(i+1)+"]的档案内容不能为空");
        		return false;
        	   }
           }
    		  $('#directoryForm').form('submit', {
    			  url:'<%=cxt%>/do/ArchiveDepartDo/add?zdbdbName=<%=zdbdbName%>',
	    	      success:function(data){
	    	    	  $("input[name='flag']").closest('tr').remove();
	    	    	  getFormData();
	    	    	  $('#okcp').attr("disabled",false);
	    	      }
    		  });
    		  $('#oktj').attr("disabled",false);//tianjia
    		  $('#okcr').attr("disabled",false);//插入
    		  $('#oksc').attr("disabled",false);//删除
    		  //$('#okcancel').attr("disabled",true);//放弃
    	  
    	  }else{
    	  
    	  	alert("请填写相关类容！");
    	  	
    	  }
           
      }
      
      function AppendData(){//添加
    	  var rows;
    	  var radios;
    	  var str;
    	  var num;
    		  rows=$('#rows').val();
    		  radios=$("input[name='flag']").length;
    		  for (var int = 0; int < rows; int++) {
	        	   str=rowContent(radios+int+1);
	    	      $('#StandardTable').append(str);    	      
		      }
    		  reIndex();
    		  num=parseInt(radios)+parseInt(rows);
    		  $('#flag'+num).attr("checked",true);
    		  $('#oktj').attr("disabled",true);//tianjia
    		  $('#okcr').attr("disabled",true);//插入
    		  $('#oksc').attr("disabled",true);//删除
    		 // $('#okcancel').attr("disabled",false);//放弃
    		// new tabTableInput("shutaGrid","text");
      }
      
      function InsertData(){//插入
    	  if( $("input[name='flag']:checked").length<1){
    		  alert('请选择要插入的位置！');
    		  return  false;
    	  }
    	  var  rows=$('#rows').val();
    	  var  radios=$("input[name='flag']").length;
    	  var checked=$("input[name='flag']:checked");
          for (var int = 0; int < rows; int++) {
        	  var str=rowContent(radios+int,1);
    	      checked.closest('tr').before(str);
	      }
	      var allInput=$("input[name='flag']");
	      var allNum=$("input[name='sort_num']");
	       for( var i = 0; i < allInput.length; i++ ) {
         		allInput.eq(i).val(i+1);
         		allNum.eq(i).val(i+1);
     		}
 	 	 $('#days').numberbox({   
 	 	    min:0 
 	 	});  
          reIndex();
          $('#oktj').attr("disabled",true);//tianjia
		  $('#okcr').attr("disabled",true);//插入
		  $('#oksc').attr("disabled",true);//删除
		  //$('#okcancel').attr("disabled",false);//放弃
      }
      
      function rowContent(num,flag){//插入的时候
    	  var str;
    	  str='<tr id="del"><td class="readTb" width="64" align=center><input type=radio  name=flag id=flag value='+(num)+'>&nbsp;</td>';
		  str+='<td class="readTb" width="30" nowrap align=center><input type=hidden name=sort_num id=sort_num size=8 value='+(num)+'>'+(num)+'</td>';
		  str+='<input type=hidden name=dalx id=dalx size=8>';
		  if(flag==1){num=num+1;}
		  str+='<td class=readTb width="330"><input type=text  name=damc id=damc'+num+' size=48 ></td>';
		  str+='<td class=readTb width="64"><input type=text size=8 name=days id=days ></td>';
		  str+='<td class=readTb width="207"><input type=text size=33 name=dabz id= dabz ></td>';
		  str+='<td class=readTb width="75" align=center nowrap></td></tr>'; 
	     return str;
      }
      
      function  DelData(){//删除
    	  if( $("input[name='flag']:checked").length<1){
    		  alert('未选择删除条目！');
    		  return  false;
    	  }
      var sort_num=$("input[name='flag']:checked").val();
    	  $.ajax({
    		  type:'post',
    	      url:'<%=cxt%>/do/ArchiveDepartDo/del?zdbdbName=<%=zdbdbName%>&archiveTypeZDB=<%=archiveTypeZDB%>&archiveType=<%=archiveType%>&lsh=<%=lsh%>&sortNum='+sort_num,
    	      dataType:'json',
    	      success:function(data){
    	    	  $("input[name='flag']").closest('tr').remove();
    	    	  getFormData();
    	      }
    	  });
    	  $('#oktj').attr("disabled",false);//tianjia
		  $('#okcr').attr("disabled",false);//插入
		  $('#oksc').attr("disabled",false);//删除
 
		 // $('#okcancel').attr("disabled",true);//放弃
      }
      
      function priMove(){//上移
    	  if( $("input[name='flag']:checked").length<1){
    		  alert('未选择上移条目！');
    		  return  false;
    	  }
    	  if($("input[name='flag']:checked").closest('tr').prev('#fuck').length<1){
	    	  $("input[name='flag']:checked").closest('tr').prev().before($("input[name='flag']:checked").closest('tr'));
	    	  reIndex();
    	  }
      }
      
      function  reIndex(){
    	 $('input.whiteCenter').each(function(index){
    		 $(this).val(index+1);
    	 });
      }
      
      function  nextMove(){//下移
    	  if( $("input[name='flag']:checked").length<1){
    		  alert('未选择下移条目！');
    		  return  false;
    	  }
    	  $("input[name='flag']:checked").closest('tr').next().after($("input[name='flag']:checked").closest('tr'));
    	  reIndex();
      }
      
      function cancelZl(){//放弃
      		getFormData();
    	  $('#oktj').attr("disabled",false);//tianjia
		  $('#okcr').attr("disabled",false);//插入
		  $('#oksc').attr("disabled",false);//删除
      }
      
      function DelAllZl(){//删除全部
    	  $.ajax({
    		  type:'post',
    	      url:'<%=cxt%>/do/ArchiveDepartDo/delAll?zdbdbName=<%=zdbdbName%>&archiveTypeZDB=<%=archiveTypeZDB%>&archiveType=<%=archiveType%>&lsh=<%=lsh%>',
    	      dataType:'json',
    	      success:function(data){
    	    	  $("input[name='flag']").closest('tr').remove();
    	      }
    	  });
      }
      
      function uploaded(){//上传扫描件
      	  $('#form').form('clear');
      	  $('#dia').show();
    	  $('#dia').dialog({
	    	title:'文件上传',
	    	modal: true,
	    	closable:true
	   	  });
    	  var archiveType='<%=archiveType%>';
    	  if('<%=archiveType%>'==2002){
    		  archiveType='xmlrb';
    	  }
    	  $('#fileName').val(archiveType+"_"+'<%=lsh%>');
    	  $('#fileName').attr('readonly','readonly');
      }
      
      function uploadFile(){
      	  var filename = $('#fileName').val();
      	  var file = $('#file').val();
      	  var len= file.lastIndexOf('.');
      	  var filetype = file.substring(len+1, file.length);
      	  if(filetype=='pdf'||filetype=='tif'){
	      	  $('#fileSubmit').form('submit',{
	      	  	 url:"<%=cxt%>/do/LocationDo/upload?zdbdbName=<%=zdbdbName%>&filename="+filename+"&file="+file+"&lsh="+<%=lsh%>+"&archiveType=<%=archiveType%>",
	      	  	 success:function(data){
	      	  	 	var all = $.parseJSON(data);
	      	  	 	alert(all.msg);
	      	  	 	$('#dia').dialog('close');
	      	  	 }
	      	  });
      	  }else{
      		  alert("文件格式为pdf或tif!");
      	  }
      	  
      }
      
      function  scan2(){//扫描件
    	  $.ajax({
    		  type:'post',                               
    	      url:'<%=cxt%>/do/ArchiveDepartDo/isExitPdf?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&lsh=<%=lsh%>&xmbh=<%=xmbh%>',
    	      dataType:'json',
    	      success:function(data){
    	    	  if(data.msg=='true'){
      	            window.open("<%=cxt%>/pages/showImg.jsp?zdbdbName=<%=zdbdbName%>&lsh=<%=lsh%>&archiveType=<%=archiveType%>&dwh=<%=dwh%>&dbName=<%=dbName%>&xmbh=<%=xmbh%>&rnum="+ Math.random());
    	    	  }else{
    	    		  alert("文档不存在！");
    	    	  }
    	      }
    	  });
      }
      
      function PrnJnmlcheck(){//卷内文件
    	  window.open("<%=cxt%>/pages/content.jsp?zdbdbName=<%=zdbdbName%>&lsh=<%=lsh%>&type=<%=archiveType%>&startNum="+$('#startNum').val());
      }
      
      function PrnWjbkcheck(){//备考表
    	  window.open("<%=cxt%>/pages/bak.jsp?zdbdbName=<%=zdbdbName%>&lsh=<%=lsh%>&tbname=<%=tab%>&archiveType=<%=archiveType%>&startNum="+$('#startNum').val());
      }
      
      function clo(flag){//关闭 返回
    	 parent.$('#listPage').show();
     	 parent.$('#modifyPage').hide(); 
     	 if(flag){
     		 $('#StandardDiv').show();
       	     $('#notStandardDiv').hide();
       	     $('#adminType').removeAttr('checked');
     	 }
      }
      
      function turnToNotStd(){
    	  window.location.href="<%=cxt%>/pages/achiveDirectory.jsp?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&lsh=<%=lsh%>";
	}

	function fzcheck() {
		var temp = $("#dwdiv").is(":hidden");
		if (temp) {
			$('#dwdiv').show();
		} else {
			$('#dwdiv').hide();
		}
	}
	
/* 	var tabTableInput = function(tableId, inputType) {
		var rowInputs = [];
		var trs = $("#" + tableId).find("tr");
		var inputRowIndex = 0;
		$.each(trs, function(i, obj) {
			if ($(obj).find("th").length > 0) { //跳过表头 
				return true;
			}
			var rowArray = [];
			var thisRowInputs;
			if (!inputType) { //所有的input 
				thisRowInputs = $(obj).find(
						"input:not(:disabled):not(:hidden):not([readonly])");
			} else {
				thisRowInputs = $(obj).find(
						"input:not(:disabled):not(:hidden):not([readonly])[type="
								+ inputType + "]");
			}
			if (thisRowInputs.length == 0) {
				return true;
			}
			thisRowInputs.each(function(j) {
				$(this).attr("_r_", inputRowIndex).attr("_c_", j);
				rowArray.push({
					"c" : j,
					"input" : this
				});
				$(this).keydown(function(evt) {
					var r = $(this).attr("_r_");
					var c = $(this).attr("_c_");
					var tRow
					if (evt.which == 38) { //上 
						if (r == 0)
							return;
						r--; //向上一行 
						tRow = rowInputs[r];
						if (c > tRow.length - 1) {
							c = tRow.length - 1;
						}
					} else if (evt.which == 40) { //下 
						if (r == rowInputs.length - 1) { //已经是最后一行 
							return;
						}
						r++;
						tRow = rowInputs[r];
						if (c > tRow.length - 1) {
							c = tRow.length - 1;
						}
					} else if (evt.which == 37) { //左 
						if (r == 0 && c == 0) { //第一行第一个,则不执行操作 
							return;
						}
						if (c == 0) { //某行的第一个,则要跳到上一行的最后一个,此处保证了r大于0 
							r--;
							tRow = rowInputs[r];
							c = tRow.length - 1;
						} else { //否则只需向左走一个 
							c--;
						}
					} else if (evt.which == 39) { //右 
						tRow = rowInputs[r];
						if (r == rowInputs.length - 1 && c == tRow.length - 1) { //最后一个不执行操作 
							return;
						}
						if (c == tRow.length - 1) { //当前行的最后一个,跳入下一行的第一个 
							r++;
							c = 0;
						} else {
							c++;
						}
					}
					$(rowInputs[r].data[c].input).focus();
				});
			});
			rowInputs.push({
				"length" : thisRowInputs.length,
				"rowindex" : inputRowIndex,
				"data" : rowArray
			});
			inputRowIndex++;
		});
	} */

</script>
</head>

<body>
	<div style="overflow:hidden;" class="StandardDiv">
		<center>
			<form name='directoryForm' id="directoryForm" method=post>
				<input type="hidden" id="archiveType" name="archiveType" /> <input
					type="hidden" id="lsh" name="lsh" />
				<div
					style="overflow-x: auto; overflow-y: auto; height: 500px; width:100%;">
					<table border="3" bordercolorlight="#FFFFFF" id="shutaGrid"
						bordercolordark="#333399" cellspacing="0" cellpadding="0"
						width="770">
						<tr>
							<td bordercolor="#C0C0C0" bordercolordark="#FFFFFF"
								bgcolor="#FFFF99" bordercolorlight="#FFFFFF"
								style="background-color: #333399" width="64"><font
								color="#FFFFFF" face="宋体" style='font-size:10pt'>&nbsp</td>
							<td bordercolor="#C0C0C0" bordercolordark="#FFFFFF"
								bgcolor="#FFFF99" bordercolorlight="#FFFFFF"
								style="background-color: #333399" width="30" nowrap><font
								color="#FFFFFF" face="宋体" style='font-size:10pt'>序号</td>
							<td bordercolor="#C0C0C0" bordercolordark="#FFFFFF"
								bgcolor="#FFFF99" bordercolorlight="#FFFFFF"
								style="background-color: #333399" width="330"><font
								color="#FFFFFF" face="宋体" style='font-size:10pt'>档案内容</td>
							<td bordercolor="#C0C0C0" bordercolordark="#FFFFFF"
								bgcolor="#FFFF99" bordercolorlight="#FFFFFF"
								style="background-color: #333399" width="64"><font
								color="#FFFFFF" face="宋体" style='font-size:10pt'>页数</td>
							<td bordercolor="#C0C0C0" bordercolordark="#FFFFFF"
								bgcolor="#FFFF99" bordercolorlight="#FFFFFF"
								style="background-color: #333399" width="207"><font
								color="#FFFFFF" face="宋体" style='font-size:10pt'>备注</td>
							<td bordercolor="#C0C0C0" bordercolordark="#FFFFFF"
								bgcolor="#FFFF99" bordercolorlight="#FFFFFF"
								style="background-color: #333399" width="75" nowrap align=center><font
								color="#FFFFFF" face="宋体" style='font-size:10pt'>页码</td>
						</tr>
						<tbody id="StandardTable">

						</tbody>
						<tr>
							<td colspan='7' align='center' width=100%><font
								style='font-size:10pt' color='blue'>添加的行数<input size='2'
									name='rows' id='rows' value='1'> <input type="button"
									class="button" value="存 盘" name="okcp" id="okcp"
									onclick="toZlAction()"> <input type="button"
									class="button" value="添 加" name="oktj" id="oktj"
									onclick="AppendData()"> <input type="button"
									class="button" value="插 入" name="okcr" id="okcr"
									onclick="InsertData()" disabled> <input type="button"
									class="button" value="删 除" name="oksc" id="oksc"
									onclick="DelData()" disabled> <input type="button"
									class="button" value="放 弃" name="okcancel" id="okcancel"
									onclick="cancelZl()"> <font style='font-size:10pt'
									color='blue'>打印的起始序号为:<input size='2' name='startNum'
										id='startNum' value='1'
										onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,'');}).call(this)"
										onblur="this.v();"> <input type="button"
										class="button" value="卷内文件" name="PrnDhgl" id="PrnDhgl"
										onclick="PrnJnmlcheck()"> <input type="button"
										class="button" value="备考表" name="PrnWjbk" id="PrnWjbk"
										onclick="PrnWjbkcheck()"> <input type="button"
										class="button" value="关 闭" onclick="clo()"> <input
										class="button" type="button" id="upload" value="上传扫描件"
										name="uploadF" onclick="uploaded()" /> <input type="button"
										class="button" value="扫描件" onclick="scan2();"> <input
										type="button" class="button" value="继 承" name="okfz" id="okfz"
										onclick="fzcheck()">
										<div id=dwdiv style="width:250;display:none">
											<font size=-1 color='blue'><b>协议号</b><input
												name="fzxyh" id="fzxyh" size="13"
										</div></td>
						</tr>
					</table>
				</div>
				<div id="chec">
					<table border=0 width="770" height=10>
						<tr>
							<td align=right><input type=checkbox name="adminType"
								id="adminType" onclick="turnToNotStd()"> <label
								style='font-size:12px'>按非标准方式管理</label> <font color=blue>(备注：当数据没有添加前，可对审批卷的管理方式进行选择。)</font>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</center>
	</div>

	<div id="dia" style="width:330px;height:170px;display: none">
		<form id="fileSubmit" method="post" action=""
			enctype="multipart/form-data">
			<table>
				<tr>
					<td style="padding-top:10px">文件名称：</td>
					<td style="padding-top:10px"><input type="text" id="fileName"
						name="fileName" value=""></td>
				</tr>
				<tr>
					<td style="padding-top:10px">选择文件：</td>
					<td style="padding-top:10px"><input type="file" id="file"
						name="file"></td>
				</tr>
				<tr>
					<td colspan=2 align=center style="padding-top:10px"><a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-save', plain:false"
						onclick="uploadFile()">提交</a>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>


