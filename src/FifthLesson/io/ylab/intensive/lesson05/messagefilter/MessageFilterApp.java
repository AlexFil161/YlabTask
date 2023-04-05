package FifthLesson.io.ylab.intensive.lesson05.messagefilter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;

public class MessageFilterApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();

        MessageProcessor messageProcessor = applicationContext.getBean(MessageProcessor.class);
        messageProcessor.loadData(new File("C:\\Users\\USER\\IdeaProjects\\YlabTask\\badWords.txt"));
        messageProcessor.messageHandler();
    }
}
