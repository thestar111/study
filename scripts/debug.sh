# author zhouping
# linux tomcat debug scripts
#!/bin/bash
# file: $CATALINA_HOME/bin/debug.sh
export JPDA_DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,address=8879,server=y,suspend=n"
export JAVA_OPTS="$JAVA_OPTS $JPDA_DEBUG"
./startup.sh; tail -f ../logs/catalina.out
