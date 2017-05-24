import cats._
import cats.implicits._

import monix.cats._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future, Promise}
import scala.util.Success

object Application extends App {
  case class User(id: Long, name: String)
  case class Photo(userId: Long, url: String)
  case class Result(u: User, p: Photo)

  val users = List(User(1L, "John Smith"))
  val photos = List(Photo(1L, "some_pic_url"))

  def findUser(id: Long): Future[Option[User]] = Future.successful(users.find(_.id == id))
  def findPhoto(id: Long): Future[Option[Photo]] = Future.successful(photos.find(_.userId == id))
  def getResult(id: Long): Future[Option[Result]] = (Applicative[Future] compose Applicative[Option]).map2(findUser(id), findPhoto(id))((u, p) => Result(u, p))

  def showResult(f: Future[Option[Result]]): Future[Unit] = {
    val promise = Promise[Unit]
    f.onComplete {
        case Success(Some(x)) =>
          println(s"Got result $x")
          promise.complete(Success(()))
        case _ =>
          println("Nothing to show")
          promise.complete(Success(()))
    }
    promise.future
  }

  def findData(id: Long): Unit = {
    println(s"Fetching data for id == $id")
    val f = getResult(id)
    Await.result(showResult(f), 3 seconds)
  }

  println("Starting")
  findData(1L)
  findData(0L)
  println("That's all folks!")
}
