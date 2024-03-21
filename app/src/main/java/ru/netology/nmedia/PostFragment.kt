package ru.netology.nmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.PostViewModel
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.LongArg

class PostFragment : Fragment() {

    companion object {
        var Bundle.postIdArg: Long by LongArg
    }


    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(
            inflater,
            container,
            false
        )

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        callback.isEnabled = true

        val postId = arguments?.postIdArg ?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: return@observe
            PostViewHolder(
                binding.singlePost,
                object : InteractionListener(requireContext(), viewModel, this) {}).bind(post)
        }

        return binding.root
    }

}