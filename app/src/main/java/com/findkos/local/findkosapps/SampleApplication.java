/*******************************************************************************
 * Copyright (c) 2013 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.findkos.local.findkosapps;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

//import com.facebook.SessionDefaultAudience;
//import com.sromku.simple.fb.Permission;
//import com.sromku.simple.fb.SimpleFacebook;
//import com.sromku.simple.fb.SimpleFacebookConfiguration;
//import com.sromku.simple.fb.utils.Logger;

public class SampleApplication extends Application {
    static SampleApplication instance;
    public static Context gContext;
    static boolean menu_spesia_is_ready = false;
    SharedPreferences preferences;
    static boolean newNotification = false;
    static String nextUrlNotification;

    public static String getNextUrlNotification() {
        return nextUrlNotification;
    }

    public static void setNextUrlNotification(String nextUrlNotification) {
        SampleApplication.nextUrlNotification = nextUrlNotification;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    private static final String APP_ID = "658178117641132";
    private static final String APP_NAMESPACE = "bundakonicare";

    @Override
    public void onCreate() {
        super.onCreate();

        gContext = this;
        instance = this;


        // set log to true
//		Logger.DEBUG_WITH_STACKTRACE = true;

//		// initialize facebook configuration
//		Permission[] permissions = new Permission[] {
//				Permission.BASIC_INFO,
//				Permission.USER_CHECKINS,
//				Permission.USER_EVENTS,
//				Permission.USER_GROUPS,
//				Permission.USER_LIKES,
//				Permission.USER_PHOTOS,
//				Permission.USER_VIDEOS,
//				Permission.FRIENDS_EVENTS,
//				Permission.PUBLISH_STREAM
//				};
//
//		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
//			.setAppId(APP_ID)
//			.setNamespace(APP_NAMESPACE)
//			.setPermissions(permissions)
//			.setDefaultAudience(SessionDefaultAudience.FRIENDS)
//			.setAskForAllPermissionsAtOnce(false)
//			.build();
//
//		SimpleFacebook.setConfiguration(configuration);


    }

    public static SampleApplication getApplication(Context context) {
        return (SampleApplication) context.getApplicationContext();
    }

    public static SampleApplication getInstance() {
        return instance;
    }

}
