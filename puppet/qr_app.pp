exec { 'install-java':
  command => '/usr/bin/apt-get update && /usr/bin/apt-get install -y openjdk-17-jre',
  unless  => '/usr/bin/java -version 2>&1 | grep "17"',
}

file { '/opt/qrgen':
  ensure => directory,
  mode   => '0755',
}

file { '/opt/qrgen/qr_code_generator-0.0.1-SNAPSHOT.jar':
  ensure => file,
  source => "file:///home/reuel/qr_code_generator-0.0.1-SNAPSHOT.jar",  # Adjust path as needed
  mode   => '0755',
}

file { '/etc/systemd/system/qr-app.service':
  ensure  => file,
  content => "[Unit]
Description=QR Code Generator App
After=network.target

[Service]
ExecStart=/usr/bin/java -jar /opt/qrgen/qr_code_generator-0.0.1-SNAPSHOT.jar --server.port=1112
SuccessExitStatus=143
Restart=on-failure
RestartSec=5

[Install]
WantedBy=multi-user.target",
  mode    => '0644',
}

exec { 'reload-systemd':
  command     => '/bin/systemctl daemon-reexec && /bin/systemctl daemon-reload',
  refreshonly => true,
  subscribe   => File['/etc/systemd/system/qr-app.service'],
}

service { 'qr-app':
  ensure    => running,
  enable    => true,
  subscribe => File['/opt/qrgen/qr_code_generator-0.0.1-SNAPSHOT.jar'],
}


