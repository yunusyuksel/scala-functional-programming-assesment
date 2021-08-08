package qimia.bigdata.artist

import java.io.{BufferedWriter, FileWriter}

object Main {
  def main(args:Array[String]) = {
    task1()
    task2()
  }

  def parseCsv[A](path:String,headerFlag:Boolean,seperator:Char,f:Array[String] => A):List[A] = {
    val io = Util.readFile(path)
    val lines = if(headerFlag) io.getLines.drop(1) else io.getLines
    val func = (x:String) => x split seperator
    val result = lines.map((line) => {
      val foo =  func andThen f
      foo(line)
    }).toList
    io.close()

    result
  }

  def task1(): Unit ={

    val artists =parseCsv("unique_artists.csv",false,'|',Artist.parse)
    val artistTags = parseCsv("artist_tag.csv",headerFlag=true,',',ArtistTag.parse)
    val artistTerms = parseCsv("artist_term.csv",headerFlag = true,',',ArtistTerm.parse)

    val groupedArtistTags: Map[String,List[ArtistTag]] = artistTags.groupBy(_.id)
    val groupedArtistTerms: Map[String,List[ArtistTerm]] = artistTerms.groupBy(_.id)

    val outputFile = new BufferedWriter(new FileWriter("result.csv"))

    outputFile.write("artist_id,artist_name,tags,terms\n")

     artists
      .foreach((artist) => {
        val tags = groupedArtistTags.getOrElse(artist.id, Nil)
          .map((tag) => tag.tagName).mkString(",")
        val terms = groupedArtistTerms.getOrElse(artist.id, Nil)
          .map((term) => term.term).mkString(",")
        val csvOutput = s"${artist.id};${artist.name};${tags};${terms}\n"
        outputFile.write(csvOutput)

      })

  }

  def task2() = {
    val tracks = parseCsv("tracks_per_year.csv",false,'|',ArtistSong.parse)
    val groupedTracks = tracks.groupBy(track => (track.year,track.artistName)).toList
    val outputFile = new BufferedWriter(new FileWriter("result2.csv"))
    groupedTracks.sortBy(_._2.length)
    .map{
      case (x,y) => s"${x._2},${x._1},${y.length}\n"
    }.foreach(outputFile.write)
  }

}
