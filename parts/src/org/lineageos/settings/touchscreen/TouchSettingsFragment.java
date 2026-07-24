/**
 * Copyright (C) 2020 The LineageOS Project
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
package org.lineageos.settings.touchscreen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;

import com.android.settingslib.widget.MainSwitchPreference;

import org.lineageos.settings.R;
import org.lineageos.settings.widget.SeekBarPreference;

public class TouchSettingsFragment extends PreferenceFragment
        implements OnCheckedChangeListener, OnPreferenceChangeListener {

    private SharedPreferences mSharedPrefs;
    private SeekBarPreference mTouchSensitivity;
    private SeekBarPreference mTouchResponse;
    private SeekBarPreference mTouchResistant;
    private MainSwitchPreference mGameMode;

    private String packageName = "";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.touch_settings);
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        Bundle bundle = getArguments();
        String appName = "";
        if (bundle != null) {
            appName = bundle.getString("appName", "");
            packageName = bundle.getString("packageName", "");
        }

        getActivity().setTitle(appName.isEmpty() ? packageName : appName);

        mGameMode = (MainSwitchPreference) findPreference(Constants.PREF_TOUCH_GAME_MODE);
        mGameMode.setPersistent(false);
        mGameMode.addOnSwitchChangeListener(this);

        mTouchResistant = (SeekBarPreference) findPreference(Constants.PREF_TOUCH_RESISTANT);
        mTouchResponse = (SeekBarPreference) findPreference(Constants.PREF_TOUCH_RESPONSE);
        mTouchSensitivity = (SeekBarPreference) findPreference(Constants.PREF_TOUCH_SENSITIVITY);

        mTouchResistant.setPersistent(false);
        mTouchResponse.setPersistent(false);
        mTouchSensitivity.setPersistent(false);

        mTouchResistant.setOnPreferenceChangeListener(this);
        mTouchResponse.setOnPreferenceChangeListener(this);
        mTouchSensitivity.setOnPreferenceChangeListener(this);

        updateDefaults();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mGameMode.setChecked(isChecked);
        mTouchSensitivity.setEnabled(isChecked);
        mTouchResponse.setEnabled(isChecked);
        mTouchResistant.setEnabled(isChecked);
        updateTouchModes(isChecked ? 1 : 0, Constants.TOUCH_GAME_MODE);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey();
        int value = (newValue instanceof Integer)
                ? (Integer) newValue
                : Integer.parseInt(newValue.toString());

        if (Constants.PREF_TOUCH_RESPONSE.equals(key)) {
            updateTouchModes(value, Constants.TOUCH_RESPONSE);
            return true;
        } else if (Constants.PREF_TOUCH_SENSITIVITY.equals(key)) {
            updateTouchModes(value, Constants.TOUCH_SENSITIVITY);
            return true;
        } else if (Constants.PREF_TOUCH_RESISTANT.equals(key)) {
            updateTouchModes(value, Constants.TOUCH_RESISTANT);
            return true;
        }
        return false;
    }

    private void updateDefaults() {
        String[] values = getTouchValues().split(",");
        boolean modeEnabled = Integer.parseInt(values[Constants.TOUCH_GAME_MODE]) == 1;
        mGameMode.setChecked(modeEnabled);

        mTouchSensitivity.setEnabled(modeEnabled);
        mTouchResponse.setEnabled(modeEnabled);
        mTouchResistant.setEnabled(modeEnabled);

        mTouchResponse.setProgress(Integer.parseInt(values[Constants.TOUCH_RESPONSE]));
        mTouchSensitivity.setProgress(Integer.parseInt(values[Constants.TOUCH_SENSITIVITY]));
        mTouchResistant.setProgress(Integer.parseInt(values[Constants.TOUCH_RESISTANT]));
    }

    private void writeTouchValues(String modes) {
        if (packageName == null || packageName.isEmpty()) {
            return;
        }
        mSharedPrefs.edit().putString(packageName, modes).apply();
    }

    public String getTouchValues() {
        String values = mSharedPrefs.getString(packageName, null);
        if (values == null || values.isEmpty()) {
            values = "0,0,0,0";
        }
        String[] parts = values.split(",");
        if (parts.length < 4) {
            values = "0,0,0,0";
        }
        return values;
    }

    public void updateTouchModes(int value, int mode) {
        if (packageName == null || packageName.isEmpty()) {
            return;
        }
        String[] values = getTouchValues().split(",");
        if (values.length < 4) {
            values = new String[] {"0", "0", "0", "0"};
        }
        values[mode] = String.valueOf(value);
        String finalValues = values[Constants.TOUCH_GAME_MODE] + "," + values[Constants.TOUCH_RESPONSE] + ","
                + values[Constants.TOUCH_SENSITIVITY] + "," + values[Constants.TOUCH_RESISTANT];
        writeTouchValues(finalValues);
    }
}
