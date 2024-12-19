import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataManager {
    private List<Object> processors = new ArrayList<>(); // список объектов-обработчиков (классы, содержащие методы с аннотацией
    private List<String> data = new ArrayList<>();
    public void registerDataProcessor(Object processor) {
        processors.add(processor);
    }
    public void loadData(String source) {
        File file = new File(source);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                data.add(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void processData() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<Void>> futures = new ArrayList<>(); // отслеживаем выполнение многопоточных задач
        for (Object processor : processors) {
            for (Method method : processor.getClass().getMethods()) {
                if (method.isAnnotationPresent(Main.DataProcessor.class)) {
                    futures.add(executor.submit(() -> { // отправляет задачу на выполнение (сама задача - лямбда выражение)
                        try {
                            method.invoke(processor, data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }));
                }
            }
        }
        for (Future<Void> future : futures) {
            future.get();
        }
        executor.shutdown();
    }

    public void saveData(String destination) {
        try {
            FileWriter writer = new FileWriter(destination);
            writer.write(String.valueOf(data));
            writer.close();
            System.out.println(String.valueOf(data) +  " - были сохранены в " + destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
