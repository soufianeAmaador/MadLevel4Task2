package com.example.madlevel4task2v20

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
@Entity(tableName = "GameHistoryTable")
data class Game(

    @PrimaryKey
    @ColumnInfo(name = "timestamp")
    var timestamp: Date,
    @ColumnInfo(name = "result")
    var result: Result,
    @ColumnInfo(name = "imageComputer")
    var imageComputer: Number,
    @ColumnInfo(name = "imageUser")
    var imageUser: Number

) : Parcelable

enum class Result(val result: String){
    USER_WINS("You win!"),
    COMPUTER_WINS("Computer wins!"),
    DRAW("DRAW")
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun resultToString(result: Result): String? {
        return result.name
    }

    @TypeConverter
    fun StringToResult(result: String): Result? {
        return Result.valueOf(result)
    }

    @TypeConverter
    fun NumberToInt(number: Number): Int {
        return number.toInt()
    }

    @TypeConverter
    fun intToNumber(int: Int): Number {
        return int
    }
}
