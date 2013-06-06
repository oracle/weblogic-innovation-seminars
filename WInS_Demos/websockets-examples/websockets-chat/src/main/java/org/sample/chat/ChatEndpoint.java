package org.sample.chat;

import weblogic.websocket.*;
import weblogic.websocket.annotation.*;
import java.util.*;

@WebSocket
(
    timeout = -1,
    pathPatterns = {"/ws/*"}
)
public class ChatEndpoint extends WebSocketAdapter 
{
    static final String joined = ": ---------- joined ----------";
    static final String left = ": ---------- left ----------";
    
    List<String> users = new ArrayList<String>();
    
    public boolean accept(WSHandshakeRequest request, WSHandshakeResponse response)
    {
        return true;
    }
    
    public void onMessage(WebSocketConnection connection, String payload) 
    {
        int joinIndex = payload.indexOf(joined);
        int leftIndex = payload.indexOf(left);
        String user ;
        StringBuffer userList ;
        
        // User is joining - add him to the List<String> of users
        // Check if he doesnt already exist
        // Modify the payload such that the joining users are added to
        // existing user list and the names are seperated by ";"
        if (joinIndex > 0)
        {
            user = payload.substring(0, joinIndex);
            
            if (!users.contains(user))
                users.add(user);
            
            userList = new StringBuffer();
            for (String s:users)
            {
                userList.append(s);
                userList.append(";");
            }
            userList.append(payload);
            payload = userList.toString();
        }
        
        // User is leaving - remove him from the List<String> of users
        // Check if he already exists of not
        if (leftIndex > 0)
        {
            user = payload.substring(0, leftIndex);
            
            for (int i=(users.size()-1); i>= 0 ; i--)
            {
               if (user.equals(users.get(i)))
               {
                   users.remove(i);
                   break;
               }    
            }
        }
                
        for (WebSocketConnection wsConn : getWebSocketContext().getWebSocketConnections()) 
        {
            try 
            {
                wsConn.send(payload);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            } 
        }    
    }
    
    public void onTimeout(WebSocketConnection connection) 
    {
        try 
        {
            connection.send("The connection is closed because of timeout!");
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
    }

    public void onError(WebSocketConnection connection, Throwable error) 
    {
        try 
        {
            connection.send("The connection is closed because of error: " + error.getMessage());
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }    
    }
}