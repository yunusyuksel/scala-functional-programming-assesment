package qimia.bigdata.artist

case class ArtistTerm(id:String,term:String) {

}

object ArtistTerm{
  def parse(parsed:Array[String]) = {
    ArtistTerm(parsed(0),parsed(1))
  }
}
