package org.techtown.unique_track.data

import org.techtown.unique_track.R
import org.techtown.unique_track.model.ItemData

class Datasource {
    fun loadItemData():List<ItemData>{
        return listOf<ItemData>(
            ItemData(R.string.item1,R.string.date1,R.drawable.image1),
            ItemData(R.string.item2,R.string.date2,R.drawable.image2),
            ItemData(R.string.item3,R.string.date3,R.drawable.image3)
        )
    }
}