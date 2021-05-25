
scp -i "/d/studying/aws/diploma.pem" execute_commands_on_ec2.sh ec2-user@ec2-18-188-125-47.us-east-2.compute.amazonaws.com:/home/ec2-user
echo "Copied latest 'execute_commands_on_ec2.sh' file from local machine to ec2 instance"

scp -i "/d/studying/aws/diploma.pem" target/diploma-0.0.1-SNAPSHOT.jar ec2-user@ec2-18-188-125-47.us-east-2.compute.amazonaws.com:/home/ec2-user
echo "Copied jar file from local machine to ec2 instance"

echo "Connecting to ec2 instance and starting server using java -jar command"
ssh -i "/d/studying/aws/diploma.pem" ec2-user@ec2-18-188-125-47.us-east-2.compute.amazonaws.com ./execute_commands_on_ec2.sh
