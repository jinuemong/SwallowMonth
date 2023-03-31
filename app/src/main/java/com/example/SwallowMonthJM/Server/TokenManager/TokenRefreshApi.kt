package com.example.SwallowMonthJM.Server.TokenManager

import com.example.SwallowMonthJM.Model.Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

//Refresh Api 호출을 위한 인터페이스
// 새로운  access , refresh token 반환
interface TokenRefreshApi {
    @POST("user/auth/refresh/")
    @FormUrlEncoded
    suspend fun patchToken(
        @Field("refresh") refresh:String
    ) : Token
}
