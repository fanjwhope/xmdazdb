<%String cxt=request.getContextPath(); 
String zdbdbName=request.getParameter("zdbdbName");
%>
<script type="text/javascript" charset="GBK">
	$(function(){
		//window.setInterval("heartBeat()",90000);
	});
	
	function heartBeat(){
		$.ajax({
			url : '<%=cxt%>/do/UserDo/heartBeat?zdbdbName=<%=zdbdbName%>',
			dataType:'json',
			type:'GET',
			cache : false,
			success:function(data){
				//do nothing
			}
		});
	}
	
</script>
