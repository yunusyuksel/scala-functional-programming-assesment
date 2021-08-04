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

    var artists = List[Artist]()

    for(line <- io.getLines()){
      val parsed = line.split('|')

      artists ::= Artist(parsed(0),parsed(3))
    }

    io.close()

    var artistTags = List[ArtistTag]()

    io = Util.readFile("artist_tag.csv")

    for(line <- io.getLines()){
      val parsed = line.split(",")


      artistTags ::= ArtistTag(parsed(0),parsed(1))
    }
    io.close()

    var artistTerms = List[ArtistTerm]()

    io = Util.readFile("artist_term.csv")

    for(line <- io.getLines()){
      val parsed = line.split(",")


      artistTerms ::= ArtistTerm(parsed(0),parsed(1))
    }


    val groupedArtistTags: Map[String,List[ArtistTag]] = artistTags.groupBy(_.id)
    val groupedArtistTerms: Map[String,List[ArtistTerm]] = artistTerms.groupBy(_.id)

    val outputFile = new BufferedWriter(new FileWriter("result.csv"))

    outputFile.write("artist_id,artist_name,tags,terms\n")


    for{
      artist <- artists
      key = artist.id
      tags = groupedArtistTags.get(key).getOrElse(Nil)
      terms = groupedArtistTerms.get(key).getOrElse(Nil)

    }
    {

      val tagString:String = tags match{
        case Nil => ""
        case _ => tags.foldLeft("") {(acc,curTag) =>
          acc + " " + curTag.tagName
        }
      }
      val termString:String = terms match{
        case Nil => ""
        case _ => terms.foldLeft("") {(acc,curTerm) =>
          acc + " " + curTerm.term
        }
      }


      val csvLine = s"${artist.id},${artist.name.trim},${tagString.trim},${termString.trim}\n"

      outputFile.write(csvLine)


    }
  }

  def task2() = {

    var io = Util.readFile("tracks_per_year.csv")
    val outputFile = new BufferedWriter(new FileWriter("result2.csv"))

    var tracks = List[ArtistSong]()

    for(line <- io.getLines){
      val parsed = line.split('|')
      tracks ::= ArtistSong(parsed(0),parsed(1),parsed(2),parsed(3))
    }
    val groupedTracks = tracks.groupBy(track => (track.year,track.artistName))
    groupedTracks.foreach(track => {
      val line = s"${track._1._2},${track._1._1},${track._2.length}\n"
      outputFile.write(line)
    })

  }





}
