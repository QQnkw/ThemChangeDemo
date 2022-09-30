package com.them.change.lib.util;


import androidx.annotation.StringDef;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@StringDef({
        SkinUpdateType.SKIN,
        SkinUpdateType.FONT,
        SkinUpdateType.LANGUAGE,
        SkinUpdateType.ALL
})
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface SkinUpdateType {
    String SKIN = "Skin";
    String FONT = "Font";
    String LANGUAGE = "Language";
    String ALL = "All";
}