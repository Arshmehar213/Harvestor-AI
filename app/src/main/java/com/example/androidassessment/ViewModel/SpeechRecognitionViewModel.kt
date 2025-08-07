package com.example.androidassessment.ViewModel


import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel


class SpeechRecognitionViewModel(application: Application) : AndroidViewModel(application) {
   private var speechRecognizer : SpeechRecognizer? = null
   private var recognitionListener : RecognitionListener? = null

    var isRecording by mutableStateOf(false)
        private set
    var recognizedText by mutableStateOf("")
        private set
    var errorMessage by mutableStateOf("")
        private set

    fun startRecording(){
        val context = getApplication<Application>().applicationContext
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply{
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, java.util.Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        if (isRecording) return

        errorMessage = ""
        isRecording = true

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

        recognitionListener = object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {

            }

            override fun onBeginningOfSpeech() {

            }

            override fun onRmsChanged(p0: Float) {

            }

            override fun onBufferReceived(p0: ByteArray?) {

            }

            override fun onEndOfSpeech() {

            }

            override fun onError(error: Int) {
                isRecording = false
                errorMessage = when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                    SpeechRecognizer.ERROR_CLIENT -> "Client side error"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
                    SpeechRecognizer.ERROR_NETWORK -> "Network error"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
                    SpeechRecognizer.ERROR_NO_MATCH -> "No speech input matched"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
                    SpeechRecognizer.ERROR_SERVER -> "Server error"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
                    else -> "Unknown error"
                }
            }

            override fun onResults(result: Bundle?) {
                isRecording = false
                val matches = result?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()){
                    recognizedText = matches[0]
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()){
                    recognizedText = matches[0]
                }
            }

            override fun onEvent(p0: Int, p1: Bundle?) {

            }
        }

        speechRecognizer?.setRecognitionListener(recognitionListener)
        speechRecognizer?.startListening(intent)
    }

    fun stopRecording(){
        if(!isRecording) return

        isRecording = false
        speechRecognizer?.stopListening()
        speechRecognizer?.destroy()
        speechRecognizer = null
        recognitionListener = null

    }

    fun clearText(){
        recognizedText = ""
        errorMessage = ""
    }

    override fun onCleared() {
        super.onCleared()
        stopRecording()
    }
}