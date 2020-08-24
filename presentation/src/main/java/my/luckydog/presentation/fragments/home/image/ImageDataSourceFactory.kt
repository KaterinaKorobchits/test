package my.luckydog.presentation.fragments.home.image

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import my.luckydog.interactors.core.image.ItemImage

class ImageDataSourceFactory(
    private val dataSource: ImageDataSource
) : DataSource.Factory<Int, ItemImage>() {

    private val sourceLiveData = MutableLiveData<ImageDataSource>()

    override fun create(): DataSource<Int, ItemImage> {
        sourceLiveData.postValue(dataSource)
        return dataSource
    }
}