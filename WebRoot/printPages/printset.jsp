<%@ page language="java" pageEncoding="GBK"%>
<%
	String cxt=request.getContextPath();
%>
<!-- MeadCo ScriptX Control -->
<object id="factory" style="display:none" viewastext
classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
codebase="Scriptx.cab#Version=6,3,435,20">
</object>
<div id="printpara" class=Noprint style="top:0;left:0;width:100%;height:40px;">
	<table border=0 style="width:100%;"><tr><td align="center">
	<IMG  style='cursor:hand' src="<%=cxt%>/images/t_print.gif" value="打印" onclick="StartPrint()">
	<IMG  style='cursor:hand' src="<%=cxt%>/images/t_pagesetup.gif"  onclick="factory.printing.PageSetup()">
	<IMG  style='cursor:hand' src="<%=cxt%>/images/t_close.gif"  onclick="parent.parent.close()">
	</td></tr></table>	
</div>
<script language=javascript>
function outInfo(){
  f.target="_top";
  f.fid.value="out";
  f.submit();
  f.target="_self";
}
function StartPrint()
{   
printpara.style.display="None";
print();
}
function window.onload()
{
	try{
          document.body.style.margin="";
					pagesetup();
		}catch(e){}
}
function fw()
{
if((print_form.id_pn_e.value - print_form.id_pn_b.value)>1000)
   {
   alert("为了保证网络速度,请您选择少于1000条,分批打印");
   return;
   }
 print_form.submit();
}
function tab1()
    {
       if(event.keyCode==13)event.keyCode=9;
    }
   document.onkeydown=tab1;
function pagesetup() 
{
	if (!factory.object ) 
	{
		alert("打印控件ScriptX没有安装，请下载后运行ScriptX.exe安装。");
		navigate("<%=cxt%>Common/xz.htm");
		return false;
	}
	factory.printing.header = "";
	factory.printing.footer = "";
	factory.printing.portrait = true;
	factory.printing.leftmargin = 0;
	factory.printing.topmargin = 0;
	factory.printing.rightmargin = 0;
	factory.printing.bottomMargin = 0;
}
function rese()
{
        csyhxh.style.display="";
}
function cancel()
{
	csyhxh.style.display="None";
}
</script>
<style>
</style>
<style media=print>
.Noprint{display:none;}
.PageNext{page-break-after: always;}
</style>