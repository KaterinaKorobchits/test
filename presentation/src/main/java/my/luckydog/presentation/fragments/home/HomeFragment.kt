package my.luckydog.presentation.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding3.widget.textChanges
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_home.*
import my.luckydog.interactors.HomeInteractor
import my.luckydog.presentation.R
import my.luckydog.presentation.core.extensions.getViewModel
import my.luckydog.presentation.databinding.FragmentHomeBinding
import my.luckydog.presentation.fragments.home.image.ImageAdapter
import my.luckydog.presentation.fragments.home.navigation.HomeNavigation
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class HomeFragment : Fragment() {

    @Inject
    lateinit var navigation: HomeNavigation

    @Inject
    lateinit var interactor: Lazy<HomeInteractor>

   // @Inject
   // lateinit var adapter: Provider<ImageAdapter>

    val adapter = ImageAdapter()

    private val viewModel: HomeViewModel by lazy(NONE) {
        getViewModel { HomeViewModel(interactor.get(), HomeForm()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater)
        .apply {
            adapter = this@HomeFragment.adapter
            form = viewModel.form
            lifecycleOwner = viewLifecycleOwner
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.interactor.storeHasAuthorize()

        view.findViewById<View>(R.id.b_log_out).setOnClickListener {
            viewModel.interactor.logout()
            navigation.showSignIn()
        }

        with(et_search) {
            textChanges()
                .debounce(600, TimeUnit.MILLISECONDS)
                .filter { it.isNotEmpty() }
                .subscribe { println("!!!!!!!!!$it") }
        }

        //form.recyclerData.addAll(getListItems())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.images.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it) })
    }

    /*private fun getListItems() = listOf(
        ItemForm(1, "item 1111"),
        ItemForm(2, "item 2222"),
        ItemForm(3, "item 3333"),
        ItemForm(4, "item 4444"),
        ItemForm(5, "item 5555"),
        ItemForm(6, "item 6666"),
        ItemForm(7, "item 7777"),
        ItemForm(8, "item 8888"),
        ItemForm(9, "item 9999")
    )*/
}