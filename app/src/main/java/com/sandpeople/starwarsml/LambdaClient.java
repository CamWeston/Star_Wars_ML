package com.sandpeople.starwarsml;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


class LambdaClient {
   private static final String getTweetsEndpoint = "https://sirxl7b15l.execute-api.us-east-1.amazonaws.com/default/twitterAPI";
   private static final String mlApiEndpoint = "https://us-central1-cs275-ml-api-260320.cloudfunctions.net/main";
   private static final String userExistsKeyName = "user_exists";
   private static final String tweetsKeyName = "tweets";
   private static final String handleHeaderName = "Handle";
   private static final String errorKeyName = "error";
   private static final String noTweetsFoundMsg = "No tweets found for ";
   private static final String failureToGetTweetsMsg = "Failed to get tweets for ";
   private static final String failureToValidateHandleMsg = "Failed twitter validation for ";

   static Gson gson = new Gson();

   static JsonObject execute(String handle){
      return getTweets(handle);
   }

   static void predictCharacter(JsonObject tweets) throws IOException {

      // Send request
      HttpURLConnection urlConnection = (HttpURLConnection) new URL(mlApiEndpoint).openConnection();
      urlConnection.setRequestProperty("Content-Type", "application/json");
      urlConnection.setDoOutput(true);
      urlConnection.setDoInput(true);
      OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
      writer.write(gson.toJson(tweets));
      writer.flush();
      writer.close();
      out.close();

      // Receive response
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      String response = bufferedReader.readLine();
      System.out.println(response);
      bufferedReader.close();

   }

   private static JsonObject getTweets(String handle){
      try {
         URL lambda = new URL(getTweetsEndpoint);
         URLConnection yc = lambda.openConnection();
         yc.setRequestProperty(handleHeaderName, handle);

         BufferedReader in = new BufferedReader(
                 new InputStreamReader(
                         yc.getInputStream()));


         String tweetsString = in.readLine();

         JsonObject tweetsJson = gson.fromJson(tweetsString, JsonObject.class);

         in.close();

         if(tweetsJson.get(userExistsKeyName) != null){
            if(!tweetsJson.get(userExistsKeyName).getAsBoolean()){
               return failedHandleValidation(handle);
            }
         }
         else{
            return failedHandleValidation(handle);
         }

         if(tweetsJson.get(tweetsKeyName) != null){
            if(tweetsJson.get(tweetsKeyName).getAsJsonArray().size() < 1){
               return noTweetsFound(handle);
            }
         }
         else{
            return failureToGetTweets(handle);
         }

         return tweetsJson;
      }
      catch (Exception e){
         return failureToGetTweets(handle);
      }
   }


   private static JsonObject noTweetsFound(String handle){
      JsonObject failureJson = new JsonObject();
      failureJson.addProperty(errorKeyName,noTweetsFoundMsg + handle);
      return failureJson;
   }

   private static JsonObject failureToGetTweets(String handle){
      JsonObject failureJson = new JsonObject();
      failureJson.addProperty(errorKeyName,failureToGetTweetsMsg + handle);
      return failureJson;
   }


   private static JsonObject failedHandleValidation(String handle){
      JsonObject failureJson = new JsonObject();
      failureJson.addProperty(errorKeyName,failureToValidateHandleMsg + handle);
      return failureJson;
   }

}
