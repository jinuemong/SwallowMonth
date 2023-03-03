package com.example.SwallowMonthJM.Network

import com.example.SwallowMonthJM.Model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

//url 관리 
interface RetrofitService {

    //로그인 요청
    @POST("user/login/")
    @FormUrlEncoded
    fun loginUser(
        @Field("userName") userName: String,
        @Field("password") password: String
    ): Call<User>

    // 가입
    @POST("user/register/")
    @FormUrlEncoded
    fun registerUser(
        @Field("userName") userName: String,
        @Field("password") password: String
    ): Call<User>

    // 유저 얻기
    @GET("user/profile/{profileId}/")
    fun getProfile(
        @Field("profileId") profileId : Int
    ): Call<Profile>

    // 유저 조회
    @GET("user/profile/")
    fun searchProfile(
        @Query("userName") userName : String
    ): Call<ArrayList<Profile>>

    // profile 수정
    @Multipart
    @POST("user/update/profile/")
    fun setUserProfile(
        @Part("profileId") profileId: RequestBody,
        @Part("userName") userName: RequestBody,
        @Part("userComment") userComment: RequestBody,
        @Part userImage: MultipartBody.Part?,
    ) : Call<Profile>

    // 전체 monthData 받기
    @GET("month/monthDatas/")
    fun getMonthDataList(
        @Query(value = "userName", encoded = true) userName: String
    ): Call<ArrayList<MonthData>>

    //특정 monthData 받기 (1개)
    @GET("month/monthDatas/")
    fun getMonthKeyDate(
        @Query(value = "userName", encoded = true) userName: String,
        @Query(value = "keyDate", encoded = true) keyDate: String
    ): Call<ArrayList<MonthData>>

    //Month Data 추가
    @POST("month/monthDatas/")
    @FormUrlEncoded
    fun makeMonthData(
        @Field("userId") userId: String,
        @Field("keyDate") keyDate: String,
        @Field("totalPer") totalPer: Int,
        @Field("totalPoint") totalPoint: Int,
        @Field("taskCount") taskCount: Int,
        @Field("dayRoutineCount") dayRoutineCount: Int,
        @Field("doneTask") doneTask: Int,
        @Field("clearRoutine") clearRoutine: Int,
    ): Call<MonthData>

    //Month Data 삭제
    @DELETE("month/monthDatas/{monthId}/")
    fun delMonthData(
        @Path("monthId") monthId: Int,
    ): Call<MonthData>

    //Task 추가
    @POST("task/tasks/")
    @FormUrlEncoded
    fun addTask(
        @Field("monthId") monthId: Int,
        @Field("userId") userId: String,
        @Field("dayIndex") dayIndex: Int,
        @Field("text") text: String,
        @Field("isDone") isDone: Boolean,
        @Field("iconType") iconType: Int,
        @Field("level") level: Int,
        @Field("per") per: Int,
    ): Call<Task>

    //Task 제거
    @DELETE("task/tasks/{id}/")
    fun delTask(
        @Path("id") id: Int,
    ): Call<Task>

    //month task 얻기
    @GET("task/tasks/")
    fun getDayTaskList(
        @Query(value = "userName", encoded = true) userName: String,
        @Query(value = "monthId", encoded = true) monthId: Int,
        ): Call<ArrayList<Task>>

    //Routine 추가
    @POST("routine/routines/")
    @FormUrlEncoded
    fun addRoutine(
        @Field("userId") userId: String,
        @Field("monthId") monthId: Int,
        @Field("keyDate") keyDate: String,
        @Field("text") text: String,
        @Field("cycle") cycle: Int,
        @Field("startNum") startNum: Int,
        @Field("totalRoutine") totalRoutine: Int,
        @Field("clearRoutine") clearRoutine: Int,
        @Field("iconType") iconType: Int,
        @Field("topText") topText: String,
    ): Call<Routine>

    //Routine 제거 routineId
    @DELETE("routine/routines/{routineId}/")
    fun delRoutine(
        @Path("routineId") routineId: Int,
    ): Call<Routine>

    //dayRoutine 추가
    @POST("routine/dayRoutines/")
    @FormUrlEncoded
    fun addDayRoutine(
        @Field("routineId") routineId: Int,
        @Field("monthId") monthId: Int,
        @Field("dayIndex") dayIndex: Int,
        @Field("clear") clear: Boolean,
    ): Call<DayRoutine>

    //month routine 얻기
    @GET("routine/routines/")
    fun getMonthRoutineList(
        @Query(value = "userName", encoded = true) userName: String,
        @Query(value = "keyDate", encoded = true) keyDate: String,
    ): Call<ArrayList<Routine>>


    //Patch Data

    //Month 수정
    @PATCH("month/monthDatas/{monthId}/")
    fun setMonthData(
        @Path("monthId")monthId: Int,
        @Body monthData: MonthData
    ):Call<MonthData>

    //Task 수정
    @PATCH("task/tasks/{id}/")
    fun setTaskData(
        @Path("id")id : Int,
        @Body task : Task
    ):Call<Task>

    //Routine 수정
    @PATCH("routine/routines/{routineId}/")
    fun setRoutineData(
        @Path("routineId")routineId : Int,
        @Body routine : Routine
    ):Call<Routine>

    //dayRoutine 수정
    @PATCH("routine/dayRoutines/{id}/")
    fun setDayRoutineData(
        @Path("id")id : Int,
        @Body dayRoutine : DayRoutine
    ):Call<DayRoutine>


    ////Relation//

    // add FriendShip
    @POST("relation/friendShips/")
    fun addFriendShip(
        @Field("name") name : String,
    ):Call<FriendShip>

    //add FUser
    @FormUrlEncoded
    @POST("relation/fusers/")
    fun addFUser(
        @Field("frId") frId:Int,
        @Field("userId") userId:String,
        @Field("otherUser") otherUser:Int,
    ):Call<FUser>


    //친구 관계 삭제 시 친구 리스트 자동 삭제
    @DELETE("relation/friendShips/{frId}")
    fun delFriendShip(
        @Path("frId") frId: Int,
    ):Call<FriendShip>

    // 내 친구 리스트
    @POST("relation/myFriends/")
    fun getMyFriends(
        @Field("userName")userName : String
    ):Call<ArrayList<FriendData>>

    //랜덤 유저 얻기
    @POST("relation/randomProfile/")
    fun getRandomProfile(
        @Field("profileId")profileId : Int,
    ):Call<ArrayList<Profile>>

    //내 알림 리스트 얻기
    @GET("user/alarms/")
    fun getAlarmList(
        @Query(value = "search", encoded = true) userName: String
    ): Call<ArrayList<Alarm>>

    // 알림 보내기
    @FormUrlEncoded
    @POST("user/alarms/")
    fun addAlarm(
        @Field("userId")userName: String,
        @Field("type")type:String,
        @Field("typeId")typeId : Int,
    ): Call<Alarm>

    @DELETE("user/alarms/{alarmId}/")
    fun delAlarm(
        @Path("alarmId")alarmId:Int,
    ): Call<Alarm>
}