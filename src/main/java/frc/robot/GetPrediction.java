package frc.robot;

import java.io.IOException;

import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;

public class GetPrediction {

    public static double getRPM(double[] pos)
            throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException {

        // load the model
        String simpleMlp = new ClassPathResource("RPM_model.h5").getFile().getPath();
        MultiLayerNetwork model = KerasModelImport.importKerasSequentialModelAndWeights(simpleMlp);

        // make a random sample
        int inputs = 2;
        INDArray features = Nd4j.zeros(inputs);
        for (int i = 0; i < inputs; i++) {
            features.putScalar(new int[] { i }, pos[i]);
        }
        // get the prediction
        double prediction = model.output(features).getDouble(0);

        return prediction;

    }

}
