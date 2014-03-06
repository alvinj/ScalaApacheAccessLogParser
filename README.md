# A Scala Apache Access Log Parser

This project can be used to parse Apache access log records
(combined records, to be specific) in JVM applications (Scala,
Java, etc.)


## Discussion

I needed an Apache logfile parser, and after looking at some other
code, I decided to write my own.


## Usage

The API is in flux, but right now the usage starts like this:

    val rawRecord = """89.166.165.223 - - [21/Jul/2009:02:48:12 -0700] "GET /foo HTTP/1.1" 404 970 "-" "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.0.11) Firefox/3.0.11""""
    
    // a `ApacheCombinedAccessLogRecord` object
    val accessLogRecord = ApacheCombinedAccessLogParser.parse(rawRecord)

I need to add some methods to parse the individual fields, but right now
the fields are correctly extracted as strings.


## Building

This project is a typical SBT project, so just use commands like this:

    sbt compile
    sbt test


## More information

For more information, see my website:

Alvin Alexander  
http://alvinalexander.com


