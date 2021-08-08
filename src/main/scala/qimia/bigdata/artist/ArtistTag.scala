package qimia.bigdata.artist

case class ArtistTag(id:String,tagName:String) {

}

object ArtistTag {
  def parse(parsed:Array[String]) = {
    ArtistTag(parsed(0),parsed(1))
  }
}
