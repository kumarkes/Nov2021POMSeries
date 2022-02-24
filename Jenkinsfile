pipeline{
    agent any
    stages{
        stage("Build")
        {
            steps{
                echo("Build")
            }
        }
        stage("Run UT's")
        {
            steps{
                echo("Run UT's")
            }
        }
        stage("Deploy to QA")
        {
            steps{
                echo("Deploy to QA")
            }
        }
        stage("Run Automation Test")
        {
            steps{
                echo("Running Automation Test Cases")
            }
        }
          stage("Deploy to Stage")
        {
            steps{
                echo("Deploy to Stage")
            }
        }
        stage("Sanity Test")
        {
            steps{
                echo("Sanity Test on Stage Env")
            }
        }
         stage("Deploy to Prod")
        {
            steps{
                echo("Deploy to Prod")
            }
        }
    }
    
}