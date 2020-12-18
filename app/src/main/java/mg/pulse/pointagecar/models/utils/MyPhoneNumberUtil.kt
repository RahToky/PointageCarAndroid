package mg.pulse.pointagecar.models.utils

fun formatPhoneNumberMG(phoneNumber:String?):String = "${phoneNumber?.substring(0,3)} ${phoneNumber?.substring(3,5)} ${phoneNumber?.substring(5,8)} ${phoneNumber?.substring(8)}"?:""
