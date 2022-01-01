import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class ClusteringModel {
    static List<String[]> dataLines = new ArrayList<>();
    //static int ClusterNum = 3;
    static ArffLoader loader;
    static String MainDTPath;
    static String EvalFileName;


    public void Clustering(String DatasetPath, int ClusterNum) throws Exception{
        File f = new File(DatasetPath);
		EvalFileName = f.getName().toString().replace(".csv", "");
        process(getDataset(DatasetPath), ClusterNum);
        
    }
    private Instances getDataset(String DatasetPath) throws IOException{
        
        //  Read the data from the arff data file
        loader = new ArffLoader();
        MainDTPath = new File(CSVtoARFF.ConvertToArff(DatasetPath)).getAbsolutePath();
        System.out.println(MainDTPath);
        loader.setSource(new File(MainDTPath));
        Instances MainDT = loader.getDataSet();
        return MainDT;

    }
    private void process(Instances MainDT, int ClusterNum) throws IOException{
        
        // Create a new KMeans instance
        SimpleKMeans skm = new SimpleKMeans();
        System.out.printf("%d Clusters\n", ClusterNum);
        try {
            // Specify the amount of clusters and building the cluster
            skm.setNumClusters(ClusterNum);
            skm.setSeed(10);
            skm.buildClusterer(MainDT);
            skm.setMaxIterations(10); 

            OutputToCSV(MainDT, skm, MainDTPath);

            System.out.println("Squared Error: " + skm.getSquaredError());
            
            PrintWriter out = new PrintWriter("Evaluation_Clustering_"+EvalFileName+".txt");
            out.println("Squared Error: " + skm.getSquaredError());
            out.println("Centroids: " + skm.getClusterCentroids());
            out.close();
        } catch (Exception e) {
            System.err.println(e);
        }
 
    }

    private void OutputToCSV(Instances MainDT, SimpleKMeans skm, String MainDTPath) throws Exception {
       //System.out.println(MainTD (1));
        String MainDTCSVPath = MainDTPath.replaceAll(".arff", "_Output.csv");
        PrintWriter pw;
        StringBuilder sb;

        pw = new PrintWriter(new File(MainDTCSVPath));
            sb=new StringBuilder();
            sb.append("Enlem");
            sb.append(",");
            sb.append("Boylam");
            sb.append(",");
            sb.append("Cluster");
            sb.append("\r\n");
    
        for (Instance instance : MainDT) {
            //System.out.printf("(%.4f, %.4f): %s%n", instance.value(0), instance.value(1), skm.clusterInstance(instance));
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
    }
    
}
