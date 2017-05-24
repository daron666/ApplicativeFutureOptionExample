import cats._
import cats.implicits._
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import monix.cats._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Success

/**
  * Created by Aleksei Terekhin on 24/05/2017.
  */
object MonixApplication extends App {

  case class User(id: Long, name: String)
  case class Photo(userId: Long, url: String)
  case class Result(u: User, p: Photo)

  val users = List(User(1L, "John Smith"))
  val photos = List(Photo(1L, "some_pic_url"))

  def findUser(id: Long): Task[Option[User]] = Task.now(users.find(_.id == id))
  def findPhoto(id: Long): Task[Option[Photo]] = Task.now(photos.find(_.userId == id))
  def getResult(id: Long): Task[Option[Result]] = (Applicative[Task] compose Applicative[Option]).map2(findUser(id), findPhoto(id))((u, p) => Result(u, p))

  def showResult(f: Task[Option[Result]]): Task[Unit] = Task.now {
    f.runOnComplete {
      case Success(Some(x)) =>
        println(s"Got result $x")
      case _ =>
        println("Nothing to show")
    }
  }

  def findData(id: Long): Unit = {
    println(s"Fetching data for id == $id")
    val f = getResult(id)
    Await.result(showResult(f).runAsync, 3 seconds)
  }

  println("Starting")
  findData(1L)
  findData(0L)
  println("That's all folks!")

}
