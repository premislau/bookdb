(Get-Content appsettings.json) |
Foreach-Object {$_ -replace '\$\{env:PostgresqlUser\}', $env:PostgresqlUser} |
Foreach-Object {$_ -replace '\$\{env:PostgresqlPassword\}', $env:PostgresqlPassword} |
Out-File appsettings.json