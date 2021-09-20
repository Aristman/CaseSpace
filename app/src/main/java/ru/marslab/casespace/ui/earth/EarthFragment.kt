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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.FragmentEarthBinding
import ru.marslab.casespace.domain.util.showMessage
import ru.marslab.casespace.ui.util.PermissionStatus
import ru.marslab.casespace.ui.util.RequestPermission

@AndroidEntryPoint
class EarthFragment : Fragment() {
    private var _binding: FragmentEarthBinding? = null
    private val binding: FragmentEarthBinding
        get() = checkNotNull(_binding) { getString(R.string.error_init_binding, this::class) }
    private val requestLocationPermission: RequestPermission = RequestPermission(
        this,
        R.string.locate_request_permission_title,
        R.string.locate_request_permission_text,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

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
        requestLocationPermission.getPermission()
        initObservers()
        binding.root.visibility = View.INVISIBLE
    }

    private fun initObservers() {
        requestLocationPermission.permission.observe(viewLifecycleOwner) { permissionStatus ->
            when (permissionStatus) {
                PermissionStatus.Granted -> {
                    binding.root.visibility = View.VISIBLE
                    getLocation()
                    location?.let {

                    }
                }
                PermissionStatus.Denied,
                PermissionStatus.Error -> {
                    findNavController().popBackStack()
                }
            }
        }

        earthViewModel.earthAssetUrl
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (requestLocationPermission.isPermissionGranted()) {
            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
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