# Devops-QRCodeGenerator
Try it out: https://devops-qrcodegenerator.onrender.com
<h2><u>Pre-requisite tools:</u></h2>
<ul>
  <li>Docker</li>
  <li>Maven</li>
  <li>Java</li>
  <li>Jenkins</li>
  <li>Grafana(only needed if you want metric monitoring)</li>
  <li>Graphite(only needed if you want metric monitoring)</li>
  <li>Ansible(if you want an alternate form of deployment instead of Jenkins)</li>
  <li>Puppet(if you want a local deployment that automatically starts up on boot)</li>
</ul>

<h2><u>Deploymentment Methods:</u></h2>
<ul>
  <li>
    <h3>Jenkins:</h3>
    <ol>
      <li>Open Jenkins and start a new pipeline project with pipeline with SCM selected</li>
      <li>Enter the URL of the Github repo of this project</li>
      <li>Build Now</li>
    </ol>
  </li>
  <li>
    <h3>Ansible:</h3>
    <ol>
      <li>Navigate to the ansible folder of this project and open it in the terminal</li>
      <li>Run the command: 'ansible-playbook playbook.yml -K'</li>
    </ol>
  </li>
  <li>
    <h3>Puppet:</h3>
    <ol>
      <li>Navigate to the puppet folder of this project and open it in the terminal</li>
      <li>Run the command 'sudo puppet apply qr_app.pp'</li>
    </ol>
  </li>
</ul>
