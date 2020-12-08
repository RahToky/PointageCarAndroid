package mg.pulse.pointagecar.viewmodels

import mg.pulse.pointagecar.models.CollaboService
import mg.pulse.pointagecar.models.entities.Collaborateur

class CollaboViewModel {

    private val collaboService:CollaboService = CollaboService()

    public fun getCollaboList():List<Collaborateur> = collaboService.getCollaboList()

}