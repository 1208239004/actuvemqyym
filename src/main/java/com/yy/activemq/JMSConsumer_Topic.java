package com.yy.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumer_Topic {
    public static final String MQ_URL = "tcp://192.168.100.188:61616";
    public static final String MyTOPIC = "topic0508";
    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(MyTOPIC);
        MessageConsumer messageConsumer = session.createConsumer(topic);

        messageConsumer.setMessageListener((message -> {
            if (null !=message && message instanceof TextMessage) {
                TextMessage textMessage =(TextMessage) message;
                try {
                    System.out.println("异步接受广播订阅文件："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        }));

    }
}
