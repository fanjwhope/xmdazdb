<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.hr.util.*"%>
 
<%@page session="true"
        isErrorPage="true"%>
 <font style='font-size:12px'>系统提示：
 	<br>
 &nbsp;&nbsp;&nbsp;	发生了不可预见的错误,请您与管理员连系！！<br>
 <%Log.error(exception.toString()+"错误页" );%>
<!--%= exception.toString()%-->
<br>
&nbsp;&nbsp;&nbsp;&nbsp;</font>