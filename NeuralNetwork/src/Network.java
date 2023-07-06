import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Network {
	private final List<Perceptron> perceptrons;



	public Network(TrainData trainData, double learningParam){
		List<InVector> trainingVecs = trainData.getVecs();
		List<String> verdicts = trainData.getVerdicts();
		perceptrons = new ArrayList<>();
		for(String verdict : verdicts){
			Perceptron perceptron = new Perceptron(trainingVecs,learningParam,verdict);
			perceptrons.add(perceptron);
		}
	}

	public String query(File file) throws IOException {
		List<Double> preValues = InVector.getValues(file);
		List<Double> values= InVector.normalize(preValues);

		var result = perceptrons.stream()
//				.peek(perceptron->System.out.println(perceptron.rawQuery(values)+" : "+perceptron.getVerdict()))
				.map(perceptron ->new Pair<Double,String>( perceptron.rawQuery(values),perceptron.getVerdict()))
				.max(Comparator.comparingDouble(Pair::getFirst))
				.orElseThrow();
		return result.getSecond();
	}

}
