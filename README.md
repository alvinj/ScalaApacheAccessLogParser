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
    
    val parser = ApacheCombinedAccessLogParser
    val accessLogRecord = parser.parse(rawRecord)    // an ApacheCombinedAccessLogRecord instance

The `ApacheCombinedAccessLogRecord` class definition looks like this:

    case class ApacheCombinedAccessLogRecord (
        clientIpAddress: String,         // may also be `hostname`
        rfc1413ClientIdentity: String,   // typically `-`
        remoteUser: String,              // typically `-`
        dateTime: String,                // [day/month/year:hour:minute:second zone]
        request: String,                 // `GET ...`
        serverStatusCode: String,        // 200, 404, etc.
        bytesSent: String,               // may be `-`
        referer: String,                 // where the visitor came from
        userAgent: String                // long string to represent the browser and OS
    )

I just added some methods to parse the `date` and `request` fields, and I'll document those
here on another day. You can see all of the current, up-to-date API by looking at the tests 
in the `ApacheCombinedAccessLogRecordSpec` class.


## Building

This project is a typical SBT project, so just use commands like this:

    sbt compile
    sbt test
    sbt package


## More information

For more information about yours truly, see my website:

Alvin Alexander  
http://alvinalexander.com


