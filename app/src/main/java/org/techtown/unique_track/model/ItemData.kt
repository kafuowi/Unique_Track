package org.techtown.unique_track.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ItemData(@StringRes val stringResourceId1 : Int,@StringRes val stringResourceId2 : Int, @DrawableRes val imageResourceId : Int) {

}