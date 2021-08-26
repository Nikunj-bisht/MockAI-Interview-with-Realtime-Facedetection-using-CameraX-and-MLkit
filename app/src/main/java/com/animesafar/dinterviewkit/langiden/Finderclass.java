package com.animesafar.dinterviewkit.langiden;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;

import org.jetbrains.annotations.NotNull;

public class Finderclass {


    public interface helper {}

    static  void forcheck(helper h , EditText textView , TextView textView2){



        LanguageIdentifier languageIdentifier = LanguageIdentification.getClient();

        languageIdentifier.identifyLanguage(textView.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        textView2.setText(s);
//                        Toast.makeText(h,s,Toast.LENGTH_LONG)
//                                .show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

                Log.d("fa----->>",e.toString());


            }
        });


    }

}