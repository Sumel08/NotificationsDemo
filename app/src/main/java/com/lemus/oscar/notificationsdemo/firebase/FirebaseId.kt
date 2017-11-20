package com.lemus.oscar.notificationsdemo.firebase

import com.google.firebase.iid.FirebaseInstanceIdService
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId



/**
 * Created by oscar on 20/11/17.
 */

class FirebaseId: FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        //super.onTokenRefresh()
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken)

        super.onTokenRefresh()
    }
}