import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Evaluation;
import weka.clusterers.Canopy;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class ClusteringModel {
    static List<String[]> dataLines = new ArrayList<>();
    static ArffLoader loader;
    static String MainDTPath;
    static String EvalFileName;


    public void Clustering(String DatasetPath, int ClusterNum) throws Exception{
        File f = new File(DatasetPath);
		EvalFileName = f.getName().toString().replace(".csv", "");
        processCanopy(getDataset(DatasetPath), ClusterNum);
        
    }
    private Instances getDataset(String DatasetPath) throws IOException{
        
        //  Read the data from the arff data file
        loader = new ArffLoader();
        MainDTPath = new File(CSVtoARFF.ConvertToArff(DatasetPath)).getAbsolutePath();
        System.out.println("Selected path for dataset is: "+MainDTPath);
        loader.setSource(new File(MainDTPath));
        Instances MainDT = loader.getDataSet();
        return MainDT;

    }

    private void processCanopy(Instances MainDT, int ClusterNum) throws Exception{

        Canopy canopy = new Canopy();
        canopy.setNumClusters(ClusterNum);
        System.out.printf("%d Clusters: \n", ClusterNum);
        canopy.setSeed(1);
        canopy.setT1(-1.25);
        canopy.setT2(-1.0);
        canopy.setMinimumCanopyDensity(2.0);
        canopy.setMaxNumCandidateCanopiesToHoldInMemory(100);
        canopy.buildClusterer(MainDT);
        canopy.setPeriodicPruningRate(10000);
        canopy.setDebug(false);
        canopy.setDoNotCheckCapabilities(false);
        canopy.setDontReplaceMissingValues(false);

        OutputToCSV(MainDT, canopy, MainDTPath); 
    }
    
    private void OutputToCSV(Instances MainDT, Canopy canopy, String MainDTPath) throws Exception {
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
            sb.append(String.valueOf(instance.value(0)));
            sb.append(",");
            sb.append(String.valueOf(instance.value(1)));
            sb.append(",");
            sb.append(String.valueOf(canopy.clusterInstance(instance)));
            sb.append("\r\n");
        
            dataLines.add(new String[]{String.valueOf(instance.value(0)), String.valueOf(instance.value(1)), 
                String.valueOf(canopy.clusterInstance(instance))});
            }
        pw.write(sb.toString());
        pw.close();
    }
}
