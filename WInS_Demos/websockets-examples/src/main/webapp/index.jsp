<html>

    <head>
        <link rel="stylesheet" type="text/css" href="css/main.css" media="screen" />
        <title>WebSocket Chat</title>
    </head>

    <body style="width: 1024px;margin: auto; background-image:url('image/bg1.png');background-repeat: repeat-x;">
        <table width='100%'>
            <tr>
                <td align='left'>
                    <img src='image/oraclelogo.png'style="height:60px; width:1075px;" />
                </td>
                
            </tr>
        </table>
        <input type="button" value="Join Chat "  id="joinchat" onClick="_chatJoin()"/>
        <input type="button" value="Leave Chat" style="position: relative;left: 800;" id="leavechat" disabled="true" onClick="_chatLeave()"/>
        </br>
        <div id="chat"><p style="position: relative;bottom:10">Chat</p></div><div id="user"><p style="position: relative;bottom:10;-webkit-margin-start: 100px;">Users</p></div>
        <table>
            <tr>

                <td>
                    <textarea name="chatlogField" style="background-color: #F1F5F8;" readonly="true" rows="14" cols="115" id="chatlogField"></textarea> 
                </td>
                <td>
                    <textarea name="userField"  style="background-color: #F1F5F8;" readonly="true" rows="14" cols="25" id="userField"></textarea>
                </td>
            </tr>
        </table>
        <div id="msgfield"><p style="position: relative;bottom:10">Type Your Message here</p></div>
        <table>
            <tr>
                <td><input type="text" size="100" name="msg" id="msg" value="" /><br/></td>
                <td><input type="button" value="Send Message" id="sendmsg" disabled="true" onClick="_chatSend(document.getElementById('msg').value)"/>
                </td>

            </tr>
        </table>
        <div id="complog"><p style="position: relative;bottom:10">Complete log</p></div>
        <textarea name="logarea" id="logarea" style="background-color: #F1F5F8;" rows="7" cols="145" readonly="true"></textarea>
        </br>



        <script type="text/javascript" src="websocket.js"></script>        

    </body>

</html>
