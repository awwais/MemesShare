package com.example.memesshare


import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target





class MainActivity : AppCompatActivity() {

    private lateinit var tvMemesImage :ImageView
    private lateinit var progress1: ProgressBar
    private lateinit var nextButton: Button
    private lateinit var shareButton: Button

    var currentImageUrl : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvMemesImage = findViewById(R.id.tvMemesImage)
        progress1 = findViewById(R.id.progressBar)
        nextButton = findViewById(R.id.nextButton)
        shareButton = findViewById(R.id.shareButton)

        loadMeme()
    }

 fun loadMeme(){
    // Instantiate the RequestQueue.
     progress1.visibility = View.VISIBLE
//     nextButton.isEnabled = false
//     shareButton.isEnabled = false

    val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
    val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
        { response ->
            // Display the first 500 characters of the response string.
           currentImageUrl = response.getString("url")
            Glide.with(this).load(currentImageUrl).listener(object:RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progress1.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progress1.visibility = View.GONE
                    return false
                }

            }).into(tvMemesImage)
        },
        {
            Toast.makeText(this,"Failed to load Check your Internet",Toast.LENGTH_SHORT).show()
            Log.i("faiiiiiled",it.localizedMessage) })

// Add the request to the RequestQueue.
MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

}
    fun shareMemes(view: View) {
         val intent = Intent(Intent.ACTION_SEND)
         intent.type = "text/plain"
         intent.putExtra(Intent.EXTRA_TEXT,currentImageUrl)
         var chooser = Intent.createChooser(intent,"Sharing ")
        startActivity(chooser)

    }
    fun nextMemes(view: View) {
     loadMeme()
    }
}