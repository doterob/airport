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
        sh 'mvn clean package install -Dmaven.test.failure.ignore=true' -Ptest
    }
    
    stage('Junit'){
        junit '**/target/surefire-reports/*.xml'
    }
    
    stage('Integración'){
        sh 'mvn integration-test -Pintegration-test'
        junit '**/target/failsafe-reports/*.xml'
    }
    
    stage('Sonar') {
        withSonarQubeEnv('sonar.bahia.net') {
			sh 'mvn sonar:sonar -Psonar -Dsonar.host.url=$SONAR_HOST -Dsonar.jdbc.url=$SONAR_BBDD'
		}
    }
    
    stage('Nexus') {		
        archiveArtifacts '**/airport-web-*.jar'
        sh 'mvn deploy -Pdeploy'
	}
	
	stage('Docker') {
        sh 'echo docker push airport'
    }
}