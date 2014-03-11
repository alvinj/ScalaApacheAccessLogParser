package com.alvinalexander.accesslogparser

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter
import org.scalatest.GivenWhenThen
import java.util.Calendar

class ApacheCombinedAccessLogRecordSpec extends FunSpec with BeforeAndAfter with GivenWhenThen {

  var records: Seq[String] = _

  before {
      records = SampleCombinedAccessLogRecords.data
  }
  
  describe("Testing the first access log record ...") {
      it("the data fields should be correct") {
          Given("the first sample log record")
          records = SampleCombinedAccessLogRecords.data
          val parser = new AccessLogParser
          val rec = parser.parseRecord(records(0))
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
              assert(rec.get.httpStatusCode == "200")
          And("bytes sent should be 16731")
              assert(rec.get.bytesSent == "16731")
          And("referer")
              assert(rec.get.referer == "http://www.google.co.in/search?hl=en&client=firefox-a&rlz=1R1GGGL_en___IN337&hs=F0W&q=reading+data+from+file+in+java&btnG=Search&meta=&aq=0&oq=reading+data+")          
          And("user agent")
              assert(rec.get.userAgent == "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 GTB5")  
      }
  }
  
  describe("Testing a second access log record ...") {
      records = SampleCombinedAccessLogRecords.data
      val parser = new AccessLogParser
      val rec = parser.parseRecord(records(1))
      it("the result should not be None") {
          assert(rec != None)
      }
      it("the individual fields should be right") {
          rec.foreach { r =>
              assert(r.clientIpAddress == "89.166.165.223")
              assert(r.rfc1413ClientIdentity == "-")
              assert(r.remoteUser == "-")
              assert(r.dateTime == "[21/Jul/2009:02:48:12 -0700]")
              assert(r.request == "GET /favicon.ico HTTP/1.1")
              assert(r.httpStatusCode == "404")
              assert(r.bytesSent == "970")
              assert(r.referer == "-")
              assert(r.userAgent == "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11")
          }
      }
  }
  
  describe("Trying to parse a record I used to fail on ...") {
      records = SampleCombinedAccessLogRecords.badRecord
      val parser = new AccessLogParser
      val rec = parser.parseRecord(records(0))
      it("the result should not be None") {
          assert(rec != None)
      }
      it("the individual fields should be right") {
          rec.foreach { r =>
              assert(r.clientIpAddress == "66.249.70.10")
              assert(r.rfc1413ClientIdentity == "-")
              assert(r.remoteUser == "-")
              assert(r.dateTime == "[23/Feb/2014:03:21:59 -0700]")
              assert(r.request == "GET /blog/post/java/how-load-multiple-spring-context-files-standalone/ HTTP/1.0")
              assert(r.httpStatusCode == "301")
              assert(r.bytesSent == "-")
              assert(r.referer == "-")
              assert(r.userAgent == "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
          }
      }
  }
  
  describe("Testing the parseRecordReturningNullObjectOnFailure method with a valid record ...") {
      records = SampleCombinedAccessLogRecords.data
      val parser = new AccessLogParser
      val rec = parser.parseRecordReturningNullObjectOnFailure(records(1))
      it("the result should not be null") {
          assert(rec != null)
      }
      it("the individual fields should be right") {
          assert(rec.clientIpAddress == "89.166.165.223")
          assert(rec.rfc1413ClientIdentity == "-")
          assert(rec.remoteUser == "-")
          assert(rec.dateTime == "[21/Jul/2009:02:48:12 -0700]")
          assert(rec.request == "GET /favicon.ico HTTP/1.1")
          assert(rec.httpStatusCode == "404")
          assert(rec.bytesSent == "970")
          assert(rec.referer == "-")
          assert(rec.userAgent == "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11")
      }
  }
  
  describe("Testing the parseRecordReturningNullObjectOnFailure method with an invalid record ...") {
      val parser = new AccessLogParser
      val rec = parser.parseRecordReturningNullObjectOnFailure("foo bar baz")
      it("the result should not be null") {
          assert(rec != null)
      }
      it("the individual fields should be blank strings") {
          assert(rec.clientIpAddress == "")
          assert(rec.rfc1413ClientIdentity == "")
          assert(rec.remoteUser == "")
          assert(rec.dateTime == "")
          assert(rec.request == "")
          assert(rec.httpStatusCode == "")
          assert(rec.bytesSent == "")
          assert(rec.referer == "")
          assert(rec.userAgent == "")
      }
  }
  
  describe("Parsing the request field ...") {
      it("a simple request should work") {
          val req = "GET /the-uri-here HTTP/1.1"
          val result = AccessLogParser.parseRequestField(req)
          assert(result != None)
          result.foreach { res =>
              val (requestType, uri, httpVersion) = res 
              assert(requestType == "GET")
              assert(uri == "/the-uri-here")
              assert(httpVersion == "HTTP/1.1")
          }
      }
      it("an invalid request should return blanks") {
          val req = "foobar"
          val result = AccessLogParser.parseRequestField(req)
          assert(result == None)
      }
  }
  
  describe("Parsing the date field ...") {
      it("a valid date field should work") {
          val date = AccessLogParser.parseDateField("[21/Jul/2009:02:48:13 -0700]")
          assert(date != None)
          date.foreach { d =>
              val cal = Calendar.getInstance
              cal.setTimeInMillis(d.getTime)
              assert(cal.get(Calendar.YEAR) == 2009)
              assert(cal.get(Calendar.MONTH) == 6)  // 0-based
              assert(cal.get(Calendar.DAY_OF_MONTH) == 21)
              assert(cal.get(Calendar.HOUR) == 2)
              assert(cal.get(Calendar.MINUTE) == 48)
              assert(cal.get(Calendar.SECOND) == 13)
          }
      }
      it("an invalid date field should return None") {
          val date = AccessLogParser.parseDateField("[foo bar]")
          assert(date == None)
      }
  }

}












