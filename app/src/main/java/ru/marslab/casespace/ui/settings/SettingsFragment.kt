package ru.marslab.casespace.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentSettingsBinding
import ru.marslab.casespace.ui.MainViewModel

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
        initView()
    }

    private fun initListeners() {
        binding.btnApplyTheme.setOnClickListener {
            requireActivity().setTheme(mainViewModel.getCurrentTheme())
            requireActivity().recreate()
        }
        binding.btnSettingsClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initView() {
        binding.run {
            when (mainViewModel.getCurrentTheme()) {
                R.style.MarsTheme -> {
                    rbThemeMars.isChecked = true
                }
                R.style.Theme_CaseSpace -> {
                    rbThemeDefault.isChecked = true
                }
                R.style.Theme_CaseSpace_Emerald -> {
                    rbThemeEmerald.isChecked = true
                }
            }
        }
    }

    private fun initObservers() {
        binding.themesChoiceGroup.setOnCheckedChangeListener { group, checkedId ->
            val newTheme = when (checkedId) {
                R.id.rb_theme_default -> R.style.Theme_CaseSpace
                R.id.rb_theme_emerald -> R.style.Theme_CaseSpace_Emerald
                R.id.rb_theme_mars -> R.style.MarsTheme
                else -> R.style.Theme_CaseSpace
            }
            mainViewModel.saveCurrentTheme(newTheme)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}