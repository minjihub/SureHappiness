package com.surehappiness.app.activity.model

import android.os.Parcel
import android.os.Parcelable

data class NewPurposeInfo(val id: Int,
                          val userStampCount: Int?,
                          val purpose: String?,
                          val endDate: String?,
                          val stampNum: Int?,
                          val purposeMemo: String?,
                          val user: UserInfo?,
                          val startDate: String?,
                          val successDate: String?,
                          val status: String?): Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readValue(UserInfo::class.java.classLoader) as? UserInfo,
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeValue(userStampCount)
        parcel.writeString(purpose)
        parcel.writeString(endDate)
        parcel.writeValue(stampNum)
        parcel.writeString(purposeMemo)
        parcel.writeString(startDate)
        parcel.writeString(successDate)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewPurposeInfo> {
        override fun createFromParcel(parcel: Parcel): NewPurposeInfo {
            return NewPurposeInfo(parcel)
        }

        override fun newArray(size: Int): Array<NewPurposeInfo?> {
            return arrayOfNulls(size)
        }
    }

}