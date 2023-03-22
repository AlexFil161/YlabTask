package ThirdLesson.DatedMap;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class DatedMapImpl implements DatedMap {
    private final HashMap<String, String> map;
    private final HashMap<String, Date> insertTimes;

    public DatedMapImpl() {
        this.map = new HashMap<>();
        this.insertTimes = new HashMap<>();
    }

    @Override
    public void put(String key, String value) {
        map.put(key, value);
        insertTimes.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
        insertTimes.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return insertTimes.get(key);
    }
}
