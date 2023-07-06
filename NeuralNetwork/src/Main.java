import java.io.File;
import java.io.IOException;
import java.util.*;


public class Main {
	public static void main(String[] args) {
		String trainingDirectory = "";
		String testDirectory = "";
		double alpha = 1; // default value
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
				case "-dir" -> trainingDirectory = args[++i];
				case "-alpha" -> alpha = Double.parseDouble(args[++i]);
				case "-test" -> testDirectory = args[++i];
			}
		}


		TrainData trainData = new TrainData(trainingDirectory);
		Network network = new Network(trainData,alpha);

		if(!testDirectory.isEmpty()){
			try {
				test(network,testDirectory);
			} catch (IOException e) {
				System.out.println("An error has occured");
				e.printStackTrace();
			}
			return;
		}

		Scanner scanner = new Scanner(System.in);
		System.out.println("Path to test file: ");
		for(String line; !(line=scanner.nextLine()).equals("q");){
			File file = new File(line);
			try {
				var result =network.query(file);
				System.out.println("result = " + result);
			} catch (IOException e) {
				System.out.println("Zły plik");
			}
		}

	}

	public static void test(Network network,String directory) throws IOException {
		File dir = new File(directory);
		if(!dir.isDirectory()){
			System.out.println("Not a directory");
			return;
		}
		double correct = 0;
		double wrong = 0;
		for(File lang : Objects.requireNonNull(dir.listFiles())){
			String verdict= lang.getName();
			for(File text : Objects.requireNonNull(lang.listFiles())){
				String answer = network.query(text);
				if(verdict.equals(answer)){
					correct++;
				}else{
					wrong++;
				}
			}

		}
		System.out.println("Accuracy: "+(correct/(correct+wrong)));
	}

}
