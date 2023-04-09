package FifthLesson.io.ylab.intensive.lesson05.eventsourcing.api;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        // пишем взаимодействие с PersonApi
        personApi.savePerson(1L, "Emily", "Elizabeth", "Johnson");
        Thread.sleep(1000);
        personApi.deletePerson(1L);
        personApi.savePerson(2L, "Benjamin", "James", "Parker");
        personApi.savePerson(3L, "Samantha", "Grace", "Thompson");
        Thread.sleep(1000);
        personApi.savePerson(3L, "William", "Edward", "Robinson");
        System.out.println(personApi.findPerson(2L));
        System.out.println(personApi.findPerson(1L));
        personApi.savePerson(4L, "Victoria", "Louise", "Adams");
        Thread.sleep(1000);
        System.out.println(personApi.findAll());
    }
}

