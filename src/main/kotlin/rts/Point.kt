package rts

data class Point(val x: Int, val y: Int)

/** 좌표 출력용 */
fun Point.fmt(): String = "(${this.x}, ${this.y})"
