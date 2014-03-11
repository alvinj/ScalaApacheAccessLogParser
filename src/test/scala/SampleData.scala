package com.alvinalexander.accesslogparser

object SampleCombinedAccessLogRecords {

  val data = """
124.30.9.161 - - [21/Jul/2009:02:48:11 -0700] "GET /java/edu/pj/pj010004/pj010004.shtml HTTP/1.1" 200 16731 "http://www.google.co.in/search?hl=en&client=firefox-a&rlz=1R1GGGL_en___IN337&hs=F0W&q=reading+data+from+file+in+java&btnG=Search&meta=&aq=0&oq=reading+data+" "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 GTB5"
89.166.165.223 - - [21/Jul/2009:02:48:12 -0700] "GET /favicon.ico HTTP/1.1" 404 970 "-" "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11"
94.102.63.11 - - [21/Jul/2009:02:48:13 -0700] "GET / HTTP/1.1" 200 18209 "http://www.developer.com/net/vb/article.php/3683331" "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)"
124.30.7.162 - - [21/Jul/2009:02:48:13 -0700] "GET /images/tline3.gif HTTP/1.1" 200 79 "http://www.devdaily.com/java/edu/pj/pj010004/pj010004.shtml" "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 GTB5"
122.165.54.17 - - [21/Jul/2009:02:48:12 -0700] "GET /java/java_oo/ HTTP/1.1" 200 32579 "http://www.google.co.in/search?hl=en&q=OO+with+java+standalone+example&btnG=Search&meta=&aq=f&oq=" "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.7) Gecko/2009021910 Firefox/3.0.7"
217.32.108.226 - - [21/Jul/2009:02:48:13 -0700] "GET /blog/post/perl/checking-testing-perl-module-in-inc-include-path/ HTTP/1.1" 200 18417 "http://www.devdaily.com/blog/post/perl/perl-error-cant-locate-module-in-inc/" "Mozilla/5.0 (X11; U; SunOS i86pc; en-US; rv:1.7) Gecko/20070606"
122.165.54.17 - - [21/Jul/2009:02:48:15 -0700] "GET /java/java_oo/java_oo.css HTTP/1.1" 200 1235 "http://www.devdaily.com/java/java_oo/" "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.7) Gecko/2009021910 Firefox/3.0.7"
""".split("\n").filter(_ != "")

  val badRecord = """
66.249.70.10 - - [23/Feb/2014:03:21:59 -0700] "GET /blog/post/java/how-load-multiple-spring-context-files-standalone/ HTTP/1.0" 301 - "-" "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)"
""".split("\n").filter(_ != "")


}

