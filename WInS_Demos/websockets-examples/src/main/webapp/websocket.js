var chathandle;
var chatPipe;
var wsUri = "ws://" + document.location.host + document.location.pathname + "ws";
//("ws://wins-vbox.localdomain:7001/chat-engine/ws");

function _chatJoin() 
{
    chathandle = prompt('Join chat with handle...', 'User-1');
    chatPipe = new WebSocket(wsUri);
    chatPipe.onopen = _chatOpen;
    chatPipe.onmessage = _chatReceive;
    chatPipe.onclose = _chatClose;
}

function _chatOpen(event) 
{
    _chatSend('---------- joined ----------');
    document.getElementById('joinchat').disabled=true;
    document.getElementById('leavechat').disabled=false;
    document.getElementById('sendmsg').disabled=false;
}

function _chatClose(event) 
{
    _chatLog('---------- WebSocket is now closed ----------');
    document.getElementById('joinchat').disabled=false;
    document.getElementById('leavechat').disabled=true;
    document.getElementById('sendmsg').disabled=true;
}

function _chatLeave() 
{
    _chatSend('---------- left ----------');
    chatPipe.close();
}

function _chatSend(msg) 
{
    chatPipe.send(chathandle + ": " + msg);
    document.getElementById('msg').select(); 
}  

function _chatReceive(event) 
{
    var count = -1;
    var tempUsers = userField.innerHTML;
    var joinTextIndex = event.data.indexOf(": ---------- joined ----------");
    var leftTextIndex = event.data.indexOf(": ---------- left ----------");
    var modifiedUserList = event.data.substring(0,joinTextIndex);
    var originalUserListIndex = modifiedUserList.lastIndexOf(";")+1;
    
    if (joinTextIndex > 0)
    {
        var allUsers = event.data.substring(0,originalUserListIndex);
        userField.innerHTML = "";
        
        while ((count = allUsers.indexOf(";")) > 0 )
        {
            var user = allUsers.substring(0,count);
            userField.innerHTML += user + "\n";
            allUsers = allUsers.substring(count+1);
        }
        count = -1;
    }

    if (leftTextIndex != -1) 
    {
        var uLeft = event.data.substring(0, leftTextIndex);
        userField.innerHTML = "";
        
        while ((count = tempUsers.indexOf("\n")) > 0 )
        {
            var user = tempUsers.substring(0,count);
            if (!(user==uLeft))
            {
                userField.innerHTML += user + "\n"; 
            }
            tempUsers= tempUsers.substring(count+1);
        }        
    }
    else 
    {
        chatlogField.innerHTML += _giveOriginalData(event.data) + "\n";        
    }    
    _chatLog(_giveOriginalData(event.data));
}

function _giveOriginalData(decoratedData)
{
    var joinTextIndex = decoratedData.indexOf(": ---------- joined ----------");
    var modifiedUserList = decoratedData.substring(0,joinTextIndex);
    var originalUserListIndex = modifiedUserList.lastIndexOf(";")+1;
    var origUserList = decoratedData.substring(originalUserListIndex);
    
    return origUserList;
}

function _chatLog(msg) 
{
    var now = new Date();
    var h = now.getHours();
    var m = now.getMinutes();
    var s = now.getSeconds();
    if (h < 10) h = '0' + h;
    if (m < 10) m = '0' + m;
    if (s < 10) s = '0' + s;

    var la = document.getElementById('logarea');
    if (!la) return;
    la.value = la.value + "[" + h + ":" + m + ":" + s + "] " + msg + '\n';
}
