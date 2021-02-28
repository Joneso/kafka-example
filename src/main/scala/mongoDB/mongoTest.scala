package mongoDB

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.{BasicDBObject, DBCollection, ServerAddress}
import com.mongodb.casbah.{MongoClient, MongoCredential, MongoDB}

object mongoTest {
  def main(args: Array[String]):Unit = {
    val collection = createDatabase("localhost", 27017, "mongo", "mongo", "angleak").getCollection("user")
    updateCollection(collection)
  }


  def createDatabase(url:String, port:Int, dbName:String, loginName:String, password: String): MongoDB = {
    val server = new ServerAddress(url, port)
    val credentials = MongoCredential.createCredential(loginName,dbName,password.toCharArray)
    val mongoClient = MongoClient(server, List(credentials))
    mongoClient.getDB(dbName)
  }

  def insertToCollection(collection : DBCollection) : Unit = {
    for(i <- 1 to 10){
      collection.insert(MongoDBObject("name"->"Jack%d".format(i), "email"->"Jonas%d@qq.com".format(i)))
    }
  }
  def updateCollection(collection: DBCollection) :Unit = {
    val query = MongoDBObject("name"->"Jack1")
    collection.update(query, new BasicDBObject("$set", MongoDBObject("email"->"Jonas1@sina.com")))
    collection.find(query).forEach(println)
  }
  def deleteData(collection: DBCollection):Unit = {
      val delete = MongoDBObject("name"->"Jack9")
      println(collection.remove(delete).getN())
  }
}
