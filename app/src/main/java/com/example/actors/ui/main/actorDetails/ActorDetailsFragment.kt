package com.example.actors.ui.main.actorDetails

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.actors.BuildConfig.BASE_URL_IMAGES
import com.example.actors.databinding.ActorsDetailsFragmentBinding
import com.example.actors.extensions.convertToGreekDate
import com.example.actors.extensions.gone
import com.example.actors.extensions.log
import com.example.actors.extensions.visible
import com.example.actors.ui.main.actorDetails.adapter.TaggedImageAdapter
import com.example.actors.ui.main.actorDetails.state.ActorsDetailsState
import com.example.actors.ui.main.actorDetails.state.ActorsImagesState
import com.example.actors.ui.main.actorDetails.state.ActorsTaggedImagesState
import com.example.actors.ui.main.actorDetails.state.RenderState
import com.example.data.businessmodel.PersonModel
import com.example.data.businessmodel.ResultModel
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActorDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ActorDetailsFragment()
    }

    private var _binding: ActorsDetailsFragmentBinding? = null
    val binding get() = _binding!!

    private val viewModel by viewModels<ActorDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActorsDetailsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        observeViewModel()
        clickListeners()
    }

    private fun initLayout() {
        // I changed the focus point of crop in order to better display the face
        val focusPoint = PointF(0f, 0.3f)
        binding.profileImageView
            .hierarchy.actualImageFocusPoint = focusPoint
    }

    private fun observeViewModel() {
        // Observe Actor state
        viewModel.getPersonDetailsState().observe(viewLifecycleOwner) { state ->
            // We created a new method in order to handle UI tests more easily
            renderState(state)
        }

        viewModel.getPersonImagesState().observe(viewLifecycleOwner) { state ->
            // We created a new method in order to handle UI tests more easily
            renderState(state)
        }

        viewModel.getPersonTaggedImagesState().observe(viewLifecycleOwner) { state ->
            // We created a new method in order to handle UI tests more easily
            renderState(state)
        }
    }

    /**
     * General method for handling all the different states
     */
    fun renderState(state: RenderState) {
        when (state) {
            // Check Actor state
            is ActorsDetailsState.Loading -> {
                showLoader(true)
                showPersonInfo(false)
            }
            is ActorsDetailsState.Error -> {
                showLoader(false)
                showErrorView(true)
            }
            is ActorsImagesState.Error -> {
                log { "If images failed to load due to some error we completely ignore them as " +
                        "there are not critical resources" }
            }
            is ActorsTaggedImagesState.Error -> {
                log { "Since tagged images are not critical resources then in case of fail we simple" +
                        " hide the recyclerview" }
                binding.taggedImagesList.gone()
            }
            is ActorsDetailsState.Success -> {
                showLoader(false)

                val personModel = state.personDetails

                personModel?.let {
                    fillDetails(personModel)
                    showPersonInfo(true)
                } ?: run {
                    showPersonInfo(false)
                    showErrorView(true)
                }
            }
            is ActorsImagesState.Success -> {
                showLoader(false)

                val personImages = state.personImages

                personImages?.let {
                    val profileImageUrl = "$BASE_URL_IMAGES${it.profiles?.getOrNull(0)?.filePath}" // I chose randomly the first profile image to display
                    binding.profileImageView.setImageURI(profileImageUrl)
                    showPersonInfo(true)
                }
            }
            is ActorsTaggedImagesState.Success -> {
                showLoader(false)

                val personTaggedImages = state.personTaggedImages

                personTaggedImages?.let {
                    setupRecyclerView(personTaggedImages.results)
                    showPersonInfo(true)
                }
            }
        }
    }

    private fun setupRecyclerView(results: List<ResultModel>?) {
        binding.taggedImagesList.layoutManager = GridLayoutManager(context,3)
        val taggedImageAdapter = TaggedImageAdapter(results ?: arrayListOf())
        binding.taggedImagesList.adapter = taggedImageAdapter
        binding.taggedImagesList.addItemDecoration(ActorImagesItemDecoration(3, 40, false))
    }

    private fun fillDetails(personModel: PersonModel) {
        (activity as AppCompatActivity).supportActionBar?.title = personModel.name

        binding.knownForView.text = personModel.knownFor
        binding.bornTextView.text = personModel.birthday.convertToGreekDate()
        binding.bornPlaceTextView.text = personModel.placeOfBirth
    }

    private fun clickListeners() {}

    private fun showLoader(show: Boolean) {
        if (show) {
            showErrorView(show = false)
            showPersonInfo(show = false)
            binding.animationView.playAnimation()
            binding.animationView.visible()
        } else {
            binding.animationView.cancelAnimation()
            binding.animationView.gone()
        }
    }

    private fun showPersonInfo(show: Boolean) {
        if (show) {
            showErrorView(show = false)
            binding.nestedRootView.visible()
        } else {
            binding.nestedRootView.gone()
        }
    }

    private fun showErrorView(show: Boolean) {
        if (show) {
            binding.errorView.visible()
        } else {
            binding.errorView.gone()
        }
    }
}