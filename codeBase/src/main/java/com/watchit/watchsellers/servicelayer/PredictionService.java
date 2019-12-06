package com.watchit.watchsellers.servicelayer;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.core.GoogleCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.automl.v1beta1.*;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.watchit.watchsellers.configs.ConfigReader;
import com.watchit.watchsellers.configs.Constants;
import com.watchit.watchsellers.dtos.WatchDto;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PredictionService {


    @Autowired
    WatchService watchService;

    /**
     * Demonstrates using the AutoML client to predict an image.
     *
     * @param projectId      the Id of the project.
     * @param computeRegion  the Region name.
     * @param modelId        the Id of the model which will be used for text classification.
     * @param filePath       the Local text file path of the content to be classified.
     * @param scoreThreshold the Confidence score. Only classifications with confidence score above
     *                       scoreThreshold are displayed.
     */
    public PredictResponse predict(
            String projectId,
            String computeRegion,
            String modelId,
            String filePath,
            String scoreThreshold) {


        GoogleCredentials credentials = null;
        PredictionServiceSettings predictionServiceSettings = null;
        try {
            credentials = GoogleCredentials.fromStream(new FileInputStream(ConfigReader.CREDENTIAL_PATH))
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));


            predictionServiceSettings =
                    PredictionServiceSettings.newBuilder()
                            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                            .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Instantiate client for prediction service.

        try (PredictionServiceClient predictionClient = PredictionServiceClient.create(predictionServiceSettings)) {

            // Get the full path of the model.
            ModelName name = ModelName.of(projectId, computeRegion, modelId);

            // Read the image and assign to payload.
            ByteString content = ByteString.copyFrom(Files.readAllBytes(Paths.get(filePath)));
            Image image = Image.newBuilder().setImageBytes(content).build();
            ExamplePayload examplePayload = ExamplePayload.newBuilder().setImage(image).build();

            // Additional parameters that can be provided for prediction e.g. Score Threshold
            Map<String, String> params = new HashMap<>();
            if (scoreThreshold != null) {
                params.put("score_threshold", scoreThreshold);
            }
            // Perform the AutoML Prediction request
            PredictResponse response = predictionClient.predict(name, examplePayload, params);


            System.out.println("Prediction results:");
            int i = 0;
            for (AnnotationPayload annotationPayload : response.getPayloadList()) {
                System.out.println("Predicted class name : " + i++ + " : " + annotationPayload.getDisplayName());
                System.out.println(
                        "Predicted class score :" + annotationPayload.getClassification().getScore());
            }

            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }


    public List<WatchDto> similarWatches(PredictResponse predictedResponse) {

        WatchDto predictedWatch = extractPredictedDetails(predictedResponse);


        return watchService.findSimilarWatches(predictedWatch);

    }

    /**
     * Constructs the watch object from the predicted response
     *
     * @param predictedResponse The response from AUTOML
     * @return {@link WatchDto}
     */
    private WatchDto extractPredictedDetails(PredictResponse predictedResponse) {

        WatchDto watchDto = new WatchDto();

        //findAllByCaseShapeOrCaseBackOrDialIndexesOrDialColorOrMovementTime


        watchDto.setCaseShape(predictedResponse.getPayload(3).getDisplayName());

        watchDto.setDialColor(predictedResponse.getPayload(11).getDisplayName().split("-")[1].trim());

        watchDto.setDialIndexes(predictedResponse.getPayload(5).getDisplayName());

        watchDto.setDialHands(predictedResponse.getPayload(6).getDisplayName().split("-")[1].trim());

        watchDto.setMovementTime(predictedResponse.getPayload(4).getDisplayName());

        watchDto.setCaseMaterial(predictedResponse.getPayload(6).getDisplayName().split("-")[1].trim());

        return watchDto;
    }
}
