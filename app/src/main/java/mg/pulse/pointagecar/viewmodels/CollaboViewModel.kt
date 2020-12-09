package mg.pulse.pointagecar.viewmodels

import mg.pulse.pointagecar.models.services.CollaboService
import mg.pulse.pointagecar.models.entities.Collaborateur

class CollaboViewModel {

    private val collaboService: CollaboService = CollaboService()

    fun getCollaboList():List<Collaborateur> = collaboService.getCollaboList()

}