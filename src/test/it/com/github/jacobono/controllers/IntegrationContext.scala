package controllers

import play.api.test.WithApplicationLoader
import SilhouetteMacwireApplicationLoader

class IntegrationContext extends WithApplicationLoader(
  applicationLoader = new SilhouetteMacwireApplicationLoader
)
