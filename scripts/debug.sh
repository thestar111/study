# author zhouping
# linux tomcat debug scripts
#!/bin/bash
# file: $CATALINA_HOME/bin/debug.sh
export JPDA_DEBUG="-server -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8879"
export JAVA_OPTS="$JAVA_OPTS $JPDA_DEBUG"
./startup.sh; tail -f ../logs/catalina.out
