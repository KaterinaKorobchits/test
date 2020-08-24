package my.luckydog.presentation.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import my.luckydog.interactors.HomeInteractor
import my.luckydog.interactors.core.image.ItemImage
import my.luckydog.presentation.fragments.home.image.ImageDataSource
import my.luckydog.presentation.fragments.home.image.ImageDataSourceFactory
import kotlin.LazyThreadSafetyMode.NONE

class HomeViewModel(
    val interactor: HomeInteractor,
    val form: HomeForm
) : ViewModel() {

    private val composite by lazy(NONE) { CompositeDisposable() }

    var images: LiveData<PagedList<ItemImage>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .setPrefetchDistance(3)
            .setEnablePlaceholders(false)
            .build()
        val dataSourceFactory = ImageDataSourceFactory(ImageDataSource(interactor, composite))
        images = LivePagedListBuilder<Int, ItemImage>(dataSourceFactory, config).build()
    }

    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}