package com.example.SwallowMonthJM.UIFragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.SwallowMonthJM.MainActivity
import com.example.SwallowMonthJM.Network.MasterApplication
import com.example.SwallowMonthJM.R
import com.example.SwallowMonthJM.databinding.FragmentProfileUpdateBinding
import com.example.SwallowMonthJM.Manager.UserManager
import com.example.SwallowMonthJM.Model.Profile
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class ProfileUpdateFragment : Fragment() {
    private var _binding: FragmentProfileUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var userManager: UserManager
    private var updateProfile : Profile? = null
    private var imageUri : Uri?=null
    private var selectPicFragment : SelectPicFragment? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        userManager = UserManager(mainActivity.application as MasterApplication, mainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.viewModel.profile.apply {
            updateProfile = this
            imageUri = Uri.parse(userImage)
            Glide.with(mainActivity)
                .load(imageUri)
                .into(binding.userImage)

            binding.insertId.setText(userName)
            binding.insertComment.setText(userComment)

        }


        selectPicFragment=  SelectPicFragment(binding.slideFrameInUpdateProfile)
            .apply {
            setOnItemClickListener(object :SelectPicFragment.OnItemClickListener{
                override fun onItemClick(lastUri: Uri?) {
                    imageUri =
                        if (lastUri.toString()!=""){
                        lastUri
                    }else{
                        Uri.parse(mainActivity.viewModel.profile.userImage)
                    }
                    Glide.with(mainActivity)
                        .load(imageUri)
                        .into(binding.userImage)
                }
            })
        }
        //슬라이드 레이아웃 view 설정
        selectPicFragment?.let {fragment->
            mainActivity.frManger.beginTransaction()
                .replace(R.id.slide_layout_in_update_profile,fragment)
                .commit()
        }
        setUpListener()
    }

    private fun setUpListener() {
        binding.backButton.setOnClickListener {
            mainActivity.onFragmentGoBack(this@ProfileUpdateFragment)
        }

        //이미지 변경
        binding.userImage.setOnClickListener {
            val state = binding.slideFrameInUpdateProfile.panelState
            // 닫힌 상태일 경우 열기
            if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                binding.slideFrameInUpdateProfile.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            }
            // 열린 상태일 경우 닫기
            else if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
                binding.slideFrameInUpdateProfile.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
        }
        //제출
        binding.commitButton.setOnClickListener {
            updateProfile?.let { up ->
                Log.d("teslsslsll 4 ", up.userName.toString())
                Log.d("teslsslsll 4 ", up.userComment.toString())
                Log.d("teslsslsll 4 ", imageUri.toString())

                userManager.setUserProfile(up,imageUri!!, paramFun = { newProfile,erMessage->
                    if (newProfile != null && erMessage=="") {
                        mainActivity.viewModel.profile = newProfile
                        mainActivity.setProfile(newProfile)
                        mainActivity.onFragmentGoBack(this@ProfileUpdateFragment)
                    }else{
                        //에러 메시지 띄우기
                        Toast.makeText(mainActivity.applicationContext,erMessage
                            ,Toast.LENGTH_SHORT).show()
                    }

                })

            }
        }
    }
}