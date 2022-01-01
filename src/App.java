public class App {

    public static void main(String[] args) throws Exception {
        ClassifierModel classifierModel = new ClassifierModel();
        System.out.println("\n\n\n\n\n\n\n\n");
        System.out.println("Fay hatli:\n");
        classifierModel.FayHatli("src/Dataset/Classification/fayhatsiz.csv");
        System.out.println("Fay hatsiz:\n");
        classifierModel.FayHatsiz("src/Dataset/Classification/fayhatli.csv");

        ClusteringModel clusteringModel = new ClusteringModel();
        System.out.println("Clustering model:\n");
        clusteringModel.Clustering("src/Dataset/Clustering/Non_Clustered_Data.csv");
    }

}
