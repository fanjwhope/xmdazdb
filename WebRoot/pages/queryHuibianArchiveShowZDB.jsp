<%@page import="com.hr.dao.BaseDao"%>
<%@page import="com.hr.global.util.Validation"%>
<%@page import="com.hr.util.Md5"%>
<%@page import="com.hr.util.BaseDataOP"%>
<%@page import="com.hr.bean.Biz"%>
<%@page import="com.hr.global.util.ArchiveUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String zdbdbName=request.getParameter("zdbdbName");

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cxt=request.getContextPath();
String lsh=request.getParameter("lsh");
String type=request.getParameter("type");
String xmbh=request.getParameter("xmbh");
String yjflag=request.getParameter("yjflag");
String tbname = request.getParameter("tbname");
String tableName= ArchiveUtil.getFullTableName(request, Biz.class);
String sql= "select biz from " + tbname.split("_")[0]+"_BIZ";
String[][] bz =BaseDao.getBaseDataOP(zdbdbName).queryRowAndCol(sql);
String id = ArchiveUtil.getDepartmentCode(request.getSession());
%>

<html>
  <head>
    <base href="<%=basePath%>">
     <jsp:include page="/publicJsp/libInclude.jsp" />
    <title>������¼</title>
    <style type="text/css">   
    TD{
       FONT-SIZE: 14PX;
     }
     .input{valign="top";height="18px";font-size:14px;padding-top:1px;padding-left:5px;padding-right:5px;
               background-color: #0094BD; color: #ffffff;
               border-left: 0 solid #FFffff; border-right: 0 solid #000099;
               border-top: 0 solid #FFffff; border-bottom: 0 solid #000099;
               cursor:hand
               }
     
     .button{
	        valign="top";height="24px";font-size:14px;padding-top:1px;padding-left:1px;padding-right:1px;
                background-color: buttonface; color: #000000;
                border-left: 1 solid #FFffff; border-right: 1 solid #000000;
                border-top: 1 solid #FFffff; border-bottom: 1 solid #000000
	       }
    </style>
    <script type="text/javascript">
    <%  int year=Integer.parseInt(Md5.date("yyyy"));%>
        $(function(){
        $("#zdbdbName").val('<%=zdbdbName%>');
        	 getForMData('<%=lsh%>','<%=tbname%>');
     	//����
     	 $('#dw').combobox({
     		url:'<%=cxt%>/do/BizDo/findAll?zdbdbName=<%=zdbdbName%>',
     	    valueField:'biz',   
     	    textField:'biz',
     	    panelHeight:100,
     	    editable:false
     	});  
     	
     	//���ʻ�׼
     	$('#lvjz').combobox({
     		url:'<%=cxt%>/do/BasicRateDo/findAll?zdbdbName=<%=zdbdbName%>',
     	    valueField:'id',   
     	    textField:'cname',
     	    panelHeight:150,
     	    editable:false
     	});
     	
   <%--   	$('#select').combobox({ 
     		url:'<%=cxt%>/do/ProjectContentDo/findToArchive?zdbdbName=<%=zdbdbName%>&lsh=<%=lsh%>&dbName=<%=tbname.split("_")[0]%>',
     	    valueField:'ename',   
     	    textField:'cname',
     	    panelHeight:100,
     	    editable:false,
     	    formatter:function(row){
     	    	var opts = $(this).combobox('options');
    	    	if(row.flag=='0'){
    	    		str='<span><font color="#FF0000">{1}</font></span>';
    	    	}else{
    	    		str='<span><font color="#00FF00">{1}</font></span>';
    	    	} 
    	    	return glb.formatString(str,row[opts.textField],row[opts.textField]);
     	    },
     	    onLoadSuccess: function () { //������ɺ�,����ѡ�е�һ��
                 var val = $(this).combobox("getData");
                 for (var item in val[0]) {
                     if (item == "ename") {
                        // $(this).combobox("select", val[0][item]);
                     }
                 }
       	    },
       	   onShowPanel:function () { //����ʱִ��
       		   var lsh='<%=lsh%>';
       		   if(!lsh){
      	    	 $('#select').combobox('reload','<%=cxt%>/do/ProjectContentDo/findToArchive?zdbdbName=<%=zdbdbName%>&dbName=<%=tbname.split("_")[0]%>');
      	       }else{
      	    	 $('#select').combobox('reload','<%=cxt%>/do/ProjectContentDo/findToArchive?zdbdbName=<%=zdbdbName%>&lsh=<%=lsh%>&xmbh=<%=xmbh%>&dbName=<%=tbname.split("_")[0]%>');
      	       }
       	   }
     	}); --%>
     	
     	//������ʽ
     	  $('#bz').combobox({ 
     		url:'<%=cxt%>/do/DBTypeDo/findAll?zdbdbName=<%=zdbdbName%>',
     	    valueField:'id',   
     	    textField:'cname',
     	    panelHeight:150,
     	    editable:false
     	}); 
     	
     	//���ʸ�������
     	$('#rate_direct').combobox({ 
     		valueField:'id',   
      	    textField:'text',
      	   panelHeight:100,
      	    editable:false,
     		data:[{   
     		    "id":"�ϸ�",   
     		    "text":"�ϸ�"
     		},{   
     		    "id":"�¸�",   
     		    "text":"�¸�"  
     		}]  
     	});
     	
     	$('#projectType').combobox({ 
     		url:'<%=cxt%>/do/ProjectTypeDo/getAllProjectType?zdbdbName=<%=zdbdbName%>',
     		valueField:'id',   
      	    textField:'name',
      	   panelHeight:100,
      	    editable:false
     	});
     	
     });
  
       

       // ��ʾ������,url���ʣ�flag  Ϊtrueʱ��ʾ�����ʵ�����Ϊ��ʱ��ҳ�����ˢ��
     function showFormData(url,flag){
    	 $.ajax({
     		type:'post',
     		url:url,
     		dataType:'JSON',
     		success:function(data){
     			if(data==null){
     				if(!flag){
     				 $('#title').html("&nbsp;��Ŀ������¼��0��");
     				}
     				return false;
     			}
     			$('#title').html("&nbsp;��Ŀ������¼��"+data.lsh+"��");
     			loadFrom(data);
     			$('#dd').removeAttr('readonly');
     			if(data.yjgc){
	     			 var strs=data.yjgc.split(";");
	     			var yjgc="";
	     			if(strs.length>0){
		     			for (var a = 0; a < strs.length; a++) {
		     				if(strs[a]){
		     				 yjgc+=strs[a]+"��\n";
		     				}
						}
		     			$('#dd').val(yjgc);
	     			 } 
     			}else{
     				$('#dd').val("");
     			}
     			$('#dd').attr("readonly","readonly");
     			$('#lsh').val(data.lsh);
     			if(data.square==1){
     				$('#squares').val('�ѽ���');
     				$('#squares').attr("disabled",'true');
     			}else{
     				$('#squares').val('�� ��');
     				$('#squares').removeAttr("disabled");
     			}
     		}
     	});
     }
       
       
     function loadFrom(data){
    	   $('#qymc').val(data.qymc);
    	   $('#dkqx').val(data.dkqx);
    	   $('#xmfzr').val(data.xmfzr);
    	   $('#xmmc').val(data.xmmc);
    	   $('#dkje').val(data.dkje);
    	   //$('#dw').combobox('setValue',data.dw);
    	   $('#xmnd').val(data.xmnd);
    	   $('#xmbh').val(data.xmbh);
    	   $('#xyjs').val(data.xyjs);
    	   $('#ywhc').val(data.ywhc);
    	   $('#bz').combobox('setValue',data.bz);
    	   $('#by1').val(data.by1);
    	   $('#lv').val(data.lv);
    	   $('#lvjz').combobox('setValue',data.lvjz);
    	   $('#rate_direct').combobox('setValue',data.rate_direct);
    	   $('#rate_float').val(data.rate_float);
    	   $('#by2').val(data.by2);
    	   $('#projectType').combobox('setValue',data.projectType);
    	   $('#ljr').val(data.ljr);
    	   $('#jcr').val(data.jcr);
    	   $('#ljDate').datebox('setValue',data.ljDate);
    	   $('#tbDate').datebox('setValue',data.tbDate);
    	   $('#ajsm').val(data.ajsm);
    	   $('#isZhuLu').val(data.isZhuLu);
       }
     
       //����lsh��ʾ�����ݣ���λ�����μ���ҳ��ʹ��
    function  getForMData(lsh,tbname){
    	var url='<%=cxt%>/do/ArchiveDo/getOne?zdbdbName=<%=zdbdbName%>&lsh='+lsh+'&tbname='+tbname;
    	showFormData(url);
    	
    }
   

     


     
        //ѡ���ܣ�Ϊ�����ݱ�дĿ¼ �� yjflag�ĵ�״̬  2�ƽ������� ��type��1  ���ϲ�ѯ��ֻ��ʾ�������޸� 
    function dhgl(){
        		var  lsh=$('#lsh').val();
        	var uri;
        	alert("here :"+'<%=yjflag%>' +" : "+ '<%=type%>' );
        	alert('<%=yjflag%>'=='2' || "1"=='<%=type%>');
        	if('<%=yjflag%>'=='2'||"1"=='<%=type%>'){
        		$('#load').attr('src',"<%=cxt%>/pages/achiveDirectoryShow.jsp?zdbdbName=<%=zdbdbName%>&archiveType=dhgl&lsh="+lsh+"&tbname=<%=tbname%>");
        	}else{
           	    $('#load').attr('src',"<%=cxt%>/pages/achiveDirectory.jsp?zdbdbName=<%=zdbdbName%>&archiveType=dhgl&lsh="+lsh);
        	}
        	$('#listPage').hide();
    		$('#modifyPage').show();       	
        }
        function wzdhgl(){
        	var  lsh=$('#lsh').val();
        	$('#load').attr('src',"<%=cxt%>/pages/achiveDirectoryShow.jsp?tbname=zdb_xmwj_Y200040043&zdbdbName=<%=zdbdbName%>&archiveType=dhgl&lsh="+lsh);
        	$('#listPage').hide();
    		$('#modifyPage').show(); 
     }
     function spgl(){
        		 var  lsh=$('#lsh').val();
	  $.ajax({
    		  type:'post',
    		  url:'/xmdazdb/do/ArchiveDepartDo/getListForZDB?zdbdbName=zdb&archiveType=xmlrb&lsh='+lsh,
    		  dataType:'json',
    		  success:function(data){
    		 	 if(data.length>0){
						    	 var archiveType="xmlrb";//$('#select').combobox('getValue');
						        	
						        	if(archiveType==''){
						        		
						        		alert("��ѡ��������ݣ�");
						        		
						        	}else{
						        	
								        	
								        	if(lsh){
									        	var select = "dhgl";//$('#select').combobox('getValue');		        
									        	 $.ajax({
									        		 type:'post',
									        	     url:'<%=cxt%>/do/ArchiveDo/isNotStand?zdbdbName=<%=zdbdbName%>&archiveType='+archiveType+'&lsh='+lsh,
									        	     dataType:'json',
									        	     success:function(data){
									        	      	$('#load').attr('src',"<%=cxt%>/pages/achiveDirectoryStandZDB.jsp?zdbdbName=<%=zdbdbName%>&archiveType="+archiveType+"&lsh="+lsh+"&type="+select);		        	    	
											}
										});
										$('#listPage').hide();
										$('#modifyPage').show();
									} else {
										alert("������¼��Ŀ��Ϣ");
										return false;
									}
						
								} 
    		 	 }else{
						if(lsh){
						window.open("spnr.jsp?zdbdbName=<%=zdbdbName%>&lsh="+$('#lsh').val()+"&tbname=zdb_xmwj_"+"<%=id%>");
						} else {
							alert("������¼��Ŀ��Ϣ");
							return false;
						}
    		 	 }
    		 	}
    		 });	      	
        }
 function wzspgl(){
        	var  lsh=$('#lsh').val();
        	$('#load').attr('src',"<%=cxt%>/pages/achiveDirectoryShow.jsp?tbname=zdb_xmwj_Y200040043&zdbdbName=<%=zdbdbName%>&archiveType=xmlrb&lsh="+lsh);
        	$('#listPage').hide();
    		$('#modifyPage').show(); 
     }
    function   clo(){
    	//if('<%=type%>'==1){
    		//window.close();
    	//}else{
    	  parent.$('#load').innerHTML="";
		  parent.$('#modifyPage').hide(); 
    	  parent.$('#listPage').show();
    	//}
    }
    function cols(){
   	 $('#jeDialog').dialog('close');
   	 $('#jeTab').empty();
    }
    function showDialog(){
      	 if(<%=(bz!=null&&bz.length>0)%>){
   	    	 var bzs=$('#dkje').val().split(";");
   	    	 var str="";
   	    	 for (var i = 0; i < bzs.length; i++) {
   	    		 var bzShow=bzs[i].split(":");
   	    		 if(bzShow.length==2){
   		    		 var numShow=bzShow[1].substring(0,bzShow[1].length-1);
   		    		 str='<tr ><td width="15px"><input type="hidden" id="bizs" name="bizs" value="'+bzShow[0]+'"/><img  src="pages/image/del.png" onclick="dell(this)" ></td>'
   		             +'<td align="right">'+bzShow[0]+'��</td>'
   		             +'<td><input type="text" id="nums" name="nums" value="'+numShow+'" style="width:85px"/> ��</td>'
   		             +'</tr>';
   		    		 $('#jeTab').append(str);
   	    		 }
   			}
   	    	 $("#jeTab input[id='nums']").numberbox({   
 	    	    min:0,   
 	    	    precision:2   
 	    	});  
 	     
         $('#jeDialog').dialog({
        		 title: '�༭���',   
        		    width: 300,   
        		    height: 220,   
        		    closed: false,
        		    closable:false,
        		    cache: false,   
        		    modal: true  
        	 });
      	 }else{
      		 alert("����Աû�����ñ��֣�����ϵ����Ա��");
   	    	 return false;
      	 }
	         
       }
    
    
    function  zj(){
	     if(2=='<%=yjflag%>'||1=='<%=type%>'){
	    	 return false;
	     }else{
	   	    $('#xmnd').val(parseInt($('#xmnd').val())+1);
	     }
   	 
    }
    function js(){
    	 if(2=='<%=yjflag%>'||1=='<%=type%>'){
    		   return false;
    	  }else{
   	           $('#xmnd').val(parseInt($('#xmnd').val())-1);
    	    	 
    	     }
    }
    </script>
  </head>
  
  <body class="easyui-layout" data-options="fit:true">
  <div data-options="region:'center',border:false" style="overflow:hidden" >
  <div id="listPage" style="width:100%;height:100%;padding:20px;margin:0px;overflow:hidden">
  <div id="formDiv" name="formDiv" >
   <center> <form id="addForm" name="addForm"   >
   <input type="hidden" id="zdbdbName" name="zdbdbName"/>
 <table border="3" width="660px" cellpadding="0" cellspacing="0" bgcolor="#FFFFE8"  bordercolordark="#333399"  bordercolorlight="buttonface" >
  <tr>
    <td width="660px" bgcolor="#333399" height="26" bordercolor="#C0C0C0" bordercolorlight="#C0C0C0" align="center">
      <p align="center"><font id="title"  name="title" color="#FFFFFF" style='font-size:14px'>&nbsp;</font></p>
    </td>
  </tr>
  <tr>
    <td width="660px" bgcolor="#C0C0C0" bordercolor="#C0C0C0" bordercolorlight="#C0C0C0" align="center">
      <table border="0" width="660px">
        <tr>
          <td style="width:130px" align="center"><font color="#000080">�����/�ͻ�����</td>
          <td style="width:300px"><input type="text" name="qymc" id='qymc' maxlength='100' style="width:290px"></td>
          <td style="width:40px"><font color="#000080">&nbsp;����</td>
          <td style="width:50px"> <input type="text" name="dkqx"  id='dkqx' maxlength='50' style="width:40px"></td>
          <td style="width:60px" align="right"><font color="#000080">&nbsp;�Ŵ�Ա</td>
          <td style="width:80px" align="left"><input type="text" name="xmfzr"  id='xmfzr' maxlength='50'style="width:70px"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="660px" bgcolor="#C0C0C0" valign="middle" bordercolor="#C0C0C0" bordercolorlight="#C0C0C0" align="center">
      <table border="0" width="660px">
        <tr>
          <td style="width:76px" align="right"><font color="#000080">�� Ŀ�� ��</font></td>
          <td style="width:229px" align="left"><input type="text" name="xmmc" id="xmmc" style="width:219px"  id='xmmc' maxlength='100'/></td>
          <td style="width:35px" align="right"><font color="#000080">���</font></td>
          <td style="width:180px"><input type="text" name="dkje"  id="dkje"  readonly="readonly"  style="width:160px"/><img  src="images/add.png" onclick="showDialog()"></td>
          <td style="width:5px">&nbsp;</td>
          <td style="width:30px" align="right"><font color="#000080">���</font></td>
          <td style="width:45px"><input type="text" id="xmnd" name="xmnd"  style="width:45px"></td>
          <td style="width:20px">
          <font color="#000080"><map name="ndmp"><area coords="0, 0, 14, 10" onclick="zj()" shape="RECT">
            <area coords="1, 7, 14, 21" onclick="js()" shape="RECT"></map>
            </font><img border="0" src="images/zj.gif" useMap="#ndmp" width="14" height="21" align="left"></td>                     
      </table>
    </td>
  </tr>
  <tr>
  	<td style="width:100%;border:0px " >
  		<table  border="0"  style="width:100%"  cellpadding="0" cellspacing="0">
  		   <tr>
    <td width="660px" bgcolor="#C0C0C0" valign="middle" bordercolor="#C0C0C0" bordercolorlight="#C0C0C0" align="center">
      <table border="0" width="660px">
        <tr>
          <td style="width:86px" align="right"><font color="#000080">����ͬ��</font></td>
          <td style="width:167px"><input type="text" name="xmbh" id="xmbh"  style="width:157px" ></td>
          <td style="width:36px"><font color="#000080">����</font></td>
          <td style="width:45px"><input type="text" name="xyjs"  id="xyjs" style="width:35px" /></td>
          <td style="width:120px" align="left"><font color="#000080">��&nbsp;����Э���</font></td>
          <td style="width:206px"><input type="text" name="ywhc"  id="ywhc" style="width:196px"  ></td>   
      </table>
    </td>
  </tr>
  <tr>
    <td width="660px" bgcolor="#C0C0C0" valign="middle" bordercolor="#C0C0C0" bordercolorlight="#C0C0C0" align="center">
      <table border="0" width="660px">
        <tr>
        	<td style="width:88px" align="right"><font color="#000080">�� ���� ʽ</font></td>
        	<td style="width:228px" ><input type="text" name="bz" id="bz"  style="width:218px" ></td>
        	<td style="width:55px" align="right"><font color="#000080">��֤��</font></td>
        	<td style="width:160px"><input type="text" name="by1" id="by1" style="width:150px"  ></td>
        	<td style="width:36px" align="right"><font color="#000080">����</font></td>
        	<td style="width:93px"><input type="text" name="lv" id="lv" style="width:83px"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="660px" bgcolor="#C0C0C0" valign="middle" bordercolor="#C0C0C0" bordercolorlight="#C0C0C0" align="center">
      <table border="0" width="660px">
        <tr>
        	<td style="width:84px" align="right"><font color="#000080">�� �ʻ� ׼</font></td>
        	<td style="width:232px" align="left"><input type="text" id="lvjz" name="lvjz" style="width:218px"/></td>
        	<td style="width:100px" align="left"><font color="#000080">���ʸ�������</font></td>
        	<td style="width:75px" align="left"><input  type=text name="rate_direct" id="rate_direct" style="font-size:30px;width:65px"></td>
        	<td style="width:19px;">&nbsp;</td>
        	<td style="width:90px" align="right"><font color="#000080">���ʸ�����</font></td>
        	<td style="width:60px"><input type=text name="rate_float" id="rate_float"  style="text-align:right;width:45px" />%</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="660px" bgcolor="#C0C0C0" valign="middle" bordercolor="#C0C0C0" bordercolorlight="#C0C0C0" align="center">
      <table border="0" width="660px">
        <tr>
        	<td style="width:84px" align="right"><font color="#000080">�� ���� ��</font></td>
        	<td style="width:120px" align="left"><input type="text" name="by2" id="by2"  style="width:120px" /></td>
        	<td style="width:70px" align="right"><font color="#000080">��ĿƷ��</font></td>
        	<td style="width:100px" align="left"><input type="text" id="projectType" name="projectType" style="width:100px"/></td>
        	<td style="width:60px" align="right"><font color="#000080">������</font></td>
        	<td style="width:80px"><input name="ljr" id="ljr" style="width:80px" /></td>
        	<td style="width:60px" align="right"><font color="#000080">�����</font></td>
        	<td style="width:80px"><input name="jcr" id="jcr" style="width:80px"/></td>
        </tr>
      </table>
    </td>
  </tr>
  		</table>
  	</td>
  </tr>
  
  <tr>
    <td width="660px" bgcolor="#C0C0C0" valign="middle" bordercolor="#C0C0C0" bordercolorlight="#C0C0C0" align="center">
      <table border="0" width="660px">
        <tr>
        	<td style="width:78px" align="right"><font color="#000080">����ʱ��</font></td>
        	<td style="width:150px" align="left"> <input type="text" name="ljDate"  id="ljDate" class="easyui-datebox" style="width: 150px;" ></td>
        	<td style="width:100px;">&nbsp;</td>
        	<td style="width:76px" align="right"><font color="#000080">���ʱ��</font></td>
        	<td style="width:120px" align="left"><input type="text" name="tbDate" id="tbDate" style="width: 150px;" class="easyui-datebox"></td>
        	<td style="width:100px;">&nbsp;</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
        <td bgColor="#c0c0c0" borderColor="#c0c0c0" borderColorLight="#c0c0c0" height="8" width="660px">
          <table border="0" width="708" height="73">
            </center>
            <tr>
              <td width="66" height="17" valign="bottom"> <p align="right"><font color="#000080">��</font><font color="#000080">��</font></p> </td>
              <center>
              <td width="642" rowspan="2" height="40">&nbsp;&nbsp;<textarea cols="80" id="ajsm" name="ajsm" rows="2"></textarea></td>
              </tr>
            </center>
            <tr>
              <td width="66" height="19" valign="top">
                <p align="right"><font color="#000080">˵��</font></td>
            </tr>
            <tr>
              <td width="66" height="17" valign="bottom">
                <p align="right"><font color="#000080">��</font><font color="#000080">��</font></p>
              </td>
              <center>
              <td width="642" rowspan="2" height="40">&nbsp;&nbsp;<textarea name="dd" id="dd" cols="80" name="dd" rows="3" >
              </textarea></td>
              </tr>
            </center>
            <tr>
              <td width="66" height="19" valign="top">
                <p align="right"><font color="#000080">����</font></td>
            </tr>
        </table>
        </td>
  </tr>
  <tr>
    <td width="661" bgcolor="#C0C0C0" height="19" bordercolor="#C0C0C0" bordercolorlight="#C0C0C0">
      <p align="center">
          <input type="hidden" id="lsh" name="lsh" >
         <input type="hidden" id="isZhuLu" name="isZhuLu" >
         <input type="hidden" id="dingwei" name="dingwei" value="0" >
        <table border="0" width="109%">
         <hr>
            <tr>
              <td  align='middle'>
                      <input type="button" class="button" value="�������"  onclick="dhgl()">&nbsp;
                     <input type="button" class="button" value="��������"  onclick="spgl()">&nbsp;
                     <input type="button"  class="button"  value="�� ��" name="okprev" id="okprev"  onclick="clo()">			    
				</td>
            </tr>
             <tr>
            	<td  align='middle'>
				 	<input type="button" class="button" value="�����Ĵ������ɨ���"  onclick="wzdhgl()">&nbsp;
                     <input type="button" class="button" value="�������������ݼ�ɨ���"  onclick="wzspgl()">&nbsp;
				</td>
            </tr>
          </table>
</td>
 
  </tr>
</table>
</center>
    </form>
  </div>
  </div>
  <div id="modifyPage" style="width:100%;height:100%;padding:0px;margin:0px;display:none;overflow:hidden">
	  	      <iframe id="load" style="width:100%;height:100%;" src="" ></iframe>
	  	</div>
	  </div>
	   <div  id="jeDialog"  style="height: 180px;width:290px;">  
     <div id="leftDiv" style="height: 150px;width:80px;;float: left;margin-top: 3px;border-right:2px solid #E0ECFF" align="center">
      <select  id="dwList" multiple="multiple" style="height: 150px;" onclick="addJe()">
      <% for(int i=0;i<bz.length;i++){ %>
         <option value="<%=bz[i][0] %>" ><%=bz[i][0] %></option>
      <%} %>
      </select>
     </div>
	 <div id="rightDiv" style="height: 150px;width:200px;float: right">
	     <table id="jeTab" style="">
	     </table>
	   </div>
	   <div id="tail" style="height: 25px;width:280px;float: inherit" align="center">
	         <input type="button" id="bzsSave" name="bzsSave" value="����" onclick="saveJe()">&#12288;&#12288;&#12288; 
	              <input type="button" id="bzsCal" name="bzsCal" value="�ر�" onclick="cols()"> 
	   </div>
 </div> 
  </body>
</html>
