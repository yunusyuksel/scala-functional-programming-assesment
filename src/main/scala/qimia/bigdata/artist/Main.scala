package qimia.bigdata.artist

import java.io.{BufferedWriter, FileWriter}
import scala.collection.mutable.ListBuffer
import scala.io.Source



object Main {
  def main(args:Array[String]) = {
    task1()
    task2()
  }


  def task1(): Unit ={
    var io = Util.readFile("unique_artists.csv")

    val artistLines = io.getLines
    val artists =
      artistLines
        .map((line) => {
          val parsed = line.split('|')
          Artist(parsed(0),parsed(3))
        }).toList

    io.close()



    io = Util.readFile("artist_tag.csv")


    val artistTagLines = io.getLines

    val artistTags = artistTagLines
      .map((line) => {
        val parsed = line.split(",")
        ArtistTag(parsed(0),parsed(1))
      }).toList


    io.close()



    io = Util.readFile("artist_term.csv")

    val artistTerms = io.getLines
      .map((line) => {
        val parsed = line.split(",")
        ArtistTerm(parsed(0),parsed(1))
      }).toList

    val groupedArtistTags: Map[String,List[ArtistTag]] = artistTags.groupBy(_.id)
    val groupedArtistTerms: Map[String,List[ArtistTerm]] = artistTerms.groupBy(_.id)

    val outputFile = new BufferedWriter(new FileWriter("result.csv"))

    outputFile.write("artist_id,artist_name,tags,terms\n")

     artists
      .foreach((artist) => {
        val tags = groupedArtistTags.get(artist.id).getOrElse(Nil)
          .map((tag) => tag.tagName).mkString(",")
        val terms = groupedArtistTerms.get(artist.id).getOrElse(Nil)
          .map((term) => term.term).mkString(",")

        val csvOutput = s"${artist.id};${artist.name.trim};${tags.trim};${terms.trim}\n"
        outputFile.write(csvOutput)

      })


  }

  def task2() = {

    var io = Util.readFile("tracks_per_year.csv")
    val outputFile = new BufferedWriter(new FileWriter("result2.csv"))

    val tracks = io.getLines
      .map((line) => {
        val parsed = line.split('|')
        ArtistSong(parsed(0),parsed(1),parsed(2),parsed(3))
      }).toList
    io.close()


    val groupedTracks = tracks.groupBy(track => (track.year,track.artistName))
    groupedTracks.foreach(track => {
      val line = s"${track._1._2},${track._1._1},${track._2.length}\n"
      outputFile.write(line)
    })



  }





}
