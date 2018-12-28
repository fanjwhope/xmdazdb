<%@page import="com.hr.dao.BaseDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.hr.info.*,com.hr.util.*"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%
	String zdbdbName=request.getParameter("zdbdbName");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String cxt = request.getContextPath();
	String archiveType = request.getParameter("archiveType");//��ַ��ô��ϴ�����
	String lsh = request.getParameter("lsh");
	String type = IncString.formatNull(request.getParameter("type"));

	BaseDataOP op = BaseDao.getBaseDataOP(zdbdbName);
	String dwh = IncString.formatNull(ArchiveUtil
			.getDepartmentCode(session));
	String dbName = IncString.formatNull(ArchiveUtil
			.getDataBaseName(session));
	String table = dbName + "_" + "xmwj_" + dwh;
	String sql = "select xmbh from " + table + " where lsh = '" + lsh
			+ "'";
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

.input {valign ="top";height ="18px";
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

.button {valign ="top";height ="24px";
	font-size: 14px;
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
</style>
<script type="text/javascript">
      $(function(){
    	  getFormData();
    	  $('#archiveType').val('<%=archiveType%>');
    	  $('#lsh').val('<%=lsh%>');
    	  $('#dia').hide();
      });
      
      //����ʱ��ȡ��
      
      function  getFormData(){
    	  $.ajax({
    		  type:'post',
    		  url:'<%=cxt%>/do/ArchiveDepartDo/getList?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&lsh=<%=lsh%>&isnotstd=1',
    		  dataType:'json',
    		  success:function(data){
    			  if(data!=null){
    			  $('#cellTable').empty();
    				  var a=1;
    				  var b=0;
    				  var dalx;var damc;var days;var archivetime;var dabz;
    				  for(var i=0;i<data.length;i++){
    					  var str='<tr id="del"><td  style="background-color:white;"><input type="radio" name="flag" value="'+(i+1)+'"'+' /></td>';		 	  
    					  str+='<td><input   type="text" name="sort_num"  size=8   style="width:40;text-align:center;" value="'+(i+1)+'"'+' class="whiteCenter" readonly></td>';
    					  if(data[i].dalx){dalx=data[i].dalx;}else{ dalx='';}
    					  str+='<td><input   type="text" name="dalx"   size=8      style="width:100;text-align:center;" value="'+dalx+'"'+' class="inputNoborder" ></td>';
    					  if(data[i].damc){damc=data[i].damc;}else{ damc='';}
    					  str+='<td><input   type="text" name="damc"   size=39      style="width:390;text-align:left;" value="'+damc+'"'+'  class="inputNoborder" ></td>';
    					  if(data[i].days){days=data[i].days;}else{days='';}
    					  str+='<td><input   type="text" name="days"   size=8      style="width:30;text-align:center;" value="'+days+'"'+' class="inputNoborder" ></td>';
    					  if(data[i].archivetime){archivetime=data[i].archivetime;}else{archivetime='';}
    					  str+='<td><input   type="text" name="archivetime"  style="width:75;text-align:left;"   value="'+archivetime+'"'+' class="inputNoborder" ></td>';
    					  if(!/^\d+$/.test(data[i].days)){
    					   str+='<td><input   type="text" name="daym"    size=12     style="width:50;text-align:center;"  class="whiteCenter1" readonly></td>';  
    					  }else{
    					   b=b+parseInt(data[i].days);
    					   str+='<td><input   type="text" name="daym"    size=12      style="width:50;text-align:center;" value="'+(a+'-'+b)+'"'+' class="whiteCenter1" readonly></td>';
    				       a=b+1;
    					  }
    					  if(data[i].dabz){dabz=data[i].dabz;}else{dabz='';}
    					  str+='<td><input   type="text" name="dabz"   size=33      style="width:200;text-align:left;"  value="'+dabz+'"'+' class="inputNoborder" ></td></tr>';
    				     $('#cellTable').append(str);
    				  }
    			  }
    		  }
    	  });
      }
      
      function toZlAction(){//����
    	  $('#directoryForm').form('submit', {
    		  url:'<%=cxt%>/do/ArchiveDepartDo/add?zdbdbName=<%=zdbdbName%>',
    	      success:function(data){
    	    	  $("input[name='flag']").closest('tr').remove();
    	    	  getFormData();
    	      }
    	  });
      }
      
      function AppendData(){//���
    	  var  rows=$('#rowNumber').val();
    	  var  radios=$("input[name='flag']").length;
          for (var int = 0; int < rows; int++) {
        	  var str=rowContent(radios+int+1);
    	      $('#cellTable').append(str);
	      }
          $('#days').numberbox({   
   	 	    min:0 
   	 	  });  
          reIndex();
      }
      
      function InsertData(){//����
    	  if( $("input[name='flag']:checked").length<1){
    		  alert('��ѡ��Ҫ�����λ�ã�');
    		  return  false;
    	  }
    	  var  rows=$('#rowNumber').val();
    	  var  radios=$("input[name='flag']").length;
    	  var checked=$("input[name='flag']:checked");
          for (var int = 0; int < rows; int++) {
        	  var str=rowContent(radios+int);
    	      checked.closest('tr').before(str);
	      }
 	 	 $('#days').numberbox({   
 	 	    min:0 
 	 	});  

          reIndex();
      }
      
      function rowContent(num){
    	  var str='<tr id="del"><td  style="background-color:white;"><input type="radio" name="flag" value="'+num+'"'+'  /></td>'		 	  
    	  +'<td><input   type="text" name="sort_num"  size=8   style="width:40;text-align:center;" value="'+num+'"'+' class="whiteCenter" readonly></td>'
	 	  +'<td><input   type="text" name="dalx"    size=8     style="width:100;text-align:center;"  class="inputNoborder" ></td>'
	 	  +'<td><input   type="text" name="damc"   size=33      style="width:390;text-align:left;"   class="inputNoborder" ></td>'
	 	  +'<td><input   type="text" name="days"   id="days"  size=8    style="width:30;text-align:center;"  class="inputNoborder" ></td>'
	 	  +'<td><input   type="text" name="archivetime" size=12 style="width:75;text-align:left;"    class="inputNoborder" ></td>'
	 	  +'<td><input   type="text" name="daym"   size=12       style="width:50;text-align:center;"  class="whiteCenter1" readonly></td>'
	 	  +'<td><input   type="text" name="dabz"   size=33      style="width:200;text-align:left;"   class="inputNoborder" ></td></tr>';
    	  return str;
      }
      
      
      
      
         function  DelData(){//ɾ��
    	  if( $("input[name='flag']:checked").length<1){
    		  alert('δѡ��ɾ����Ŀ��');
    		  return  false;
    	  }
      var sort_num=$("input[name='flag']:checked").val();
      $('#directoryForm').form('submit', {
    		  url:'<%=cxt%>/do/ArchiveDepartDo/add?zdbdbName=<%=zdbdbName%>',
    	      success:function(data){
    	    	  $("input[name='flag']").closest('tr').remove();
    	    	  getFormData();
    	    	  $.ajax({
    		  type:'post',
    	      url:'<%=cxt%>/do/ArchiveDepartDo/del?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&lsh=<%=lsh%>&sortNum='+sort_num,
    	      dataType:'json',
    	      success:function(data){
    	    	  $("input[name='flag']").closest('tr').remove();
    	    	  getFormData();
    	      }
    	  });
    	  $('#oktj').attr("disabled",false);//tianjia
		  $('#okcr').attr("disabled",false);//����
		  $('#oksc').attr("disabled",false);//ɾ��
		 // $('#okcancel').attr("disabled",true);//����
    	      }
    	  });
    	  
      }
      
      
      function priMove(){//����
    	  if( $("input[name='flag']:checked").length<1){
    		  alert('δѡ��������Ŀ��');
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
      
      function  nextMove(){//����
    	  if( $("input[name='flag']:checked").length<1){
    		  alert('δѡ��������Ŀ��');
    		  return  false;
    	  }
    	  $("input[name='flag']:checked").closest('tr').next().after($("input[name='flag']:checked").closest('tr'));
    	  reIndex();
      }
      
      function cancelZl(){//����
    	  $("input[name='flag']").closest('tr').remove();
    	  getFormData();
      }
      
      function DelAllZl(){//ɾ��ȫ��
    	  $.ajax({
    		  type:'post',
    	      url:'<%=cxt%>/do/ArchiveDepartDo/delAll?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&lsh=<%=lsh%>',
    	      dataType:'json',
    	      success:function(data){
    	    	  $("input[name='flag']").closest('tr').remove();
    	      }
    	  });
      }
      
      function uploaded(){//�ϴ�ɨ���
      	  $('#form').form('clear');
      	  $('#dia').show();
    	  $('#dia').dialog({
	    	title:'�ļ��ϴ�',
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
      		  alert("�ļ���ʽΪpdf��tif!");
      	  }
      	  
      }
      
      function  scan2(){//ɨ���
    	  $.ajax({
    		  type:'post',                               
    	      url:'<%=cxt%>/do/ArchiveDepartDo/isExitPdf?zdbdbName=<%=zdbdbName%>&archiveType=<%=archiveType%>&lsh=<%=lsh%>&xmbh=<%=xmbh%>',
    	      dataType:'json',
    	      success:function(data){
    	    	  if(data.msg=='true'){
      	            window.open("<%=cxt%>/pages/showImg.jsp?zdbdbName=<%=zdbdbName%>&lsh=<%=lsh%>&archiveType=<%=archiveType%>&dwh=<%=dwh%>&dbName=<%=dbName%>&xmbh=<%=xmbh%>&rnum="+ Math.random());
    	    	  }else{
    	    		  alert("�ĵ������ڣ�");
    	    	  }
    	      }
    	  });
      }
      
      function PrnJnmlcheck(){//�����ļ�
    	  window.open("<%=cxt%>/pages/content.jsp?zdbdbName=<%=zdbdbName%>&lsh=<%=lsh%>&type=<%=archiveType%>");
      }
      
      function PrnWjbkcheck(){//������
    	  window.open("<%=cxt%>/pages/bak.jsp?zdbdbName=<%=zdbdbName%>&lsh=<%=lsh%>&tbname=<%=table%>");
	}

	function clo(flag) {//�ر� ����
		parent.$('#listPage').show();
		parent.$('#modifyPage').hide();
		if (flag) {
			$('#StandardDiv').show();
			$('#notStandardDiv').hide();
			$('#adminType').removeAttr('checked');
		}
	}

	function turnToNotStd() {
		$('#StandardDiv').hide();
		$('#notStandardDiv').show();
	}

	function fzcheck() {
		var fzxyh = $('#fzxyh').val();
		if (fzxyh) {
			$('#dwdiv').hide();
		} else {
			$('#dwdiv').show();
		}
	}
</script>
</head>

<body>
	<div align="center" style="overflow:hidden;" id="notStandardDiv">
		<form id="directoryForm" name="directoryForm" method="post">
			<input type="hidden" id="archiveType" name="archiveType" /> <input
				type="hidden" id="lsh" name="lsh" /> <input type="hidden"
				id="isnotstd" name="isnotstd" value="1" />
			<div
				style="overflow-x: auto; overflow-y: auto; height: 500px; width:100%;">

				<table>
					<tr>
						<table id="MaincellTable" name="MaincellTable" width="860" align="center"
							style="border-collapse:collapse;border-color:#000000" border=1
							cellspacing="0" cellpadding="0">
							<tr style="background-color:#40B0C0" height="18px" id="fuck"
								align="center">
								<td width='10' nowrap></td>
								<td width='40' style='font-size:10pt;color:#FFFFFF'>���</td>
								<td width='100' style='font-size:10pt;color:#FFFFFF'>�Ӻ�</td>
								<td width='390' style='font-size:10pt;color:#FFFFFF'>��������</td>
								<td width='30' style='font-size:10pt;color:#FFFFFF'>ҳ��</td>
								<td width='75' style='font-size:10pt;color:#FFFFFF'>����</td>
								<td width='50' style='font-size:10pt;color:#FFFFFF'>ҳ��</td>
								<td width='200' style='font-size:10pt;color:#FFFFFF'>��ע</td>
							</tr>
							 <tbody id="cellTable">
   
  							 </tbody>
						</table>
					</tr>
					<tr>
						<br>
						<fieldset align=center style="width:660">
							<div id="notStandard">
								<table width="660" align="center">
									<tr>
										<td><div align="center">
												<font style="font-size:13px;color:#0070B0" valign=middle>��ӵ�����:</font>
												<input style="width:30px;height:20px;" type="text"
													id="rowNumber" name="rowNumber" value="1"> <input
													class="button" type="button" name="saveBtn" value="�� ��"
													onclick="toZlAction()"><input class="button"
													type="button" id="addBtn" name="addBtn" value="�� ��"
													onclick="AppendData()"><input class="button"
													type="button" id="insertBtn" name="insertBtn" value="�� ��"
													onclick="InsertData()"><input class="button"
													type="button" id="delBtn" name="delBtn" value="ɾ ��"
													onclick="DelData()"><input class="button"
													type="button" name="priBtn" value="�� ��" onclick="priMove()"><input
													class="button" type="button" name="nextBtn" value="�� ��"
													onclick="nextMove()"><input class="button"
													type="button" id="abanBtn" name="abanBtn" value="�� ��"
													onclick="cancelZl()"><input class="button"
													type="button" name="abanBtn" value="ȫ��ɾ��"
													onclick="DelAllZl()"> <br> <input
													class="button" type="button" id="upload" value="�ϴ�ɨ���"
													name="uploadF" onclick="uploaded()" /><input
													class="button" type="button" name="abanBtn" value="ɨ���"
													onclick="scan2();"> ��ӡ��Χ<input type=text size=3
													name="beginNo" id="beginNo" value=1>-<input
													type=text size=3 name="endNo" id="endNo" value="0"><input
													type="button" class="button" value="�����ļ�" name="PrnDhgl"
													id="PrnDhgl" onclick="PrnJnmlcheck()"><input
													type="button" class="button" value="������" name="PrnWjbk"
													id="PrnWjbk" onclick="PrnWjbkcheck()"><input
													class="button" type="button" name="abanBtn" value="�� ��"
													onclick="clo(1)">
												<div id=dwdiv style="width:250;display:none">
													<font style='font-size:10pt' color='blue'><b>��ͬ��</font></b><input
														name="fzxyh" id="fzxyh" size="13"
												</div>
									</tr>
								</table>
							</div>
						</fieldset>
					</tr>
				</table>
				</div>
		</form>
	</div>



	<div id="dia" style="width:330px;height:170px;">
		<form id="fileSubmit" method="post" action=""
			enctype="multipart/form-data">
			<table>
				<tr>
					<td style="padding-top:10px">�ļ����ƣ�</td>
					<td style="padding-top:10px"><input type="text" id="fileName"
						name="fileName" value=""></td>
				</tr>
				<tr>
					<td style="padding-top:10px">ѡ���ļ���</td>
					<td style="padding-top:10px"><input type="file" id="file"
						name="file"></td>
				</tr>
				<tr>
					<td colspan=2 align=center style="padding-top:10px"><a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-save', plain:false"
						onclick="uploadFile()">�ύ</a>
				</tr>
			</table>
	</form>
	</div>
</body>
</html>

