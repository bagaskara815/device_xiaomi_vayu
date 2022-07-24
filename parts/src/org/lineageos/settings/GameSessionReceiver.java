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

    private static final String THERMAL_PROP = "persist.deviceparts.game.thermal";
    private static final String THERMAL_NORMAL = "normal";
    private static final String THERMAL_GAME = "game";

    @Override
    public void onReceive(final Context context, Intent intent) {
        String state = THERMAL_NORMAL;

        if (intent.getAction().equals(GAME_START)) {
            state = THERMAL_GAME;
        } else if (intent.getAction().equals(GAME_STOP)) {
            state = THERMAL_NORMAL;
        }

        setThermalState(state);
    }

    private void setThermalState(String state) {
        if (SystemProperties.get(THERMAL_PROP, THERMAL_NORMAL).equals(state))
            return;

        SystemProperties.set(THERMAL_PROP, state);
    }
}
