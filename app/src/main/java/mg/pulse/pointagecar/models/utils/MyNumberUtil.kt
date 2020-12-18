package mg.pulse.pointagecar.models.utils

fun toStringLeadingZero(value:Int):String{
    if(value>10)
        return value.toString()
    else
        return "0"+value
}