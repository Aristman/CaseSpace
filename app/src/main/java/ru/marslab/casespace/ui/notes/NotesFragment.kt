package ru.marslab.casespace.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentNotesBinding
import ru.marslab.casespace.domain.util.handleError
import ru.marslab.casespace.domain.util.visible
import ru.marslab.casespace.ui.custom.BaseFragment
import ru.marslab.casespace.ui.model.NoteUi
import ru.marslab.casespace.ui.notes.adapter.NotesAdapter
import ru.marslab.casespace.ui.util.ViewState

@AndroidEntryPoint
class NotesFragment : BaseFragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding: FragmentNotesBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }

    private val notesViewModel by viewModels<NotesViewModel>()

    private val notesAdapter: NotesAdapter by lazy {
        NotesAdapter() { item ->
            Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
        }
    }

    private var isExpandFabMenu = false

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
        initRV()
        initListeners()
        initObservers()
    }

    private fun initRV() {
        binding.rvNotes.adapter = notesAdapter
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            notesViewModel.notes.collect { result ->
                when (result) {
                    ViewState.Init -> {
                        setViewInitState()
                        notesViewModel.getAllNotes()
                    }
                    is ViewState.LoadError -> {
                        this@NotesFragment.handleError(result.error) { notesViewModel.getAllNotes() }
                    }
                    ViewState.Loading -> {
                        setViewWorkState()
                    }
                    is ViewState.Successful<*> -> {
                        val notesList = (result.data as List<*>).map { it as NoteUi }
                        notesAdapter.submitList(notesList)
                    }
                }
            }
        }
    }

    private fun setViewWorkState() {
        binding.root.apply {
            alpha = 1f
            isClickable = true
        }
    }

    private fun setViewInitState() {
        binding.root.apply {
            alpha = 0.3f
            isClickable = false
        }
    }

    private fun initListeners() {
        binding.fabNotes.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root)
            binding.noteButtonsGroup.visible(!isExpandFabMenu)
            if (isExpandFabMenu) {
                collapseFabMenu()
            } else {
                expandFabMenu()
            }
            isExpandFabMenu = !isExpandFabMenu
        }
    }

    private fun collapseFabMenu() {
        binding.fabNotes.animate()
            .rotation(180f)
            .alpha(1f)
            .start()

    }

    private fun expandFabMenu() {
        binding.fabNotes.animate()
            .rotation(-180f)
            .alpha(0.5f)
            .start()

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}