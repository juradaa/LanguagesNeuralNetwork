import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Perceptron {

	private List<Double> weights;
	private final double learningParam;

	public String getVerdict() {
		return verdict;
	}

	private final String verdict;
	private final static int MAX_TRIES = 290;
	public Perceptron(List<InVector> trainingData, double learningParam, String verdict) {
		this.verdict = verdict;
		this.learningParam = learningParam;
		prepareRandomWeights(trainingData);
		double accuracy =0.0;
		int tries = 0;
		do{
			boolean succeeded;
			double all=0;
			double successful = 0;
			Collections.shuffle(trainingData);
			for (InVector trainingDatum : trainingData) {
				 succeeded= train(trainingDatum);
				 if(succeeded){
					 successful++;
				 }
				 all++;
				 accuracy=successful/all;
			}
			tries++;
		}while(accuracy<1 && tries < MAX_TRIES);
		weights = InVector.normalize(weights);
	}

	private void prepareRandomWeights(List<InVector> trainingData) {
		int length = trainingData.get(0).getValues().size();
		weights =  new ArrayList<>();
		for (int i = 0; i < length; i++) {
			weights.add(Math.random()*10-5);
		}
	}

	public int query(List<Double> request) {
		double net = rawQuery(request);
		return net > 0 ? 1 : 0;
	}

	public double rawQuery(List<Double> request) {
		double net = 0;
		for (int i = 0; i < weights.size(); i++) {
			net+= request.get(i)*weights.get(i);
		}
		return net;
	}

	private boolean train(InVector vector){
		List<Double> values = vector.getValues();
		int result = query(values);
		String expected = vector.getDecision();
		int correct = expected.equals(verdict) ? 1 : 0;


		for (int i = 0; i < weights.size(); i++) {
			double newWeight = weights.get(i) + (correct-result) * values.get(i)*learningParam;
			weights.set(i, newWeight);
		}
		return (correct-result==0);
	}
}
