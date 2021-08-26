package com.animesafar.dinterviewkit.langiden;

import android.annotation.SuppressLint;
import android.media.Image;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;

import org.jetbrains.annotations.NotNull;

public class Textanalyzer implements ImageAnalysis.Analyzer {
TextView textView;
    Textanalyzer(TextView textView){ this.textView = textView;}

    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void analyze(@NonNull @NotNull ImageProxy image) {

        Image image1 = image.getImage();

        InputImage inputImage = InputImage.fromMediaImage(image1,image.getImageInfo().getRotationDegrees());

        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

recognizer.process(inputImage)
        .addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(@NonNull @NotNull Text text) {

                textView.setText(text.getText());

                image.close();
            }
        }).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull @NotNull Exception e) {
image.close();
    }
});

    }
}
