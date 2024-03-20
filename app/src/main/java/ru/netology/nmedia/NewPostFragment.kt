package ru.netology.nmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.dto.PostViewModel
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.StringArg

class NewPostFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            AndroidUtils.hideKeyboard(requireView())
            viewModel.cancelEdit()
            findNavController().navigateUp()
        }
        callback.isEnabled = true

        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        arguments?.textArg
            ?.let(binding.edit::setText)

        binding.ok.setOnClickListener {
            viewModel.changeContentAndSave(binding.edit.text.toString())
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root

//        binding.edit.requestFocus()

//        val intent = Intent()

//        val postText = intent.getStringExtra(NewPostFragment.KEY_TEXT)
//        if (!postText.isNullOrBlank()) {
//            binding.edit.setText(postText)
//        } else {
//            binding.edit.setText("")
//        }
//
//        binding.ok.setOnClickListener {
//            val text = binding.edit.text.toString()
//            if (text.isNotBlank()) {
//                activity?.setResult(Activity.RESULT_OK, Intent().apply { putExtra(KEY_TEXT, text) })
//            } else {
//                activity?.setResult(Activity.RESULT_CANCELED, intent)
//            }
//            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
//        }
//
//        return binding.root
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}

//    companion object {
//        const val KEY_TEXT = "post_text"
//    }
//}
//
//object NewPostContract : ActivityResultContract<String?, String?>() {
////    override fun createIntent(context: Context, input: String?) =
////        Intent(context, NewPostActivity::class.java).apply { putExtra(NewPostActivity.KEY_TEXT, input) }
//
//
//    override fun createIntent(context: Context, input: String?): Intent {
//        return Intent(context, NewPostFragment::class.java).putExtra(
//            NewPostFragment.KEY_TEXT,
//            input
//        )
//    }
//
//    override fun parseResult(resultCode: Int, intent: Intent?) =
//        intent?.getStringExtra(NewPostFragment.KEY_TEXT)
//
//}