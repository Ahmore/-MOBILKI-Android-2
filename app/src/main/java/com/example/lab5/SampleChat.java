package com.example.lab5;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SampleChat {

    //UWAGA! zakladamy, ze TOPIC = NICK = clientId
    String topic;
    int qos = 0;
    String broker;
    String clientId;
    String ip;
    MemoryPersistence persistence = new MemoryPersistence();

    MqttClient sampleClient = null;


    public void startMQTT() {

        try {

            Scanner s = new Scanner(System.in);

            System.out.println("Podaj adres IP brokera");
            ip = s.nextLine();

            System.out.println("Podaj NICK");
            clientId = s.nextLine();

            broker = "tcp://" + ip + ":1883";
            topic = clientId;

            sampleClient = new MqttClient(broker, clientId, persistence);
            sampleClient.setCallback(new SampleChatCallback());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);

            //zapisujemy sie na wszystkie rozmowy
            System.out.println("Connected");
            sampleClient.subscribe("#");
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void chat_loop() throws MqttPersistenceException, MqttException {
        Scanner s = new Scanner(System.in);

        String line;

        System.out.println("napisz wiadomosc - pusta linia konczy rozmowe");
        while (true) {
            line = s.nextLine();
            if (line.length() == 0) break;

            MqttMessage message = new MqttMessage(line.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
        }


        System.out.println("KONIEC");
        //disconnect
        sampleClient.disconnect();
    }

    /**
     * @param args
     * @throws MqttException
     * @throws MqttPersistenceException
     */
    public static void main(String[] args) throws MqttPersistenceException, MqttException {
        SampleChat chat = new SampleChat();

        chat.startMQTT();

        chat.chat_loop();

    }

}
