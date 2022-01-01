import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class CSVtoARFF {
    public static String ConvertToArff(String dataPath) throws IOException {

        String sourcePath = dataPath;
        String destPath = dataPath.replace(".csv", ".arff");
        CSVLoader loader = new CSVLoader();

        loader.setSource(new File(sourcePath));
        Instances dataSet = loader.getDataSet();

        BufferedWriter writer = new BufferedWriter(new FileWriter(destPath));
        writer.write(dataSet.toString());
        writer.flush();
        writer.close();
        return destPath;
    }
}
