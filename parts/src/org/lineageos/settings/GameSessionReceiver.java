/*
 * Copyright (C) 2022 Chaldeaprjkt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;

public class GameSessionReceiver extends BroadcastReceiver {

    private static final boolean DEBUG = false;
    private static final String TAG = "DeviceParts";
    private static final String GAME_START = "io.chaldeaprjkt.gamespace.action.GAME_START";
    private static final String GAME_STOP = "io.chaldeaprjkt.gamespace.action.GAME_STOP";

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(GAME_START)) {
            onGameStart();
        } else if (intent.getAction().equals(GAME_STOP)) {
            onGameStop();
        }
    }

    private void onGameStart() {
        SystemProperties.set("persist.deviceparts.game.thermal", "game");
    }

    private void onGameStop() {
        SystemProperties.set("persist.deviceparts.game.thermal", "normal");
    }
}
