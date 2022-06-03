<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
  <body>
    <h1>${msg}</h1>
    <h2>Today is <fmt:formatDate value="${today}" pattern="EEEE, MMMM d, yyyy, h:mm a z" /></h2>
  </body>
</html>
