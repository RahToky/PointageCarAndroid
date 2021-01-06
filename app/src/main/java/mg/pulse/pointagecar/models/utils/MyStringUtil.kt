package mg.pulse.pointagecar.models.utils

fun getNameInitial(firstName:String?, lastName:String):String{
 return "${firstName?.get(0)}${lastName[0]}"
}