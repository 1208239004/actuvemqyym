package com.yy.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumers {
    public static final String MQ_URLS="tcp://192.168.100.188:61616";
    public static final String MQ_QUEUE="avtyy0508";
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URLS);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(MQ_QUEUE);
        MessageConsumer messageConsumer = session.createConsumer(queue);

         /*同步阻塞方式receive() ，订阅者或接收者调用MessageConsumer的receive()方法来接收消息，
        receive()将一直阻塞
        receive(long timeout) 按照给定的时间阻塞，到时间自动退出*/
         TextMessage textMessage =null;
         while (true){
             textMessage = (TextMessage) messageConsumer.receive();
             if ( null != textMessage ) {
                 System.out.println("receive()方式收到点对点消息："+textMessage.getText());
                 textMessage.acknowledge();
             }else {
                 break;
             }
         }

//        messageConsumer.setMessageListener(message -> {
//            if ( null!= message && message instanceof TextMessage) {
//                TextMessage textMessage1 =(TextMessage) message;
//                try {
//                    System.out.println("监听器方式收到点对点消息:"+textMessage.getText());
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

         messageConsumer.close();
         session.close();
         connection.close();
    }
}
