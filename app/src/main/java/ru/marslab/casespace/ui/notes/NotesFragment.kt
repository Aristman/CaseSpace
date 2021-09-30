package ru.marslab.casespace.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentNotesBinding
import ru.marslab.casespace.ui.custom.BaseFragment

class NotesFragment : BaseFragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding: FragmentNotesBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewNavigate(wikiSearch = false)
        initListeners()
    }

    private fun initListeners() {
        binding.fabNotes.setOnClickListener {
            it.animate()
                .rotation(-180f)
                .start()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}