package ThirdLesson.OrgStructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OrgStructureParserImpl implements OrgStructureParser {
    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        Map<Long, Employee> employees = new HashMap<>();
        try (FileReader fileInput = new FileReader(csvFile);
             BufferedReader bufferedReader = new BufferedReader(fileInput)) {
            bufferedReader.readLine();
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                String[] lineInfo = line.split(";");

                Employee employee = createEmployee(lineInfo, employees);
                employees.put(employee.getId(), employee);
            }

            for (Employee employee : employees.values()) {
                if (employee.getBoss() == null) {
                    return employee;
                }
            }
        }
        return null;
    }

    private Employee createEmployee(String[] lineInfo, Map<Long, Employee> employees) {
        Employee employee = new Employee();
        employee.setId(Long.parseLong(lineInfo[0]));
        if (lineInfo[1].equals("")) {
            employee.setBossId(null);
        } else {
            employee.setBossId(Long.parseLong(lineInfo[1]));
        }
        employee.setName(lineInfo[2]);
        employee.setPosition(lineInfo[3]);

        for (Employee worker : employees.values()) {
            Long bossId = worker.getBossId();
            if (bossId != null) {
                Employee boss = employees.get(bossId);
                worker.setBoss(boss);
                boss.getSubordinate().add(worker);
            }
        }
        return employee;
    }
}
