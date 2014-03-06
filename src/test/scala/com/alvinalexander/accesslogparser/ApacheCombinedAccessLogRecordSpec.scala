package com.alvinalexander.accesslogparser

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter
import org.scalatest.GivenWhenThen

class ApacheCombinedAccessLogRecordSpec extends FunSpec with BeforeAndAfter with GivenWhenThen {

  var records: Seq[String] = _

  before {
      records = SampleCombinedAccessLogRecords.data
  }
  
  describe("Our test data ...") {
      it("should have seven records") {
          assert(records.length == 7)
      }
  }

  describe("The first record should not be null") {
      it("the data fields should be correct") {
          Given("the first sample log record")
          records = SampleCombinedAccessLogRecords.data
          val rec = ApacheCombinedAccessLogParser.parse(records(0))
          println("IP ADDRESS: " + rec.get.clientIpAddress)
          Then("parsing record(0) should not return None")
              assert(rec != None)
          And("the ip address should be correct")
              assert(rec.get.clientIpAddress == "124.30.9.161")              
          And("client identity")
              assert(rec.get.rfc1413ClientIdentity == "-")          
          And("remote user")
              assert(rec.get.remoteUser == "-")          
          And("date/time")
              assert(rec.get.dateTime == "[21/Jul/2009:02:48:11 -0700]")          
          And("request")
              assert(rec.get.request == "GET /java/edu/pj/pj010004/pj010004.shtml HTTP/1.1")          
          And("status code should be 200")
              assert(rec.get.serverStatusCode == "200")          
          And("bytes sent should be 16731")
              assert(rec.get.bytesSent == "16731")          
          And("referer")
              assert(rec.get.referer == "http://www.google.co.in/search?hl=en&client=firefox-a&rlz=1R1GGGL_en___IN337&hs=F0W&q=reading+data+from+file+in+java&btnG=Search&meta=&aq=0&oq=reading+data+")          
          And("user agent")
              assert(rec.get.userAgent == "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 GTB5")  
      }
//      it("those fields should not be null") {
//          rec.foreach { r =>
//              assert(r.clientIpAddress == "124.30.9.161")
//              assert(r.rfc1413ClientIdentity == "-")
//              assert(r.remoteUser == "-")
//              assert(r.dateTime == "[21/Jul/2009:02:48:11 -0700]")
//              assert(r.request == "GET /java/edu/pj/pj010004/pj010004.shtml HTTP/1.1")
//              assert(r.serverStatusCode == "200")
//              assert(r.bytesSent == "16731")
//              assert(r.referer == "http://www.google.co.in/search?hl=en&client=firefox-a&rlz=1R1GGGL_en___IN337&hs=F0W&q=reading+data+from+file+in+java&btnG=Search&meta=&aq=0&oq=reading+data+")
//              assert(r.userAgent == "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 GTB5")
//          }
//          println(rec.get)
//      }
  }

}


// 124.30.9.161 - - [21/Jul/2009:02:48:11 -0700] "GET /java/edu/pj/pj010004/pj010004.shtml HTTP/1.1" 200 16731 "http://www.google.co.in/search?hl=en&client=firefox-a&rlz=1R1GGGL_en___IN337&hs=F0W&q=reading+data+from+file+in+java&btnG=Search&meta=&aq=0&oq=reading+data+" "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 GTB5"

