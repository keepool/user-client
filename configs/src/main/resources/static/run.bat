cd /d C:\%HOMEPATH%\AppData\Local\chia-blockchain\app*\resources\app.asar.unpacked\daemon
chia start farmer-only -r
chia start harvester -r

pause