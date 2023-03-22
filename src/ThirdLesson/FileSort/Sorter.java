package ThirdLesson.FileSort;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sorter {
    private static final int BUFFER_SIZE = 1024;
    public File sortFile(File dataFile) throws IOException {
        List<File> sortedFiles = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile))) {
            List<Long> elements = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                elements.add(Long.parseLong(line));
                if (elements.size() == BUFFER_SIZE) {
                    sortedFiles.add(sortElements(elements));
                    elements.clear();
                }
            }
            if (!elements.isEmpty()) {
                sortedFiles.add(sortElements(elements));
            }
        }

        File outputFile = new File(dataFile.getParent(), "sorted_" + dataFile.getName());
        if (mergeFiles(sortedFiles).renameTo(outputFile)) {
            return outputFile;
        }

        return null;
    }

    private File sortElements(List<Long> elements) throws IOException {
        Collections.sort(elements);
        return getFile(elements);
    }

    private File mergeFiles(List<File> sortedFiles) throws IOException {
        while (sortedFiles.size() > 1) {
            List<File> mergedFiles = new ArrayList<>();
            for (int i = 0; i < sortedFiles.size(); i += 2) {
                if (i == sortedFiles.size() - 1) {
                    mergedFiles.add(sortedFiles.get(i));
                } else {
                    File mergedFile = mergeSortedElements(sortedFiles.get(i), sortedFiles.get(i + 1));
                    mergedFiles.add(mergedFile);
                }
            }
            sortedFiles = mergedFiles;
        }
        return sortedFiles.get(0);
    }

    private File mergeSortedElements(File fileOne, File fileTwo) throws IOException {
        List<Long> mergedElements = new ArrayList<>();
        try (BufferedReader bufferedReaderOne = new BufferedReader(new FileReader(fileOne));
             BufferedReader bufferedReaderTwo = new BufferedReader(new FileReader(fileTwo))) {
            String lineOne = bufferedReaderOne.readLine();
            String lineTwo = bufferedReaderTwo.readLine();
            while (lineOne != null && lineTwo != null) {
                Long numOne = Long.parseLong(lineOne);
                Long numTwo = Long.parseLong(lineTwo);
                if (numOne < numTwo) {
                    mergedElements.add(numOne);
                    lineOne = bufferedReaderOne.readLine();
                } else {
                    mergedElements.add(numTwo);
                    lineTwo = bufferedReaderTwo.readLine();
                }
            }
            while (lineOne != null) {
                mergedElements.add(Long.parseLong(lineOne));
                lineOne = bufferedReaderOne.readLine();
            }
            while (lineTwo != null) {
                mergedElements.add(Long.parseLong(lineTwo));
                lineTwo = bufferedReaderTwo.readLine();
            }
        }
        return getFile(mergedElements);
    }

    private File getFile(List<Long> mergedElements) throws IOException {
        File mergedFile = File.createTempFile("sorter", ".tmp");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(mergedFile))) {
            for (Long num : mergedElements) {
                bufferedWriter.write(String.valueOf(num));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            mergedFile.deleteOnExit();
        }
        return mergedFile;
    }
}
