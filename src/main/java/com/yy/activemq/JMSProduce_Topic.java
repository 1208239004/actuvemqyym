package com.yy.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProduce_Topic {
    public  static  final String MQ_URL="tcp://192.168.100.188:61616";
    public  static  final String My_TOPIC = "topic0508";
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(My_TOPIC);

        MessageProducer messageProducer = session.createProducer(topic);

        for (int i = 1; i <=6 ; i++) {
            TextMessage textMessage = session.createTextMessage("广播订阅方式：" + i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("广播订阅消息发送成功");

    }
}
