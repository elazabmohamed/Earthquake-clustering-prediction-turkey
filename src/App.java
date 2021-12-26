import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ArffLoader;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.clusterers.SimpleKMeans;
public class App {
    static List<String[]> dataLines = new ArrayList<>();
    static int ClusterNum = 3;
    public static void main(String[] args) throws Exception {
        //  Read the data from the arff data file
        ArffLoader loader= new ArffLoader();
        String Dataset_Arff_path = new File("src/Dataset/Dataset_numeric_2.arff") .getAbsolutePath();
        System.out.println(Dataset_Arff_path);
        loader.setSource(new File(Dataset_Arff_path));
        Instances dt= loader.getDataSet();

        // Prepare the csv file for the data to be printed
        String Dataset_Output_CSV_path = new File("src/Dataset/Dataset_output.csv") .getAbsolutePath();
        PrintWriter pw= new PrintWriter(new File(Dataset_Output_CSV_path));
        StringBuilder sb=new StringBuilder();
        sb.append("Enlem");
        sb.append(",");
        sb.append("Boylam");
        sb.append(",");
        sb.append("Cluster");
        sb.append("\r\n");

        // Create a new KMeans instance
        SimpleKMeans skm = new SimpleKMeans();
        System.out.printf("%d clusters:%n", ClusterNum);
        try {
            // Specify the amount of clusters and building the cluster
            skm.setNumClusters(ClusterNum);
            skm.setSeed(10);
            //skm.setDontReplaceMissingValues(false);
   
            // Divide dataset into training and test data //

            //skm.setCanopyPeriodicPruningRate(100);
            //skm.setCanopyT1(0.5);
            //skm.setCanopyT2(1.0);
            //skm.setInitializationMethod(new SelectedTag(SimpleKMeans.FARTHEST_FIRST,SimpleKMeans.TAGS_SELECTION));
            skm.buildClusterer(dt);
            //skm.setMaxIterations(10); 
            for (Instance instance : dt) {

                System.out.printf("(%.4f, %.4f): %s%n", instance.value(0), instance.value(1), skm.clusterInstance(instance));
                sb.append(String.valueOf(instance.value(0)));
                sb.append(",");
                sb.append(String.valueOf(instance.value(1)));
                sb.append(",");
                sb.append(String.valueOf(skm.clusterInstance(instance)));
                sb.append("\r\n");

                dataLines.add(new String[]{String.valueOf(instance.value(0)), String.valueOf(instance.value(1)), 
                    String.valueOf(skm.clusterInstance(instance))});
            }

        pw.write(sb.toString());
        pw.close();
        System.out.println(skm.getSquaredError());

    
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
