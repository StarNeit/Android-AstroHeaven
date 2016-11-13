package com.ah.androidapp.model;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.ah.androidapp.model.Settings;

/**
 * Created by coneits on 8/21/16.
 */
public class SettingsManangement {

    public static void saveSettings(Settings settings){settings.save();}

    public static void removeSettings(){
        new Delete().from(Settings.class).execute();
    }

    public static Settings getSettings() {
        return new Select().from(Settings.class).executeSingle();
    }
}
