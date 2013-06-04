<%@ page import="java.util.*"%>
<%@ page import="com.oracle.demo.session.*"%>
<%@ page import="com.oracle.demo.model.City"%>
<%@ page isErrorPage="true"%>

<html>
  <head>
    <title>The Little Aussie Tripper - Version <%=application.getInitParameter("app.version")%></title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"></meta>
    <meta HTTP-EQUIV="Pragma" CONTENT="no-cache"/>
    <meta HTTP-EQUIV="Expires" CONTENT="-1"/>
    <meta HTTP-EQUIV "Cache-Control", CONTENT="no-cache"/>
    <link href="css/blaf.css" rel="stylesheet" media="screen"></link>
    <link href="css/jdeveloper.css" rel="stylesheet" media="screen"></link>
  </head>

  <script language="JavaScript">
  function showLastHost() {
    var host = '<%=(String)session.getAttribute("HOST")%>';
    var url = '<%=(String)session.getAttribute("URL")%>';
    alert('** Last Request Details **\n\n' + host + '\n' + url);
  }
  </script>

  <body>

    <h2>
    The Little Aussie Tripper - Version <%=application.getInitParameter("app.version")%>
    </h2>
  <%
    if("true".equalsIgnoreCase(request.getParameter("invalidate"))) {
        request.getSession().invalidate();
        //request.getRequestDispatcher("logout.jsp").forward(request, response);
  %>
    <p>
    Your client session has now ended.    
    </p>
    <p>
    Click <a href="index.jsp">here</a> to access the application again.    
    </p>
  
  <%      
    } else {
  %>


    <p>
      This is a simple application which is used to demonstrate the persistence of client state across multiple HTTP requests.
    </p>
    <h3>
      Instructions
    </h3>
    <ul>
      <li>
          Click on the a city name in the map below to visit it -- the cities
          you visit will be displayed in a list
      </li>
      <li>
          Click [Show Last Request] to see the hostname of the server which
          serviced the last request and the URL used
      </li>
      <li>
          Click [Clear Trips] to clear the list
      </li>
      <li>
          Click [Invalidate] to invalidate the session
      </li>
    </ul>
    <fieldset>
    <table width="90%" border="0" cellspacing="5" cellpadding="5">
      <tr>
        <td align="center" valign="top"><h3>
            Select a Destination ....
          </h3></td>
        <td align="center" valign="top"><h3>
            Places Visited ...
          </h3></td>
      </tr>
      <tr>
        <td  width="430" align="center" valign="top">
          <img height="333" alt=""
               src="images/australia-map.gif"
               width="422"
               usemap="#DESTINATIONS"
               border="0"></img>
          <map name="DESTINATIONS">
            <area shape="RECT" alt="" coords="304,291,402,330"
                  href="trip?city=Hobart">
            </area>
            <area shape="RECT" alt="" coords="295,254,398,286"
                  href="trip?city=Melbourne">
            </area>
            <area shape="RECT" alt="" coords="321,188,405,249"
                  href="trip?city=Sydney"></area>
            <area shape="RECT" alt="" coords="296,115,415,186"
                  href="trip?city=Brisbane">
            </area>
            <area shape="RECT" alt="" coords="203,193,320,253"
                  href="trip?city=Adelaide">
            </area>
            <area shape="RECT" alt="" coords="170,83,261,156"
                  href="trip?city=Alice Springs"></area>
            <area shape="RECT" alt="" coords="167,11,261,81"
                  href="trip?city=Darwin"></area>
            <area shape="RECT" alt="" coords="23,173,144,264"
                  href="trip?city=Perth"></area>
            <area shape="RECT" alt="" coords="0,0,413,324"
                  href="JavaScript:alert('You didn\'t click a city to visit')"></area>
        </map>
       </td>
        <td width="621" align="center" valign="top" >
           <a href="index.jsp?invalidate=true">[Invalidate Session]</a>&nbsp;
           <a href="trip?clear=true">[Clear Trips]</a>&nbsp;
           <a href="JavaScript:showLastHost();")>[Show Last Request]</a>
           <%if (session.getAttribute("HOST")!=null) { %>
           <p class="note">
             <%=session.getAttribute("HOST")%>
             <br/>
             <%=session.getAttribute("URL")%>
           </p>
           <% } %>

          <%
            List<City> cities = (List)session.getAttribute("CITIES");
            String host = (String)session.getAttribute("HOST");
            if(cities!=null)
            {
          %>
          <P>You have visited
              <strong><%=cities.size()%>
                 </strong>cities</p>
          <table cellpadding="0" cellspacing="2">
          <%
              Iterator iter = cities.iterator();
              int count = 0;
              while(iter.hasNext()) {
          %>
            <tr>
              <td><%=++count%></td>
              <td>&nbsp;</td>
              <td><%=((City)iter.next()).toHTML()%></td>
            </tr>
          <%
              }
            }
          %>
          </table>
        </td>
      </tr>
    </table>
    </fieldset>
    <%
    }
    %>
  </body>
</html>