package Messaging;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;


@Startup
@Singleton 
public class JMSClient {
    
    // Injecting the queue
    @Resource(mappedName = "java:/jms/queue/DLQ")
    private Queue myTrelloQueue;
    
    @Inject
    private JMSContext context;
    
    public void sendMessage(String msg) {
        try {
            JMSProducer producer = context.createProducer();
            // Using the injected queue
            producer.send(myTrelloQueue, msg);
            System.out.println("Message sent: " + msg);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
