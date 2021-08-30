package com.animesafar.dinterviewkit.datapackage;

import java.util.Scanner;

public class Sample {

    public static  void main(String ...arg){

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = sc.nextInt();
        }
        int counter = 0;
          for(int i=0;i<n;i++){
              int sum  = 0;
              for(int j=i;j<n;j++){

                  sum+=arr[j];
                  if(sum == 0){
                      counter++;
                  }
              }

          }
          if(counter > 0){

              System.out.println(1);

          }else{

              System.out.println(0);

          }
    }


}
