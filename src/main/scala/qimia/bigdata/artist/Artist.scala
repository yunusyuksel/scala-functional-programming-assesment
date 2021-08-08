package qimia.bigdata.artist

case class Artist(id:String,name:String)

object Artist {
  def parse(parsed:Array[String]) = {
    Artist(parsed.head,parsed.last)
  }
}
