/*
 * Copyright (C) 2011-2015 PILE Project, Inc. <dev@pileproject.com>
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

package com.pileproject.drive.setting.machine;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.pileproject.drive.R;
import com.pileproject.drive.execution.NxtController.SensorProperty.LineSensor;
import com.pileproject.drive.execution.NxtController.SensorProperty.SoundSensor;
import com.pileproject.drive.preferences.BlockPreferences;

public class NxtThresholdFragment extends DialogFragment {
    private SeekBar mLightSensorSeekBar = null;
    private TextView mLightSensorText = null;

    private SeekBar mSoundSensorSeekBar = null;
    private TextView mSoundSensorText = null;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setTitle(R.string.setting_threshold);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_nxt_threshold, container, false);

        mLightSensorSeekBar = (SeekBar) v.findViewById(R.id.setting_threshold_lightSensor);
        mLightSensorText = (TextView) v.findViewById(R.id.setting_threshold_lightSensorValueText);

        mSoundSensorSeekBar = (SeekBar) v.findViewById(R.id.setting_threshold_soundSensor);
        mSoundSensorText = (TextView) v.findViewById(R.id.setting_threshold_soundSensorValueText);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // NOTE: this is necessary because this screen collapsed on API 23+
        resizeDialog();

        mLightSensorSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLightSensorText.setText(getString(R.string.setting_threshold_unit_percent,
                                                   progress + LineSensor.PctMin));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                BlockPreferences.get(getActivity())
                        .setLineSensorThreshold(seekBar.getProgress() + LineSensor.PctMin);
            }
        });

        int savedLightValue = BlockPreferences.get(getActivity()).getLineSensorThreshold();
        mLightSensorSeekBar.setMax(LineSensor.PctMax - LineSensor.PctMin);
        mLightSensorSeekBar.setProgress(savedLightValue - LineSensor.PctMin);

        mSoundSensorSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSoundSensorText.setText(getString(R.string.setting_threshold_unit_dB,
                                                   progress + SoundSensor.SI_dB_SiMin));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                BlockPreferences.get(getActivity())
                        .setSoundSensorThreshold(seekBar.getProgress() + SoundSensor.SI_dB_SiMin);
            }
        });

        int savedSoundValue = BlockPreferences.get(getActivity()).getSoundSensorThreshold();
        mSoundSensorSeekBar.setMax(SoundSensor.SI_dB_SiMax - SoundSensor.SI_dB_SiMin);
        mSoundSensorSeekBar.setProgress(savedSoundValue - SoundSensor.SI_dB_SiMin);
    }

    /**
     * This function should be called in {@link DialogFragment#onActivityCreated(Bundle)}.
     * Otherwise, the dialog size will never be changed
     */
    private void resizeDialog() {
        Dialog dialog = getDialog();

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        // resize window large enough to display list views
        int dialogWidth = (int) (metrics.widthPixels * 0.8);

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = dialogWidth;
        dialog.getWindow().setAttributes(lp);
    }
}
