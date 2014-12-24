<%@page contentType="text/html;charset=UTF-8"%>
Server IP:<%=request.getLocalAddr()%> PORT:<%=request.getLocalPort()%>
<br>RemoteHost:<%=request.getRemoteHost()%>
<br>RemoteAddr:<%=request.getRemoteAddr()%>
<br>real-IP:<%=request.getHeader("X-Real-IP")%>
<br>forwarded-IP:<%=request.getHeader("x-forwarded-for")%>
<br>Proxy-Client:<%=request.getHeader("Proxy-Client-IP")%>
<br>WL-Proxy-Client-IP:<%=request.getHeader("WL-Proxy-Client-IP")%>
