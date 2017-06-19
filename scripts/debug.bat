rem --------------------------------------------------
rem auth zhouping
rem desc windows tomcat debug bat scripts
rem --------------------------------------------------

cd %CATALINE_HOME%/bin
rem list port
set JPDA_ADDRESS=6888
rem set list style
set JPDA_TRANSPORT=dt_socket
set CATALINA_OPTS=-server -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=6888

rem run startup
startup
