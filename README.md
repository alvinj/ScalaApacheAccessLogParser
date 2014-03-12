# A Scala Apache Access Log Parser

This project can be used to parse Apache access log records in JVM applications (Scala,
Java, etc.) It is specifically written to work with "combined records", as that's
the only access log format I've used since the 1990s.


## Discussion

In short, I needed an Apache access log parser, and after looking at some other
code, I decided to write my own.


## Usage

The API is in flux, but right now the usage starts like this:

    val rawRecord = """89.166.165.223 - - [21/Jul/2009:02:48:12 -0700] "GET /foo HTTP/1.1" 404 970 "-" "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.0.11) Firefox/3.0.11""""
    
    val parser = AccessLogParser
    val accessLogRecord = parser.parse(rawRecord)    // an AccessLogRecord instance

The `AccessLogRecord` class definition looks like this:

    case class AccessLogRecord (
        clientIpAddress: String,         // should be an ip address, but may also be the hostname if hostname-lookups are enabled
        rfc1413ClientIdentity: String,   // typically '-'
        remoteUser: String,              // typically '-'
        dateTime: String,                // [day/month/year:hour:minute:second zone]
        request: String,                 // 'GET /foo ...'
        httpStatusCode: String,          // 200, 404, etc.
        bytesSent: String,               // may be '-'
        referer: String,                 // where the visitor came from
        userAgent: String                // long string to represent the browser and OS
    )

In the test code you'll see that I use the parser like this:

    val parser = new AccessLogParser
    val rec = parser.parseRecord(rawRecord)
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

If you don't like using the Option/Some/None pattern, I added a method named `parseRecordReturningNullObjectOnFailure`
that returns a "Null Object" version of an `AccessLogRecord` instead of an Option.

I also added some methods to parse the `date` and `request` fields, and I'll document those
here on another day. You can see all of the current, up-to-date API by looking at the tests 
in the `AccessLogRecordSpec` class.


## Building

This project is a typical Scala/SBT project, so just use commands like this:

    sbt compile
    sbt test
    sbt package


## More information

I've added more documentation about this library at the following URLs. First, the basic documentation
on this library is at this URL:

* [My Scala Apache access log parser library](http://alvinalexander.com/scala/scala-apache-access-log-parser-library-java-jvm)

Next, I've written two articles on how to use this library to analyze Apache access log records with
Apache Spark and Scala:

* [Analyzing Apache access logs with Spark and Scala](http://alvinalexander.com/scala/analyzing-apache-access-logs-files-spark-scala)
* [Generating a list of URLs from Apache access log files, sorted by hit count, using Apache Spark (and Scala)](http://alvinalexander.com/scala/analyzing-apache-access-logs-files-spark-scala-part-2)

For more information about yours truly:

* [See my website](http://alvinalexander.com)
* [Find me here on Twitter]([https://twitter.com/alvinalexander)

All the best,    
Alvin Alexander  
http://alvinalexander.com









