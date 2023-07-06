import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrainData {
	List<InVector> vecs;
	List<String> verdicts;
	public TrainData(String dirPath) {
		vecs = new ArrayList<>();
		verdicts = new ArrayList<>();
		List<File> langDirs = getCountryDirectories(dirPath);
		for (var langDir : langDirs) {
			verdicts.add(langDir.getName());
			readDir(langDir);
		}
	}

	private List<File> getCountryDirectories(String dirPath) {
		File mainDir = new File(dirPath);
		if (!mainDir.isDirectory()) {
			throw new IllegalArgumentException("dirPath must be a path to a directory");
		}
		return List.of(Objects.requireNonNull(mainDir.listFiles()));
	}

	private void readDir(File langDir) {

		for (var file : Objects.requireNonNull(langDir.listFiles())) {
			try {
				vecs.add(InVector.createVector(file, langDir.getName()));
			} catch (IOException e) {
				System.out.println("file " + file.getName() + " could not be read from");
			}
		}
	}

	public List<InVector> getVecs() {
		return vecs;
	}

	public List<String> getVerdicts() {
		return verdicts;
	}
}
