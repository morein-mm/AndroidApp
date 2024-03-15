package ru.netology.nmedia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val postText = intent.getStringExtra(NewPostActivity.KEY_TEXT)
        if (!postText.isNullOrBlank()) {
            binding.edit.setText(postText)
        } else {
            binding.edit.setText("")
        }

        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            if (text.isNotBlank()) {
                setResult(RESULT_OK, Intent().apply { putExtra(KEY_TEXT, text) })
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    companion object {
        const val KEY_TEXT = "post_text"
    }
}

object NewPostContract : ActivityResultContract<String?, String?>() {
//    override fun createIntent(context: Context, input: String?) =
//        Intent(context, NewPostActivity::class.java).apply { putExtra(NewPostActivity.KEY_TEXT, input) }


    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, NewPostActivity::class.java).putExtra(
            NewPostActivity.KEY_TEXT,
            input
        )
    }

    override fun parseResult(resultCode: Int, intent: Intent?) =
        intent?.getStringExtra(NewPostActivity.KEY_TEXT)

}