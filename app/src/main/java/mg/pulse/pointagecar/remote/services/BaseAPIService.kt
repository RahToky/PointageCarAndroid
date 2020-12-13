package mg.pulse.pointagecar.remote.services

import mg.pulse.pointagecar.remote.PointageAPI

abstract class BaseAPIService {
    protected val pointageAPI: PointageAPI? = PointageAPI.getInstance()
}