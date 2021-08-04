package qimia.bigdata.artist

import scala.io.{BufferedSource, Source}
import scala.reflect.ClassManifest

object Util {
  def readFile(fileName:String) ={
    val io = Source.fromFile(fileName)
    io
  }

  def parseCsv(fileName:String) = {
    var io = readFile(fileName)

  }

  def artistListFactory(io:BufferedSource, key:String) = {
    var pattern=' '

    key match{
      case "Artist" => {
          pattern = '|'
          var list = List[Artist]()
          for(line <- io.getLines){
            val parsed = line.split(pattern)
            list ::= Artist(parsed(0),parsed(3))
          }
          list
      }
      case "ArtistTerms" => {
          pattern=','
          var list = List[ArtistTerm]()
          for(line <- io.getLines){
            val parsed = line.split(pattern)
            list ::= ArtistTerm(parsed(0),parsed(1))
          }
          list
      }
      case "ArtistTag" => {
        pattern= ','
        var list = List[ArtistTag]()
      }
    }


  }

}
