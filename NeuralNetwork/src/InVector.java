import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class InVector {
	private final List<Double> values;
	private final String decision;

	private InVector(List<Double> values, String decision) {
		this.values = values;
		this.decision = decision;
	}

	public static InVector createVector(File file, String positive) throws IOException {
		List<Double> values = getValues(file);
		return new InVector(values, positive);

	}

	public static List<Double> getValues(File file) throws IOException {
		var characters = Files.lines(file.toPath())
				.map(String::toLowerCase)
				.map(InVector::convertToCharList)
				.flatMap(Collection::stream)
				.filter(c -> (c >= 'a') && (c <= 'z'))
				.toList();
		List<Double> values = new ArrayList<>();

		for (int i = 'a'; i <= 'z'; i++) {
			double value = Collections.frequency(characters, (char) i) / ((double) characters.size());
			values.add(value);
		}
		values.add(-1d);
		return values;
	}

	private static List<Character> convertToCharList(String line) {
		return line.chars()
				.mapToObj(c -> (char) c)
				.toList();
	}

	public static List<Double> normalize(List<Double> vec) {
		double sum = 0;
		for (var element : vec) {
			sum+=Math.pow(element,2);
		}
		double len= Math.sqrt(sum);
		vec=vec.stream().map(d->d/len).collect(Collectors.toList());
		return vec;
	}


	public String getDecision() {
		return decision;
	}

	public List<Double> getValues() {
		return values;
	}
}
