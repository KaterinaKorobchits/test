package my.luckydog.presentation

import android.os.Parcel
import android.os.Parcelable

data class ParcelItem(
    val firstString: String,
    val secondString: String,
    val thirdString: String,
    val int: Int
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<ParcelItem> {
            override fun createFromParcel(parcel: Parcel) = ParcelItem(parcel)
            override fun newArray(size: Int) = arrayOfNulls<ParcelItem>(size)
        }
    }

    private constructor(parcel: Parcel) : this(
        firstString = parcel.readString() ?: "",
        secondString = parcel.readString() ?: "",
        thirdString = parcel.readString() ?: "",
        int = parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstString)
        parcel.writeString(secondString)
        parcel.writeString(thirdString)
        parcel.writeInt(int)
    }

    override fun describeContents() = 0
}