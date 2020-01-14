package my.luckydog.presentation.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import my.luckydog.interactors.HomeInteractor
import my.luckydog.presentation.R
import my.luckydog.presentation.fragments.home.navigation.HomeNavigation
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var navigation: HomeNavigation

    @Inject
    lateinit var interactor: HomeInteractor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        interactor.storeHasAuthorize()

        view.findViewById<View>(R.id.b_log_out).setOnClickListener {
            interactor.logout()
            navigation.showSignIn()
        }
    }
}