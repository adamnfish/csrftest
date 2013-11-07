package controllers

import play.api._
import play.api.mvc._
import play.filters.csrf._


object CSRFController extends Controller {

  def get = CSRFAddToken {
    Action { implicit request =>
      Ok(views.html.form())
    }
  }

  def post = CSRFCheck {
    Action { implicit request =>
      Ok(views.html.form())
    }
  }
}
