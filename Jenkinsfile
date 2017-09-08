node('java8') {
   
    stage('Clonar') {
        		//checkout scm: [$class: 'MercurialSCM',
        	    //	source: '$SCM_URL/hg/airport',
        	    //	browser:[$class: 'HgWeb', url: "$SCM_URL/hg/airport"],
        		//	revision: 'default',
        		//	clean: true,
        		//	credentialsId: 'bahia.net-jenkins'],
        		//poll: false
                git credentialsId: 'github-doterob', url: 'https://github.com/doterob/airport'
            
    }
    
    stage('Build') {
        sh 'mvn clean package install -Dmaven.test.failure.ignore=true'
    }
    
    stage('Junit'){
        junit '**/target/surefire-reports/*.xml'
    }
    
    stage('Integración'){
        sh 'mvn integration-test -P integration-test'
        junit '**/target/failsafe-reports/*.xml'
    }
    
    stage('Sonar') {
        withSonarQubeEnv('sonar.bahia.net') {
			sh 'mvn sonar:sonar -Dsonar.host.url=$SONAR_HOST -Dsonar.jdbc.url=$SONAR_BBDD'
		}
    }
    
    stage('Nexus') {		
        archiveArtifacts '**/airport-web-*.jar'
        nexusArtifactUploader artifacts: [[artifactId: 'airport-web', classifier: '', file: 'airport-web/target/airport-web-0.0.1-SNAPSHOT.jar', type: 'jar']], credentialsId: 'nexus.bahia.net', groupId: 'es.bahiasoftware.airport', nexusUrl: '192.168.1.39:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'bahia-snapshot', version: '0.0.1-SNAPSHOT'
    }
	
	stage('Docker') {
        sh 'echo docker push airport'
    }
}