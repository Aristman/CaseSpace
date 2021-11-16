package ru.marslab.casespace.ui.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.marslab.casespace.ui.ViewElementsVisibility

fun Fragment.initViewNavigate(
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

fun Fragment.setToolbarTitle(title: String) {
    (this.requireActivity() as AppCompatActivity).supportActionBar?.title = title
}