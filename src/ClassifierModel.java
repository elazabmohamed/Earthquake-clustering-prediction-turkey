import java.io.File;
import java.util.ArrayList;
import java.util.List;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.classifiers.Evaluation;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;


public class ClassifierModel {
	static ArffLoader loader;

	public void FayHatli(String DatasetPath) throws Exception{
		process(getDataset(DatasetPath));
	}
	
	public void FayHatsiz(String DatasetPath) throws Exception{
		process(getDataset(DatasetPath));
	}


   private List<Instances> getDataset(String DatasetPath) throws Exception{
	loader = new ArffLoader();
	Instances MainDT;
	Instances NumericMainDT;
	Instances TestDT;
	Instances TrainDT;

	String Dataset = new File(CSVtoARFF.ConvertToArff(DatasetPath)).getAbsolutePath();
	System.out.println(Dataset);
	loader.setSource(new File(Dataset));
	NumericMainDT= loader.getDataSet();

	NumericToNominal convert= new NumericToNominal();
	
	convert.setInputFormat(NumericMainDT);
	MainDT = Filter.useFilter(NumericMainDT, convert);


	int trainSize = (int) Math.round(MainDT.numInstances() * 0.8);
	int testSize = MainDT.numInstances() - trainSize;
	TrainDT = new Instances(MainDT, 0, trainSize);
	TrainDT.setClassIndex(MainDT.numAttributes() - 1);
	TestDT = new Instances(MainDT, trainSize, testSize);
	TestDT.setClassIndex(MainDT.numAttributes() - 1);

	List<Instances> instances = new ArrayList<Instances>();
	instances.add(TrainDT);
	instances.add(TestDT);
	return instances;

   }

   private  void process(List<Instances> instances) throws Exception {
    Classifier classifier = new J48();
	classifier.buildClassifier(instances.get(0));
	Evaluation eval = new Evaluation(instances.get(0));
	//eval.crossValidateModel(classifier, Trainingdt, 10, new Random(1));
	eval.evaluateModel(classifier, instances.get(1));

	System.out.println("\tDecision Tree Evaluation ");
	System.out.println(eval.toSummaryString());
	System.out.print(" the expression for the input data as per alogorithm is ");
	System.out.println(classifier);
	System.out.println(eval.toMatrixString());
	System.out.println(eval.toClassDetailsString());

	/*
	ArrayList<Prediction> predictions = eval.predictions();
	for (int i = 0, testSize = TestDT.size(); i < testSize-1; i++) {
        Instance instance = TestDT.get(i);
        Prediction prediction = predictions.get(i);

        if (prediction.actual() != prediction.predicted()) {

            System.out.println(instance);;

        }

    }

	System.out.println("***************************************");
	for (int i = 0, trainSize = TrainDT.size(); i < 300; i++) {
        Instance instance = TrainDT.get(i);
        Prediction prediction = predictions.get(i);

        if (prediction.actual() != prediction.predicted()) {

            System.out.println(instance);;

        }
	}
	*/
   }

   





}
