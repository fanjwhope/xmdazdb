<%@page import="com.hr.global.util.Validation"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String lsh = request.getParameter("lsh");
String archiveType = request.getParameter("archiveType");
String dwh = request.getParameter("dwh");
String tbname=request.getParameter("tbname");
String dbName = request.getParameter("dbName");
if(!Validation.isEmpty(tbname)){
	dbName=tbname.split("_")[0];
}
String xmbh = request.getParameter("xmbh");
String zdbdbName=request.getParameter("zdbdbName");

%>

<!DOCTYPE html>
<html>
  <head>
    <title>ɨ���Ԥ��</title>
    <script src="../js/jquery-1.8.0.min.js"></script>
    <script>
    
     var path;
    
    $(function(){
    	
    	fixationDiv();
    
    	load(1);
    	
    	$("#down").click(function(){
    		download(path);
    	});
    	
    	//���ڴ�С�仯���¹̶�λ��
    	$(window).resize(function() {
    		fixationDiv();
		});
    	
    });
    
    function load(num){
    	window.scroll(0, 0);
    	$.ajax({
    		url:"<%=path%>/do/ArchiveDo/getJpgToShow?zdbdbName=<%=zdbdbName%>",
    		type:"post",
    		async: false,
    		data:{'lsh':'<%=lsh%>', 'archiveType':'<%=archiveType%>', 'page':num, 'dwh':'<%=dwh%>', 'dbName':'<%=dbName%>', 'xmbh':'<%=xmbh%>'},
    		success:function(data){
    			
    			var all = $.parseJSON(data);
    			var filePaths = all.files;
    			
    			if(filePaths.length>1){
    				var str = "";
    				for (var i = 0; i < filePaths.length; i++) {
    					var arr = filePaths[i].replace(/\\/g,"\\\\\\\\");
						str += "<a href = '#' id = '"+(i+1)+"' name = 'flag'>��"+(i+1)+"�� </a><span id = 's_"+(i+1)+"' name = '"+arr+"' style = 'display:none'></span>";
					}
					
					$("#count").css({display:"block"}); 
					$("#down").hide();
					$("#count").html("�� "+filePaths.length+" ɨ���  "+str);
					
    			}else{
    				path = filePaths[0];
    			
    			}
    			
    			//var all = eval('('+data+')');  //���������Ч����ͬ
    			var type = '<%=archiveType%>';
		    	if(type == '2002')
		    		type = 'xmlrb';
		    	var src = "<%=path%>/picture?zdbdbName=<%=zdbdbName%>&path=" + all.dirPath + num + ".jpg&flag=" + Math.random();
    			$('#jpg').attr("src", src);
    			$("#currentPages").html(num);
    			$("#pages").html(all.currentPage);
    			$("#currentPages").attr("value", num);
    			$("#pages").attr("value", all.currentPage); 
    			
    			bindClick();
    		}
    	});
    };
    
    //���¼�
    function bindClick(){
    	$("a[name='flag']").click(function(){
    	
    		var id = "s_"+$(this).attr("id");
    		var param = $("#"+id).attr("name");
    		download(param);
    	});
    
    }
    
    
    //�̶�btndiv��λ��
    function fixationDiv(){
    	
    	var width = document.body.clientWidth;
    	var divWidth = $("#btn").width();
    	var center = width/2-divWidth/2;
    	$("#btn").css({left:center});
    
    }
    
    
     function replaceall(s1,s2){    
		return this.replace(new RegExp(s1,"gm"),s2);    
	}
    
    function show(temp){
    	var currentPages = Number($('#currentPages').val());
    	var pages = Number($('#pages').val());
    	
    	if(pages == 0){
    		alert("ɨ��������ڣ�");
    		$('#pic').attr("value", "");
    		return;
    	}
    	switch(temp){
    		case 'now':
		    	var num = Number($('#pic').val());
		    	if(num > 0 && num <= pages)
		    	   	load(num);
		    	else{
		    		alert("��������ȷ��ҳ�룡");
		    	}
		    	$('#pic').attr("value", "");
    			break;
    		case 'prev':
    			if(currentPages == 1)
    				alert("��ǰΪ��һҳ��");
    			else
    				load(currentPages - 1);
    			break;
    		case 'next':
    			var pages = $("#pages").html();
    			if(currentPages == pages)
    				alert("��ǰΪ���һҳ��");
    			else
    				load(currentPages + 1);
    		 	break;	
    	}
    };

     function download(filepath) { 
            var form = $("<form>");   //����һ��form��
            form.attr('style', 'display:none');   //��form������Ӳ�ѯ����
            form.attr('target', '');
            form.attr('method', 'post');
            form.attr('action', '<%=path%>/do/DownloadScanningCopy/Download?zdbdbName=<%=zdbdbName%>');

            var input1 = $('<input>');
            input1.attr('type', 'hidden');
            input1.attr('name', 'path');
            input1.attr('value', filepath);
            $('body').append(form);  //����������web�� 
            form.append(input1);   //����ѯ�����ؼ��ύ������
            form.submit();

         }
    </script>
    <style>

a:link{ 
text-decoration:none; 
} 
a:visited{ 
text-decoration:none; 
} 
a:hover{ 
text-decoration:none; 
} 
a:active{ 
text-decoration:none; 
} 

#btn li{
	list-style:none;
}

@media print {
.notprint {
	display:none;
}
.PageNext {
	page-break-after:always;
}
}
 @media screen {
.notprint {
	display:inline;
	cursor:hand;
}
}
</style>
  </head>
  	
  <body>
  	<div id="btn" style="position:fixed;top:10px;" class="notprint" >
  		<ul>
  			<li>
  				<input type="button" value="��һҳ" onclick="show('prev')">
		  		<font size=2>��<small id="currentPages"></small>/<small id="pages"></small>ҳ</font>
		  		<input type="button" value="��һҳ" onclick="show('next')">
		  		&nbsp;&nbsp;����<input type="text" id="pic" value="" size="4" maxlength="4" style="ime-mode:disabled"/>ҳ
		  		&nbsp;&nbsp;<input type="button" value="ȷ��" onclick="show('now')">
		  		&nbsp;<input type="button" value="��ӡ" onclick="window.print()">
		  		<input type="button" id = "down" value="����" >
  			</li>
  			<li>
  				 <center><span id = "count" style="display: none;padding-top:10px;"></span></center> 
  			</li>
  		</ul>
  	</div>
  	<div align="center" style="margin-top:90px">
  		<img id="jpg" src="" />
  	</div>
  </body>
</html>
