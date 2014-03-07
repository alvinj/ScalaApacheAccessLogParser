package com.alvinalexander.accesslogparser

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















