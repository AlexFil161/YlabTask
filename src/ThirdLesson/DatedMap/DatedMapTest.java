package ThirdLesson.DatedMap;

public class DatedMapTest {
    public static void main(String[] args) throws InterruptedException {
        DatedMapImpl datedMap = new DatedMapImpl();
        datedMap.put("KeyOne", "ValueOne");
        Thread.sleep(1000);
        datedMap.put("KeyTwo", "ValueTwo");
        Thread.sleep(1000);
        datedMap.put("KeyThree", "ValueThree");
        Thread.sleep(1000);
        datedMap.put("KeyFour", "ValueFour");

        System.out.println(datedMap.getKeyLastInsertionDate("KeyOne"));
        System.out.println(datedMap.getKeyLastInsertionDate("KeyTwo"));
        System.out.println(datedMap.getKeyLastInsertionDate("KeyThree"));
        System.out.println(datedMap.getKeyLastInsertionDate("KeyFour"));

    }
}