echo off
set GOOGLE_GENAI_USE_VERTEXAI=FALSE
set GOOGLE_API_KEY=API_KEY_GOES_HERE

echo on
mvn compile exec:java -Dexec.args="--server.port=8082 -adk.agents.source-dir=src\main\java\org\nvision\learn\agents\sample_agents --logging.level.com.google.adk.dev=TRACE --logging.level.com.google.adk.demo.agents=TRACE"