<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-7
  Time: 下午5:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="util.GlobalConfigHelper" %>
<title>${ param.title } - <%= GlobalConfigHelper.getConfigFromContext(application).getProperty("WebsiteName") %>
</title>
