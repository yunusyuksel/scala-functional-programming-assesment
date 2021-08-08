package qimia.bigdata.artist

case class ArtistSong(year:String,artistId:String,artistName:String,songTitle:String)

object ArtistSong{
  def parse(parsed:Array[String]) = {
    ArtistSong(parsed(0),parsed(1),parsed(2),parsed(3))
  }
}


