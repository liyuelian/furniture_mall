<%--
  Created by IntelliJ IDEA.
  User: li
  Date: 2022/12/21
  Time: 17:14
  Version: 1.0
--%>
<%--    直接请求到CustomerFurnServlet，
        获取网站首页要显示的分页数据，类似我们的网站入口页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--注意这里如果没有子元素的话不要分行！！！--%>
<jsp:forward page="/customerFurnServlet?action=pageByName&pageNo=1"></jsp:forward>



