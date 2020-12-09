package mg.pulse.pointagecar.models.services

import mg.pulse.pointagecar.models.entities.Collaborateur

class CollaboService {

    fun getCollaboList():List<Collaborateur> = listOf(
        Collaborateur("1","IT0001","Edmond","RAKOTONDRAZAFY"),
        Collaborateur("2","IT0002","Domoina","RASOLONIRINA"),
        Collaborateur("3","IT0003","Benjamin","RANDRIANJAFY"),
        Collaborateur("4","IT0004","Lala","HARILANTO"),
        Collaborateur("5","IT0005","Tahina","RANDRIANIRINA"),
        Collaborateur("6","IT0006","Rivo","RAKOTOARISOA"),
        Collaborateur("7","IT0007","Mamitiana","RANDIAMBOAVONJY"),
        Collaborateur("8","IT0008","Daniel","RAKOTOMANGA"),
        Collaborateur("9","IT0009","Toky","RANDRIANJAFY"),
        Collaborateur("10","IT0010","Mirana","ANDRIANIAINA"),
        Collaborateur("11","IT0011","Clara","RAVELOMANANTSOA")
    )

}