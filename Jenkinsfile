node('android') {
 2    step([$class: 'StashNotifier'])
 3    checkout scm
 4    stage('Build') {
 5      try {
 6        sh './gradlew --refresh-dependencies clean assemble'
 7        lock('emulator') {
 8          sh './gradlew connectedCheck'
 9        }
10        currentBuild.result = 'SUCCESS'
11      } catch(error) {
12        slackSend channel: '#build-failures', color: 'bad', message: "This build is broken ${env.BUILD_URL}", token: 'XXXXXXXXXXX'
13        currentBuild.result = 'FAILURE'
14      } finally {
15        junit '**/test-results/**/*.xml'
16      }
17    }
18    stage('Archive') {
19      archiveArtifacts 'app/build/outputs/apk/*'
20    }
21    step([$class: 'StashNotifier'])
22}