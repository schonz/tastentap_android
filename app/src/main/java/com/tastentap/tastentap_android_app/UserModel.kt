package com.tastentap.tastentap_android_app

import android.util.Log
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.regions.Regions

object UserModel {
    private var _userToken: String? = null
    private const val poolId = BuildConfig.COGNITO_USER_POOL_ID
    private const val clientId = BuildConfig.COGNITO_CLIENT_ID
    private const val clientSecret = BuildConfig.COGNITO_CLIENT_SECRET

    fun establish(username: String, pass: String, context: android.content.Context, callback: (Boolean) -> Unit){
        val userPool = CognitoUserPool(context, poolId, clientId, clientSecret, Regions.US_EAST_1)
        val authHandler = object : AuthenticationHandler {
            override fun onSuccess(userSession: CognitoUserSession, newDevice: CognitoDevice?) {
                if(userSession.idToken.jwtToken != null) {
                    _userToken = userSession.idToken.jwtToken
                    callback(true)
                }
                else {
                    Log.e("UserModel", "ID Token is null")
                    callback(false)
                }
            }

            override fun getAuthenticationDetails(authContinuation: AuthenticationContinuation, userId: String) {
                val authenticationDetails = AuthenticationDetails(userId, pass, null)
                authContinuation.setAuthenticationDetails(authenticationDetails)
                authContinuation.continueTask()
            }

            override fun onFailure(exception: Exception) {
                exception.printStackTrace()
                callback(false)
            }

            override fun getMFACode(multiFactorAuthenticationContinuation: MultiFactorAuthenticationContinuation) {
                // Handle MFA if needed
            }

            override fun authenticationChallenge(continuation: ChallengeContinuation) {
                // Handle authentication challenge if needed
            }
        }

        val userObj = userPool.getUser(username)
        userObj.getSessionInBackground(authHandler)
    }
}