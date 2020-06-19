package com.android.albumlist.presentation.component.photo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.android.albumlist.data.Resource
import com.android.albumlist.domain.Photo
import com.android.albumlist.util.MainCoroutineRule
import com.util.InstantExecutorExtension
import com.android.albumlist.util.TestModelsGenerator
import com.android.albumlist.data.error.Error
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class PhotosViewModelTest {

    // Subject under test
    private val photosViewModel: PhotosViewModel = mockk()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var photoTitle: String
    private val testModelsGenerator: TestModelsGenerator = TestModelsGenerator()


    @Before
    fun setUp() {

        // Create class under test
        // We initialise the repository with no tasks
        photoTitle = testModelsGenerator.getStupSearchTitle()
        val photosSuccess = MutableLiveData<Resource<List<Photo>>>()
        every { photosViewModel.listResourcePhotosMutableLiveData } returns photosSuccess
    }

    @Test
    fun handlePhotosList() {
        // Let's do an answer for the liveData
        val photosTest = testModelsGenerator.generateListPhotos()
        val photosSuccess = MutableLiveData<Resource<List<Photo>>>()
        photosSuccess.value = Resource.Success(photosTest)

        //1- Mock calls
        every { photosViewModel.getPhotosFromWS() } just Runs
        every { photosViewModel.listResourcePhotosMutableLiveData } returns photosSuccess

        //2- active observer for livedata
        photosViewModel.listResourcePhotosMutableLiveData.observeForever { }

        //3-verify
        val isEmptyList = photosViewModel.listResourcePhotosMutableLiveData.value?.data.isNullOrEmpty()
        assert(photosTest == photosViewModel.listResourcePhotosMutableLiveData.value?.data)
        assert(!isEmptyList)
    }


    @Test
    fun handleEmptyList() {
        // Let's do an answer for the liveData
        val photosTest = testModelsGenerator.generatePhotosWithEmptyList()
        val photosSuccess = MutableLiveData<Resource<List<Photo>>>()
        photosSuccess.value = Resource.Success(photosTest)

        //1- Mock calls
        every { photosViewModel.getPhotosFromWS() } just Runs
        every { photosViewModel.listResourcePhotosMutableLiveData } returns photosSuccess

        //2- active observer for livedata
        photosViewModel.listResourcePhotosMutableLiveData.observeForever { }

        //3-verify
        val isEmptyList = photosViewModel.listResourcePhotosMutableLiveData.value?.data.isNullOrEmpty()
        assert(photosTest == photosViewModel.listResourcePhotosMutableLiveData.value?.data)
        assert(isEmptyList)
    }


    @Test
    fun handlePhotosError() {
        // Let's do an answer for the liveData
        val listPhotosFail = MutableLiveData<Resource<List<Photo>>>()
        listPhotosFail.value = Resource.DataError(Error.NETWORK_ERROR)

        //1- Mock calls
        every { photosViewModel.getPhotosFromWS() } just Runs
        every { photosViewModel.listResourcePhotosMutableLiveData } returns listPhotosFail

        //2-active observer for livedata
        photosViewModel.listResourcePhotosMutableLiveData.observeForever { }

        //3-verify
        assert(Error.NETWORK_ERROR == photosViewModel.listResourcePhotosMutableLiveData.value?.errorCode)
    }



}