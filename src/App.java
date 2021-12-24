import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.clusterers.SimpleKMeans;
public class App {
    static List<String[]> dataLines = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        //  Read the data from the arff data file
        ArffLoader loader= new ArffLoader();
        String Dataset_Arff_path = new File("src/Dataset/afad_deprem_listesi_arff_two.arff") .getAbsolutePath();
        System.out.println(Dataset_Arff_path);
        loader.setSource(new File(Dataset_Arff_path));
        Instances dt= loader.getDataSet();

        // Prepare the csv file for the data to be printed
        String Dataset_Output_CSV_path = new File("src/Dataset/Dataset.csv") .getAbsolutePath();
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
        System.out.printf("%d clusters:%n", 3);
        try {
            // Specify the amount of clusters and building the cluster
            skm.setNumClusters(3);
            skm.buildClusterer(dt);
            for (Instance instance : dt) {

                System.out.printf("(%.4f, %.4f): %s%n", instance.value(4), instance.value(4), skm.clusterInstance(instance));
                sb.append(String.valueOf(instance.value(3)));
                sb.append(",");
                sb.append(String.valueOf(instance.value(4)));
                sb.append(",");
                sb.append(String.valueOf(skm.clusterInstance(instance)));
                sb.append("\r\n");

                dataLines.add(new String[]{String.valueOf(instance.value(3)), String.valueOf(instance.value(4)), 
                    String.valueOf(skm.clusterInstance(instance))});
            }
            
        pw.write(sb.toString());
        pw.close();
    
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
