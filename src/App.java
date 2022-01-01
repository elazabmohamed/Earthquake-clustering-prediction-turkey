public class App {

    public static void main(String[] args) throws Exception {
        ClassifierModel WfaultLine = new ClassifierModel();
        System.out.println("\n\n\n\n\n\n\n\n");
        System.out.println("Fay hatli:\n");
        WfaultLine.Classifier("src/Dataset/Classification/fayhatsiz.csv");

        ClassifierModel WOfaultLine = new ClassifierModel();
        System.out.println("Fay hatsiz:\n");
        WOfaultLine.Classifier("src/Dataset/Classification/fayhatli.csv");

        ClusteringModel clusteringModel = new ClusteringModel();
        System.out.println("Clustering model:\n");
        clusteringModel.Clustering("src/Dataset/Clustering/Non_Clustered_Data.csv", 3);
    }

}
