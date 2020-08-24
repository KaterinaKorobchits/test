package my.luckydog.presentation.fragments.home.image

import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import my.luckydog.interactors.HomeInteractor
import my.luckydog.interactors.core.image.ImageEffect
import my.luckydog.interactors.core.image.ItemImage

class ImageDataSource(
    private val case: HomeInteractor,
    private val composite: CompositeDisposable
) : PageKeyedDataSource<Int, ItemImage>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ItemImage>
    ) {
        println("!!!DataSource,  count " + params.requestedLoadSize)
        composite.add(case.images("grooming").subscribe({
            callback.onResult((it as ImageEffect.Success).items, null, 2)
        }, { callback.onResult(emptyList(), null, null) }))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ItemImage>) {
        println("!!!DataSource,  count " + params.requestedLoadSize)
        composite.add(case.images("grooming dog and cat").subscribe({
            if (it is ImageEffect.NoMore) callback.onResult(emptyList(), null)
            else if (it is ImageEffect.Success) callback.onResult(it.items, params.key + 1)
        }, { callback.onResult(emptyList(), null) }))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ItemImage>) {
    }

}