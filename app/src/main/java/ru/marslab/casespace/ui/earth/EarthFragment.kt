package ru.marslab.casespace.ui.earth

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentEarthBinding
import ru.marslab.casespace.domain.repository.Constant
import ru.marslab.casespace.domain.util.handleError
import ru.marslab.casespace.domain.util.showMessage
import ru.marslab.casespace.ui.custom.BaseFragment
import ru.marslab.casespace.ui.earth.adapter.EarthImageAdapter
import ru.marslab.casespace.ui.model.EarthUi
import ru.marslab.casespace.ui.util.PermissionStatus
import ru.marslab.casespace.ui.util.RequestPermission
import ru.marslab.casespace.ui.util.ViewState
import java.util.*

@AndroidEntryPoint
class EarthFragment : BaseFragment() {
    private var _binding: FragmentEarthBinding? = null
    private val binding: FragmentEarthBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }
    private val requestLocationPermission: RequestPermission = RequestPermission(
        this,
        R.string.locate_request_permission_title,
        R.string.locate_request_permission_text,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val earthImageAdapter: EarthImageAdapter by lazy { EarthImageAdapter(this) }

    private val earthViewModel by viewModels<EarthViewModel>()

    private var location: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
        loadContent()
    }

    @SuppressLint("InflateParams")
    private fun initView() {
        setHasOptionsMenu(true)
        binding.earthImagePager.adapter = earthImageAdapter
        TabLayoutMediator(binding.earthImageTab, binding.earthImagePager) { tab, position ->
            val tabView = LayoutInflater.from(requireContext())
                .inflate(R.layout.layout_earth_tab, null)
            tabView.findViewById<ImageView>(R.id.earth_thumb_image).apply {
                load(earthImageAdapter.getItemThumbUrl(position))
            }
            tab.customView = tabView
        }.attach()
    }

    private fun initObservers() {
        requestLocationPermission.permission.observe(viewLifecycleOwner) { permissionStatus ->
            when (permissionStatus) {
                PermissionStatus.Granted -> {
                    binding.root.visibility = View.VISIBLE
                }
                PermissionStatus.Denied,
                PermissionStatus.Error -> {
                    findNavController().popBackStack()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            earthViewModel.earthImageList.collect { viewResult ->
                when (viewResult) {
                    is ViewState.Successful<*> -> {
                        val earthImagesList = (viewResult.data as List<*>).map { it as EarthUi }
                        showMainContent()
                        earthImageAdapter.setImageList(earthImagesList)
                    }
                    is ViewState.LoadError -> {
                        this@EarthFragment.handleError(viewResult.error) {
                            location?.let { earthViewModel.getEarthImageList(Constant.EPIC_COLLECTION_NATURAL) }
                        }
                    }
                    ViewState.Loading -> {
                        showLoading()
                    }
                    ViewState.Init -> {
                        setInitStatusView()
                    }
                }
            }
        }
    }

    private fun loadContent() {
        earthViewModel.run {
            getEarthImageList(Constant.EPIC_COLLECTION_NATURAL)
        }

    }

    private fun setInitStatusView() {
        binding.root.visibility = View.INVISIBLE
        requestLocationPermission.getPermission()
    }


    private fun showMainContent() {
        binding.run {
            loadingIndicator.visibility = View.GONE
            earthImagePager.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        binding.run {
            loadingIndicator.visibility = View.VISIBLE
            earthImagePager.visibility = View.GONE
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (requestLocationPermission.isPermissionGranted()) {
            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location == null) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        Constant.GPS_REFRESH_PERIOD,
                        Constant.GPS_DISTANCE
                    ) {
                        location = it
                    }
                }
            } else {
                requireView().showMessage(R.string.no_gps)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}