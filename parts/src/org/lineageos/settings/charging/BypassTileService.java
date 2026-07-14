/*
 * Copyright (C) 2021 Paranoid Android
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

package org.lineageos.settings.charging;

import android.content.Context;
import android.os.SystemProperties;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import org.lineageos.settings.R;
import org.lineageos.settings.utils.FileUtils;

public class BypassTileService extends TileService {

    private static final String BYPASS_PROP = "persist.deviceparts.bypass";
    private static final String BYPASS_NODE = "/sys/class/power_supply/battery/input_suspend";
    private static final int BYPASS_OFF = 0;
    private static final int BYPASS_ON = 1;

    private Context context;
    private Tile tile;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    private int getCurrentMode() {
        String val = FileUtils.readOneLine(BYPASS_NODE);
        if (val == null) return -1;
        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void updateTile() {
        int mode = getCurrentMode();
        if (mode < 0) {
            tile.setState(Tile.STATE_UNAVAILABLE);
            tile.setSubtitle(context.getResources().getString(R.string.kernel_not_supported));
            tile.updateTile();
            return;
        }
        boolean active = mode == BYPASS_ON;
        tile.setState(active ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.setSubtitle(active
                ? context.getResources().getString(R.string.bypass_tile_active)
                : context.getResources().getString(R.string.bypass_tile_inactive));
        tile.updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        tile = getQsTile();
        if (!FileUtils.fileExists(BYPASS_NODE)) {
            tile.setState(Tile.STATE_UNAVAILABLE);
            tile.setSubtitle(getResources().getString(R.string.kernel_not_supported));
            tile.updateTile();
            return;
        }
        updateTile();
    }

    @Override
    public void onClick() {
        super.onClick();
        int mode = getCurrentMode();
        if (mode < 0) return;

        int newMode = (mode == BYPASS_ON) ? BYPASS_OFF : BYPASS_ON;
        FileUtils.writeLine(BYPASS_NODE, Integer.toString(newMode));
        SystemProperties.set(BYPASS_PROP, Integer.toString(newMode));
        updateTile();
    }
}
