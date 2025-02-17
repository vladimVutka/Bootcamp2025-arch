package ru.sicampus.bootcamp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.sicampus.bootcamp.R
import ru.sicampus.bootcamp.databinding.OrganizationProfileBinding
import ru.sicampus.bootcamp.ui.Profile.ProfilViewModel
import ru.sicampus.bootcamp.ui.list.ListFragment
import ru.sicampus.bootcamp.ui.list.ProfileFragment
import ru.sicampus.bootcamp.ui.list.VolunteerCenterViewModel
import ru.sicampus.bootcamp.ui.map.MapFragment
import ru.sicampus.bootcamp.utils.collectWithLifecycle

class VolunteerCenterFragment: Fragment(R.layout.organization_profile)
{
    private var _viewBinding: OrganizationProfileBinding? = null
    private val viewBinding : OrganizationProfileBinding get() = _viewBinding!!

    private val viewModel by viewModels<VolunteerCenterViewModel> { VolunteerCenterViewModel.Factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _viewBinding = OrganizationProfileBinding.bind(view)
        viewBinding.back.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, MapFragment())
                .commitAllowingStateLoss()
        }
        var l: Int = 0
        viewBinding.logInBtn1.setOnClickListener {
            if(l == 0)
            {
                l = 1
                viewBinding.logInBtn1.text = "idi nahui"
                Log.d("cccccc", "idi nahui")
            }
            else
            {
                l = 0
                viewBinding.logInBtn1.text = "Присоединиться"
                Log.d("cccccc", "idi nahui")
            }
        }

        viewBinding.profileIc.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ProfileFragment())
                .commitAllowingStateLoss()
        }
        viewBinding.listIc.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ListFragment())
                .commitAllowingStateLoss()
        }
        viewBinding.mapIc.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, MapFragment())
                .commitAllowingStateLoss()
        }

        viewModel.state.collectWithLifecycle(this) { state ->
            when (state) {
                is VolunteerCenterViewModel.State.Show -> {
                    viewBinding.profileText.text = state.items.name
                    viewBinding.description.text = state.items.info
                    Log.d("FFF", "${state.items.name}")
                }

                is VolunteerCenterViewModel.State.Error -> {
                    Log.d("FFF", "${state.text}}")
                }

                else -> {}
            }

    }}

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
