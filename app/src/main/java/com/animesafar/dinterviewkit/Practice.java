package com.animesafar.dinterviewkit;

public class Practice {


    public static void main(String[] isha){

        String text = "Hello Isha";

         for(int i=0;i<text.length();i++){

             char ch = text.charAt(i);

              if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'){
                  System.out.println("Found a vowel "+ch  +" at index "+i);
              }

         }

    }

}
