(Get-Content src/main/resources/application.yml) |
Foreach-Object {$_ -replace '\$\{env:PostgresqlUser\}', $env:PostgresqlUser} |
Foreach-Object {$_ -replace '\$\{env:PostgresqlPassword\}', $env:PostgresqlPassword} |
Out-File src/main/resources/application.yml