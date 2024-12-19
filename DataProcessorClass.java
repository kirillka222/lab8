import java.util.List;
import java.util.stream.Collectors;

public class DataProcessorClass {

    @Main.DataProcessor(value = "Фильтрация данных")
    public void filterData(List<String> data) {
        data.removeIf(item -> item.length() < 5); // Удаляем строки длиной меньше 5
        System.out.println("После фильтрации: " + data);
    }

    @Main.DataProcessor(value = "Трансформация данных")
    public void transformData(List<String> data) {
        List<String> transformed = data.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        System.out.println("После трансформации: " + transformed);
        data.clear();
        data.addAll(transformed);
    }

    @Main.DataProcessor(value = "Агрегация данных")
    public void aggregateData(List<String> data) {
        String aggregated = data.stream().collect(Collectors.joining(", "));
        System.out.println("Агрегированные данные: " + aggregated);
    }

    @Main.DataProcessor(value = "Сортировка данных")
    public void sortData(List<String> data) {
        String sorted = String.valueOf(data.stream().sorted().collect(Collectors.toList()));
        System.out.println("Отсортированные данные: " + sorted);
    }
}
// data.stream создает поток из элементов списка дата