package com.sandpeople.starwarsml;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Template class to interact with AWS.
 */
public class Client {
   private static final String getTweetsEndpoint = "https://q6yxb3mll8.execute-api.us-east-1.amazonaws.com/live/handle";
   private static Gson gson = new Gson();
   private static OkHttpClient httpClient = new OkHttpClient();


   private static boolean isTwitterHandleValid(String handle){



      return true;
   }

   public static JsonObject execute(String handle){
      if(isTwitterHandleValid(handle)) {
         JsonObject tweets = getTweets(handle);
      }

      return failedHandleValidation(handle);
   }

   private static JsonObject getTweets(String handle){

      try {

         Request request = buildGetTweetsRequest(handle);
         Response response = exectuteRequest(request);
         if (response == null) return failureToGetTweets(handle);
         System.out.println(response.body().string());

      }
      catch (Exception e){}

      return null;
   }

   private static JsonObject failureToGetTweets(String handle){
      JsonObject failureJson = new JsonObject();
      failureJson.addProperty("Error","Failed to get tweets for " + handle);
      return failureJson;
   }

   private static Response exectuteRequest(Request request){
      try {
         return httpClient.newCall(request).execute();
      }
      catch (Exception e){
         e.printStackTrace();
      }
      return null;
   }

   private static Request buildGetTweetsRequest(String handle){
      return new Request.Builder()
              .url(getTweetsEndpoint)
              .get()
              .addHeader("Content-Type", "application/json")
              .addHeader("Handle", "chrisBrown")
              .build();
   }

   private static JsonObject failedHandleValidation(String handle){
      JsonObject failureJson = new JsonObject();
      failureJson.addProperty("Error","Failed twitter validation for " + handle);
      return failureJson;
   }

}
