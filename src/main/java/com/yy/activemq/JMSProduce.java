package com.yy.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProduce {
    public static  final String MQ_URLS="tcp://192.168.100.188:61616";
    public static  final String MyQUEUE="avtyy0508";
    public static void main(String[] args) throws JMSException {
        //1 通过ConnectionFactory工厂
        ActiveMQConnectionFactory activeMQConnectionFactory= new ActiveMQConnectionFactory(MQ_URLS);

        //2 获得connection对象并启动
        Connection connection =activeMQConnectionFactory.createConnection();
        connection.start();
        //3 通过connection对象获得session对象
        // 第一个参数叫mq的事务/第二个参数叫消息的签收，此时忽略用默认
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        //4 通过session获得目的地
        Queue queue = session.createQueue(MyQUEUE);

        //5 通过session获得消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);


        //6 开始生产3条消息发送到Activemq上
        for (int i = 0; i <5 ; i++) {
            TextMessage textMessage = session.createTextMessage("act--!!:" + i);
            messageProducer.send(textMessage);
        }

        //8 释放资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("发送MQ成功");


    }
}
