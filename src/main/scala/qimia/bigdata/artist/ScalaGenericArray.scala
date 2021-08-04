package qimia.bigdata.artist

import scala.reflect.ClassTag

class ScalaGenericArray[T](implicit classTag:ClassTag[T], len:Integer ){ //Let the compiler help us pass in type information

  val arr: Array[T] = new Array[T](len)

  }


