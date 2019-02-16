package com.davidbragadeveloper.todoapp.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.util.Date



interface Taskeable : Parcelable


enum class HighPriority (val priorityValue: Int){

    HIGH(2),
    MEDIUM(1),
    LOW(0);



}

data class Task (val id: Long,
                 val content: String,
                 val createdAt: Date,
                 val isDone: Boolean,
                 val highPriority: HighPriority
): Taskeable{
    constructor(parcel: Parcel) : this (
        parcel.readLong(),
        parcel.readString() ?: "",
        Date(parcel.readLong()),
        parcel.readByte() != 0.toByte(),
        HighPriority.valueOf(parcel.readString()?:"LOW")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(content)
        parcel.writeLong(createdAt.time)
        parcel.writeByte(if (isDone) 1 else 0)
        parcel.writeString(highPriority.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }

}

data class Subtask(
    val id : Long,
    val content: String,
    val createdAt: Date,
    val isDone: Boolean,
    val highPriority: HighPriority,
    val taskId: Long
):Taskeable{

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()?:"",
         Date(parcel.readLong()),
        parcel.readByte() != 0.toByte(),
         HighPriority.valueOf(parcel.readString()?:"LOW"),
         parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(content)
        parcel.writeLong(createdAt.time)
        parcel.writeByte(if (isDone) 1 else 0)
        parcel.writeString(highPriority.toString())
        parcel.writeLong(taskId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }

}

