<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.time.Duration" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Weather Rest Client</title>
  <link rel="stylesheet" href="style.css" type="text/css">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
<script type="text/javascript">
    $(document).ready(() => {
      $('#frmInput').on('submit', async (e) => {
        e.preventDefault();
        let loc = $('#txtLoc').val();
        let dtm = $('#txtDate').val();
        let url = "api/weatherResource/" + encodeURI(dtm) + "?location=" + encodeURI(loc) + "&width=750&height=500";
        // $('#imgGraph').attr("src", "https://i.stack.imgur.com/i0Khf.gif");
        $('#imgGraph').attr("src", "https://i.stack.imgur.com/MEBIB.gif");
        let data = await $.get(url);
        $('#imgGraph').attr("src", "data:image/png;base64," + data.image);
        return false;
      });

    });

</script>
  <body>
  <h1><%= "Weather REST client" %></h1>
  <br/>
  <form id="frmInput" >
    <label for="txtLoc">Location</label>
    <input type="text" id="txtLoc" name="txtLoc" value="Maribor"><br>
    <label for="txtDate">Date</label>
    <input type="text" id="txtDate" name="txtDate" value="<%= new SimpleDateFormat("dd.MM.yyyy").format(Date.from(Instant.now().minus(Duration.ofDays(3))))%>"><br>
    <input type="submit" id="butSubmit" value="Draw Graph">
  </form>
  <div id="graph">
    <img id="imgGraph">
  </div>
  </body>
</html>