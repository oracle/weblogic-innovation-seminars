package com.oracle.example;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.jms.*;

@Named(value = "jMSMessageProducerBean")
@RequestScoped
public class JMSMessageProducerBean {

    @Resource(mappedName = "jms/testQueue")
    private Queue myQueue;
    @Resource(mappedName = "jms/connectionFactory")
    private ConnectionFactory myQueueFactory;
    private String message;

    public JMSMessageProducerBean() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void send() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            sendJMSMessageToMyQueue(message);
            FacesMessage facesMessage = new FacesMessage("Message sent: " + message);
            facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
            facesContext.addMessage(null, facesMessage);
        } catch (JMSException jmse) {
            FacesMessage facesMessage = new FacesMessage("Message NOT sent: " + message);
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(null, facesMessage);
        }
    }

    private Message createJMSMessageForjmsMyQueue(Session session, Object messageData) throws JMSException {
        // TODO create and populate message to send
        TextMessage tm = session.createTextMessage();
        tm.setText(messageData.toString());
        return tm;
    }

    private void sendJMSMessageToMyQueue(Object messageData) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            connection = myQueueFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(myQueue);
            messageProducer.send(createJMSMessageForjmsMyQueue(session, messageData));
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}