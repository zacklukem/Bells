package io.github.zacklukem.bells

import java.util.*

/**
 * // TODO write javadoc
 *
 * @author zmayhew
 */
class Schedule {
    lateinit var monday: Array<Block>
    lateinit var tuesday: Array<Block>
    lateinit var wednesday: Array<Block>
    lateinit var thursday: Array<Block>
    lateinit var friday: Array<Block>

    constructor() {}
    constructor(monday: Array<Block>, tuesday: Array<Block>, wednesday: Array<Block>, thursday: Array<Block>, friday: Array<Block>) {
        this.monday = monday
        this.tuesday = tuesday
        this.wednesday = wednesday
        this.thursday = thursday
        this.friday = friday
    }

    fun getNext(time: Time): Pair<Time, String> {
        val today: Array<Block> = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> monday
            Calendar.TUESDAY -> tuesday
            Calendar.WEDNESDAY -> wednesday
            Calendar.THURSDAY -> thursday
            Calendar.FRIDAY -> friday
            else -> return Pair(Time(0, 0), "Schools out")
        }

        for (i in today.indices) {
            val block = today[i]

            if (block.start.mathTime() > time.mathTime()) {
                return Pair(block.start, "until " + block.name + " starts at " + block.start.toString())
            }

            if (block.end.mathTime() > time.mathTime()) {
                return Pair(block.end, "until " + block.name + " ends at " + block.end.toString())
            }
        }
        return Pair(Time(0, 0), "Schools out")
    }

    class Block(var name: String, var start: Time, var end: Time)
    class Time {

        var hour: Int = 0
        var minute: Int = 0

        constructor(hour: Int, minute: Int) {
            this[hour] = minute
        }

        constructor(time: String) {
            this.set(time)
        }

        operator fun set(hour: Int, minute: Int) {
            this.hour = hour
            this.minute = minute
        }

        fun set(time: String) {
            val t0 = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            set(Integer.parseInt(t0[0]), Integer.parseInt(t0[1]))
        }

        fun mathTime(): Float {
            return hour.toFloat() + minute.toFloat() / 60
        }

        override fun toString(): String {
            var minuteA = "" + minute
            if (minuteA.length < 2) {
                minuteA = "0$minute"
            }
            return hour.toString() + ":" + minuteA
        }
    }

    companion object {

        val mv_bells = Schedule()

        init {
            val monday = arrayOf(Block("first period", Time(8, 40), Time(9, 25)), Block("second period", Time(9, 30), Time(10, 15)), Block("third period", Time(10, 20), Time(11, 10)), Block("brunch", Time(11, 10), Time(11, 25)), Block("fourth period", Time(11, 30), Time(12, 15)), Block("fifth period", Time(12, 20), Time(13, 5)), Block("lunch", Time(13, 5), Time(13, 45)), Block("sixth period", Time(13, 50), Time(14, 35)), Block("seventh period", Time(14, 40), Time(15, 25)))
            val tuesfri = arrayOf(Block("first period", Time(8, 0), Time(8, 45)), Block("second period", Time(8, 50), Time(9, 35)), Block("tutorial", Time(9, 40), Time(10, 15)), Block("third period", Time(10, 20), Time(11, 10)), Block("brunch", Time(11, 10), Time(11, 25)), Block("fourth period", Time(11, 40), Time(12, 15)), Block("fifth period", Time(12, 20), Time(13, 5)), Block("lunch", Time(13, 5), Time(13, 45)), Block("sixth period", Time(13, 50), Time(14, 35)), Block("seventh period", Time(14, 40), Time(15, 25)))
            val wednesday = arrayOf(Block("fourth period", Time(8, 55), Time(10, 30)), Block("tutorial", Time(10, 35), Time(11, 15)), Block("brunch", Time(11, 15), Time(11, 30)), Block("fifth period", Time(11, 35), Time(13, 5)), Block("lunch", Time(13, 5), Time(13, 45)), Block("sixth period", Time(13, 50), Time(15, 20)))
            val thursday = arrayOf(Block("first period", Time(8, 0), Time(9, 30)), Block("second period", Time(9, 40), Time(11, 10)), Block("brunch", Time(11, 10), Time(11, 25)), Block("third period", Time(11, 30), Time(13, 5)), Block("lunch", Time(13, 5), Time(13, 45)), Block("seventh period", Time(13, 50), Time(15, 20)))
            mv_bells.monday = monday
            mv_bells.tuesday = tuesfri
            mv_bells.wednesday = wednesday
            mv_bells.thursday = thursday
            mv_bells.friday = tuesfri
        }
    }
}
