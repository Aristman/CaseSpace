package ru.marslab.casespace.ui.custom

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.marslab.casespace.ui.ViewElementsVisibility

open class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewNavigate()
    }

    fun initViewNavigate(
        toolbar: Boolean = true,
        buttonNav: Boolean = true,
        wikiSearch: Boolean = false,
        wikiMenuItem: Boolean = true
    ) {
        (this.requireActivity() as? ViewElementsVisibility)?.run {
            toolbarVisibility(toolbar)
            buttonNavVisibility(buttonNav)
            wikiSearchVisibility(wikiSearch)
            wikiMenuItemVisibility(wikiMenuItem)
        }
    }

    fun setToolbarTitle(title: String) {
        (this.requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }
}