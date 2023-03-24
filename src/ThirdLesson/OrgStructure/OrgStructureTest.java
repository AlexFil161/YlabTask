package ThirdLesson.OrgStructure;

import java.io.File;
import java.io.IOException;

public class OrgStructureTest {
    public static void main(String[] args) throws IOException {
        OrgStructureParserImpl orgStructureParser = new OrgStructureParserImpl();
        Employee bossResult = orgStructureParser.parseStructure(new File
                ("C:\\Users\\USER\\IdeaProjects\\YlabTask\\CSV.txt"));
        System.out.println(bossResult.getPosition());
    }
}
