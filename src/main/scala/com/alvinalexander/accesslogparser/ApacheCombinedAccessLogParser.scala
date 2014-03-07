package com.alvinalexander.accesslogparser

import java.util.regex.Pattern
import java.text.SimpleDateFormat
import java.util.Locale
import scala.util.control.Exception._  // allCatch

/**
 * A sample record:
 * 94.102.63.11 - - [21/Jul/2009:02:48:13 -0700] "GET / HTTP/1.1" 200 18209 "http://acme.com/foo.php" "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)"
 * 
 * I put this code in the 'class' so (a) the pattern could be pre-compiled and (b) the user can create
 * multiple instances of this parser, in case they want to work in a multi-threaded way.
 * I don't know that this is necessary, but I think it is for this use case.
 * 
 */
class ApacheCombinedAccessLogParser {

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
    def parseRecord(record: String): Option[ApacheCombinedAccessLogRecord] = {
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

/**
 * A sample record:
 * 94.102.63.11 - - [21/Jul/2009:02:48:13 -0700] "GET / HTTP/1.1" 200 18209 "http://acme.com/foo.php" "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)"
 */
object ApacheCombinedAccessLogParser {

    /**
     * @param A String like "GET /the-uri-here HTTP/1.1"
     * @return A Tuple3(requestType, uri, httpVersion). requestType is GET, POST, etc.
     * 
     * Returns a Tuple3 of three blank strings if the method fails.
     */
    def parseRequestField(request: String): Option[Tuple3[String, String, String]] = {
        val arr = request.split(" ")
        if (arr.size == 3) Some((arr(0), arr(1), arr(2))) else None
    }
    
    /**
     * @param A String that looks like "[21/Jul/2009:02:48:13 -0700]"
     */
    def parseDateField(field: String): Option[java.util.Date] = {
        val dateRegex = "\\[(.*?) .+]"
        val datePattern = Pattern.compile(dateRegex)
        val dateMatcher = datePattern.matcher(field)
        if (dateMatcher.find) {
            val dateString = dateMatcher.group(1)
            println("***** DATE STRING" + dateString)
            // HH is 0-23; kk is 1-24
            val dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH)
            allCatch.opt(dateFormat.parse(dateString))  // return Option[Date]
        } else {
            None
        }
    }

}




