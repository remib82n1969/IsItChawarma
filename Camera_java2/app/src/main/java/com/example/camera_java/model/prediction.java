package com.example.camera_java.model;

import com.google.ar.core.Session;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.util.Arrays;
/* imported in build.gradle but not working :
dependencies{
    implementation 'org.tensorflow:tensorflow:2.8.0'
    implementation 'org.tensorflow:tensorflow-core:2.8.0'
    implementation 'org.tensorflow:tensorflow-hub:0.9.0'
}
* */
public class Model {
    public static void predict(String[] args) {
        // Load the model
        SavedModelBundle model = SavedModelBundle.load("<model_folder_path>", "serve");

        // Create a session to run the model
        Session session = model.session();

        // Prepare input data (this depends on your model)
        float[][] inputData = {{0.1f, 0.2f, 0.3f}, {0.4f, 0.5f, 0.6f}};
        Tensor inputTensor = Tensor.create(inputData);

        // Run the model
        Tensor outputTensor = session.runner()
                .feed("<input_tensor_name>", inputTensor)
                .fetch("<output_tensor_name>")
                .run()
                .get(0);

        // Get the output data (this depends on your model)
        float[][] outputData = new float[2][3];
        outputTensor.copyTo(outputData);

        // Use the output data in your application
        System.out.println(Arrays.deepToString(outputData));

        // Close the session and model
        outputTensor.close();
        inputTensor.close();
        session.close();
        model.close();
    }
}
