/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package websocket.snake;

import java.awt.Color;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


import weblogic.websocket.ClosingMessage;
import weblogic.websocket.WebSocketAdapter;
import weblogic.websocket.WebSocketConnection;
import weblogic.websocket.WebSocketContext;
import weblogic.websocket.annotation.WebSocket;

/**
 * Example web socket servlet for simple multiplayer snake.
 *
 * This example was originally from the Apache Tomcat project
 * and has been ported to use the WebLogic WebSocket API as a
 * demonstration.
 */
@WebSocket(pathPatterns = "/snake.ws")
public class SnakeWebSocketWLS extends WebSocketAdapter {

    private static final long serialVersionUID = 1L;
    private final Logger log = Logger.getLogger(SnakeWebSocketWLS.class.getName());
    
    public static final int PLAYFIELD_WIDTH = 640;
    public static final int PLAYFIELD_HEIGHT = 480;
    public static final int GRID_SIZE = 10;
    private static final long TICK_DELAY = 100;
   
    private static final Random random = new Random();
   
    private final Timer gameTimer = new Timer(SnakeWebSocketWLS.class.getSimpleName() + " Timer");
    private final ConcurrentHashMap<Integer, Snake> snakes = new ConcurrentHashMap<Integer, Snake>();

    public void init(WebSocketContext context) {
        super.init(context);

        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    tick();
                } catch (RuntimeException e) {
                    log.fine("Caught to prevent timer from shutting down: " + e.getMessage());
                }
            }
        }, TICK_DELAY, TICK_DELAY);
    }

    private void tick() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<Snake> iterator = getSnakes().iterator();
                iterator.hasNext();) {
            Snake snake = iterator.next();
            snake.update(getSnakes());
            sb.append(snake.getLocationsJson());
            if (iterator.hasNext()) {
                sb.append(',');
            }
        }
        broadcast(String.format("{'type': 'update', 'data' : [%s]}",
                sb.toString()));
    }

    private void broadcast(String message) {
        for (WebSocketConnection connection : getWebSocketContext().getWebSocketConnections()) {
            try {
                connection.send(message);
            } catch (IOException ignore) {
                // Ignore
            }
        }
    }

    @Override
    public void onOpen(WebSocketConnection connection) {
        log.info("onOpen: " + connection.toString() + " " + connection.hashCode());

        // Use connection hashCode to identify connection
        int snakeId = connection.hashCode();

        // Store snake with connection id
        Snake snake = new Snake(snakeId, connection);
        snakes.put(snakeId, snake);
        
        StringBuilder sb = new StringBuilder();
        for (Iterator<Snake> iterator = getSnakes().iterator();
                iterator.hasNext();) {
            Snake localSnake = iterator.next();
            sb.append(String.format("{id: %d, color: '%s'}",
                    Integer.valueOf(localSnake.getId()), localSnake.getHexColor()));
            if (iterator.hasNext()) {
                sb.append(',');
            }
        }
        broadcast(String.format("{'type': 'join','data':[%s]}", sb.toString()));
    }

    public void onClose(WebSocketConnection connection, ClosingMessage closingMessage) {
        log.info("onClose: " + connection.toString());
        int snakeId = connection.hashCode();
        snakes.remove(snakeId);
        broadcast(String.format("{'type': 'leave', 'id': %d}", snakeId));
    }

    public void onMessage(WebSocketConnection connection, String message) {
        
        // Find the snake for this connection
        int snakeId = connection.hashCode();
        Snake snake = snakes.get(snakeId);

        if ("west".equals(message)) {
            snake.setDirection(Direction.WEST);
        } else if ("north".equals(message)) {
            snake.setDirection(Direction.NORTH);
        } else if ("east".equals(message)) {
            snake.setDirection(Direction.EAST);
        } else if ("south".equals(message)) {
            snake.setDirection(Direction.SOUTH);
        }
    }
    
    @Override
    public void destroy() {
        super.destroy();
        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }

    private Collection<Snake> getSnakes() {
        return Collections.unmodifiableCollection(snakes.values());
    }

    public static String getRandomHexColor() {

        float hue = random.nextFloat();
        // sat between 0.1 and 0.9
        float saturation = (random.nextInt(2000) + 7000) / 10000f;
        float luminance = 0.9f;
        Color color = Color.getHSBColor(hue, saturation, luminance);
        return '#' + Integer.toHexString(
                (color.getRGB() & 0xffffff) | 0x1000000).substring(1);
    }

    public static Location getRandomLocation() {
        int x = roundByGridSize(
                random.nextInt(SnakeWebSocketWLS.PLAYFIELD_WIDTH));
        int y = roundByGridSize(
                random.nextInt(SnakeWebSocketWLS.PLAYFIELD_HEIGHT));
        return new Location(x, y);
    }

    private static int roundByGridSize(int value) {
        value = value + (SnakeWebSocketWLS.GRID_SIZE / 2);
        value = value / SnakeWebSocketWLS.GRID_SIZE;
        value = value * SnakeWebSocketWLS.GRID_SIZE;
        return value;
    }
}
