public class MainApp {
    public static void main(String[] args) throws Exception {
        DataManager dataManager = new DataManager();

        dataManager.registerDataProcessor(new DataProcessorClass());

        dataManager.loadData("/Users/kirill/Downloads/lab8/data.txt");

        dataManager.processData();
        dataManager.saveData("Example.txt");
    }
}
