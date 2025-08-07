package com.example.androidassessment.AiService

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.google.auth.oauth2.ServiceAccountCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class VertexService(val context: Context) {

    private val client = OkHttpClient()
    private val projectId = "your-project-id"
    private val location = "us-central1"
    private val modelId = "gemini-1.5-pro"

    private suspend fun getToken() : String = withContext(Dispatchers.IO){
        val inputStream: InputStream = context.assets.open("service-account-key.json")

        val credentials = ServiceAccountCredentials.fromStream(inputStream)
            .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))
        credentials.refreshAccessToken().tokenValue
    }

    suspend fun textGeneration(prompt : String) : Result<String> = withContext(Dispatchers.IO){
        try {
            val accessToken = getToken()
            val endpoint = "https://$location-aiplatform.googleapis.com/v1/projects/$projectId/locations/$location/publishers/google/models/$modelId:generateContent"

            val requestBody = JSONObject().apply {
                put("contents", org.json.JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "user")
                        put("parts", org.json.JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.7)
                    put("topP", 0.8)
                    put("topK", 40)
                    put("maxOutputTokens", 1024)
                })
            }

        val request = Request.Builder()
            .url(endpoint)
            .addHeader("Authorization", "Bearer $accessToken")
            .addHeader("Content-Type", "application/json")
            .post(requestBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val jsonResponse = JSONObject(responseBody ?: "")
            val candidates = jsonResponse.optJSONArray("candidates")

            if (candidates != null && candidates.length() > 0) {
                val firstCandidate = candidates.getJSONObject(0)
                val content = firstCandidate.getJSONObject("content")
                val parts = content.getJSONArray("parts")
                val text = parts.getJSONObject(0).getString("text")

                Result.success(text)
            } else {
                Result.failure(Exception("No response generated"))
            }
        } else {
            Result.failure(Exception("API call failed: ${response.code}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
   }

   suspend fun ImageInput(bitmap: Bitmap , text : String){
       try {
           val url = "https://us-central1-aiplatform.googleapis.com/v1/projects/YOUR_PROJECT_ID/locations/us-central1/publishers/google/models/gemini-pro-vision:predict"
           val base64Image = convertToBase64(bitmap)
           val token = null //Assign the Token.

           val json = """
           {
           "instances" : [
           {
           "prompt" : [
           {"text" : "$text"},
           {"image" : "bytesBase64Encoded" : "$base64Image"}}      
    ]
    }
    ],
    "parameters" : {}
       """

           val mediaType = "application/json".toMediaType()
           val body = RequestBody.create(mediaType , json)

           val request = Request.Builder()
               .url(url)
               .addHeader("Authorization", "Bearer $token")
               .addHeader("Content-Type", "application/json")
               .post(body)
               .build()

           client.newCall(request).enqueue(object : Callback{
               override fun onFailure(call: Call, e: IOException) {
                   Log.e("ImageInputError" , e.message.toString())

               }

               override fun onResponse(call: Call, Response: Response) {
                   val response = Response.body?.string()
                   Result.success(response)
               }
           })
       }catch (e : Exception){
           Log.e("ErrorInImage" , e.message.toString())
           Result.failure<Exception>(e)
       }
   }

    private fun convertToBase64(bitmap: Bitmap): String? {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG , 100 , stream)
        val byteArray = stream.toByteArray()
        return android.util.Base64.encodeToString(byteArray,android.util.Base64.NO_WRAP)
    }
}