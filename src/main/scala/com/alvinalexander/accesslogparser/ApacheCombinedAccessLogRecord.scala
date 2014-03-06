package com.alvinalexander.accesslogparser

import java.util.regex.Pattern

/**
 * @see http://httpd.apache.org/docs/2.2/logs.html for details
 */
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

/**
 * A sample record:
 * 94.102.63.11 - - [21/Jul/2009:02:48:13 -0700] "GET / HTTP/1.1" 200 18209 "http://acme.com/foo.php" "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)"
 * 
 */
object ApacheCombinedAccessLogParser {

    val ddd = "\\d{1,3}"                      // at least 1 but not more than 3 times (possessive)
    val ip = s"($ddd\\.$ddd\\.$ddd\\.$ddd)?"  // like `123.456.7.89`
    val client = "(\\S+)"                     // '\S' is 'non-whitespace character'
    val user = "(\\S+)"
    val dateTime = "(\\[.+?\\])"              // like `[21/Jul/2009:02:48:13 -0700]`
    val request = "\"(.*?)\""                 // any number of any character, reluctant
    val status = "(\\d{3})"
    val bytes = "(\\d+)"
    val referer = "\"(.*?)\""
    val agent = "\"(.*?)\""
    val regex = s"$ip $client $user $dateTime $request $status $bytes $referer $agent"
    val p = Pattern.compile(regex)
    
    // note: group(0) is the entire record that was matched (skip it)
    def parse(record: String): Option[ApacheCombinedAccessLogRecord] = {
        val matcher = p.matcher(record)
        if (matcher.find) {
            Some(ApacheCombinedAccessLogRecord(
                matcher.group(1),
                matcher.group(2),
                matcher.group(3),
                matcher.group(4),
                matcher.group(5),
                matcher.group(6),
                matcher.group(7),
                matcher.group(8),
                matcher.group(9)))
        } else {
            None
        }
        
    }

}


