package com.example.actors

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.actors.ui.main.actorDetails.ActorDetailsFragment
import com.example.actors.ui.main.actorDetails.state.ActorsDetailsState
import com.example.actors.ui.main.actorDetails.state.ActorsImagesState
import com.example.actors.ui.main.actorDetails.state.ActorsTaggedImagesState
import com.example.actors.ui.main.actorDetails.state.RenderState
import com.example.data.businessmodel.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ActorsDetailsStateInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun assert_the_layout_according_to_ActorsDetailsState_Loading_state() {
        launchSurveyListFragment(ActorsDetailsState.Loading)

        onView((withId(R.id.animationView))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView((withId(R.id.nestedRootView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.errorView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun assert_the_person_details_according_to_ActorsDetailsState_SuccessDetails_state() {
        val mockedPersonModel = PersonModel(
            "1983-08-20",
            "Los Angeles, California, USA",
            "Acting",
            null,
            "Andrew Garfield"
        )

        launchSurveyListFragment(ActorsDetailsState.Success(mockedPersonModel))

        onView((withId(R.id.animationView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.errorView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.nestedRootView))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView((withId(R.id.knownForView)))
            .check(matches(withText("Acting")))

        onView((withId(R.id.bornTextView)))
            .check(matches(withText("20 Αυγ 1983")))

        onView((withId(R.id.bornPlaceTextView)))
            .check(matches(withText("Los Angeles, California, USA")))
    }

    @Test
    fun assert_the_profile_image_according_to_ActorsDetailsState_SuccessImages_state() {
        val mockedPersonImagesModel = PersonImagesModel(
            37625L,
            arrayListOf(ProfileModel("/h0A3pzHaNTVRD7xNfabuoyadJba.jpg"))
        )

        launchSurveyListFragment(ActorsImagesState.Success(mockedPersonImagesModel))

        onView((withId(R.id.animationView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.errorView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.nestedRootView))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView((withId(R.id.profileImageView)))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun assert_the_tagged_images_according_to_ActorsDetailsState_SuccessTaggedImages_state() {
        val mockedPersonTaggedImagesModel = PersonTaggedImagesModel(
            arrayListOf(ResultModel("/sVYgFC6z0RZJrgUpve4XlyrVrgr.jpg"))
        )

        launchSurveyListFragment(ActorsTaggedImagesState.Success(mockedPersonTaggedImagesModel))

        onView((withId(R.id.animationView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.errorView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.nestedRootView))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView((withId(R.id.taggedImagesList)))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(RecyclerViewMatcher(R.id.taggedImagesList)
            .atPositionOnView(0, R.id.taggedImageView))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun assert_the_layout_according_to_ActorsDetailsState_Error_state() {

        launchSurveyListFragment(ActorsDetailsState.Error(401, null))

        onView((withId(R.id.animationView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.nestedRootView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.errorView))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    private fun launchSurveyListFragment(renderState: RenderState) {
        launchFragmentInHiltContainer<ActorDetailsFragment> {
            (this as ActorDetailsFragment).renderState(
                state = renderState
            )
        }
    }
}