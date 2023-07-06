# LanguagesNeuralNetwork

Single layer neural network (local architecture)

## Usage
The app requires training data folder that contains folders named after a language containing text files in that language.
Testing the app requires a test data folder with an identical structure.

Program arguments such as ``-dir "Training data directory" -test "Testing data directory"`` will calculate the accuracy of the network and display it to the user. The arguments ``-dir Languages -test tests`` will train and test the model using data provided in the repository.
To classify a text file use ``-dir "Training data directory"`` (omitting the test data) and provide the path to the file when asked.