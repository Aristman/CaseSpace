package ru.marslab.casespace.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.LayoutBottomNavBinding
import ru.marslab.casespace.domain.util.showMessage

class BottomNavDrawerFragment : BottomSheetDialogFragment() {
    companion object {
        const val FRAGMENT_TAG = "bottom_nav_drawer"
    }
    private var _binding: LayoutBottomNavBinding? = null
    private val binding: LayoutBottomNavBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutBottomNavBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainNavView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_fragment_one -> {
                    requireView().showMessage(item.title.toString())
                    this.dismiss()
                    true
                }
                R.id.item_fragment_two -> {
                    requireView().showMessage(item.title.toString())
                    this.dismiss()
                    true
                }
                R.id.item_fragment_three -> {
                    requireView().showMessage(item.title.toString())
                    this.dismiss()
                    true
                }
                else -> false
            }
        }
    }
}