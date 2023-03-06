package com.example.SwallowMonthJM.Manager

import android.util.Log
import com.example.SwallowMonthJM.Model.*
import com.example.SwallowMonthJM.Network.MasterApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RelationManager(
    private val masterApp:MasterApplication
) {

    fun makeNewFriendRelation(userName:String,otherUser:Int,targetUser: String
                              ,paramFunc: (FriendShip?, message: String?) -> Unit){
        // make friend ship - user - alarm
        addFriendRelation("$userName make FriendShip!",paramFunc={ friendShip, message ->
            if (message==null){
                addFUser(friendShip!!.frId,userName,otherUser, paramFunc = { _,_-> })
                sendAlarm(targetUser,"FriendShip",friendShip.frId, paramFunc = {_,_-> })
                paramFunc(friendShip,null)
            }else{paramFunc(null,"error")}
        })
    }
    private fun addFriendRelation(name : String, paramFunc:(FriendShip?, message:String?)->Unit){
        masterApp.service.addFriendShip(name)
            .enqueue(object : Callback<FriendShip>{
                override fun onResponse(call: Call<FriendShip>, response: Response<FriendShip>) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null, response.errorBody()!!.string())

                    }
                }

                override fun onFailure(call: Call<FriendShip>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }

    fun checkFriend(userName:String, targetUser: Int,paramFunc: (FriendData?, message: String?) -> Unit){
        masterApp.service.checkFriendShip(userName,targetUser)
            .enqueue(object : Callback<FriendData>{
                override fun onResponse(call: Call<FriendData>, response: Response<FriendData>) {
                    if (response.isSuccessful){
                        paramFunc(response.body()!!,null)
                    }else{
                        paramFunc(null,"error:"+response.errorBody()!!.string())
                    }
                }
                override fun onFailure(call: Call<FriendData>, t: Throwable) {
                    Log.d("testseswtestes",t.toString())

                    paramFunc(null,"error")

                }

            })
    }
    fun addFUser(frId:Int,userId:String,otherUser:Int,paramFunc: (FUser?, message: String?) -> Unit){
        masterApp.service.addFUser(frId,userId,otherUser)
            .enqueue(object :  Callback<FUser>{
                override fun onResponse(call: Call<FUser>, response: Response<FUser>) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<FUser>, t: Throwable) {
                    paramFunc(null, "error")
                }

            })
    }

    fun delFriendShip(frId : Int, paramFunc: (FriendShip?, message: String?) -> Unit){
        masterApp.service.delFriendShip(frId)
            .enqueue(object : Callback<FriendShip>{
                override fun onResponse(call: Call<FriendShip>, response: Response<FriendShip>) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null, response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<FriendShip>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }

    fun getFriendList(userName: String, paramFunc: (ArrayList<Profile>?, message: String?) -> Unit){
        masterApp.service.getFriends(userName)
            .enqueue(object : Callback<ArrayList<Profile>>{
                override fun onResponse(
                    call: Call<ArrayList<Profile>>,
                    response: Response<ArrayList<Profile>>
                ) {
                    if(response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Profile>>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }

    fun getRandomProfileList(profileId : Int,paramFunc: (ArrayList<Profile>?, message: String?) -> Unit){
        masterApp.service.getRandomProfile(profileId)
            .enqueue(object : Callback<ArrayList<Profile>>{
                override fun onResponse(
                    call: Call<ArrayList<Profile>>,
                    response: Response<ArrayList<Profile>>
                ) {
                    if(response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Profile>>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }

    fun getMyAlarmList(userName: String,paramFunc: (ArrayList<Alarm>?, message: String?) -> Unit){
        masterApp.service.getAlarmList(userName)
            .enqueue((object :Callback<ArrayList<Alarm>>{
                override fun onResponse(
                    call: Call<ArrayList<Alarm>>,
                    response: Response<ArrayList<Alarm>>
                ) {
                    if(response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null, response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Alarm>>, t: Throwable) {
                    paramFunc(null,"error")
                }

            }))
    }

    fun sendAlarm(targetUser : String, type : String, typeId: Int,paramFunc: (Alarm?, message: String?) -> Unit){
        masterApp.service.addAlarm(targetUser,type,typeId)
            .enqueue(object : Callback<Alarm>{
                override fun onResponse(call: Call<Alarm>, response: Response<Alarm>) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<Alarm>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }

    fun delAlarm(alarmId:Int,paramFunc: (Alarm?, message: String?) -> Unit){
        masterApp.service.delAlarm(alarmId)
            .enqueue(object : Callback<Alarm>{
                override fun onResponse(call: Call<Alarm>, response: Response<Alarm>) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<Alarm>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }


}